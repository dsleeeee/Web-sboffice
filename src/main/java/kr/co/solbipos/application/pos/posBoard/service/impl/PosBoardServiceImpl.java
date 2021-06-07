package kr.co.solbipos.application.pos.posBoard.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.pos.posBoard.service.PosBoardService;
import kr.co.solbipos.application.pos.posBoard.service.PosBoardVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.enums.StatusFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PosBoardServiceImpl.java
 * @Description : POS 화면에서 게시판(포스용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("posBoardService")
public class PosBoardServiceImpl implements PosBoardService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PosBoardMapper posBoardMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PosBoardServiceImpl(PosBoardMapper posBoardMapper, MessageService messageService) {
        this.posBoardMapper = posBoardMapper;
        this.messageService = messageService;
    }

    /** 게시판 조회 */
    @Override
    public List<DefaultMap<Object>> getPosBoardList(PosBoardVO posBoardVO, SessionInfoVO sessionInfoVO) {

        posBoardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            posBoardVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        posBoardVO.setUserId(posBoardVO.getUserId());

        return posBoardMapper.getPosBoardList(posBoardVO);
    }

    /** 게시판 메뉴 권한 조회 */
    @Override
    public String getBoardAuth(SessionInfoVO sessionInfoVO, String menuCd) {
        PosBoardVO posBoardVO = new PosBoardVO();
        posBoardVO.setMenuCd(menuCd);
        posBoardVO.setPosUserId(sessionInfoVO.getUserId());
        return posBoardMapper.getBoardAuth(posBoardVO);
    }
}