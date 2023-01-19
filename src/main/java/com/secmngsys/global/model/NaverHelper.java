package com.secmngsys.global.model;

import com.secmngsys.global.util.YamlLoadFactoryUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "naver")
@PropertySource(value={"profile/prd/endpoint.yml"}, factory = YamlLoadFactoryUtil.class)
@Getter
@Setter
@ToString
public class NaverHelper {

    private String secretKey;
    private String name;

}
