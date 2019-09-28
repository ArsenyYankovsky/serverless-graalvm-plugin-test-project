package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DirectInvocationHelloHandler implements RequestHandler<Void, String> {

    @Override
    public String handleRequest(Void input, Context context) {
        return "Hello direct invocation";
    }
}
