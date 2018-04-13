package kr.co.solbipos.pos.service.confg.func;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 * 
 * @author 김지은
 */
public interface FuncService {
    
    /**
     * POS 기능정의 리스트 조회
     * @param type
     * @return
     */
    List<DefaultMap<String>> getFuncList(SessionInfo sessionInfo);
}
