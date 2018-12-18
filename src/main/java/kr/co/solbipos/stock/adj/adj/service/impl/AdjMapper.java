package kr.co.solbipos.stock.adj.adj.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdjMapper {
    /** 조정관리 - 조정관리 리스트 조회(본사) */
    List<DefaultMap<String>> getHqAdjList(AdjVO adjVO);

    /** 조정관리 - 조정관리 리스트 조회(매장) */
    List<DefaultMap<String>> getStAdjList(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 전부 삭제(본사) */
    int deleteHqAllAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 전부 삭제(매장) */
    int deleteStAllAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 HD 삭제(본사) */
    int deleteHqAdjHd(AdjVO adjVO);

    /** 조정관리 - 조정 HD 삭제(매장) */
    int deleteStAdjHd(AdjVO adjVO);

    /** 조정관리 - 조정 진행구분 및 제목 조회(본사) */
    DefaultMap<String> getHqProcFgCheck(AdjVO adjVO);

    /** 조정관리 - 조정 진행구분 및 제목 조회(매장) */
    DefaultMap<String> getStProcFgCheck(AdjVO adjVO);

    /** 조정관리 - 조정등록 상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqAdjRegistList(AdjVO adjVO);

    /** 조정관리 - 조정등록 상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStAdjRegistList(AdjVO adjVO);

    /** 조정관리 - 조정 신규 SEQ 조회(본사) */
    String getHqNewSeqNo(AdjVO adjVO);

    /** 조정관리 - 조정 신규 SEQ 조회(매장) */
    String getStNewSeqNo(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 등록(본사) */
    int insertHqAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 등록(매장) */
    int insertStAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 수정(본사) */
    int updateHqAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 수정(매장) */
    int updateStAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 삭제(본사) */
    int deleteHqAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 DTL 삭제(매장) */
    int deleteStAdjDtl(AdjVO adjVO);

    /** 조정관리 - 조정 HD 등록(본사) */
    int insertHqAdjHd(AdjVO adjVO);

    /** 조정관리 - 조정 HD 등록(매장) */
    int insertStAdjHd(AdjVO adjVO);

    /** 조정관리 - 조정 HD 수정(본사) */
    int updateHqAdjHd(AdjVO adjVO);

    /** 조정관리 - 조정 HD 수정(매장) */
    int updateStAdjHd(AdjVO adjVO);

    /** 조정관리 - 조정상세 상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqAdjDtlList(AdjVO adjVO);

    /** 조정관리 - 조정상세 상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStAdjDtlList(AdjVO adjVO);

    /** 조정관리 엑셀업로드 - 엑셀업로드 수량추가 */
    int insertExcelUploadAddQty(ExcelUploadVO excelUploadVO);

    /** 조정관리 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteAdjToExcelUploadData(ExcelUploadVO excelUploadVO);

    /** 조정관리 엑셀업로드 - 엑셀업로드 한 수량을 조정수량으로 입력 */
    int insertAdjToExcelUploadData(ExcelUploadVO excelUploadVO);

    /** 조정관리 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(ExcelUploadVO excelUploadVO);

}
