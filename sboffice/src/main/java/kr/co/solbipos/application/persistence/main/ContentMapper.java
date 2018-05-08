package kr.co.solbipos.application.persistence.main;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.domain.login.SessionInfoVO;

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
