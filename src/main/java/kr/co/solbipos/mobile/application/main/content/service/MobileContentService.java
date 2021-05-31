package kr.co.solbipos.mobile.application.main.content.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : MobileContentService.java
 * @Description : (모바일) 어플리케이션 > 메인 > 내용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.27  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2021.05.27
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileContentService {

    /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getMobileStoreCntList(SessionInfoVO sessionInfoVO);

    /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getMobilePosCntList(SessionInfoVO sessionInfoVO);

    /** 주간매출(매장수/포스수) */
    List<DefaultMap<String>> getMobileWeekSaleList(SessionInfoVO sessionInfoVO);

    /** 공지사항 */
    List<DefaultMap<String>> getMobileNoticeList(SessionInfoVO sessionInfoVO);

    /** 주간 POS 설치현황(신규설치/재설치) */
    List<DefaultMap<String>> getMobileWeekPosInstList(SessionInfoVO sessionInfoVO);

    /** 주간 POS 설치 상위 대리점 */
    List<DefaultMap<String>> getMobileWeekPosInstTopList(SessionInfoVO sessionInfoVO);

    /** 주간 매출 상위 가맹점 */
    List<DefaultMap<String>> getMobileWeekSaleAgencyTopList(SessionInfoVO sessionInfoVO);

    /** 매출현황 */
    List<DefaultMap<String>> getMobileSaleWeekList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 상품 */
    List<DefaultMap<String>> getMobileSaleProdWeekList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 가맹점 */
    List<DefaultMap<String>> getMobileSaleStoreWeekList(SessionInfoVO sessionInfoVO);

    /** 오늘의 매출건수 */
    List<DefaultMap<String>> getMobileDaySaleCntList(SessionInfoVO sessionInfoVO);

    /** 오늘의 매출금액 */
    List<DefaultMap<String>> getMobileDaySaleAmtList(SessionInfoVO sessionInfoVO);
}