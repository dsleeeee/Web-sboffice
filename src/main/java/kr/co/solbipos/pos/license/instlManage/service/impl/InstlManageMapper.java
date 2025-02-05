package kr.co.solbipos.pos.license.instlManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstlManageMapper {

    /** 업체현황탭 - 업체현황조회 */
    List<DefaultMap<Object>> getAgencyList(InstlManageVO instlManageVO);

    /** 업체현황탭 - 업체현황 상세조회 */
    List<DefaultMap<Object>> getAgencyDtlList(InstlManageVO instlManageVO);

    /** 업체현황탭 - 매장별매출 상세조회 */
    List<DefaultMap<Object>> getAgencyPurchsList(InstlManageVO instlManageVO);

    /** 설치현황탭 - 설치현황조회 */
    List<DefaultMap<Object>> getInstlList(InstlManageVO instlManageVO);

    /** 설치현황탭 - 설치현황 상세조회 */
    List<DefaultMap<Object>> getInstlDetailList(InstlManageVO instlManageVO);

    /** 설치현황탭 - 설치현황 설치정보 상세조회 */
    List<DefaultMap<Object>> getInstlDtlList(InstlManageVO instlManageVO);

    /** 설치요청 목록 조회 */
    List<DefaultMap<String>> getInstlRequestList(InstlManageVO instlManageVO);

    /** 설치요청 목록 상세 */
    List<DefaultMap<String>> getInstlRequestDtl(InstlManageVO instlManageVO);

    /** 설치요청 목록 삭제 */
    int delRequestDtl(InstlManageVO instlManageVO);

    /** 본사일때 대리점정보 */
    List<DefaultMap<String>> getAgencyInfo(SessionInfoVO sessionInfoVO);
}
