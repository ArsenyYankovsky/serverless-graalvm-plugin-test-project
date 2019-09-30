package com.serverless.runtime;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.serverless.ApiGatewayResponse;
import com.serverless.HttpsTestHandler;
import com.serverless.runtime.base.JacksonBaseRuntime;

public class HttpsTestRuntime extends JacksonBaseRuntime<Void, ApiGatewayResponse> {
    @Override
    protected TypeReference<Void> getRequestType() {
        return new TypeReference<Void>() {
        };
    }

    @Override
    protected Void deserializeRequest(String requestBody) {
        return null;
    }

    @Override
    protected RequestHandler<Void, ApiGatewayResponse> createHandler() {
        return new HttpsTestHandler();
    }

    public static void main(String... args) {
        new HttpsTestRuntime().start();
    }
}
