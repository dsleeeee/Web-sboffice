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

    /**
     * 환경변수 설정이 안됐을 경우
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/envError.sb")
    public String envError(HttpServletRequest request, HttpServletResponse response, Model model) {

        CodeType codeType =  CodeType.valueOf(request.getParameter("codeType"));
        String codeCd = request.getParameter("codeCd");
        String codeNm = "";
        String codeStr = "";

        // 본사 환경변수가 없을 경우
        if(codeType == CodeType.HQ_ENV ) {

            EnvstVO envstVO = new EnvstVO();
            envstVO.setEnvstCd(codeCd);

            codeNm = cmmEnvUtil.getEnvNm(envstVO);
            codeStr = "'[" + codeCd + "]" + codeNm + "'";
        }
        // 매장 환경변수가 없을 경우
        else if(codeType == CodeType.STORE_ENV) {

            EnvstVO envstVO = new EnvstVO();
            envstVO.setEnvstCd(codeCd);

            codeNm = cmmEnvUtil.getEnvNm(envstVO);
            codeStr = "'[" + codeCd + "]  " + codeNm + "'";
        }

        model.addAttribute("codeType", codeType);
        model.addAttribute("codeCd", codeCd);
        model.addAttribute("codeNm", codeNm);
        model.addAttribute("codeStr", codeStr);

        return "error/envError";
    }


}


