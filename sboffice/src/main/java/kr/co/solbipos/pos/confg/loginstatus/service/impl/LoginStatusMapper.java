package kr.co.solbipos.pos.confg.loginstatus.service.impl;

import java.util.List;
import kr.co.solbipos.pos.confg.loginstatus.service.LoginStatusVO;

/**
 *
 * @author 정용길
 *
 */
public interface LoginStatusMapper {
    public <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO);
}
