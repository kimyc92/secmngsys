package com.secmngsys.domain.user.model.dto;

import com.secmngsys.global.constraint.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

//@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor(force=true)
public class UserDto {

    private String Id;
    @NotBlank(message = "시스템 코드")
    private String sysCd;
    @NotBlank
    private String userId;

    private String userNm;

    private String userHpNo;

    private String email;

    @Password
    private String password;
}
