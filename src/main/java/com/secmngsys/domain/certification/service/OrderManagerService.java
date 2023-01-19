package com.secmngsys.domain.certification.service;


import com.secmngsys.domain.certification.model.dto.OrderDto;
import com.secmngsys.global.configuration.code.ErrorCode;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseError;
import com.secmngsys.global.model.ResponseSuccess;
import lombok.Data;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class OrderManagerService {

    final Map<String, OrderDto> orders = new HashMap<>();
    final Map<String, String> orderStatusMap = new HashMap<>();

    public void newOrder(Exchange exchange){
        OrderDto order =  exchange.getMessage().getBody(OrderDto.class);
        String id = exchange.getIn().getHeader("id", String.class);
        orders.put(id, order);
        System.out.println("Persisted Order. ID: [" + id + "] [" + order + "]");
//        if(1==1){
//            //exchange.getIn().setBody("zzzzzzzzzzzzzzzzzzzzzzz");
//            throw new RuntimeException("ddddddddddddddddddddddd");
//        }
        //return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    public ResponseSuccess cancelOrder(Exchange exchange){
        String id = exchange.getIn().getHeader("id", String.class);
        System.out.println("Cancelling Order. ID: [" + id + "]");
//        orders.remove(id);
//        final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf("asd1121211212asdasdasdasdasd"));
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody();
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    public ResponseSuccess shipOrder(Exchange exchange){
        String id = exchange.getIn().getHeader("id", String.class);
        OrderDto order = orders.get(id);
        System.out.println("Preparing to ship Order. ID: [" + order + "]");
        if(order.getQuantity() > 10){
            //exchange.getIn().setBody("zzzzzzzzzzzzzzzzzzzzzzz");
            throw new RuntimeException("Too many items to ship. Can't ship.");
        }
        //exchange.getIn().setBody("zzzzzzzzzzz1111zzzzzzzzzzzz");
        System.out.println("ExchangeId -> "+exchange.getExchangeId());
        System.out.println("Shipped Order. ID: [" + orders.get(id) + "]");
        return GlobalResponseHandler.of(order, SuccessCode.SUCCESS);
    }

    public ResponseSuccess cancelShipping(Exchange exchange) {
        String id = exchange.getIn().getHeader("id", String.class);
        OrderDto order = orders.get(id);
        System.out.println("ExchangeId -> "+exchange.getExchangeId());
        System.out.println("Cancelling ship Order. ID: [" + order + "]");
        orderStatusMap.put(id, "Cancelled");
        System.out.println("Cancelled Shipping Order. ID: [" + orders.get(id) + "]");
        //final ResponseError response = ResponseError.of(ErrorCode.BAD_REQUEST, String.valueOf("asdasdasdasdasdasd"));
        //return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST).getBody();
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);

    }

    public ResponseSuccess completeShipping(Exchange exchange) {
        String id = exchange.getIn().getHeader("id", String.class);

        OrderDto order = orders.get(id);
        System.out.println("ExchangeId -> "+exchange.getExchangeId());
        System.out.println("Completing shipping Order. ID: [" + order + "]");
        orderStatusMap.put(id, "Shipped");

        return GlobalResponseHandler.of(order, SuccessCode.SUCCESS);
    }
}
