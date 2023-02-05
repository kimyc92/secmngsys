package com.secmngsys.global.configuration.database;


import com.secmngsys.global.model.EndpointHelper;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "prd")
@ConstructorBinding
public class DataSourceProperties {

    private final DataSource master;
    private final DataSource sms;

    @Setter
    @Getter
    @ToString
    public static class DataSource {
        private Map<String, Object> repository;
        private DataSourceDef datasource;

        @Setter
        @Getter
        @ToString
        public static class DataSourceDef {
            @NonNull @NotEmpty private String uniqueName;
            @NonNull @NotEmpty private String poolSize;
            @NonNull @NotEmpty private String maximumPoolSize;
            @NonNull @NotEmpty private String jdbcUrl;
            @NonNull @NotEmpty private String username;
            @NonNull @NotEmpty private String password;
            @NonNull @NotEmpty private String driverClassName;
            private String autoCommit;
            @NonNull @NotEmpty private String connectionTimeout;
            @NonNull @NotEmpty private String validationTimeout;
            @NonNull @NotEmpty private String maxLifetime;
            @NonNull @NotEmpty private String mapUnderscoreToCamelCase;
        }
    }
}
