package kr.co.solbipos.base.prod.dlvrProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.validate.Save;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.dlvrProd.service.DlvrProdService;
import kr.co.solbipos.base.prod.dlvrProd.service.DlvrProdVO;
import kr.co.solbipos.base.prod.prodImg.service.ProdImgVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : DlvrProdController.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 10. 14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/dlvrProd")
public class DlvrProdController {

    private final SessionService sessionService;
    private final DlvrProdService dlvrProdService;

    /** Constructor Injection */
    @Autowired
    public DlvrProdController(SessionService sessionService, DlvrProdService dlvrProdService) {
        this.sessionService = sessionService;
        this.dlvrProdService = dlvrProdService;
    }

    /**
     * 배달시스템 상품 명칭 매핑 - 메인화면
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2020.10.14
     * @return
     */
    @RequestMapping(value = "/dlvrProd/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        DlvrProdVO dlvrProdVO = new DlvrProdVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 배달앱 구분코드 조회
        List<DefaultMap<String>> dlvrColList = dlvrProdService.getDlvrColList(dlvrProdVO, sessionInfoVO);

        // 배달앱 구분코드를 , 로 연결하는 문자열 생성
        String dlvrCol = "";
        for(int i=0; i < dlvrColList.size(); i++) {
            dlvrCol += (dlvrCol.equals("") ? "" : ",") + dlvrColList.get(i).getStr("dlvrCd");
        }
        model.addAttribute("dlvrColList", dlvrColList);
        model.addAttribute("dlvrCol", dlvrCol);

        return "base/prod/dlvrProd/dlvrProdNmMapping";
    }

    /**
     * 배달시스템 상품 명칭 매핑 - 상품목록조회
     *
     * @param dlvrProdVO
     * @param request
     * @author 이다솜
     * @since 2020.10.22
     * @return
     */
    @RequestMapping(value = "/dlvrProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(DlvrProdVO dlvrProdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = dlvrProdService.getProdList(dlvrProdVO, sessionInfoVO);

        return returnListJson(Status.OK, list, dlvrProdVO);
    }

    /**
     * 배달시스템 상품 명칭 매핑 - 배달상품명칭 저장
     *
     * @param dlvrProdVOs
     * @param request
     * @author 이다솜
     * @since 2020.10.22
     * @return
     */
    @RequestMapping(value = "/dlvrProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody DlvrProdVO[] dlvrProdVOs, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = dlvrProdService.saveDlvrProdNm(dlvrProdVOs, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑 - 배달상품명칭 복사
     *
     * @param dlvrProdVO
     * @param request
     * @author 이다솜
     * @since 2021.01.12
     * @return
     */
    @RequestMapping(value = "/dlvrProd/copyDlvrProdNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyDlvrProdNm(@RequestBody DlvrProdVO dlvrProdVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int regCnt = dlvrProdService.copyDlvrProdNm(dlvrProdVO, sessionInfoVO);

        return returnListJson(Status.OK, regCnt);
    }

    /**
     * 배달시스템 상품 명칭 매핑 - 상품명칭 엑셀 업로드 전 상품코드 유효여부 체크
     *
     * @param dlvrProdVO
     * @param request
     * @author 이다솜
     * @since 2021.01.15
     * @return
     */
    @RequestMapping(value = "/dlvrProd/chkDlvrProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkDlvrProd(DlvrProdVO dlvrProdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int getCnt = dlvrProdService.chkDlvrProd(dlvrProdVO, sessionInfoVO);

        return returnJson(Status.OK, getCnt);
    }

}
