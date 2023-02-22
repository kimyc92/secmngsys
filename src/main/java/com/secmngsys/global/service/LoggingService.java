package com.secmngsys.global.service;

import com.secmngsys.domain.certification.service.CreditService;
import com.secmngsys.domain.certification.service.OrderManagerService;
import com.secmngsys.domain.user.model.dto.UserDto;
import com.secmngsys.domain.user.model.vo.UserVo;
import com.secmngsys.global.configuration.camel.CamelConfig;
import com.secmngsys.global.dao.LoggingDao;
import com.secmngsys.global.listener.CamelLogListener;
import com.secmngsys.global.util.HttpClientInfoUtil;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.CamelLogger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.apache.ibatis.session.SqlSessionFactory;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Validated
@Service
public class LoggingService {

//    @Autowired
//    LoggingDao loggingDao;

//    public LoggingService(LoggingDao loggingDao) {
//        this.loggingDao = loggingDao;
//    }

//    public void insertLoggingInfo(@Valid UserDto userDto) {
//        loggingDao.selectUserInfo(userDto);
//    }
    public void insertLoggingInfo(@NotEmpty String ip, @NotEmpty String camelLog){
        System.out.println("ip -> "+ip);
        System.out.println("message -> "+camelLog);
        LoggingDao loggingDao = new LoggingDao();
        loggingDao.insertLoggingInfo(ip, camelLog);
    }
//    public void insertLoggingInfo(Exchange exchange, String message) {
//        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
//        String ip = new HttpClientInfoUtil(request).getClientIP();
//
////        CamelLogListener clog = new CamelLogListener();
////        clog
////        ExtendedCamelContext ecc = (ExtendedCamelContext) camelContext;
////        System.out.println(exchange.getMessage().getBody());
////        camelContext.getRegistry()
//
//        System.out.println("message -> "+message);
////        System.out.println(camelContext.getLogListeners());
//        System.out.println(
//        String.format("[%s] [%s]: %s : %s", exchange.getFromRouteId()
//                , exchange.getIn().getHeader("CamelHttpMethod")
//                , exchange.getIn().getHeader("CamelHttpUrl")
//                , message));
//
//        System.out.println("ip -> "+ip);
//        //System.out.println("camelLog -> "+((ExtendedCamelContext) camelContext).getLogListeners());
//    }
}
