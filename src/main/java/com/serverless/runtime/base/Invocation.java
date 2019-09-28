package com.serverless.runtime.base;

public class Invocation {
    private String requestId;
    private String body;
    private String runtimeApiEndpoint;

    public Invocation(String requestId, String body, String runtimeApiEndpoint) {
        this.requestId = requestId;
        this.body = body;
        this.runtimeApiEndpoint = runtimeApiEndpoint;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRuntimeApiEndpoint() {
        return runtimeApiEndpoint;
    }

    public void setRuntimeApiEndpoint(String runtimeApiEndpoint) {
        this.runtimeApiEndpoint = runtimeApiEndpoint;
    }
}
