package com.secmngsys.domain.certification.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Slf4j
public class smsSendInfoDto {

    @NotBlank
    private String smsSendDate;
    @NotBlank
    private String smsSendTime;
    @NotBlank
    private String smsSendSeq;
    @NotBlank
    private String userId;
    @NotBlank
    private String userNm;
    @NotBlank
    private String userHpNo;

}
