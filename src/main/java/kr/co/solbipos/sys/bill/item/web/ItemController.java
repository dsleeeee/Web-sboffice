package kr.co.solbipos.sys.bill.item.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import java.util.ArrayList;
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
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.bill.item.service.ItemService;
import kr.co.solbipos.sys.bill.item.service.ItemVO;

/**
 * @Class Name : ItemController.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력코드 구성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sys/bill/item")
public class ItemController {
    
    @Autowired
    ItemService itemService;
    @Autowired
    SessionService sessionService;
    
    /**
     * 출력코드 구성 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return String
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/item/view.sb", method = RequestMethod.GET)
    public String itemView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        
        return "sys/bill/item/item";
    }
    
    /**
     * 출력코드 구성 - 출력물코드 목록 조회
     * 
     * @param request
     * @param response
     * @param itemVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/item/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getItemList(HttpServletRequest request, HttpServletResponse response,
            ItemVO itemVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물코드 목록 조회
        list = itemService.getItemList(itemVO);

        return ReturnUtil.returnListJson(Status.OK, list, itemVO);

    }
    
    /**
     * 출력코드 구성 - 출력물코드 저장
     * 
     * @param request
     * @param response
     * @param itemVOs
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 06. 15.
     */
    @RequestMapping(value = "/item/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVanCmpnyList(@RequestBody ItemVO[] itemVOs,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = itemService.saveItemList(itemVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }
    
}
