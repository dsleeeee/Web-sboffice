package kr.co.solbipos.store.manage.pwdManageStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.pwdManageStore.service.PwdManageStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PwdManageStoreMapper.java
 * @Description : 기초관리 > 비밀번호 임의변경 > 비밀번호 임의변경(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PwdManageStoreMapper {

    /** 비밀번호 임의변경(매장) - 조회 */
    List<DefaultMap<Object>> getPwdManageStoreList(PwdManageStoreVO pwdManageStoreVO);
}