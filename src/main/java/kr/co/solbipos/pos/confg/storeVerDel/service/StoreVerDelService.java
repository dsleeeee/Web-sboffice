package kr.co.solbipos.pos.confg.storeVerDel.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
* @Class Name : StoreVerDelService.java
* @Description : 포스관리 > POS 설정관리 > 매장별 POS 버전 삭제
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2023.10.12  이다솜      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 이다솜
* @since 2023.10.12
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface StoreVerDelService {

    /** 매장별 포스 버전 정보 조회 */
    List<DefaultMap<Object>> getStoreVerList(StoreVerDelVO storeVerDelVO, SessionInfoVO sessionInfoVO);

    /** 매장별 포스 버전 정보 조회 엑셀다운로드 */
    List<DefaultMap<Object>> getStoreVerExcelList(StoreVerDelVO storeVerDelVO, SessionInfoVO sessionInfoVO);

    /** 매장별 포스 버전 삭제 */
    int deleteStoreVer(StoreVerDelVO[] storeVerDelVOs, SessionInfoVO sessionInfoVO);
}
