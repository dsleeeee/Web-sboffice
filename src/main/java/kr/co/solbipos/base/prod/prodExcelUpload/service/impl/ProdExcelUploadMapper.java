package kr.co.solbipos.base.prod.prodExcelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdExcelUploadMapper.java
 * @Description : 기초관리 > 상품관리 > 상품엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface ProdExcelUploadMapper {

    /** 상품분류 콤보 조회 */
    List<DefaultMap<String>> prodClassComboList(ProdExcelUploadVO prodExcelUploadVO);

    /** 검증결과 전체 삭제 */
    int getProdExcelUploadCheckDeleteAll(ProdExcelUploadVO prodExcelUploadVO);

    /** 검증결과 전체 삭제 */
    int getProdExcelUploadCheckDelete(ProdExcelUploadVO prodExcelUploadVO);

    /** 검증결과 조회 */
    List<DefaultMap<Object>> getProdExcelUploadCheckList(ProdExcelUploadVO prodExcelUploadVO);

    /** 업로드시 임시테이블 저장 */
    int getProdExcelUploadCheckSave(ProdExcelUploadVO prodExcelUploadVO);

    /** 상품유형 검증 조회 */
    String getProdTypeFgCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 판매상품여부 검증 조회 */
    String getSaleProdYnCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 발주상품구분 검증 조회 */
    String getPoProdFgCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 발주단위구분 검증 조회 */
    String getPoUnitFgCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 과세여부 검증 조회 */
    String getVatFgCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 재고관리여부 검증 조회 */
    String getStockProdYnCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 거래처 검증 조회 */
    String getVendrCdCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 상품분류 검증 조회 */
    String getProdClassCdCheck(ProdExcelUploadVO prodExcelUploadVO);

    /** 상품코드 자동채번 */
    String getProdCd(ProdExcelUploadVO prodExcelUploadVO);
}