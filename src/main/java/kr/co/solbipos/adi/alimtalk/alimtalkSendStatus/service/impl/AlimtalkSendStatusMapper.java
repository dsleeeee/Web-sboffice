package kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AlimtalkSendStatusMapper.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송결과
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface AlimtalkSendStatusMapper {

    /** 알림톡 전송결과 - 조회 */
    List<DefaultMap<Object>> getAlimtalkSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO);

    /** 알림톡 전송결과 - 예약취소 */
    int getAlimtalkSendStatusReserveCancelSaveDelete(AlimtalkSendStatusVO alimtalkSendStatusVO);

    /** 알림톡 전송이력 복구 */
    int getAlkSendSeqRecoverSaveUpdate(AlimtalkSendStatusVO alimtalkSendStatusVO);

    /** 알림톡 일자별 전송현황 - 조회 */
    List<DefaultMap<Object>> getAlimtalkDaySendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO);

    /** 알림톡 기간별 전송현황 - 조회 */
    List<DefaultMap<Object>> getAlimtalkPeriodSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO);
}