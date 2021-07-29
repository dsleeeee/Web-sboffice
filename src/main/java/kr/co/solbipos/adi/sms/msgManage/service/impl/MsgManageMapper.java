package kr.co.solbipos.adi.sms.msgManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.msgManage.service.MsgManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MsgManageMapper.java
 * @Description : 부가서비스 > SMS관리 > 메세지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MsgManageMapper {

    /** 메세지관리 - 그룹 조회 */
    List<DefaultMap<Object>> getMsgManageList(MsgManageVO msgManageVO);

    /** 그룹코드(자동채번) */
    String getMsgManageMsgGrpCd(MsgManageVO msgManageVO);

    /** 메세지관리 - 그룹 저장 insert */
    int getMsgManageSaveInsert(MsgManageVO msgManageVO);

    /** 메세지관리 - 그룹 저장 update */
    int getMsgManageSaveUpdate(MsgManageVO msgManageVO);

    /** 메세지관리 - 그룹 저장 delete */
    int getMsgManageSaveDelete(MsgManageVO msgManageVO);

    /** 메세지관리 - 메세지서식 조회 */
    List<DefaultMap<Object>> getMsgManageDtlList(MsgManageVO msgManageVO);

    /** SEQ_NO(자동채번) */
    String getMsgManageMsgSeqNo(MsgManageVO msgManageVO);

    /** 메세지관리 - 메세지서식 저장 insert */
    int getMsgManageDtlSaveInsert(MsgManageVO msgManageVO);

    /** 메세지관리 - 메세지서식 저장 update */
    int getMsgManageDtlSaveUpdate(MsgManageVO msgManageVO);

    /** 메세지관리 - 메세지서식 저장 delete */
    int getMsgManageDtlSaveDelete(MsgManageVO msgManageVO);

    /** 메세지관리 - 메세지서식 저장 전체 delete */
    int getMsgManageDtlSaveDeleteAll(MsgManageVO msgManageVO);

    /** 메세지관리 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getMsgManageStoreRegistList(MsgManageVO msgManageVO);

    /** 메세지관리 매장적용 팝업 - 저장(메세지그룹) insert */
    int getMsgManageStoreRegistSaveInsert(MsgManageVO msgManageVO);

    /** 메세지관리 매장적용 팝업 - 저장(메세지서식) insert */
    int getMsgManageStoreRegistDtlSaveInsert(MsgManageVO msgManageVO);
}