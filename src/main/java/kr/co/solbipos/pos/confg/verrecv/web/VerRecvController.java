package kr.co.solbipos.pos.confg.verrecv.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvService;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

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
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/pos/confg/verRecv/")
public class VerRecvController {

    private final VerRecvService verRecvService;

    /** Constructor Injection */
    @Autowired
    public VerRecvController(VerRecvService verRecvService) {
        this.verRecvService = verRecvService;
    }

    /**
     * 버전별수신현황 - 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verRecv/list.sb", method = RequestMethod.GET)
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
    @RequestMapping(value = "verRecv/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verrecvlist(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verRecvService.selectVerList(verRecv);

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
    @RequestMapping(value = "verRecv/storeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verrecvStoreList(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verRecvService.selectStoreList(verRecv);

        return returnListJson(Status.OK, list, verRecv);
    }

    /**
     * 버전별수신현황 - 매장 리스트 엑셀조회
     * @param verRecv
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "verRecv/storeExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verrecvStoreExcelList(VerRecvVO verRecv, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

//        System.out.println("test11111");
        List<DefaultMap<String>> list = verRecvService.selectStoreExcelList(verRecv);

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
    @RequestMapping(value = "storeRecv/list.sb", method = RequestMethod.GET)
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
    @RequestMapping(value = "storeRecv/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storerecvList(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verRecvService.selectStoreRecvList(verRecv);

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
    @RequestMapping(value = "storeRecv/storeDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result storerecvStoreDtl(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verRecvService.selectStoreDtl(verRecv);

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
    @RequestMapping(value = "verStore/list.sb", method = RequestMethod.GET)
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
    @RequestMapping(value = "verStore/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verstoreList(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verRecvService.selectVerStoreList(verRecv);

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
    @RequestMapping(value = "verStore/storeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verstoreStoreList(VerRecvVO verRecv, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<String>> list = verRecvService.selectVerStoreDtlList(verRecv);

        return returnListJson(Status.OK, list, verRecv);
    }

}
