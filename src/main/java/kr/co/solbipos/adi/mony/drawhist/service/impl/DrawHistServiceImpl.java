package kr.co.solbipos.adi.mony.drawhist.service.impl;

import kr.co.solbipos.adi.mony.drawhist.service.DrawHistService;
import kr.co.solbipos.adi.mony.drawhist.service.DrawHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DrawHistServiceImpl.java
 * @Description : 부가서비스 > 금전처리 > 돈통오픈기록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김태수      최초생성
 *
 * @author NHN한국사이버결제 KCP 김태수
 * @since 2018.08.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("DrawHistService")
@Transactional
public class DrawHistServiceImpl implements DrawHistService {

    private final DrawHistMapper DrawHistMapper;

    /** Constructor Injection */
    @Autowired
    public DrawHistServiceImpl(
        kr.co.solbipos.adi.mony.drawhist.service.impl.DrawHistMapper drawHistMapper) {
        DrawHistMapper = drawHistMapper;
    }

    @Override
    public List<DrawHistVO> selectDrawHist(DrawHistVO DrawHistVO, SessionInfoVO sessionInfoVO) {
    	DrawHistVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
        	DrawHistVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }
        return DrawHistMapper.selectDrawHist(DrawHistVO);
    }

}

