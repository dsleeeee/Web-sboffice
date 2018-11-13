package kr.co.solbipos.stock.disuse.hqDisuse.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqDisuseService {
    /** 폐기관리 - 폐기관리 리스트 조회 */
    List<DefaultMap<String>> getHqDisuseList(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 삭제 */
    int deleteHqDisuse(HqDisuseVO[] hqDisuseVOs, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqDisuseRegistList(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기상품 저장 */
    int saveHqDisuseRegist(HqDisuseVO[] hqDisuseVOs, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기등록시 상품정보 조회 */
    DefaultMap<String> getProdInfo(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getHqDisuseDtlList(HqDisuseVO hqDisuseVO);

    /** 폐기관리 - 폐기 상세 상품 저장 */
    int saveHqDisuseDtl(HqDisuseVO[] hqDisuseVOs, SessionInfoVO sessionInfoVO);
}
