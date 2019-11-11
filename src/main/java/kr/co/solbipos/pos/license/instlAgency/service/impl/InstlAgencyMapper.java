package kr.co.solbipos.pos.license.instlAgency.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.pos.service.MemberVO;
import kr.co.solbipos.pos.license.instlAgency.service.InstlAgencyVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstlAgencyMapper {
    /** 설치업체관리 조회 */
    List<DefaultMap<String>> getInstlAgency(InstlAgencyVO instlAgencyVO);

    /** 설치업체관리 상세정보 조회 */
    DefaultMap<String> getInstlAgencyDtl(InstlAgencyVO instlAgencyVO);

    /** 설치업체관리 등록 */
    int insertAgency(InstlAgencyVO instlAgencyVO);

    /** 설치업체관리 수정 */
    int updateAgency(InstlAgencyVO instlAgencyVO);

    /** 설치업체 사원목록 조회 */
    List<DefaultMap<String>> getAgencyEmp(InstlAgencyVO instlAgencyVO);

    /** 설치업체 사원상세 조회 */
    DefaultMap<String> getAgencyEmpDtl(InstlAgencyVO instlAgencyVO);

    /** 설치업체 사원등록 */
    int insertEmployee(InstlAgencyVO instlAgencyVO);

    /** 설치업체 사원수정 */
    int updateEmployee(InstlAgencyVO instlAgencyVO);
}
