package kr.co.solbipos.pos.confg.mainVerManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.mainVerManage.service.MainVerManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MainVerManageMapper.java
 * @Description : 포스관리 > POS 설정관리 > POS 메인버전관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MainVerManageMapper {

    /** POS 메인버전관리 - 조회 */
    List<DefaultMap<Object>> getMainVerManageList(MainVerManageVO mainVerManageVO);

    /** POS 메인버전관리 - 메인버전 삭제 */
    int getMainVerManageDel(MainVerManageVO mainVerManageVO);

    /** 메인버전 등록 팝업 - 조회 */
    List<DefaultMap<Object>> getMainVerRegistList(MainVerManageVO mainVerManageVO);

    /** 메인버전 등록 팝업 - 등록 insert */
    int getMainVerRegistSaveInsert(MainVerManageVO mainVerManageVO);
}