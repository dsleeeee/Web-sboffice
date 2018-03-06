package kr.co.solbipos.service.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.board.total.Total;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.persistence.main.ContentMapper;
import kr.co.solbipos.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    ContentMapper contentMapper;

    
    @Override
    public List<Map<String, String>> getDateSelList(String type) {
        
        // type 1 : 일별(1주), 요일별(1개월), 월별(1년)
        // type 2 : 오늘, 1주일, 1개월
        
        List<Map<String,String>> resList = new ArrayList<Map<String, String>>();
        
        if(type.equals("1")){

            Map<String,String> dateMap1 = new HashMap<String, String>(); 
            dateMap1.put("name", "일별(1주)");
            dateMap1.put("date", DateUtil.addDays(-7,"MM월 dd일") + " ~ "+ DateUtil.addDays(0,"MM월 dd일"));
            
            Map<String,String> dateMap2 = new HashMap<String, String>(); 
            dateMap2.put("name", "요일별(1개월)");
            dateMap2.put("date", DateUtil.addMonth(-1, "MM월 dd일") + " ~ "+ DateUtil.addMonth(0,"MM월 dd일"));
            
            Map<String,String> dateMap3 = new HashMap<String, String>(); 
            dateMap3.put("name", "월별(1년)");
            dateMap3.put("date", DateUtil.addYear(-1, "yyyy년 MM월 dd일") + " ~ "+ DateUtil.addYear(0,"yyyy년 MM월 dd일"));

            resList.add(dateMap1);
            resList.add(dateMap2);
            resList.add(dateMap3);
            
        }else{

            Map<String,String> dateMap1 = new HashMap<String, String>(); 
            dateMap1.put("name", "오늘");
            dateMap1.put("date", DateUtil.addDays(0,"MM월 dd일") + " ~ "+ DateUtil.addDays(0,"MM월 dd일"));
            
            Map<String,String> dateMap2 = new HashMap<String, String>(); 
            dateMap2.put("name", "1주일");
            dateMap2.put("date", DateUtil.addDays(-7,"MM월 dd일") + " ~ "+ DateUtil.addDays(0,"MM월 dd일"));
            
            Map<String,String> dateMap3 = new HashMap<String, String>(); 
            dateMap3.put("name", "1개월");
            dateMap3.put("date", DateUtil.addMonth(-1, "MM월 dd일") + " ~ "+ DateUtil.addMonth(0,"MM월 dd일"));
            
            resList.add(dateMap1);
            resList.add(dateMap2);
            resList.add(dateMap3);
        }
        
        return resList;
    }
    
    
    @Override
    public Map<String, Object> getHqMain(SessionInfo sessionInfo) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        // 총 매장수, 총 포스수, 주간 폐점매장
        // 매출현황
        // 메츨 상품 순위
        // 매출 상위 가맹점
        // 수발주 정보
        // 회원 현황
        // 공지사항
        List<Total> noticeList = contentMapper.getNotice(sessionInfo);
        log.error(">>>>> noticeList : "+ noticeList.size());
        resultMap.put("noticeList", noticeList);
        // 날씨
        return resultMap;
    }

    @Override
    public List<Total> getNotice(SessionInfo sessionInfo) {
        return contentMapper.getNotice(sessionInfo);
    }
    

}
