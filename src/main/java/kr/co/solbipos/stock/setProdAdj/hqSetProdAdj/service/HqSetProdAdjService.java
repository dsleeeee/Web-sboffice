package kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqSetProdAdjService {
    /** 세트재고조정 - 세트재고조정 리스트 조회 */
    List<DefaultMap<String>> getHqSetProdAdjList(HqSetProdAdjVO qSetProdAdjVO);

    /** 세트재고조정 - 세트재고 삭제 */
    int deleteHqSetProdAdj(HqSetProdAdjVO[] qSetProdAdjVOs, SessionInfoVO sessionInfoVO);

    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회 */
    List<DefaultMap<String>> getHqSetProdAdjRegistList(HqSetProdAdjVO qSetProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회 */
    List<DefaultMap<String>> getHqSetProdAdjRegistCompstList(HqSetProdAdjVO qSetProdAdjVO);

    /** 세트재고조정 - 세트재고조정 등록 */
    int saveHqSetProdAdjRegist(HqSetProdAdjVO[] qSetProdAdjVOs, SessionInfoVO sessionInfoVO);

}
