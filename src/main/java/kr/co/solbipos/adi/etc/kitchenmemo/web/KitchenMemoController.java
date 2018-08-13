package kr.co.solbipos.adi.etc.kitchenmemo.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoService;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;


/**
* @Class Name : KitchenMemoController.java
* @Description : 부가서비스 > 주방메모관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/adi/etc/kitchenmemo/kitchenmemo/")
public class KitchenMemoController {

    @Autowired
    KitchenMemoService kitchenMemoService;

    @Autowired
    SessionService sessionService;

    @Autowired
    MessageService messageService;

    /**
     * 부가서비스 > 주방메모관리
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String kitchenmemoList(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<Object>> list = kitchenMemoService.selectKitchenMemo(sessionInfoVO);
        model.addAttribute("kitchenMemoList", convertToJson(list));

        return "adi/etc/kitchenmemo/kitchenMemo";
    }

    /**
     * 저장
     * @param kitchenMemoVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result kitchenmemoSave(@RequestBody KitchenMemoVO[] kitchenMemoVO, HttpServletRequest request,
            HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kitchenMemoService.save(kitchenMemoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
