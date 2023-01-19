package com.secmngsys.domain.certification.route;

import com.secmngsys.domain.certification.model.dto.OrderDto;
import com.secmngsys.domain.certification.service.CreditService;
import com.secmngsys.domain.certification.service.OrderManagerService;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.route.GlobalRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.model.rest.RestParamType.body;

@Component
public class OrderController extends RouteBuilder {

    private final OrderManagerService orderManagerService;
    private final CreditService creditService;

    public OrderController(OrderManagerService orderManagerService, CreditService creditService) {
        this.orderManagerService = orderManagerService;
        this.creditService = creditService;
    }

    @Override
    public void configure() throws Exception {
//        restConfiguration()
//                .component("servlet")
//                .bindingMode(RestBindingMode.json)
//                .dataFormatProperty("prettyPrint", "true")
//                .apiProperty("api.title", "Saga Order Creator")
//                .apiProperty("api.version", "1.0")
//        //.apiContextListing(true)
//        ;
        //super.configure();
        //restConfiguration().clientRequestValidation(true);
//        onException(Exception.class)
//                .handled(true)
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
//                .setHeader(Exchange.CONTENT_TYPE, constant("text/json"))
//                .log("asdasdasdasd!@?!?@!?@!@?!@?!@!?@!@?!@?!@?!@?!@!?@!?@?")
//                .setBody().simple(new ResponseEntity<>("${exception.message}", HttpStatus.BAD_REQUEST).getBody());

//        errorHandler(defaultErrorHandler()
//                .maximumRedeliveries(5)
//                .redeliveryDelay(2000)
//                .retryAttemptedLogLevel(LoggingLevel.WARN));
//        // in case of a http exception then retry at most 3 times
//        // and if exhausted then upload using ftp instead
//        onException(IOException.class, HttpOperationFailedException.class)
//                .maximumRedeliveries(3)
//                .handled(true)
//                .log("dzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
//                ;
//
//        rest()
//                .consumes("application/json").produces("application/json")
//                .post("/orders")
//                .responseMessage("201", "When Created")
//                .description("Create a new order").type(OrderDto.class).outType(ResponseSuccess.class)
//                .param().name("body").type(body).description("Payload for an Order")
//                .endParam()
//                .to("direct:order")
//
//        ;
//
//        rest()
//                .produces("application/json")
//                .get("/databases")
//                .to("direct:databases");
//
//        from("direct:databases")
//                .setBody(e -> {
//                    Map<String, Object> databases = new LinkedHashMap<>();
//                    databases.put("Customer Account", creditService.getCustomerAccount());
//                    databases.put("Order Amount", creditService.getOrderAmount());
//                    databases.put("Order Status", orderManagerService.getOrderStatusMap());
//                    databases.put("Z Orders", orderManagerService.getOrders());
//                    return databases;
//                })
//        ;
    }
}
