package kr.co.solbipos.application.main.content.service.impl;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * 메인 컨텐츠 조회 매퍼
 *
 * @author 김지은
 *
 */
public interface ContentMapper {

    /**
     * 공지사항 조회
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getNotice(SessionInfoVO sessionInfoVO);

}
