package kr.co.solbipos.sale.moms.posRcvSaleMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsService;
import kr.co.solbipos.sale.moms.posRcvSaleMoms.service.PosRcvSaleMomsVO;

import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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
 * @Class Name : PosRcvSaleMomsController.java
 * @Description : 맘스터치 > 정산 > POS내역수신(매출)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/moms/posRcvSaleMoms")
public class PosRcvSaleMomsController {

    private final SessionService sessionService;
    private final PosRcvSaleMomsService posRcvSaleMomsService;
    private final StoreChannelService storeChannelService;

    @Autowired
    public PosRcvSaleMomsController(SessionService sessionService, PosRcvSaleMomsService posRcvSaleMomsService, StoreChannelService storeChannelService) {
        this.sessionService = sessionService;
        this.posRcvSaleMomsService = posRcvSaleMomsService;
        this.storeChannelService = storeChannelService;
    }

    /**
     * POS내역수신(매출) - 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String posRcvSaleMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 주문채널 구분자 조회
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        String dlvrInFgColNm = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
            dlvrInFgColNm += (dlvrInFgColNm.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFgNm");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);
        model.addAttribute("dlvrInFgColNm", dlvrInFgColNm);

        return "sale/moms/posRcvSaleMoms/posRcvSaleMoms";
    }

    /**
     * POS내역수신(매출) - 리스트 조회
     *
     * @param posRcvSaleMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2026. 09. 18.
     */
    @RequestMapping(value = "/posRcvSaleMoms/getPosRcvSaleMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosRcvSaleMomsList(PosRcvSaleMomsVO posRcvSaleMomsVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = posRcvSaleMomsService.getPosRcvSaleMomsList(posRcvSaleMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, posRcvSaleMomsVO);
    }
}
