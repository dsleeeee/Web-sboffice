package kr.co.solbipos.service.main;

import java.util.List;
import java.util.Map;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.main.MainSrchFg;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 메인 컨텐츠를 가져오기 위한 서비스
 * 
 * @author 김지은
 *
 */
public interface ContentService {

    
    /**
     * 메인에서 사용할 날짜 select box
     * @param type
     * @return
     */
    List<Map<String,String>> getDateSelList(MainSrchFg type);
    
    /**
     * 본사의 메인 컨텐츠 조회
     * @param sessionInfo
     * @return
     */
    Map<String, Object> getHqMain(SessionInfo sessionInfo);

    /**
     * 본사 - 공지사항 조회
     * @param sessionInfo
     * @return
     */
    List<DefaultMap<String>> getNotice(SessionInfo sessionInfo);
    
}
