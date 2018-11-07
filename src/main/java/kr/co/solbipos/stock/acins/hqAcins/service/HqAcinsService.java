package kr.co.solbipos.stock.acins.hqAcins.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqAcinsService {
    /** 실사관리 - 실사관리 리스트 조회 */
    List<DefaultMap<String>> getHqAcinsList(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 삭제 */
    int deleteHqAcins(HqAcinsVO[] hqAcinsVOs, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAcinsRegistList(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사상품 저장 */
    int saveHqAcinsRegist(HqAcinsVO[] hqAcinsVOs, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사등록시 상품정보 조회 */
    DefaultMap<String> getProdInfo(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAcinsDtlList(HqAcinsVO hqAcinsVO);

    /** 실사관리 - 실사 상세 상품 저장 */
    int saveHqAcinsDtl(HqAcinsVO[] hqAcinsVOs, SessionInfoVO sessionInfoVO);

}
