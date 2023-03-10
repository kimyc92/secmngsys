package com.secmngsys.domain.certification.mapper;

import com.secmngsys.domain.certification.model.dto.CertificationDto;
import com.secmngsys.domain.certification.sql.CertificationSql;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CertificationMapper {

    @InsertProvider(type = CertificationSql.class, method = "insertSmsSendsInfo")
    void insertSmsSendsInfo(@Param("certificationDto") CertificationDto certificationDto);

    @InsertProvider(type = CertificationSql.class, method = "insertSmsDbSendsInfo")
    void insertSmsDbSendsInfo(@Param("certificationDto") CertificationDto certificationDto);

    @SelectProvider(type = CertificationSql.class, method = "selectSmsSendsSeq")
    CertificationDto selectSmsSendsSeq();

    @DeleteProvider(type = CertificationSql.class, method = "deleteSmsSendsinfo")
    void deleteSmsSendsInfo(@Param("certificationDto") CertificationDto certificationDto);

    @UpdateProvider(type = CertificationSql.class, method = "updateSmsSendsInfo")
    void updateSmsSendsInfo(@Param("certificationDto") CertificationDto certificationDto);

    @UpdateProvider(type = CertificationSql.class, method = "updateSmsSendsInfoCompensation")
    void updateSmsSendsInfoCompensation();


//    @SelectProvider(type = UserSql.class, method = "selectUserInfo")
//    UserVo selectUserInfo(UserDto userDTO);

}
