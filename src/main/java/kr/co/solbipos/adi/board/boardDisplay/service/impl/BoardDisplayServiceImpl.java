package kr.co.solbipos.adi.board.boardDisplay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.board.boardDisplay.service.BoardDisplayService;
import kr.co.solbipos.adi.board.boardDisplay.service.BoardDisplayVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BoardDisplayServiceImpl.java
 * @Description : 부가서비스 > 게시판 > 일반게시판노출순서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.06.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("boardDisplayService")
@Transactional
public class BoardDisplayServiceImpl implements BoardDisplayService {
    private final BoardDisplayMapper boardDisplayMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardDisplayServiceImpl(BoardDisplayMapper boardDisplayMapper){
        this.boardDisplayMapper = boardDisplayMapper;
    }

    /** 일반게시판노출순서 - 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardDisplayList(BoardDisplayVO boardDisplayVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        boardDisplayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        boardDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            boardDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        boardDisplayVO.setUserId(sessionInfoVO.getUserId());

        String currentDate = currentDateString();
        boardDisplayVO.setDate(currentDate);

        boardDisplayVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        return boardDisplayMapper.getBoardDisplayList(boardDisplayVO);
    }

    /** 일반게시판노출순서 - 저장 */
    @Override
    public int getBoardDisplaySave(BoardDisplayVO[] boardDisplayVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(BoardDisplayVO boardDisplayVO : boardDisplayVOs) {
            boardDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            boardDisplayVO.setModDt(currentDt);
            boardDisplayVO.setModId(sessionInfoVO.getUserId());

            if (boardDisplayVO.getStatus() == GridDataFg.INSERT) {
                // 노출여부 Y
                procCnt = boardDisplayMapper.getBoardDisplaySaveTopYn(boardDisplayVO);

            } else if (boardDisplayVO.getStatus() == GridDataFg.UPDATE) {
                // 순서, 강조여부
                procCnt = boardDisplayMapper.getBoardDisplaySaveDispSeq(boardDisplayVO);

            } else if (boardDisplayVO.getStatus() == GridDataFg.DELETE) {
                // 노출여부 N
                procCnt = boardDisplayMapper.getBoardDisplaySaveTopYn(boardDisplayVO);
            }
        }

        return procCnt;
    }

    /** 상위노출게시물선택 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardDisplayAddList(BoardDisplayVO boardDisplayVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        boardDisplayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        boardDisplayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            boardDisplayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        boardDisplayVO.setUserId(sessionInfoVO.getUserId());

        String currentDate = currentDateString();
        boardDisplayVO.setDate(currentDate);

        boardDisplayVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        return boardDisplayMapper.getBoardDisplayAddList(boardDisplayVO);
    }
}
