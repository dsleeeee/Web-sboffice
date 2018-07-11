package kr.co.solbipos.pos.confg.verrecv.web;

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
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvService;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvVO;

/**
* @Class Name : VerRecvController.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 수신현황
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
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
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
    public Result verrecvlist(VerRecvVO verRecv, HttpServletRequest request,
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
    public Result verrecvStoreList(VerRecvVO verRecv, HttpServletRequest request,
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
    public Result storerecvList(VerRecvVO verRecv, HttpServletRequest request,
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
    public Result storerecvStoreDtl(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectStoreDtl(verRecv);
        
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
    public Result verstoreList(VerRecvVO verRecv, HttpServletRequest request,
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
    public Result verstoreStoreList(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = service.selectVerStoreDtlList(verRecv);
        
        return returnListJson(Status.OK, list, verRecv);
    }
    
}
