package kr.co.solbipos.pos.service.confg.func;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.pos.domain.confg.func.Func;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 * 
 * @author 김지은
 */
public interface FuncService {
    
    /**
     * 기능구분 상세 조회
     * 
     * @param type
     * @return
     */
    List<DefaultMap<String>> list(Func func);
    
    /**
     * 기능구분상세 저장
     * 
     * @param func
     * @param sessionInfo
     * @return
     */
    int save(Func[] func, SessionInfo sessionInfo);

}
