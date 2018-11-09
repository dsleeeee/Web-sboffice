package kr.co.solbipos.stock.adj.hqAdj.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqAdjService {
    /** 조정관리 - 조정관리 리스트 조회 */
    List<DefaultMap<String>> getHqAdjList(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 삭제 */
    int deleteHqAdj(HqAdjVO[] hqAdjVOs, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAdjRegistList(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정상품 저장 */
    int saveHqAdjRegist(HqAdjVO[] hqAdjVOs, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정등록시 상품정보 조회 */
    DefaultMap<String> getProdInfo(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getHqAdjDtlList(HqAdjVO hqAdjVO);

    /** 조정관리 - 조정 상세 상품 저장 */
    int saveHqAdjDtl(HqAdjVO[] hqAdjVOs, SessionInfoVO sessionInfoVO);
}
