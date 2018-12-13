package kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExcelUploadMapper {
    /** 엑셀업로드 - 엑셀업로드 입력 */
    int insertExcelUpload(ExcelUploadVO excelUploadVO);

    /** 엑셀업로드 - 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 상품코드인 경우 PROD_CD 컬럼에 UPDATE 한다. */
    int updateExcelUploadProdCd(ExcelUploadVO excelUploadVO);

    /** 엑셀업로드 - 엑셀업로드 데이터 중 PROD_BARCD_CD 의 값이 바코드인 경우 상품코드를 찾아 PROD_CD 컬럼에 UPDATE 한다. */
    int updateExcelUploadBarcdCd(ExcelUploadVO excelUploadVO);

    /** 엑셀업로드 - UPRC 를 LAST_COST_UPRC 로 UPDATE 한다. */
    int updateExcelUploadUprc(ExcelUploadVO excelUploadVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadVO excelUploadVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadVO excelUploadVO);

}
