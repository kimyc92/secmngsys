package com.secmngsys.domain.user.model.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDrmVo extends UserVo {
//    public void dd(){
//        this.set
//    }
    private String dsdcode;
    private String deptCode;
    private String deptName;
    private String positionCode;
    private String positionName;
    private String roleCode;
    private String roleName;
    private String updatedTs;
    private String validStartTs;
    private String validEndTs;
    private String useYn;
    private String initialPasswordYn;
    private String pwdUpdatedTs;
    private String usedPwd;
    private String pwdErrCnt;
    private String linkedYn;


}
