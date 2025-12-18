package kr.co.solbipos.kookmin.stock.stockDisuse.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import kr.co.solbipos.kookmin.stock.stockDisuse.service.StockDisuseVO;

import java.util.List;
/**
 * @Class Name  : StockDisuseMapper.java
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
public interface StockDisuseMapper {
    /** 사유값 */
    List<DefaultMap<String>> getDisuseReason(SessionInfoVO sessionInfoVO);

    /** 재고폐기 - 재고폐기 리스트 조회 */
    List<DefaultMap<String>> getSearchStockDisuseList(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기 DTL 전부 삭제 */
    int deleteAllDisuseDtl(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기 HD 전부 삭제 */
    int deleteDisuseHd(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기등록 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchDisuseRegistList(StockDisuseVO stockDisuseVO);

    /** 신규 seq 조회 */
    String getMaxSeqNo(StockDisuseVO newSeqNoVO);

    /** 재고폐기 - 폐기 DTL 삭제 */
    int deleteDisuseDtl(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기 DTL 등록 */
    int insertDisuseDtl(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기 DTL 수정 */
    int updateDisuseDtl(StockDisuseVO stockDisuseVO);

    /** 재고폐기 - 폐기 HD 등록 */
    int insertDisuseHd(StockDisuseVO stockDisuseHdVO);

    /** 재고폐기 - 폐기 HD 수정 */
    int updateDisuseHd(StockDisuseVO stockDisuseHdVO);

    /** 재고폐기 - 폐기 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchDisuseDtlList(StockDisuseVO stockDisuseVO);

    /** 재고폐기 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteDisuseToExcelUploadData(StockAcinsVO stockAcinsVO);

    /** 재고폐기 엑셀업로드 - 엑셀업로드 한 수량을 폐기수량으로 입력 */
    int insertDisuseToExcelUploadData(StockAcinsVO stockAcinsVO);

    /** 재고폐기 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(StockAcinsVO stockAcinsVO);
}
