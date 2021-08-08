package kr.co.solbipos.pos.loginstatus.service;

import java.util.List;

/**
 * @Class Name : LoginStatusService.java
 * @Description : 포스관리 > POS 설정관리 > POS 로그인현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface LoginStatusService {
    /** POS 로그인 조회 */
    <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO);
}
