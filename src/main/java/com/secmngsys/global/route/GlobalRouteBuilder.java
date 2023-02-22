package com.secmngsys.global.route;

import com.secmngsys.global.configuration.swagger.SwaggerProperties;
import com.secmngsys.global.exception.camel.GlobalCamelException;
import com.secmngsys.global.process.GlobalHttpLoggingProcess;
import com.secmngsys.global.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Configuration;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.camel.LoggingLevel.INFO;

@Slf4j
@Configuration
public class GlobalRouteBuilder extends RouteBuilder {

    @Autowired
    CamelContext camelContext;

    @Autowired
    SwaggerProperties swaggerProperties;

    protected void onOpenApi(){
        restConfiguration()
                //.apiComponent("openapi")
                .apiContextPath("/api-doc")
//                .apiProperty("api.title", "Secmngsys.KR Camel Rest APIs")
//                .apiProperty("api.version", "1.0.0")
//                .apiProperty("api.description", "안녕?")
//                .apiProperty("api.contact.email", "kimyc@suhyup.co.kr")
        ;
    }

    protected void onIntercept(boolean bool){
        interceptFrom("rest:*")
                .process(exchange -> getContext().getTracer().setEnabled(bool))
                //.process(new GlobalHttpLoggingProcess())
                //.bean(LoggingService.class, "insertLoggingInfo")
                .log(INFO, "${body}")
        ;
    }

    protected void onException(){
        onException(Throwable.class)
                //.process(new CamelExceptionHandlerProcess())
                .bean(GlobalCamelException.class, "GlobalCamelException")
                //.marshal().json()
                //.setHeader(HTTP_RESPONSE_CODE, 400)
                //.log(ERROR, "Received body ${body}")
                .handled(true)
                .end()
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
        this.onIntercept(false);
        this.onException();
    }

    public void configure(boolean bool) {
        this.onIntercept(bool);
        this.onException();
    }
}
