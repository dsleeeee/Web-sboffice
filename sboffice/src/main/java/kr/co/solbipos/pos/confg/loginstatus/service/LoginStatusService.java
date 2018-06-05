package kr.co.solbipos.pos.confg.loginstatus.service;

import java.util.List;

/**
 *
 * @author 정용길
 *
 */
public interface LoginStatusService {
    <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO);
}