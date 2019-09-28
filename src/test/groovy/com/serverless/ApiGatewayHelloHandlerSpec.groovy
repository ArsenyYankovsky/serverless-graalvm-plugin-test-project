package com.serverless

import spock.lang.Specification

class ApiGatewayHelloHandlerSpec extends Specification {
    def "happy path"() {
        when:
        def result = new URL("https://fj2fsn84tg.execute-api.eu-north-1.amazonaws.com/dev/hello").text

        then:
        result == "{\"message\":\"Go Serverless v1.x! Your function executed successfully!\"}"
    }
}
