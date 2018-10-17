package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnDstbCloseStoreService {
    /** 반품마감 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseStoreList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 리스트 확정 */
    int saveRtnDstbCloseStoreConfirm(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO);

    /** 반품마감 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseStoreDtlList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 상세 리스트 저장 */
    int saveRtnDstbCloseStoreDtl(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO);

    /** 반품마감 - 추가분배시 주문가능여부 조회 */
    DefaultMap<String> getOrderFg(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbAddList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배 상세 리스트 저장 */
    int saveDstbAdd(RtnDstbCloseStoreVO[] rtnDstbCloseStoreVOs, SessionInfoVO sessionInfoVO);
}
