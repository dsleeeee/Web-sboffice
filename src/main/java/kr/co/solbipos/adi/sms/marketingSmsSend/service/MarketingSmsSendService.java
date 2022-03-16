package kr.co.solbipos.adi.sms.marketingSmsSend.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MarketingSmsSendService.java
 * @Description : 부가서비스 > SMS관리 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MarketingSmsSendService {

    /** 메세지그룹 컬럼 리스트 조회 */
    List<DefaultMap<String>> getMsgGrpColList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 회원등급 리스트 조회 */
    List<DefaultMap<String>> getMembrClassList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 메세지관리 - 메세지서식 조회(최근이력) */
    List<DefaultMap<Object>> getMarketingSmsSendMsgManageDtlList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 회원 조회 */
    List<DefaultMap<Object>> getMarketingSmsSendList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 검색 결과 저장 */
    String getMarketingSmsSendListSave(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 1000건 이상 전송시 전송테이블에 몇건 Insert 됬는지 조회 */
    DefaultMap<String> getSmsSendInsert1000Count(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 본인인증 여부 조회 */
    int getVerifyChk(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 본인인증 요청 저장 */
    int saveVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 본인인증 결과 저장 */
    int updateVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);

    /** 마케팅용 SMS전송 - 발신번호 공통코드에 등록되 있는지 확인(특수부가사업자 승인 전 임시사용) */
    DefaultMap<String> getTelNoNmCodeChk(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO);
}