package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpsTestHandler implements RequestHandler<Void, String> {
    @Override
    public String handleRequest(Void input, Context context) {
        try {
            URL url = new URL("https://fj2fsn84tg.execute-api.eu-north-1.amazonaws.com/dev/hello");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream is = connection.getInputStream();

            String body = inputStreamToString(is);

            return body;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
}
