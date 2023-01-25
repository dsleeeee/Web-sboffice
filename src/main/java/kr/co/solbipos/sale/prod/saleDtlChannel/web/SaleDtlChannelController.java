package kr.co.solbipos.sale.prod.saleDtlChannel.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.saleDtlChannel.service.SaleDtlChannelService;
import kr.co.solbipos.sale.prod.saleDtlChannel.service.SaleDtlChannelVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SaleDtlChannelController.java
 * @Description : 맘스터치 > 상품매출분석 > 매출상세현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.28   권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/saleDtlChannel")
public class SaleDtlChannelController {

    private final SessionService sessionService;
    private final SaleDtlChannelService saleDtlChannelService;
    private final DayProdService dayProdService;
    private final StoreChannelService storeChannelService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleDtlChannelController(SessionService sessionService, SaleDtlChannelService saleDtlChannelService, DayProdService dayProdService, StoreChannelService storeChannelService, CmmCodeUtil cmmCodeUtil){
        this.sessionService = sessionService;
        this.saleDtlChannelService = saleDtlChannelService;
        this.dayProdService = dayProdService;
        this.storeChannelService = storeChannelService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.12.28
     */
    @RequestMapping(value = "/saleDtlChannel/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("momsHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 사용자별 지사 콤보박스 조회
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        model.addAttribute("branchCdComboList", branchCdComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N));

        // 사용자별 코드별 공통코드 콤보박스 조회
        // - 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        model.addAttribute("momsTeamComboList", momsTeamComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N));

        // - AC점포별
        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
        model.addAttribute("momsAcShopComboList", momsAcShopComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N));

        // - 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N));

        // - 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        model.addAttribute("momsCommercialComboList", momsCommercialComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N));

        // - 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N));

        // - 매장관리타입
        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboList.isEmpty() ? comboListAll() : cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N));

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

        return "sale/prod/saleDtlChannel/saleDtlChannelTab";
    }

    /**
     * 매출상세현황(채널별) 조회
     * @param request
     * @param response
     * @param model
     * @param saleDtlChannelVO
     * @return
     * @author  권지현
     * @since   2022.12.28
     */
    @RequestMapping(value = "/saleDtlChannel/getSaleDtlChannelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlChannelList(HttpServletRequest request, HttpServletResponse response, Model model, SaleDtlChannelVO saleDtlChannelVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = saleDtlChannelService.getSaleDtlChannelList(saleDtlChannelVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleDtlChannelVO);
    }

//    /**
//     * 매출상세현황(채널별) 조회(엑셀용)
//     * @param request
//     * @param response
//     * @param model
//     * @param saleDtlChannelVO
//     * @return
//     * @author  권지현
//     * @since   2022.12.28
//     */
//    @RequestMapping(value = "/saleDtlChannel/getSaleDtlChannelExcelList.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getSaleDtlChannelExcelList(HttpServletRequest request, HttpServletResponse response, Model model, SaleDtlChannelVO saleDtlChannelVO) {
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        List<DefaultMap<String>> list = saleDtlChannelService.getSaleDtlChannelExcelList(saleDtlChannelVO, sessionInfoVO);
//
//        return ReturnUtil.returnListJson(Status.OK, list, saleDtlChannelVO);
//    }

    /**
     * 빈 콤보박스 셋팅
     * @return
     */
    public String comboListAll(){

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> m = new HashMap<>();
        m.put("name", "전체");
        m.put("value", "");
        list.add(m);

        return convertToJson(list);
    }

    /**
     * 매출상세현황(채널별) 매출 다운로드 탭 - 조회
     *
     * @param saleDtlChannelVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.01.06
     */
    @RequestMapping(value = "/saleDtlChannelExcel/getSaleDtlChannelExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlChannelList(SaleDtlChannelVO saleDtlChannelVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleDtlChannelService.getSaleDtlChannelExcelList(saleDtlChannelVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleDtlChannelVO);
    }

    /**
     * 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 저장
     *
     * @param saleDtlChannelVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.01.06
     */
    @RequestMapping(value = "/saleDtlChannelExcel/getSaleDtlChannelSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlChannelSave(@RequestBody SaleDtlChannelVO saleDtlChannelVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleDtlChannelService.getSaleDtlChannelSave(saleDtlChannelVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출상세현황(채널별) 매출 다운로드 탭 - 삭제
     *
     * @param saleDtlChannelVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.01.06
     */
    @RequestMapping(value = "/saleDtlChannelExcel/getSaleDtlChannelDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlChannelDel(@RequestBody SaleDtlChannelVO[] saleDtlChannelVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = saleDtlChannelService.getSaleDtlChannelDel(saleDtlChannelVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인
     *
     * @param saleDtlChannelVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.01.06
     */
    @RequestMapping(value = "/saleDtlChannelExcel/getSaleDtlChannelChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlChannelChk(SaleDtlChannelVO saleDtlChannelVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = saleDtlChannelService.getSaleDtlChannelChk(saleDtlChannelVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 매출상세현황(채널별) 매출 다운로드 탭 - 첨부파일 다운로드
     *
     * @param saleDtlChannelVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.01.06
     */
    @RequestMapping(value="/saleDtlChannelExcel/getSaleDtlChannelDownload.sb")
    @ResponseBody
    public void getSaleDtlChannelDownload(SaleDtlChannelVO saleDtlChannelVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) throws Exception {

//        File file = new File("D:\\Workspace\\javaWeb\\testSaleReport\\", saleDtlChannelVO.getFileName());
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "/MediaBase/SaleReport/", saleDtlChannelVO.getFileName());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(saleDtlChannelVO.getFileName(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(saleDtlChannelVO.getFileName().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
