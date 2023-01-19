package kr.co.solbipos.store.storeMoms.storeBatchChange.service.impl;

import com.sun.javafx.binding.StringFormatter;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.storeBatchChange.service.StoreBatchChangeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreBatchChangeMapper.java
 * @Description : 맘스터치 > 매장관리 > 점포영업지역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.17  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.01.17
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface StoreBatchChangeMapper {

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreBatchChangeVO storeBatchChangeVO);

    /** 매장정보 저장 */
    int getStoreBatchChangeSave(StoreBatchChangeVO storeBatchChangeVO);
    int getStoreInfoBatchChangeSave(StoreBatchChangeVO storeBatchChangeVO);
    
    /** 검증결과 삭제 */
    int getStoreExcelUploadCheckDeleteAll(StoreBatchChangeVO storeBatchChangeVO);

    /** 매장코드 */
    int getStoreCdChk(StoreBatchChangeVO storeBatchChangeVO);
    /** 지사코드 */
    String getBranchCdChk(StoreBatchChangeVO storeBatchChangeVO);
    /** 본사공통코드 */
    String getHqNmcodeChk(StoreBatchChangeVO storeBatchChangeVO);

    /** 검증결과 저장 */
    int getStoreExcelUploadCheckSave(StoreBatchChangeVO storeBatchChangeVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getStoreExcelUploadCheckList(StoreBatchChangeVO storeBatchChangeVO);

    /** 매장정보 저장 */
    int getSimpleStoreSave(StoreBatchChangeVO storeBatchChangeVO);
    int getSimpleStoreInfoSave(StoreBatchChangeVO storeBatchChangeVO);

    /** 저장 완료된 검증결과 삭제 */
    int getStoreExcelUploadCheckDelete(StoreBatchChangeVO storeBatchChangeVO);
}
