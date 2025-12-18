package kr.co.solbipos.kookmin.stock.stockAdjust.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;

import java.util.List;
/**
 * @Class Name  : StockAdjustService.java
 * @Description : 국민대 > 재고관리 > 재고조정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StockAdjustService {

    /** 사유값 */
    List<DefaultMap<String>> getAdjustReason(SessionInfoVO sessionInfoVO);

    /** 재고조정 - 재고조정 리스트 조회 */
    List<DefaultMap<String>> getSearchAdjustList(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정 삭제 */
    int deleteAdjust(StockAdjustVO[] stockAdjustVOs, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchAdjustDtlList(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정 상세 상품 저장 */
    int saveAdjustDtl(StockAdjustVO[] stockAdjustVOs, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정등록 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchAdjustRegistList(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정상품 저장 */
    int saveAdjustRegist(StockAdjustVO[] stockAdjustVOs, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 조정등록시 상품정보 조회 */
    DefaultMap<String> getAdjustInfo(StockAdjustVO stockAdjustVO, SessionInfoVO sessionInfoVO);

    /** 재고조정 - 엑셀업로드 */
    int excelUpload(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);
}
