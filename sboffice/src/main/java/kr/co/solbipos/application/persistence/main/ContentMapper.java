package kr.co.solbipos.application.persistence.main;

import java.util.List;
import kr.co.solbipos.application.domain.board.total.Total;
import kr.co.solbipos.application.domain.login.SessionInfo;

/**
 * 메인 컨텐츠 조회 매퍼
 * 
 * @author 김지은
 *
 */
public interface ContentMapper {

    /**
     * 공지사항 조회
     * @param sessionInfo
     * @return
     */
    List<Total> getNotice(SessionInfo sessionInfo);

}
