package kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.info.dlvrExcelUpload.service.DlvrExcelUploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface DlvrExcelUploadMapper {
    /** 배달지엑셀업로드 임시테이블 데이터 전체 삭제 */
    int getDlvrExcelUploadDeleteAll(DlvrExcelUploadVO dlvrExcelUploadVO);

    /** 업로드시 임시테이블 저장 */
    int getDlvrExcelUploadCheckSave(DlvrExcelUploadVO dlvrExcelUploadVO);

    /** 임시테이블 조회 */
    List<DefaultMap<Object>> getDlvrExcelUploadCheckList(DlvrExcelUploadVO dlvrExcelUploadVO);

    /** 배달지엑셀업로드 임시테이블 데이터 삭제 */
    int getDlvrExcelUploadCheckDelete(DlvrExcelUploadVO dlvrExcelUploadVO);

    /** cidCallSeq값 */
    String getCidCallSeq(DlvrExcelUploadVO dlvrExcelUploadVO);

    /** 저장 */
    int getDeliveryTelNoSave(DlvrExcelUploadVO dlvrExcelUploadVO);
}
