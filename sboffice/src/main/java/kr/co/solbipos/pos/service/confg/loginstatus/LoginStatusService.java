package kr.co.solbipos.pos.service.confg.loginstatus;

import java.util.List;
import kr.co.solbipos.pos.domain.confg.loginstatus.LoginStatusVO;

/**
 *
 * @author 정용길
 *
 */
public interface LoginStatusService {
    <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO);
}