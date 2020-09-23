package kr.co.solbipos.stock.acins.acins.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.stock.acins.acins.service.AcinsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AcinsMapper {
    /** 실사관리 - 실사관리 리스트 조회(본사) */
    List<DefaultMap<String>> getHqAcinsList(AcinsVO acinsVO);

    /** 실사관리 - 실사관리 리스트 조회(매장) */
    List<DefaultMap<String>> getStAcinsList(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 전부 삭제(본사) */
    int deleteHqAllAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 전부 삭제(매장) */
    int deleteStAllAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 HD 삭제(본사) */
    int deleteHqAcinsHd(AcinsVO acinsVO);

    /** 실사관리 - 실사 HD 삭제(매장) */
    int deleteStAcinsHd(AcinsVO acinsVO);

    /** 실사관리 - 실사 진행구분 및 제목 조회(본사) */
    DefaultMap<String> getHqProcFgCheck(AcinsVO acinsVO);

    /** 실사관리 - 실사 진행구분 및 제목 조회(매장) */
    DefaultMap<String> getStProcFgCheck(AcinsVO acinsVO);

    /** 실사관리 - 실사등록 상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqAcinsRegistList(AcinsVO acinsVO);

    /** 실사관리 - 실사등록 상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStAcinsRegistList(AcinsVO acinsVO);

    /** 실사관리 - 실사 신규 SEQ 조회(본사) */
    String getHqNewSeqNo(AcinsVO acinsVO);

    /** 실사관리 - 실사 신규 SEQ 조회(매장) */
    String getStNewSeqNo(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 등록(본사) */
    int insertHqAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 등록(매장) */
    int insertStAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 수정(본사) */
    int updateHqAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 수정(매장) */
    int updateStAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 삭제(본사) */
    int deleteHqAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 DTL 삭제(매장) */
    int deleteStAcinsDtl(AcinsVO acinsVO);

    /** 실사관리 - 실사 HD 등록(본사) */
    int insertHqAcinsHd(AcinsVO acinsVO);

    /** 실사관리 - 실사 HD 등록(매장) */
    int insertStAcinsHd(AcinsVO acinsVO);

    /** 실사관리 - 실사 HD 수정(본사) */
    int updateHqAcinsHd(AcinsVO acinsVO);

    /** 실사관리 - 실사 HD 수정(매장) */
    int updateStAcinsHd(AcinsVO acinsVO);

    /** 실사관리 - 실사상세 상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqAcinsDtlList(AcinsVO acinsVO);

    /** 실사관리 - 실사상세 상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStAcinsDtlList(AcinsVO acinsVO);

    /** 실사관리 엑셀업로드 - 엑셀업로드 수량추가 */
    int insertExcelUploadAddQty(ExcelUploadMPSVO excelUploadMPSVO);

    /** 실사관리 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteAcinsToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 실사관리 엑셀업로드 - 엑셀업로드 한 수량을 실사수량으로 입력 */
    int insertAcinsToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 실사관리 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(ExcelUploadMPSVO excelUploadMPSVO);

}
