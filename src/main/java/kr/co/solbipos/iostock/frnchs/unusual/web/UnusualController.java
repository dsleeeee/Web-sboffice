package kr.co.solbipos.iostock.frnchs.unusual.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualService;
import kr.co.solbipos.iostock.frnchs.unusual.service.UnusualVO;

/**
 * @Class Name : ProdController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 상품별 입출고내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.05  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/frnchs/unusual")
public class UnusualController {
    private final SessionService sessionService;
    private final UnusualService unusualService;

    @Autowired
    public UnusualController(SessionService sessionService, UnusualService unusualService) {
        this.sessionService = sessionService;
        this.unusualService = unusualService;
    }

    /**
     * 거래처 상품별 입출고내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/unUsual/view.sb", method = RequestMethod.GET)
    public String unusualView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/frnchs/unusual/unusual";
    }


    /**
     * 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/unUsual/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUnusualList(HttpServletRequest request, HttpServletResponse response,
        Model model, UnusualVO unusualVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = unusualService.getUnusualList(unusualVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, unusualVO);
    } 
}
