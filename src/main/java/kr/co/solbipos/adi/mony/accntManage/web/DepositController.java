package kr.co.solbipos.adi.mony.accntManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.mony.accntManage.service.DepositService;
import kr.co.solbipos.adi.mony.accntManage.service.impl.AccntVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : DepositController.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/adi/mony/accntManage/")
public class DepositController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DepositService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public DepositController(DepositService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /**
     * 계정관리 - 화면 이동
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 김지은
     * @since 2018.10.12
     */
    @RequestMapping(value = "deposit/depositView.sb", method = RequestMethod.GET)
    public String depositView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "adi/mony/accntManage/depositView";
    }

    /**
     * 계정 조회
     * @param accntVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/getDepositAccntList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDepositAccntList(AccntVO accntVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> storeList = service.getDepositAccntList(accntVO, sessionInfoVO);

        return returnListJson(Status.OK, storeList, accntVO);
    }

    /**
      * 계정 정보 저장
      * @param accntVOs
      * @param request
      * @param response
      * @param model
      * @return
      */
    @RequestMapping(value = "deposit/saveDepositAccntList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDepositAccntList(@RequestBody AccntVO[] accntVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{

            result = service.saveDepositAccntList(accntVOs, sessionInfoVO);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return returnListJson(Status.OK, result);
    }
}
