package kr.co.solbipos.pos.persistence.confg.loginstatus;

import java.util.List;
import kr.co.solbipos.pos.domain.confg.loginstatus.LoginStatusVO;

/**
 *
 * @author 정용길
 *
 */
public interface LoginStatusMapper {
    public <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO);
}
