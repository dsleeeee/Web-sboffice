package kr.co.solbipos.adi.mony.drawhist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.mony.drawhist.service.DrawHistService;
import kr.co.solbipos.adi.mony.drawhist.service.DrawHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;


/**
* @Class Name : DrawHistController.java
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
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/adi/mony/drawhist/drawhist/")
public class DrawHistController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DrawHistService drawHistService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public DrawHistController(DrawHistService drawHistService, SessionService sessionService) {
        this.drawHistService = drawHistService;
        this.sessionService = sessionService;
    }

    /**
     * 부가서비스 > 금전처리 > 돈통오픈기록 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String drawhistList(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "adi/mony/drawhist/drawHist";
    }

    /**
     * 부가서비스 > 금전처리 > 돈통오픈기록 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result drawhistListPost(DrawHistVO drawHistVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        LOGGER.debug(drawHistVO.toString());

        List<DrawHistVO> result = drawHistService.selectDrawHist(drawHistVO, sessionInfoVO);

        return returnListJson(Status.OK, result, drawHistVO);
    }
}
