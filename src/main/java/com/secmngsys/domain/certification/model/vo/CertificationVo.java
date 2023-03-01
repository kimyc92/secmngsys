package com.secmngsys.domain.certification.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CertificationVo {
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
