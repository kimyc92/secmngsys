package com.secmngsys.global.model;

import com.secmngsys.global.util.YamlLoadFactoryUtil;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@Validated
@ConfigurationProperties(prefix = "endpoint")
@PropertySource(value={"profile/prd/endpoint.yml"})//, factory = YamlLoadFactoryUtil.class)
@Configuration(value="endpointHelper")
public class EndpointHelper {

    @NonNull @NotEmpty
    private Map<String, App> app;

    @Setter
    @Getter
    @ToString
    public static class App {

        @NonNull @NotEmpty
        private String base_path;
        @NonNull @NotEmpty
        private Map<String, EndpointDef> endpoint;

        @Setter
        @Getter
        @ToString
        public static class EndpointDef {

            @NonNull @NotEmpty
            private String port;
            @NonNull @NotEmpty
            private String url;
            @NonNull @NotEmpty
            private String to_route;
        }
    }
}
