package kr.co.solbipos.dlvr.anals.dlvrReceipt.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptService;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : DlvrReceiptController.java
 * @Description : 배달관리 > 배달분석 > 배달지별 내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/dlvr/manage/anals/")
public class DlvrReceiptController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrReceiptService dlvrReceiptService;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final TodayDtlService todayDtlService;

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public DlvrReceiptController(DlvrReceiptService dlvrReceiptService, SessionService sessionService, MessageService messageService,
                                 TodayDtlService todayDtlService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.dlvrReceiptService = dlvrReceiptService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.todayDtlService = todayDtlService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 배달지별 내역 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dlvrZone/list.sb", method = RequestMethod.GET)
    public String selectList(HttpServletRequest request, HttpServletResponse response, Model model) {
        TodayDtlVO todayDtlVO = new TodayDtlVO();

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = todayDtlService.getPayColList(todayDtlVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");

            // 맘스터치 결제수단 '식권' -> '식권대장' 으로 변경표기 (20240909)
            if(sessionInfoVO.getHqOfficeCd().equals("DS021") || sessionInfoVO.getHqOfficeCd().equals("DS034") || sessionInfoVO.getHqOfficeCd().equals("H0393")){
                if(payColList.get(i).getStr("payCd").equals("14")){
                    payColList.get(i).put("payNm", "식권대장");
                }
            }
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = todayDtlService.getDcColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 객수 조회
        List<DefaultMap<String>> guestColList = todayDtlService.getGuestColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);
        return "dlvr/anals/dlvrReceipt/dlvrReceipt";
    }

    /**
     * 회원정보 리스트 조회
     *
     * @param dlvrReceiptVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dlvrZone/getDlvrReceiptList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dlvrReceiptService.getDlvrReceiptList(dlvrReceiptVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrReceiptVO);
    }

    /**
     * 회원정보 리스트 조회
     *
     * @param dlvrReceiptVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dlvrZone/getDlvrReceiptDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> result = dlvrReceiptService.getDlvrReceiptDetailList(dlvrReceiptVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, result, dlvrReceiptVO);
    }
}
