package com.secmngsys.global.configuration.swagger;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "swagger.api")
@ConstructorBinding
public class SwaggerProperties {

    @NonNull @NotEmpty
    private final String title;
    @NonNull @NotEmpty
    private final String version;
    @NonNull @NotEmpty
    private final String description;
    private final ContactDef contact;

    @Getter
    @RequiredArgsConstructor
    public static class ContactDef {

        @NonNull @NotEmpty
        private String name;
        @NonNull @NotEmpty
        private String email;
    }
}
