package kr.co.solbipos.pos.persistence.confg.func;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.pos.domain.confg.func.Func;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 * 
 * @author 김지은
 */
public interface FuncMapper {
    /**
     * 기능구분 상세 조회
     * 
     * @param type
     * @return
     */
    List<DefaultMap<String>> getFuncList(Func func);

    /**
     * 기능구분 상세 등록
     * 
     * @param func
     * @return
     */
    int insertFunc(Func func);
    
    /**
     * 기능구분 상세 수정
     * 
     * @param func
     * @return
     */
    int updateFunc(Func func);
    
    /**
     * 기능구분 상세 삭제
     * @param func
     * @return
     */
    int deleteFunc(Func func);
}
