package kr.co.solbipos.membr.anals.membrRecomendr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.membrRecomendr.service.MembrRecomendrService;
import kr.co.solbipos.membr.anals.membrRecomendr.service.MembrRecomendrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/membr/anals/recomendr/")
public class MembrRecomendrController {

        private final SessionService sessionService;
        private final MembrRecomendrService membrRecomendrService;

        /** Constructor Injection */
        @Autowired
        public MembrRecomendrController(SessionService sessionService, MembrRecomendrService membrRecomendrService){
            this.sessionService = sessionService;
            this.membrRecomendrService = membrRecomendrService;
        }

        /**
         * 페이지 이동
         *
         * @param request
         * @param response
         * @param model
         * */
        @RequestMapping(value = "recomendr/list.sb", method = RequestMethod.GET)
        public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

            return "membr/anals/recomendr/membrRecomendr";
        }

        /**
         * 회원 유치사원 조회
         *
         * @param membrRecomendrVO
         * @param request
         * @param response
         * @param model
         * @return
         */
        @RequestMapping(value = "membrPoint/getMembrPointList.sb", method = RequestMethod.POST)
        @ResponseBody
        public Result getMembrRecomendrList(MembrRecomendrVO membrRecomendrVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

            List<DefaultMap<Object>> result = membrRecomendrService.getMembrRecomendrList(membrRecomendrVO, sessionInfoVO);

            return ReturnUtil.returnListJson(Status.OK, result, membrRecomendrVO);
        }
}
