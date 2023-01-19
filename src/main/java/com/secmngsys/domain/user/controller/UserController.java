package com.secmngsys.domain.user.controller;

import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.service.UserService;
import com.secmngsys.global.configuration.code.SuccessCode;
import com.secmngsys.global.handler.GlobalResponseHandler;
import com.secmngsys.global.model.ResponseSuccess;
import com.secmngsys.global.route.test.Order;
import com.secmngsys.global.route.test.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/common")
public class UserController {

   // UserService userService;
    private ProducerTemplate producer;

    public void setProducerTemplate(ProducerTemplate producerTemplate) {
        this.producer = producerTemplate;

    }

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping(value="/user-info")
    public ResponseSuccess userInfo(@RequestBody @Valid UserDto userDto){
        System.out.println("4444");
        //userService.selectOneUserInfo(userDto);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/{id}")
    public ResponseSuccess getOrder(@PathParam("id") int orderId) {
        Order order = producer.requestBody("direct:getOrder",
                orderId, Order.class);
        if (order != null) {
            return GlobalResponseHandler.of(SuccessCode.SUCCESS);
        } else {
            return GlobalResponseHandler.of(SuccessCode.SUCCESS);
        }

    }

    @PutMapping
    public ResponseSuccess updateOrder(Order order) {
        producer.sendBody("direct:updateOrder", order);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @PostMapping
    public ResponseSuccess createOrder(Order order) {
        String id = producer.requestBody("direct:createOrder",
                order, String.class);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ResponseSuccess cancelOrder(@PathParam("id") int orderId) {
        producer.sendBody("direct:cancelOrder", orderId);
        return GlobalResponseHandler.of(SuccessCode.SUCCESS);
    }

}
