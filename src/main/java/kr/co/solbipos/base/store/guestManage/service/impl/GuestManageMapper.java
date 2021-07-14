package kr.co.solbipos.base.store.guestManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.guestManage.service.GuestManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : GuestManageMapper.java
 * @Description : 기초관리 > 매장관리 > 객층관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.07.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface GuestManageMapper {

    /** 객층관리 - 조회 */
    List<DefaultMap<Object>> getGuestManageList(GuestManageVO guestManageVO);

    /** 객층관리 - 저장 insert */
    int getGuestManageSaveInsert(GuestManageVO guestManageVO);

    /** 객층관리 - 저장 delete */
    int getGuestManageSaveDelete(GuestManageVO guestManageVO);

    /** 객층관리 매장적용 - 조회 */
    List<DefaultMap<Object>> getGuestManageStoreRegistList(GuestManageVO guestManageVO);

    /** 객층관리 매장적용 - 저장 insert */
    int getGuestManageStoreRegistSaveInsert(GuestManageVO guestManageVO);
}