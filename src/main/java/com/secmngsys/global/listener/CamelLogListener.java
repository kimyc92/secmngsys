package com.secmngsys.global.listener;

import com.secmngsys.global.dao.LoggingDao;
import com.secmngsys.global.service.LoggingService;
import com.secmngsys.global.util.HttpClientInfoUtil;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.spi.CamelLogger;
import org.apache.camel.spi.LogListener;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class CamelLogListener implements LogListener {

//    private LoggingService loggingService;
//
//    public CamelLogListener(LoggingService loggingService) {
//        this.loggingService = loggingService;
//    }

    @Override
    public String onLog(Exchange exchange, CamelLogger camelLogger, String message) {
        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
        camelLogger.setLogName("camel.access.log");
        String ip = new HttpClientInfoUtil(request).getClientIP();
        String camelLog = String.format("[%s] [%s]: %s : %s", exchange.getFromRouteId()
                 , exchange.getIn().getHeader("CamelHttpMethod")
                 , exchange.getIn().getHeader("CamelHttpUrl")
                 , message);

        LoggingService loggingService = new LoggingService();
        loggingService.insertLoggingInfo(ip, camelLog);
        return camelLog;
    }

}
