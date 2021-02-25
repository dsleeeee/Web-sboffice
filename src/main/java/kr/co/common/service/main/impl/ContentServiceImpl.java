package kr.co.common.service.main.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.main.ContentService;
import kr.co.common.utils.DateUtil;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.main.content.service.impl.ContentMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("contentService")
public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper;

    /** Constructor Injection */
    @Autowired
    public ContentServiceImpl(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
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
            result = contentMapper.getStoreCntList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = contentMapper.getStoreCntAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getStoreCntHqList(sessionInfoVO);
        }

        return result;
    }

    /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    @Override
    public List<DefaultMap<String>> getPosCntList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = contentMapper.getPosCntList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = contentMapper.getPosCntAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getPosCntHqList(sessionInfoVO);
        }

        return result;
    }

    /** 주간매출(매장수/포스수) */
    @Override
    public List<DefaultMap<String>> getWeekSaleList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = contentMapper.getWeekSaleList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = contentMapper.getWeekSaleAgencyList(sessionInfoVO);
        }

        return result;
    }

    /** 공지사항 */
    @Override
    public List<DefaultMap<String>> getNoticeList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = contentMapper.getNoticeList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = contentMapper.getNoticeAgencyList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getNoticeHqList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getNoticeStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 주간 POS 설치현황(신규설치/재설치) */
    @Override
    public List<DefaultMap<String>> getWeekPosInstList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {
            result = contentMapper.getWeekPosInstList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            result = contentMapper.getWeekPosInstAgencyList(sessionInfoVO);
        }

        return result;
    }

    /** 주간 POS 설치 상위 대리점 */
    @Override
    public List<DefaultMap<String>> getWeekPosInstTopList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getWeekPosInstTopList(sessionInfoVO);
    }

    /** 주간 매출 상위 가맹점 */
    @Override
    public List<DefaultMap<String>> getWeekSaleAgencyTopList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getWeekSaleAgencyTopList(sessionInfoVO);
    }

    /** 매출현황 일별(1주) */
    @Override
    public List<DefaultMap<String>> getSaleWeekList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getSaleWeekList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getSaleWeekStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출현황 요일별(1개월) */
    @Override
    public List<DefaultMap<String>> getSaleMonthList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getSaleMonthList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getSaleMonthStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출현황 월별(1년) */
    @Override
    public List<DefaultMap<String>> getSaleYearList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getSaleYearList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getSaleYearStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 오늘 */
    @Override
    public List<DefaultMap<String>> getSaleProdDayList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getSaleProdDayList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getSaleProdDayStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 1주일 */
    @Override
    public List<DefaultMap<String>> getSaleProdWeekList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getSaleProdWeekList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getSaleProdWeekStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 상품 1개월 */
    @Override
    public List<DefaultMap<String>> getSaleProdMonthList(SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result = contentMapper.getSaleProdMonthList(sessionInfoVO);

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result = contentMapper.getSaleProdMonthStoreList(sessionInfoVO);
        }

        return result;
    }

    /** 매출 상위 가맹점 오늘 */
    @Override
    public List<DefaultMap<String>> getSaleStoreDayList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getSaleStoreDayList(sessionInfoVO);
    }

    /** 매출 상위 가맹점 1주일 */
    @Override
    public List<DefaultMap<String>> getSaleStoreWeekList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getSaleStoreWeekList(sessionInfoVO);
    }

    /** 매출 상위 가맹점 1개월 */
    @Override
    public List<DefaultMap<String>> getSaleStoreMonthList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getSaleStoreMonthList(sessionInfoVO);
    }

    /** 오늘의 매출건수 */
    @Override
    public List<DefaultMap<String>> getDaySaleCntList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getDaySaleCntList(sessionInfoVO);
    }

    /** 오늘의 매출금액 */
    @Override
    public List<DefaultMap<String>> getDaySaleAmtList(SessionInfoVO sessionInfoVO) {
        return contentMapper.getDaySaleAmtList(sessionInfoVO);
    }
}