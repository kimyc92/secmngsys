package com.secmngsys.domain.user.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserVo {

    private String sysCd;
    @NotBlank
    private String userId;
    @NotBlank
    private String userNm;
    @NotBlank
    private String userHpNo;
    @NotBlank
    private String companyNm;
    @NotBlank
    private String deptNm;
}
