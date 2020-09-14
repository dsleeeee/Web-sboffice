package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : excelUploadMPSMapper.java
 * @Description : 공통팝업 수불/재고 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ExcelUploadMPSMapper {

    /** 엑셀업로드 - 엑셀업로드 입력 */
    int insertExcelUpload(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다. */
    int updateExcelUploadProdCd(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다. */
    int updateExcelUploadBarcdCd(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - UPRC 를 LAST_COST_UPRC 로 UPDATE 한다. */
    int updateExcelUploadUprc(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadMPSVO excelUploadMPSVO);
}