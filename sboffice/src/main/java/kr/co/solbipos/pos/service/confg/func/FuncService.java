package kr.co.solbipos.pos.service.confg.func;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.pos.domain.confg.func.FuncVO;

/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 *
 * @author 김지은
 */
public interface FuncService {

    /**
     * 기능구분 상세 조회
     *
     * @param funcVO
     * @return
     */
    List<DefaultMap<String>> list(FuncVO funcVO);

    /**
     * 기능구분상세 저장
     *
     * @param funcVO[]
     * @param sessionInfoVO
     * @return
     */
    int save(FuncVO[] funcVOs, SessionInfoVO sessionInfoVO);

}
