package kr.co.solbipos.adi.sms.sendStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SendStatusMapper.java
 * @Description : 부가서비스 > SMS관리 > 문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.18  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SendStatusMapper {

    /** 메세지그룹 컬럼 리스트 조회 */
    List<DefaultMap<String>> getMsgGrpColList(SendStatusVO sendStatusVO);

    /** 문자전송현황 - 조회 */
    List<DefaultMap<Object>> getSendStatusList(SendStatusVO sendStatusVO);

    /** 문자전송현황 - 예약취소 */
    int getSendStatusReserveCancelSaveDelete(SendStatusVO sendStatusVO);
    int getSendStatusReserveCancelSaveDeleteLMS(SendStatusVO sendStatusVO);

    /** 잔여금액 복구 */
    int getSmsAmtRecoverSaveUpdate(SendStatusVO sendStatusVO);

    /** 전송이력 복구 */
    int getSmsSendSeqRecoverSaveUpdate(SendStatusVO sendStatusVO);

    /** 일자별전송현황 복구 */
    int getDaySmsSendRecoverSaveUpdate(SendStatusVO sendStatusVO);

    /** 일자별 전송현황 - 조회 */
    List<DefaultMap<Object>> getDaySendStatusList(SendStatusVO sendStatusVO);

    /** 기간별 전송현황 - 조회 */
    List<DefaultMap<Object>> getPeriodSendStatusList(SendStatusVO sendStatusVO);


    /** 공통 화면 상단 SMS전송(당일) 표시 - [125 SMS전송현황표시]에 등록된 본사 하위 매장인지 조회 */
    DefaultMap<String> getCmmMainTopStoreCount(SendStatusVO sendStatusVO);

    /** 공통 화면 상단 SMS전송(당일) 표시 - 오늘 SMS전송 건수 조회 */
    DefaultMap<String> getCmmMainTopSmsSendCount(SendStatusVO sendStatusVO);
}