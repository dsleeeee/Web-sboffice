package kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service.StoreMrpizzaBatchChangeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreMrpizzaBatchChangeMapper.java
 * @Description : 미스터피자 > 매장관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreMrpizzaBatchChangeMapper {

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 매장정보 저장 */
    int getStoreBatchChangeSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);
    int getStoreInfoBatchChangeSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 검증결과 삭제 */
    int getStoreExcelUploadCheckDeleteAll(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 저장 완료된 검증결과 삭제 */
    int getStoreExcelUploadCheckDelete(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 매장코드 */
    int getStoreCdChk(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);
    /** 본사공통코드 */
    String getHqNmcodeChk(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 검증결과 저장 */
    int getStoreExcelUploadCheckSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getStoreExcelUploadCheckList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 검증결과 저장 */
    int getStoreBatchChangeUploadSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 매장정보 저장 */
    int getSimpleStoreInfoSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getTmpStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO);
}
