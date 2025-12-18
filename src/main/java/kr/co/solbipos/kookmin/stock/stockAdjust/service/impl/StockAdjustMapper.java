package kr.co.solbipos.kookmin.stock.stockAdjust.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.stock.stockAcins.service.StockAcinsVO;
import kr.co.solbipos.kookmin.stock.stockAdjust.service.StockAdjustVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : StockAdjustMapper.java
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
@Mapper
@Repository
public interface StockAdjustMapper {

    /** 사유값 */
    List<DefaultMap<String>> getAdjustReason(SessionInfoVO sessionInfoVO);

    /** 재고조정 - 재고조정 리스트 조회 */
    List<DefaultMap<String>> getSearchAdjustList(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정 DTL 삭제 */
    int deleteAllAdjustDtl(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정 HD 삭제 */
    int deleteAdjustHd(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchAdjustDtlList(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정등록 상품 삭제 */
    int deleteAdjustDtl(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정등록 상품 입력 */
    int insertAdjustDtl(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정등록 상품 수정 */
    int updateAdjustDtl(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정등록 HD 수정 */
    int updateAdjustHd(StockAdjustVO stockAdjustHdVO);

    /** 재고조정 - 조정등록 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchAdjustRegistList(StockAdjustVO stockAdjustVO);

    /** 재고조정 - 조정등록 SEQ 조회 */
    String getMaxSeqNo(StockAdjustVO newSeqNoVO);

    /** 재고조정 - 조정등록 HD 입력 */
    int insertAdjustHd(StockAdjustVO stockAdjustHdVO);

    /** 재고조정 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품 삭제 */
    int deleteAdjustToExcelUploadData(StockAcinsVO stockAcinsVO);

    /** 재고조정 엑셀업로드 - 엑셀업로드 한 수량을 실사수량으로 입력 */
    int insertAdjustToExcelUploadData(StockAcinsVO stockAcinsVO);

    /** 재고조정 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(StockAcinsVO stockAcinsVO);
}
