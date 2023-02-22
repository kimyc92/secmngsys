package com.secmngsys.global.process;

import com.secmngsys.global.listener.CamelLogListener;
import com.secmngsys.global.service.LoggingService;
import com.secmngsys.global.util.HttpClientInfoUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
//import org.apache.camel.component.cxf.common.message.CxfConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class GlobalHttpLoggingProcess implements Processor {

//    private LoggingService loggingService;
//
//    @Autowired
//    public GlobalHttpLoggingProcess(LoggingService loggingService) {
//        this.loggingService = loggingService;
//    }

    @Override
    public void process(Exchange exchange) {
        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
        String ip = new HttpClientInfoUtil(request).getClientIP();
        exchange.setProperty("loggingIP", ip);
        exchange.setProperty("loggingIP", ip);

        //loggingService.insertLoggingInfo(ip, clog.getCamelLog());
        System.out.println("ip -> "+ip);
    }
}
