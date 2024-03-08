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

    /** 포스버전 조회*/
    List<DefaultMap<String>> getSelectVerList();

    /** 포스버전 조회*/
    List<DefaultMap<String>> getSelectSubPos();
}
