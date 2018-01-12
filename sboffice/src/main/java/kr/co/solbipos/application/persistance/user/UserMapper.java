package kr.co.solbipos.application.persistance.user;

import kr.co.solbipos.application.domain.user.User;

/**
 * 
 * @author 정용길
 *
 */
public interface UserMapper {
    /**
      * 담당자 이름, 핸드폰 번호로 userId 조회
      * @param user
      * @return
      */
    String selectUserCheck(User user);
}
