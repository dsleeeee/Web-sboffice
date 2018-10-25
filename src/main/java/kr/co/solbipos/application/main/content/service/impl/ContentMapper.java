package kr.co.solbipos.application.main.content.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : ContentMapper.java
* @Description : 어플리케이션 > 메인 > 내용
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface ContentMapper {

    /**
     * 공지사항 조회
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getNotice(SessionInfoVO sessionInfoVO);

}
