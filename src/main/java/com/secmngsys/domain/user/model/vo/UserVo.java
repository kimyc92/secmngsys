package com.secmngsys.domain.user.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
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
