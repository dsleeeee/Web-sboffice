package kr.co.solbipos.dlvr.anals.dlvrInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoService;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoVO;
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
 * @Class Name : DlvrInfoController.java
 * @Description : 배달관리 > 배달분석 > 배달내역
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
@RequestMapping("/dlvr/manage/anals")
public class DlvrInfoController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final SessionService sessionService;
  private final DlvrInfoService dlvrInfoService;
  private final TodayDtlService todayDtlService;

  @Autowired
  public DlvrInfoController(SessionService sessionService, DlvrInfoService dlvrInfoService, TodayDtlService todayDtlService) {
    this.sessionService = sessionService;
    this.dlvrInfoService = dlvrInfoService;
    this.todayDtlService = todayDtlService;
  }


  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/dlvrInfo/list.sb", method = RequestMethod.GET)
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

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
    return "dlvr/anals/dlvrInfo/dlvrInfo";
  }


    /**
   * 배달내역 조회
   *
   * @return
   */
  @RequestMapping(value = "/dlvr/getDlvrInfoList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDlvrInfoList(DlvrInfoVO dlvrInfoVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = dlvrInfoService.getDlvrInfoList(dlvrInfoVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result, dlvrInfoVO);
  }

    /**
   * 배달내역 엑셀다운로드 조회
   *
   * @return
   */
  @RequestMapping(value = "/dlvr/getDlvrInfoExcelList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDlvrInfoExcelList(DlvrInfoVO dlvrInfoVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = dlvrInfoService.getDlvrInfoExcelList(dlvrInfoVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result, dlvrInfoVO);
  }

  /**
   * 영수번호 상세팝업 - 영수증상세조회
   * @param   request
   * @param   response
   * @param   model
   * @param   dlvrInfoVO
   * @return  String
   * @author  김유승
   * @since   2025. 08. 26.
   */
  @RequestMapping(value = "/dlvr/getBillInfo.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getBillInfo(HttpServletRequest request, HttpServletResponse response,
                            Model model, DlvrInfoVO dlvrInfoVO) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    DefaultMap<String> result = dlvrInfoService.getBillInfo(dlvrInfoVO, sessionInfoVO);

    return ReturnUtil.returnJson(Status.OK, result);
  }

  /**
   * 영수증 상세조회
   *
   * @return
   */
  @RequestMapping(value = "/dlvr/getBillInfoList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getBillInfoList(DlvrInfoVO dlvrInfoVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = dlvrInfoService.getBillInfoList(dlvrInfoVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result);
  }

  /**
   * 영수번호 상세팝업 - 영수증상세 결제내역 조회
   * @param   request
   * @param   response
   * @param   model
   * @param   dlvrInfoVO
   * @return  String
   * @author  김유승
   * @since   2025. 08. 26.
   */
  @RequestMapping(value = "/dlvr/billPayInfo.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getBillPayInfo(HttpServletRequest request, HttpServletResponse response,
                               Model model, DlvrInfoVO dlvrInfoVO) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    DefaultMap<String> result = dlvrInfoService.getBillPayInfo(dlvrInfoVO, sessionInfoVO);

    return ReturnUtil.returnJson(Status.OK, result);
  }


  /**
   * 영수번호 상세팝업 - 영수증상세 방문인원 조회
   * @param   request
   * @param   response
   * @param   model
   * @param   dlvrInfoVO
   * @return  String
   * @author  김유승
   * @since   2025. 08. 26.
   */
  @RequestMapping(value = "/dlvr/billGuestInfo.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getBillGuestInfo(HttpServletRequest request, HttpServletResponse response,
                                 Model model, DlvrInfoVO dlvrInfoVO) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    DefaultMap<String> result = dlvrInfoService.getBillGuestInfo(dlvrInfoVO, sessionInfoVO);

    return ReturnUtil.returnJson(Status.OK, result);
  }


  /**
   * 영수번호 상세팝업 - 매출종합 리스트 조회
   * @param   request
   * @param   response
   * @param   model
   * @param   dlvrInfoVO
   * @return  String
   * @author  김유승
   * @since   2025. 08. 26.
   */
  @RequestMapping(value = "/dlvr/billProdList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getBillProdList(HttpServletRequest request, HttpServletResponse response,
                                Model model, DlvrInfoVO dlvrInfoVO) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    List<DefaultMap<String>> list = dlvrInfoService.getBillProdList(dlvrInfoVO, sessionInfoVO);

    return ReturnUtil.returnListJson(Status.OK, list, dlvrInfoVO);
  }

}
