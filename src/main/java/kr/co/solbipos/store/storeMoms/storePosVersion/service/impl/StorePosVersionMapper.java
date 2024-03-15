package kr.co.solbipos.store.storeMoms.storePosVersion.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StorePosVersionMapper.java
 * @Description : 맘스터치 > 매장관리 > 매장포스버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.30  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.03.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface StorePosVersionMapper {

    /** 조회 */
    List<DefaultMap<String>> getStorePosVersionList(StorePosVersionVO storePosVersionVO);

    /** 포스버전 조회 */
    List<DefaultMap<String>> getSelectVerList(StorePosVersionVO storePosVersionVO);

    /** 포스버전 조회 */
    List<DefaultMap<String>> getSelectSubPos();

    /** 패치정보 상세 팝업 */
    List<DefaultMap<String>> getPatchDtlList(StorePosVersionVO storePosVersionVO);

    /** 버전 적용 매장 등록 */
    String registStore(StorePosVersionVO storePosVersionVO);

    /** 버전 적용 여부 체크 */
    int registChk(StorePosVersionVO storePosVersionVO);

    /** 포스패치로그 조회 */
    List<DefaultMap<String>> getPosPatchLogList(StorePosVersionVO storePosVersionVO);
}
