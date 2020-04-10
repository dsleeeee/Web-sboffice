package kr.co.solbipos.sale.status.item.item.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.gift.service.GiftVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.sale.status.item.item.service.ItemItemService;
import kr.co.solbipos.sale.status.item.item.service.ItemItemVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : ItemItemController.java
 * @Description : 매출관리 > 매출현황 > 매출항목표시
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.26  이승규      최초생성
 *
 * @author 엠투엠글로벌 이승규
 * @since 2020.03.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("sale/status/item/item")
public class ItemItemController {

    private final SessionService sessionService;
    private final ItemItemService itemItemService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public ItemItemController(SessionService sessionService, ItemItemService itemItemService, MessageService messageService) {
        this.sessionService = sessionService;
        this.itemItemService = itemItemService;
        this.messageService = messageService;
    }

    /***
     * 매출항목표시리스트 화면
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/item/itemItem";
    }

    /**
     * 매출항목표시목록 조회
     * @param request
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author 이한빈
     * @since 2018. 08. 16.
     */
    @ResponseBody
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    public Result getItemItemList(HttpServletRequest request, ItemItemVO itemItemVO,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 매장사원목록 조회
        List<DefaultMap<String>> list = itemItemService.getItemList(itemItemVO, sessionInfoVO);

        return returnListJson(Status.OK, list, itemItemVO);
    }

    /**
     * 매출항목표시정보 수정
     * @param itemItemVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody ItemItemVO[] ItemItemVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        EmpResult itemItemResult = null;

        try{
            itemItemResult = itemItemService.saveItemInfo(ItemItemVOs, sessionInfoVO);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return returnJson(Status.OK, itemItemResult);
    }
}
