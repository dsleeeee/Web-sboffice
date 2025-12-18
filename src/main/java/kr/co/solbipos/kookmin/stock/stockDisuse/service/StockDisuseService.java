package kr.co.solbipos.kookmin.stock.stockDisuse.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;

import java.util.List;
/**
 * @Class Name  : StockDisuseService.java
 * @Description : 국민대 > 재고관리 > 재고폐기
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StockDisuseService {

    /** 사유값  */
    List<DefaultMap<String>> getDisuseReason(SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 재고폐기 리스트 조회 */
    List<DefaultMap<String>> getSearchStockDisuseList(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 폐기 삭제 */
    int deleteDisuse(StockDisuseVO[] stockDisuseVOS, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 폐기 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 폐기등록 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchDisuseRegistList(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 폐기상품 저장 */
    int saveDisuse(StockDisuseVO[] stockDisuseVOS, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 폐기 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchDisuseDtlList(StockDisuseVO stockDisuseVO, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 폐기 상세 상품 저장 */
    int saveDisuseDtl(StockDisuseVO[] stockDisuseVOS, SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 엑셀업로드 */
    int excelUpload(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);
}
