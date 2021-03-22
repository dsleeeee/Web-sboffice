package kr.co.solbipos.mobile.application.main.content.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.DateUtil;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.application.main.content.service.MobileContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class Name : MobileContentServiceImpl.java
 * @Description : 어플리케이션 > 메인 > 내용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.10  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("mobileContentService")
public class MobileContentServiceImpl implements MobileContentService {

    private final MobileContentMapper mobileContentMapper;

    /** Constructor Injection */
    @Autowired
    public MobileContentServiceImpl(MobileContentMapper mobileContentMapper) {
        this.mobileContentMapper = mobileContentMapper;
    }

    //    @Override
//    public Map<String, Object> getHqMain(SessionInfoVO sessionInfoVO) {
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//
//        // 총 매장수, 총 포스수, 주간 폐점매장
//        // 매출현황
//        // 메츨 상품 순위
//        // 매출 상위 가맹점
//        // 수발주 정보
//        // 회원 현황
//        // 공지사항
//        List<DefaultMap<String>> noticeList = contentMapper.getNotice(sessionInfoVO);
//        resultMap.put("noticeList", noticeList);
//        // 날씨
//
//        return resultMap;
//    }

    @Override
    public List<Map<String, String>> getDateSelList(MainSrchFg type) {
        /**
         TYPE1 : 일별(1주), 요일별(1개월), 월별(1년)
         TYPE2 : 오늘, 1주일, 1개월
         */
        List<Map<String,String>> resList = new ArrayList<Map<String, String>>();

        if(type == MainSrchFg.TYPE1){

            Map<String,String> dateMap1 = new HashMap<String, String>();
            dateMap1.put("name", "일별(1주)");
            dateMap1.put("dateStr", DateUtil.addDays(-7,"MM월 dd일") + " ~ "+ DateUtil.addDays(0,"MM월 dd일"));

            Map<String,String> dateMap2 = new HashMap<String, String>();
            dateMap2.put("name", "요일별(1개월)");
            dateMap2.put("dateStr", DateUtil.addMonth(-1, "MM월 dd일") + " ~ "+ DateUtil.addMonth(0,"MM월 dd일"));

            Map<String,String> dateMap3 = new HashMap<String, String>();
            dateMap3.put("name", "월별(1년)");
            dateMap3.put("dateStr", DateUtil.addYear(-1, "yyyy년 MM월 dd일") + " ~ "+ DateUtil.addYear(0,"yyyy년 MM월 dd일"));

            resList.add(dateMap1);
            resList.add(dateMap2);
            resList.add(dateMap3);

        }else{

            Map<String,String> dateMap1 = new HashMap<String, String>();
            dateMap1.put("name", "오늘");
            dateMap1.put("dateStr", DateUtil.addDays(0,"MM월 dd일") + " ~ "+ DateUtil.addDays(0,"MM월 dd일"));

            Map<String,String> dateMap2 = new HashMap<String, String>();
            dateMap2.put("name", "1주일");
            dateMap2.put("dateStr", DateUtil.addDays(-7,"MM월 dd일") + " ~ "+ DateUtil.addDays(0,"MM월 dd일"));

            Map<String,String> dateMap3 = new HashMap<String, String>();
            dateMap3.put("name", "1개월");
            dateMap3.put("dateStr", DateUtil.addMonth(-1, "MM월 dd일") + " ~ "+ DateUtil.addMonth(0,"MM월 dd일"));

            resList.add(dateMap1);
            resList.add(dateMap2);
            resList.add(dateMap3);
        }

        return resList;
    }

    /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    @Override
    public List<DefaultMap<String>> getStoreCntList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getStoreCntList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getStoreCntAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getStoreCntHqList(sessionInfoVO);
        }

        return result;
    }

    /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    @Override
    public List<DefaultMap<String>> getPosCntList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getPosCntList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getPosCntAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getPosCntHqList(sessionInfoVO);
        }

        return result;
    }

    /** 주간매출(매장수/포스수) */
    @Override
    public List<DefaultMap<String>> getWeekSaleList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getWeekSaleList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getWeekSaleAgencyList(sessionInfoVO);
        }

        return result;
    }

    /** 공지사항 */
    @Override
    public List<DefaultMap<String>> getNoticeList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getNoticeList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getNoticeAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getNoticeHqList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getNoticeStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 주간 POS 설치현황(신규설치/재설치) */
    @Override
    public List<DefaultMap<String>> getWeekPosInstList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = mobileContentMapper.getWeekPosInstList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = mobileContentMapper.getWeekPosInstAgencyList(sessionInfoVO);
        }

        return result;
    }

    /** 주간 POS 설치 상위 대리점 */
    @Override
    public List<DefaultMap<String>> getWeekPosInstTopList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getWeekPosInstTopList(sessionInfoVO);
    }

    /** 주간 매출 상위 가맹점 */
    @Override
    public List<DefaultMap<String>> getWeekSaleAgencyTopList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getWeekSaleAgencyTopList(sessionInfoVO);
    }

    /** 매출현황 일별(1주) */
    @Override
    public List<DefaultMap<String>> getSaleWeekList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getSaleWeekList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getSaleWeekStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출현황 요일별(1개월) */
    @Override
    public List<DefaultMap<String>> getSaleMonthList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getSaleMonthList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getSaleMonthStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출현황 월별(1년) */
    @Override
    public List<DefaultMap<String>> getSaleYearList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getSaleYearList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getSaleYearStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 오늘 */
    @Override
    public List<DefaultMap<String>> getSaleProdDayList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getSaleProdDayList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getSaleProdDayStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 1주일 */
    @Override
    public List<DefaultMap<String>> getSaleProdWeekList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getSaleProdWeekList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getSaleProdWeekStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 1개월 */
    @Override
    public List<DefaultMap<String>> getSaleProdMonthList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = mobileContentMapper.getSaleProdMonthList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = mobileContentMapper.getSaleProdMonthStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 가맹점 오늘 */
    @Override
    public List<DefaultMap<String>> getSaleStoreDayList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getSaleStoreDayList(sessionInfoVO);
    }

    /** 매출 상위 가맹점 1주일 */
    @Override
    public List<DefaultMap<String>> getSaleStoreWeekList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getSaleStoreWeekList(sessionInfoVO);
    }

    /** 매출 상위 가맹점 1개월 */
    @Override
    public List<DefaultMap<String>> getSaleStoreMonthList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getSaleStoreMonthList(sessionInfoVO);
    }

    /** 오늘의 매출건수 */
    @Override
    public List<DefaultMap<String>> getDaySaleCntList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getDaySaleCntList(sessionInfoVO);
    }

    /** 오늘의 매출금액 */
    @Override
    public List<DefaultMap<String>> getDaySaleAmtList(SessionInfoVO sessionInfoVO) {
        return mobileContentMapper.getDaySaleAmtList(sessionInfoVO);
    }

}
