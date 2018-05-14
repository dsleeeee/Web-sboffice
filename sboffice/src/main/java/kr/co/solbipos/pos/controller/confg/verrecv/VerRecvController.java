package kr.co.solbipos.pos.controller.confg.verrecv;

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
import kr.co.solbipos.pos.domain.confg.verrecv.VerRecv;
import kr.co.solbipos.pos.service.confg.verrecv.VerRecvService;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * 포스관리 > POS 설정관리 > POS 버전 수신현황
 * 
 * @author 김지은
 */
@Slf4j
@Controller
@RequestMapping(value = "/pos/confg/verrecv/")
public class VerRecvController {
    
    @Autowired
    VerRecvService service;
    
    
    /**
     * 버전별수신현황 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verrecv/list.sb", method = RequestMethod.GET)
    public String verrecvlist(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        return "pos/confg/verrecv/verrecv";
    }
    
    /**
     * 버전별수신현황 - 버전 리스트 조회
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verrecv/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verrecvlist(VerRecv verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectVerList(verRecv);
        
        return returnListJson(Status.OK, list, verRecv);
    }
    
    /**
     * 버전별수신현황 - 매장 리스트 조회
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verrecv/storeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verrecvStoreList(VerRecv verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectStoreList(verRecv);
        
        return returnListJson(Status.OK, list, verRecv);
    }
    
    /**
     * 매장별수신현황 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storerecv/list.sb", method = RequestMethod.GET)
    public String storerecvList(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        return "pos/confg/verrecv/storerecv";
    }
    
    /**
     * 매장별수신현황 - 매장 리스트 조회
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storerecv/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storerecvList(VerRecv verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectStoreRecvList(verRecv);
        
        return returnListJson(Status.OK, list, verRecv);
    }
    
    /**
     * 매장별수신현황 - 매장 리스트 조회 - 매장상세
     * 
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "storerecv/storeDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storerecvStoreDtl(VerRecv verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectStoreDtl(verRecv);
        
        log.debug("list ====> "+ list.size());
        
        return returnListJson(Status.OK, list);
    }
    

    /**
     * 버전별매장현황 - 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verstore/list.sb", method = RequestMethod.GET)
    public String verstoreList(HttpServletRequest request, HttpServletResponse response, 
            Model model) {
        return "pos/confg/verrecv/verstore";
    }
    
    /**
     * 버전별매장현황 - 버전 리스트 조회
     * 
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verstore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verstoreList(VerRecv verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectVerStoreList(verRecv);
        
        return returnListJson(Status.OK, list, verRecv);
    }
    
    /**
     * 버전별매장현황 - 매장 리스트 조회
     * 
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verstore/storeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verstoreStoreList(VerRecv verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectVerStoreDtlList(verRecv);
        
        return returnListJson(Status.OK, list, verRecv);
    }
    
}
