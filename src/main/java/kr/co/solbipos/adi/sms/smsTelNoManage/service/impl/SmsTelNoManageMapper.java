package kr.co.solbipos.adi.sms.smsTelNoManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SmsChargeMapper.java
 * @Description : 부가서비스 > SMS관리 > SMS충전/KCP PG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SmsTelNoManageMapper {

    /** 발신번호관리 - 조회 */
    List<DefaultMap<Object>> getSmsTelNoManageList(SmsTelNoManageVO smsTelNoManageVO);

    /** 발신번호관리 - 발신번호 등록 요청 저장  */
    int getSmsTelNoManageSave(SmsTelNoManageVO smsTelNoManageVO);

    /** 발신번호관리 저장 */
    int getSmsTelNoManageChk(SmsTelNoManageVO smsTelNoManageVO);

    /** 발신번호관리 - 발신번호 등록  */
    int getSmsTelNoManageUpdate(SmsTelNoManageVO smsTelNoManageVO);

    /** 발신번호관리 - 저장 */
    int getSmsTelNoManageSaveUpdate(SmsTelNoManageVO smsTelNoManageVO);

    /** HCS_CRTLG_T.OGN_CD 값 가져옴 */
    String getOrdrIdxx(SmsTelNoManageVO smsTelNoManageVO);

    /** 발신번호차단 탭 - 조회 */
    List<DefaultMap<Object>> getSmsTelNoStopList(SmsTelNoManageVO smsTelNoManageVO);

    /** 발신번호차단 탭 - 저장 */
    int getSmsTelNoStopSaveUpdate(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리 팝업 - 조회 */
    List<DefaultMap<Object>> getSmsGeneralNoManageList(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리 팝업 - 저장 update */
    int getSmsGeneralNoManageSaveUpdate(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리 팝업 - 발신번호 삭제 */
    int getGeneralNoSmsNoSaveDelete(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리 팝업 - 발신번호 등록 요청 저장(발신번호 등록전 상태로) */
    int getSmsGeneralNoSmsNoSaveInsert(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리2 팝업 - 조회 */
    List<DefaultMap<Object>> getSmsGeneralNoManage2List(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리2 팝업 - 저장 update */
    int getSmsGeneralNoManage2SaveUpdate(SmsTelNoManageVO smsTelNoManageVO);

    /** SMS 발신번호 서류인증 미리보기 팝업 - 조회 */
    DefaultMap<String> getSmsPreviewFileNm(SmsTelNoManageVO smsTelNoManageVO);

    /** 일반번호 인증요청 처리2 팝업 - 중복체크 */
    List<DefaultMap<String>> getDupChkTelNo(SmsTelNoManageVO smsTelNoManageVO);
}