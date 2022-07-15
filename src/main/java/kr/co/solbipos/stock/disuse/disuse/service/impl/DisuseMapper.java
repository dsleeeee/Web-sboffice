package kr.co.solbipos.stock.disuse.disuse.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;
import kr.co.solbipos.stock.disuse.disuse.service.DisuseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DisuseMapper {
    /** 폐기관리 - 폐기관리 리스트 조회(본사) */
    List<DefaultMap<String>> getHqDisuseList(DisuseVO disuseVO);

    /** 폐기관리 - 폐기관리 리스트 조회(매장) */
    List<DefaultMap<String>> getStDisuseList(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 전부 삭제(본사) */
    int deleteHqAllDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 전부 삭제(매장) */
    int deleteStAllDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 HD 삭제(본사) */
    int deleteHqDisuseHd(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 HD 삭제(매장) */
    int deleteStDisuseHd(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 진행구분 및 제목 조회(본사) */
    DefaultMap<String> getHqProcFgCheck(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 진행구분 및 제목 조회(매장) */
    DefaultMap<String> getStProcFgCheck(DisuseVO disuseVO);

    /** 폐기관리 - 폐기등록 상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqDisuseRegistList(DisuseVO disuseVO);

    /** 폐기관리 - 폐기등록 상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStDisuseRegistList(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 신규 SEQ 조회(본사) */
    String getHqNewSeqNo(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 신규 SEQ 조회(매장) */
    String getStNewSeqNo(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 등록(본사) */
    int insertHqDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 등록(매장) */
    int insertStDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 수정(본사) */
    int updateHqDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 수정(매장) */
    int updateStDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 삭제(본사) */
    int deleteHqDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 DTL 삭제(매장) */
    int deleteStDisuseDtl(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 HD 등록(본사) */
    int insertHqDisuseHd(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 HD 등록(매장) */
    int insertStDisuseHd(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 HD 수정(본사) */
    int updateHqDisuseHd(DisuseVO disuseVO);

    /** 폐기관리 - 폐기 HD 수정(매장) */
    int updateStDisuseHd(DisuseVO disuseVO);

    /** 폐기관리 - 폐기상세 상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqDisuseDtlList(DisuseVO disuseVO);

    /** 폐기관리 - 폐기상세 상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStDisuseDtlList(DisuseVO disuseVO);

    /** 폐기관리 엑셀업로드 - 엑셀업로드 수량추가 */
    int insertExcelUploadAddQty(ExcelUploadStoreVO excelUploadStoreVO);

    /** 폐기관리 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteDisuseToExcelUploadData(ExcelUploadStoreVO excelUploadStoreVO);

    /** 폐기관리 엑셀업로드 - 엑셀업로드 한 수량을 폐기수량으로 입력 */
    int insertDisuseToExcelUploadData(ExcelUploadStoreVO excelUploadStoreVO);

    /** 폐기관리 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(ExcelUploadStoreVO excelUploadStoreVO);

    /** 사유 */
    List<DefaultMap<String>> getDisuseReason(SessionInfoVO sessionInfoVO);
}
