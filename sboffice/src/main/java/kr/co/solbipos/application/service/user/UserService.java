package kr.co.solbipos.application.service.user;

import kr.co.solbipos.application.domain.user.User;

/**
 * 
 * @author 정용길
 *
 */
public interface UserService {
    /**
      * 담당자 이름, 핸드폰 번호로 userId 조회
      * @param user
      * @return
      */
    String selectUserCheck(User user);
}
