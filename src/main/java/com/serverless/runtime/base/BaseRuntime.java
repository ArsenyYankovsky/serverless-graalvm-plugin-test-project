package com.serverless.runtime.base;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class BaseRuntime<I, O> {
    protected HttpURLConnection getUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        return conn;
    }

    protected void postToUrl(String urlString, String contents) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.connect();
        conn.getOutputStream().write(contents.getBytes(StandardCharsets.UTF_8));
        conn.getOutputStream().close();
        System.out.println(inputStreamToString(conn.getInputStream()));

    }

    protected String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        return textBuilder.toString();
    }

    protected Invocation getNextInvocation() throws IOException {
        String runtimeApiEndpoint = System.getenv("AWS_LAMBDA_RUNTIME_API");
        HttpURLConnection connection = getUrl("http://" + runtimeApiEndpoint + "/2018-06-01/runtime/invocation/next");

        String requestId;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            requestId = connection.getHeaderField("lambda-runtime-aws-request-id");
        } else {
            System.out.println("Error getting next request");
            System.out.println(inputStreamToString(connection.getErrorStream()));
            throw new RuntimeException("Erorr getting next request");
        }

        InputStream is = connection.getInputStream();

        String body = inputStreamToString(is);

        return new Invocation(requestId, body, runtimeApiEndpoint);

    }

    protected abstract I deserializeRequest(String requestBody) throws IOException;

    protected abstract String serializeResponse(O response) throws JsonProcessingException;

    protected abstract RequestHandler<I, O> createHandler();

    public void start() {
        try {
            RequestHandler<I, O> handler = createHandler();

            while (true) {
                Invocation nextInvocation = getNextInvocation();
                processInvocation(handler, nextInvocation);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void processInvocation(RequestHandler<I, O> handler, Invocation nextInvocation) throws IOException {
        try {
            I request = deserializeRequest(nextInvocation.getBody());

            O response = handler.handleRequest(request, null);

            String responseString = serializeResponse(response);
            postToUrl("http://" +
                            nextInvocation.getRuntimeApiEndpoint() +
                            "/2018-06-01/runtime/invocation/" +
                            nextInvocation.getRequestId() +
                            "/response",
                    responseString);
        } catch (Throwable t) {
            t.printStackTrace();
            postToUrl("http://" +
                            nextInvocation.getRuntimeApiEndpoint() +
                            "/2018-06-01/runtime/invocation/" +
                            nextInvocation.getRequestId() +
                            "/error",
                    t.getMessage()
            );
        }
    }
}
