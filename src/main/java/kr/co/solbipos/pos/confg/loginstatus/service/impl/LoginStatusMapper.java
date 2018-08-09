package kr.co.solbipos.pos.confg.loginstatus.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.solbipos.pos.confg.loginstatus.service.LoginStatusVO;

/**
 * @Class Name : LoginStatusMapper.java
 * @Description : 포스관리 > POS 설정관리 > POS 로그인현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface LoginStatusMapper {
    public <E> List<E> selectLoginStatus(LoginStatusVO loginStatusVO);
}
