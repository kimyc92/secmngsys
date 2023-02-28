package com.secmngsys.domain.certification.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CertificationDto {

    private String id;
    @NotBlank
    private String userId;
    @NotBlank
    private String userNm;
    @NotBlank
    private String userHpNo;

    private String certificationNumber;
    private String smsSendDate;
    private String smsSendTime;
    private String smsSendSeq;
    private String smsSendStatusCd;
    private String smsSendStatusNm;



}
