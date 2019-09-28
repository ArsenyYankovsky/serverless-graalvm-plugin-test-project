package com.serverless


import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.lambda.model.InvokeRequest
import com.amazonaws.services.lambda.model.InvokeResult
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class DirectInvocationHelloHandlerSpec extends Specification {
    def lambdaClient = AWSLambdaClientBuilder.standard().withRegion("eu-north-1").build()

    def "happy path"() {
        when:

        InvokeRequest request = new InvokeRequest()
                .withFunctionName("serverless-graalvm-plugin-test-project-dev-hello-direct")
                .withInvocationType("RequestResponse")

        InvokeResult response = lambdaClient.invoke(request)
        def responseString = new String(response.payload.array(), StandardCharsets.UTF_8)

        then:
        responseString == "\"Hello direct invocation\""
    }
}
