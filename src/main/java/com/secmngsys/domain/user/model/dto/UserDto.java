package com.secmngsys.domain.user.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

//@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor(force=true)
public class UserDto {

    private String Id;
    @NotBlank
    private String userId;
    @NotBlank
    private String userNm;
    @NotBlank // @Valid시 표시될 메세지 설정 가능 (message = "메세지")
    private String userHpNo;

}
