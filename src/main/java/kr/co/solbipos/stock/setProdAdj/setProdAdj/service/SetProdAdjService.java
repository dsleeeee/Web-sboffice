package kr.co.solbipos.stock.setProdAdj.setProdAdj.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface SetProdAdjService {
    /** 세트재고조정 - 세트재고조정 리스트 조회 */
    List<DefaultMap<String>> getSetProdAdjList(SetProdAdjVO setProdAdjVO, SessionInfoVO sessionInfoVO);

    /** 세트재고조정 - 세트재고 삭제 */
    int deleteSetProdAdj(SetProdAdjVO[] setProdAdjVOs, SessionInfoVO sessionInfoVO);

    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회 */
    List<DefaultMap<String>> getSetProdAdjRegistList(SetProdAdjVO setProdAdjVO, SessionInfoVO sessionInfoVO);

    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회 */
    List<DefaultMap<String>> getSetProdAdjRegistCompstList(SetProdAdjVO setProdAdjVO, SessionInfoVO sessionInfoVO);

    /** 세트재고조정 - 세트재고조정 등록 */
    int saveSetProdAdjRegist(SetProdAdjVO[] setProdAdjVOs, SessionInfoVO sessionInfoVO);
}
