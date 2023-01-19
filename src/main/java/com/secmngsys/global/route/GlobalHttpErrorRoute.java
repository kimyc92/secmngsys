package com.secmngsys.global.route;


import com.secmngsys.global.exception.camel.CustomHttpRequestException;
import com.secmngsys.global.exception.camel.GlobalCamelException;
import com.secmngsys.global.model.ResponseError;
import com.secmngsys.global.process.GlobalHttpErrorProcess;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.ERROR;

@Slf4j
@Component
public class GlobalHttpErrorRoute  extends GlobalRouteBuilder  {


    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/error").tag("/error").description("Http Request Error")
            .consumes("application/json").produces("application/json")
            .post().to("direct:httpError").responseMessage().code("4XX").message("Client Error Responses")
                .responseModel(ResponseError.class).endResponseMessage()
            .get().to("direct:httpError").responseMessage().code("4XX").message("Client Error Responses")
                .responseModel(ResponseError.class).endResponseMessage()
            .delete().to("direct:httpError").responseMessage().code("4XX").message("Client Error Responses")
                .responseModel(ResponseError.class).endResponseMessage()
            .patch().to("direct:httpError").responseMessage().code("4XX").message("Client Error Responses")
                .responseModel(ResponseError.class).endResponseMessage()
            .put().to("direct:httpError").responseMessage().code("4XX").message("Client Error Responses")
                .responseModel(ResponseError.class).endResponseMessage()
            .head().to("direct:httpError").responseMessage().code("4XX").message("Client Error Responses")
                .responseModel(ResponseError.class).endResponseMessage()
        ;

        from("direct:httpError")
            .log(ERROR, "HTTP REQUEST ERROR")
            .process(new GlobalHttpErrorProcess())
            //.bean(GlobalCamelException.class, "GlobalHttpException")
        ;
    }
}
