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

    /** 검증결과 전체 삭제 */
    int getProdExcelUploadCheckDeleteAll(ProdExcelUploadVO prodExcelUploadVO);

    /** 검증결과 조회 */
    List<DefaultMap<Object>> getProdExcelUploadList(ProdExcelUploadVO prodExcelUploadVO);

    /** 업로드시 임시테이블 저장 */
    int getProdExcelUploadAddSave(ProdExcelUploadVO prodExcelUploadVO);

    /** 상품분류코드 조회 */
//    String getCheckProdClassCd(ProdExcelUploadVO prodExcelUploadVO);

    /** 상품분류 조회 */
//    int getProdClassCd(ProdExcelUploadVO prodExcelUploadVO);

    /** 상품유형 조회 */
    int getProdTypeFg(ProdExcelUploadVO prodExcelUploadVO);

    /** 판매상품여부 조회 */
    int getSaleProdYn(ProdExcelUploadVO prodExcelUploadVO);

    /** 발주상품구분 조회 */
    int getPoProdFg(ProdExcelUploadVO prodExcelUploadVO);

    /** 과세여부 조회 */
    int getVatFg(ProdExcelUploadVO prodExcelUploadVO);

    /** 재고관리여부 조회 */
    int getStockProdYn(ProdExcelUploadVO prodExcelUploadVO);
}