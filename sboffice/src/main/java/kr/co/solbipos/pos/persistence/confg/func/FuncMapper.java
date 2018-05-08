package kr.co.solbipos.pos.persistence.confg.func;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.pos.domain.confg.func.FuncVO;

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
    List<DefaultMap<String>> getFuncList(FuncVO funcVO);

    /**
     * 기능구분 상세 등록
     *
     * @param funcVO
     * @return
     */
    int insertFunc(FuncVO funcVO);

    /**
     * 기능구분 상세 수정
     *
     * @param funcVO
     * @return
     */
    int updateFunc(FuncVO funcVO);

    /**
     * 기능구분 상세 삭제
     * @param funcVO
     * @return
     */
    int deleteFunc(FuncVO funcVO);
}
