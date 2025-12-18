package kr.co.solbipos.kookmin.stock.stockAcins.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : StockAcinsMapper.java
 * @Description : 국민대 > 재고관리 > 재고실사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface StockAcinsMapper {

    /** 실사사유 조회 */
    List<DefaultMap<String>> getSearchAcinsReason(SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사관리 리스트 조회 */
    List<DefaultMap<String>> getSearchAcinsList(StockAcinsVO stockAcinsVO);

    /** 재고실사 - 실사 DTL 전체 삭제 */
    int deleteAllAcinsDtl(StockAcinsVO stockAcinsVO);

    /** 재고실사 - 실사 HD 삭제 */
    int deleteAcinsHd(StockAcinsVO stockAcinsVO);

    /** 재고실사 - 실사등록 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchAcinsRegistList(StockAcinsVO stockAcinsVO);

    /** 재고실사 - 실사등록 SEQ 조회 */
    String getMaxSeqNo(StockAcinsVO newSeqNoVO);

    /** 실사관리 - 실사 상품 DTL 삭제(본사) */
    int deleteAcinsDtl(StockAcinsVO stockAcinsVO);

    /** 실사관리 - 실사 상품 DTL 등록 */
    int insertAcinsDtl(StockAcinsVO stockAcinsVO);

    /** 실사관리 - 실사 상품 DTL 수정 */
    int updateAcinsDtl(StockAcinsVO stockAcinsVO);

    /** 실사관리 - 실사 HD 등록 */
    int insertAcinsHd(StockAcinsVO stockAcinsHdVO);

    /** 실사관리 - 실사 HD 수정 */
    int updateAcinsHd(StockAcinsVO stockAcinsHdVO);

    /** 재고실사 - 실사 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(StockAcinsVO stockAcinsVO);

    /** 재고실사 - 실사등록시 상품정보 조회 */
    List<DefaultMap<String>> getSearchAcinsDtlList(StockAcinsVO stockAcinsVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(StockAcinsVO stockAcinsVO);

    /** 엑셀업로드 - 엑셀업로드 입력 */
    int insertExcelUpload(StockAcinsVO stockAcinsVO);

    /** 엑셀업로드 - 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다. */
    int updateExcelUploadProdCd(StockAcinsVO stockAcinsVO);

    /** 엑셀업로드 - 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다. */
    int updateExcelUploadBarcdCd(StockAcinsVO stockAcinsVO);

    /** 엑셀업로드 - UPRC 를 LAST_COST_UPRC 로 UPDATE 한다. */
    int updateExcelUploadUprc(StockAcinsVO stockAcinsVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(StockAcinsVO stockAcinsVO);

    /** 재고실사 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품 삭제 */
    int deleteAcinsToExcelUploadData(StockAcinsVO stockAcinsVO);

    /** 재고실사 엑셀업로드 - 엑셀업로드 한 수량을 실사수량으로 입력 */
    int insertAcinsToExcelUploadData(StockAcinsVO stockAcinsVO);

    /** 재고실사 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(StockAcinsVO stockAcinsVO);
}
