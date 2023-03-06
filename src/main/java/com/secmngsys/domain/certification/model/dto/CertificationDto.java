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
    private String cmpMsgId;
    private String msgGroupId;
    private String cmpUsrId;
    private String msgGb;
    private String wrtDttm;
    private String sndDttm;
    private String regSndDttm;
    private String regRcvDttm;
    private String cmpSndDttm;
    private String cmpRcvDttm;
    private String sndPhnId;
    private String rcvPhnId;
    private String callback;
    private String sndMsg;
    private String smsSt;

}
