package com.serverless.runtime.base;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class JacksonBaseRuntime<I, O> extends BaseRuntime<I, O> {
    protected ObjectMapper mapper = createObjectMapper();

    protected ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    @Override
    protected I deserializeRequest(String requestBody) throws IOException {
        TypeReference<I> requestType = getRequestType();

        if (new TypeReference<Void>() {}.equals(requestType)) {
            return null;
        }

        return mapper.readValue(requestBody, requestType);
    }

    @Override
    protected String serializeResponse(O response) throws JsonProcessingException {
        return mapper.writeValueAsString(response);
    }

    protected abstract TypeReference<I> getRequestType();
}
