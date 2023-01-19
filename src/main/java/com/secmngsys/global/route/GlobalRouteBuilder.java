package com.secmngsys.global.route;

import com.secmngsys.global.configuration.swagger.SwaggerProperties;
import com.secmngsys.global.exception.camel.CustomHttpRequestException;
import com.secmngsys.global.exception.camel.GlobalCamelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelException;
import org.apache.camel.Configuration;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.saga.InMemorySagaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.apache.camel.LoggingLevel.ERROR;

@Slf4j
@Configuration
public class GlobalRouteBuilder extends RouteBuilder {

    @Autowired
    CamelContext camelContext;

    @Autowired
    SwaggerProperties swaggerProperties;

    protected void onOpenApi(){

        System.out.println("swaggerProperties.toString() - "+swaggerProperties.getContact().getName());

        restConfiguration()
                //.apiComponent("openapi")
                .apiContextPath("/api-doc")

//                .apiProperty("api.title", "Secmngsys.KR Camel Rest APIs")
//                .apiProperty("api.version", "1.0.0")
//                .apiProperty("api.description", "안녕?")
//                .apiProperty("api.contact.email", "kimyc@suhyup.co.kr")
        ;
    }

    @Override
    public void configure() throws Exception {
        //camelContext.addService(new InMemorySagaService());
        //onOpenApi();

//        onException(CustomHttpRequestException.class)
//                .handled(true)
//                .bean(GlobalCamelException.class, "GlobalHttpErrorException")
//                .log("CustomHttpRequestException!!!");
//
//        onException(RuntimeCamelException.class)
//                      .handled(true)
//                .bean(GlobalCamelException.class, "GlobalCamelException")
//                    .log("asdasdasdasdasdsad!!!");
//
//        onException(HttpOperationFailedException.class)
//                .handled(true)
//                        .bean(GlobalCamelException.class, "GlobalCamelException")
//                        .log("asdasdasdasdasdsad!!!");
//
//        onException(CamelException.class)
//                .handled(true)
//                .bean(GlobalCamelException.class, "GlobalCamelException")
//                .log("asdasdasdasdasdsad!!!");
//
//        onException(Exception.class)
//                .handled(true)
//                .bean(GlobalCamelException.class, "GlobalCamelException")
//                .log("asdasdasdasdasdsad!!!");
//

        onException(Throwable.class)
                //.process(new CamelExceptionHandlerProcess())
                .bean(GlobalCamelException.class, "GlobalCamelException")
                //.marshal().json()
                //.setHeader(HTTP_RESPONSE_CODE, 400)
                //.log(ERROR, "Received body ${body}")
                .handled(true)
                .end();
    }

    public void configure(boolean bool) {
        camelContext.getTracer().setEnabled(bool);

        //onOpenApi();
        onException(Throwable.class)
                .bean(GlobalCamelException.class, "GlobalCamelException")
                .handled(true)
                .end();
    }
}
