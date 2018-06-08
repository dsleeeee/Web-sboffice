package kr.co.solbipos.store.manage.storemanage.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
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
import kr.co.solbipos.store.manage.storemanage.service.StoreManageService;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 가맹점관리 > 매장관리 > 매장정보관리
 * 
 * @author 김지은
 */
@Slf4j
@Controller
@RequestMapping(value = "/store/manage/storeManage/")
public class StoreManageController {

    @Autowired
    StoreManageService service;
    
    /**
     * 매장정보관리 화면 이동
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/view.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        return "store/manage/storeManage/storeManage";
    }
    
    /**
     * 매장 목록 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        List<DefaultMap<String>> list = service.getStoreList(storeManageVO);
        
        return returnListJson(Status.OK, list, storeManageVO);
    }
    
    /**
     * 매장 정보 상세 조회
     * @param storeManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storeManage/getStoreDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreDetail(StoreManageVO storeManageVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        DefaultMap<String> result = service.getStoreDetail(storeManageVO);
        
        return returnJson(Status.OK, result);
    }
}
