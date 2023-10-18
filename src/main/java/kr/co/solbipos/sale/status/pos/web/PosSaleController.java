package kr.co.solbipos.sale.status.pos.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.status.pos.service.PosSaleService;
import kr.co.solbipos.sale.status.pos.service.PosSaleVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.common.service.message.MessageService;

import java.util.List;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 포스별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.21  이승규      최초생성
 *
 * @author 엠투엠글로벌 이승규
 * @since 2020.01.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/pos")
public class PosSaleController {
    private final SessionService sessionService;
    private final PosSaleService posSaleService;
    private final MessageService messageService;
    private final DayService dayService;

    @Autowired
    public PosSaleController(SessionService sessionService, PosSaleService posSaleService, MessageService messageService, DayService dayService) {
        this.sessionService = sessionService;
        this.posSaleService = posSaleService;
        this.messageService = messageService;
        this.dayService = dayService;
    }


    /**
     * 포스별매출 일자별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
    public String posDayView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = dayService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
            timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

        return "sale/status/pos/posSale";
    }


    /**
     * 포스별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/getPosDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayList(HttpServletRequest request, HttpServletResponse response,
                                Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            //throw new JsonException(Status.FAIL, messageService.get("prodsale.day.require.selectStore"));
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosDayList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posDayVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }

    /**
     * 포스별매출 일자별 - 리스트 조회(엑셀)
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/day/getPosDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayExcelList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosDayExcelList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posDayVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }


    /**
     * 포스별매출 일자별 - 포스 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "pos/posNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosNmList(HttpServletRequest request, HttpServletResponse response,
                               Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");

            if (arrPosNo.length > 0) {
                if (arrPosNo[0] != null && !"".equals(arrPosNo[0])) {
                    posSaleVO.setArrPosNo(arrPosNo);
                }
            }
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");
            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }
        }

        List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
    }

    /**
     * 포스별매출 요일별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/dayOfWeek/getPosDayOfWeekList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayOfWeekList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosDayOfWeekList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posDayOfWeekVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }

    /**
     * 포스별매출 요일별 - 차트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/dayOfWeek/getPosDayOfWeekChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayOfWeekChartList(HttpServletRequest request, HttpServletResponse response,
                                           Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosDayOfWeekChartList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posDayOfWeekVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }

    /**
     * 포스별매출 월별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/month/getPosMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosMonthList(HttpServletRequest request, HttpServletResponse response,
                                  Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosMonthList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posMonthVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }


    /**
     * 포스별매출 월별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/month/getPosMonthExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosMonthExcelList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosMonthExcelList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posMonthVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }

    /**
     * 상품별탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/prod/getPosProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosProdList(HttpServletRequest request, HttpServletResponse response,
                                 Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);
            if (list.size() > 0) {
                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);
            }
        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosProdList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posProdVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }

    /**
     * 상품별탭 - 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  이승규
     * @since   2020. 01. 21.
     */
    @RequestMapping(value = "/prod/getPosProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosProdExcelList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);
            if (list.size() > 0) {
                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);
            }
        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosProdExcelList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posProdVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }

    /**
     * 설정기간별탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayPeriod/getPosDayPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodList(HttpServletRequest request, HttpServletResponse response,
                                      Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posSaleService.getPosDayPeriodList(posSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
    }

    /**
     * 설정기간별탭 - 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayPeriod/getPosDayPeriodExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodExcelList(HttpServletRequest request, HttpServletResponse response,
                                           Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posSaleService.getPosDayPeriodExcelList(posSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
    }

    /**
     * 설정기간별탭 - 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayPeriod/getPosDayPeriodDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodDtlList(HttpServletRequest request, HttpServletResponse response,
                                         Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posSaleService.getPosDayPeriodDtlList(posSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
    }

    /**
     * 설정기간별탭 - 상세 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayPeriod/getPosDayPeriodDtlExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodDtlExcelList(HttpServletRequest request, HttpServletResponse response,
                                              Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posSaleService.getPosDayPeriodDtlExcelList(posSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
    }

    /**
     * 포스별매출 시간대별별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   posSaleVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/hour/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosHourList(HttpServletRequest request, HttpServletResponse response,
                                Model model, PosSaleVO posSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (posSaleVO.getPosNo() != null && !"".equals(posSaleVO.getPosNo())) {
            String[] arrPosNo = posSaleVO.getPosNo().split(",");
            posSaleVO.setArrPosNo(arrPosNo);
            posSaleVO.setArrStorePos(arrPosNo);
        } else {
            String[] arrStoreCd = posSaleVO.getStoreCd().split(",");

            if (arrStoreCd.length > 0) {
                if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
                    posSaleVO.setArrStoreCd(arrStoreCd);
                }
            }

            List<DefaultMap<String>> list = posSaleService.getPosNmList(posSaleVO, sessionInfoVO);

            if (list.size() > 0) {

                String arrStorePos[] = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    DefaultMap<String> map = list.get(i);
                    String storePos = map.getStr("posCd");
                    arrStorePos[i] = storePos;
                }

                posSaleVO.setArrStorePos(arrStorePos);

            }

        }

        if (posSaleVO.getArrStorePos() == null) {
            return ReturnUtil.returnListJson(Status.OK, null, posSaleVO);
        } else {
            List<DefaultMap<String>> list = posSaleService.getPosHourList(posSaleVO, sessionInfoVO);
            //System.out.println("list.size() :: "+posDayVO.getArrPosCd().length);
            return ReturnUtil.returnListJson(Status.OK, list, posSaleVO);
        }
    }
}
