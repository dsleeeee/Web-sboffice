package kr.co.solbipos.application.service.user;

import java.util.HashMap;

/**
 * 
 * @author 정용길
 *
 */
public interface UserService {
    /**
     * 담당자 이름, 핸드폰 번호로 userId 조회
     * @param param
     * @return
     */
    
    String selectUserCheck(HashMap<String, String> param);
}
