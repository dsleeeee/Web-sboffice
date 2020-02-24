package kr.co.solbipos.sys.board.boardMaster.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sys.board.boardMaster.service.BoardMasterService;
import kr.co.solbipos.sys.board.boardMaster.service.BoardMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BoardMasterServiceImpl.java
 * @Description : 시스템관리 > 게시판관리 > 게시판관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.02.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("boardMasterService")
@Transactional
public class BoardMasterServiceImpl implements BoardMasterService {
    private final BoardMasterMapper boardMasterMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardMasterServiceImpl(BoardMasterMapper boardMasterMapper) {
        this.boardMasterMapper = boardMasterMapper;
    }

    /** 게시판관리 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardMasterList(BoardMasterVO boardMasterVO, SessionInfoVO sessionInfoVO) {

        return boardMasterMapper.getBoardMasterList(boardMasterVO);
    }

    /** 게시판관리 저장 */
    @Override
    public int getBoardMasterSave(BoardMasterVO[] boardMasterVOs, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(BoardMasterVO boardMasterVO : boardMasterVOs) {

            boardMasterVO.setModDt(currentDt);
            boardMasterVO.setModId(sessionInfoVO.getUserId());

            if (boardMasterVO.getStatus() == GridDataFg.INSERT) {
                boardMasterVO.setRegDt(currentDt);
                boardMasterVO.setRegId(sessionInfoVO.getUserId());

                // 게시판코드 (자동 채번)
                String BoardCd = boardMasterMapper.getBoardMasterBoardCd(boardMasterVO);
                boardMasterVO.setBoardCd(BoardCd);

                procCnt = boardMasterMapper.getBoardMasterSaveInsert(boardMasterVO);

            } else if(boardMasterVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = boardMasterMapper.getBoardMasterSaveUpdate(boardMasterVO);

            } else if (boardMasterVO.getStatus() == GridDataFg.DELETE) {
                procCnt = boardMasterMapper.getBoardMasterSaveDelete(boardMasterVO);
            }
        }

        return procCnt;
    }
}
