package com.serverless.runtime;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.serverless.ApiGatewayHelloHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.runtime.base.JacksonBaseRuntime;

import java.util.Map;

public class ApiGatewayRuntime extends JacksonBaseRuntime<Map<String, Object>, ApiGatewayResponse> {
    @Override
    protected TypeReference<Map<String, Object>> getRequestType() {
        return new TypeReference<Map<String, Object>>() {};
    }

    @Override
    protected RequestHandler<Map<String, Object>, ApiGatewayResponse> createHandler() {
        return new ApiGatewayHelloHandler();
    }

    public static void main(String... args) {
        new ApiGatewayRuntime().start();
    }
}
