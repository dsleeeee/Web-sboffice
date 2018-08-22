package kr.co.solbipos.application.common.web;

import kr.co.common.data.enums.CodeType;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 정용길
 */

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SessionService sessionService;

    @Autowired
    MessageService messageService;

    @Autowired
    CmmEnvUtil cmmEnvUtil;

    @RequestMapping(value = "/403.sb")
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/403";
    }

    @RequestMapping(value = "/application/pos/403.sb")
    public String posDenied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "application/pos/403";
    }

    @RequestMapping(value = "/404.sb")
    public String notFound(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/404";
    }

    @RequestMapping(value = "/500.sb")
    public String serverError(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/500";
    }

    @RequestMapping(value = "/envError.sb")
    public String envError(HttpServletRequest request, HttpServletResponse response, Model model) {

        LOGGER.info("■■■■■■■■■■■■■■■■■■■■■■■■■■ envError여기왔다");
        LOGGER.info("■■■■■■■■■■■■■■■■■■■■■■■■■■ msg : " + String.valueOf(request.getParameter("codeType")));
        LOGGER.info("■■■■■■■■■■■■■■■■■■■■■■■■■■ msg : " + request.getParameter("codeCd"));

        CodeType codeType =  CodeType.valueOf(request.getParameter("codeType"));
        String codeCd = request.getParameter("codeCd");
        String codeNm = "";
        String codeStr = "";
        String msg = "";


        if(codeType == CodeType.HQ_ENV ) {

            EnvstVO envstVO = new EnvstVO();
            envstVO.setEnvstCd(codeCd);

            codeNm = cmmEnvUtil.getEnvNm(envstVO);

            codeStr = "[" + codeCd + "]" + codeNm;

            msg = messageService.get("cmm.hqEnv.error", codeStr);

        } else if(codeType == CodeType.ST_ENV) {

            EnvstVO envstVO = new EnvstVO();
            envstVO.setEnvstCd(codeCd);

            codeNm = cmmEnvUtil.getEnvNm(envstVO);

            codeStr = "[" + codeCd + "]" + codeNm;

            msg = messageService.get("cmm.storeEnv.error", codeStr);    // TODO 이거 코드 메세지처리해야함

        }

        model.addAttribute("codeCd", codeCd);
        model.addAttribute("codeNm", codeNm);
        model.addAttribute("msg", msg);

        return "error/envError";
    }


}


