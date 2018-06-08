package kr.co.solbipos.store.manage.virtuallogin.web;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
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
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginService;
import kr.co.solbipos.store.manage.virtuallogin.service.VirtualLoginVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : VirtualLoginController.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Slf4j
@Controller
@RequestMapping(value = "/store/manage/virtualLogin")
public class VirtualLoginController {

    /** service */
    @Autowired
    VirtualLoginService virtualLoginService;

    /**
     * 가상로그인 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/virtualLogin/virtualLogin";
    }

    /**
     * 가상로그인 - 조회
     * @param   request
     * @param   response
     * @param   virtualLoginVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVirtualLoginList(HttpServletRequest request, HttpServletResponse response,
            VirtualLoginVO virtualLoginVO, Model model) {

        List<DefaultMap<String>> list = virtualLoginService.getVirtualLoginList(virtualLoginVO);

        return returnListJson(Status.OK, list, virtualLoginVO);

    }

    /**
     * 가상로그인 - 로그인 수행
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 08.
     */
    @RequestMapping(value = "/virtualLogin/login.sb", method = RequestMethod.POST)
    public String virtualLoginLogin(HttpServletRequest request, HttpServletResponse response,
            Model model) {





        return "";
    }

}
