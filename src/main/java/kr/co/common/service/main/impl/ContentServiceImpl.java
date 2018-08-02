package kr.co.common.service.main.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.main.ContentService;
import kr.co.common.utils.DateUtil;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.main.content.service.impl.ContentMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

@Service("contentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentMapper contentMapper;


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


    @Override
    public Map<String, Object> getHqMain(SessionInfoVO sessionInfoVO) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 총 매장수, 총 포스수, 주간 폐점매장
        // 매출현황
        // 메츨 상품 순위
        // 매출 상위 가맹점
        // 수발주 정보
        // 회원 현황
        // 공지사항
        List<DefaultMap<String>> noticeList = contentMapper.getNotice(sessionInfoVO);
        resultMap.put("noticeList", noticeList);
        // 날씨
        return resultMap;
    }

    @Override
    public List<DefaultMap<String>> getNotice(SessionInfoVO sessionInfoVO) {
        return contentMapper.getNotice(sessionInfoVO);
    }


}
