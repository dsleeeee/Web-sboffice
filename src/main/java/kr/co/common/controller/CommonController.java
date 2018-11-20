package kr.co.common.controller;

import kr.co.common.data.domain.CustomComboVO;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.code.CmmCodeService;
import kr.co.common.service.message.MessageResolveService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : CommonController.java
 * @Description : 공통 컨트롤러
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.30  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {

    private final SessionService sessionService;
    @Qualifier("messageResolveService")
    private final MessageResolveService messageResolveService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmCodeService cmmCodeService;

    /** Constructor Injection */
    @Autowired
    public CommonController(SessionService sessionService, MessageResolveService messageResolveService,
        CmmCodeUtil cmmCodeUtil, CmmCodeService cmmCodeService) {
        this.sessionService = sessionService;
        this.messageResolveService = messageResolveService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmCodeService = cmmCodeService;
    }


    /**
     * 주소 검색 : juso.go.kr API 연동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return void
     * @author 노현수
     * @since 2018. 10. 30.
     */
    @RequestMapping(value="/getAddress.do")
    @ResponseBody
    public Result getAddress(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 요청변수설정
//        String currentPage = request.getParameter("currentPage");
//        String countPerPage = request.getParameter("countPerPage");
//        String keyword = request.getParameter("keyword2");
        String currentPage = "1";
        String countPerPage = "10";
        String resultType = "json";
        String confmKey = "U01TX0FVVEgyMDE4MTAzMDE2MzI1NDEwODI2Nzg=";
        String keyword = "디지털로31길";
        // API 호출URL 정보설정
        String apiUrl= "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+
            "&countPerPage="+countPerPage+
            "&keyword="+ URLEncoder.encode(keyword,"UTF-8")+
            "&confmKey="+confmKey+"&resultType="+resultType;

        URL url = new URL(apiUrl);
        String line = "";
        String data="";
        BufferedReader br = new BufferedReader(new InputStreamReader( url.openStream(),
            StandardCharsets.UTF_8));

        //버퍼에 있는 정보를 하나의 문자열로 변환.
        while ((line = br.readLine()) != null) {
            data = data.concat(line);
            System.out.println("################################ " + data);  // 받아온 데이터를 확인해봅니다.
        }

        return returnJson(Status.OK, data);

//        StringBuffer sb = new StringBuffer();
//        String tempStr = null;
//
//        while(true){
//            tempStr = br.readLine();
//            if(tempStr == null) break;
//            sb.append(tempStr); // 응답결과XML 저장
//        }
//        br.close();
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/xml");
//        response.getWriter().write(sb.toString()); // 응답결과반환

    }

    /**
     * 공통코드 목록 조회
     *
     * @param   request HttpServletRequest
     * @param   response HttpServletResponse
     * @param   model Model
     * @return  Result
     * @author  노현수
     * @since   2018. 11. 12.
     */
    @RequestMapping(value = "/getComboData.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getComboData(HttpServletRequest request, HttpServletResponse response, Model model) {

        String result = "";
        String comCdFg = request.getParameter("code");
        String comboType = CmmUtil.nvl(request.getParameter("type"), "");

        // 콤보데이터 조회
        if ( "".equals(comboType) ) {
            // 코드값만
            result = cmmCodeUtil.getCommCodeExcpAll(comCdFg);
        } else if ( "S".equals(comboType) ) {
            // "선택" 포함
            result = cmmCodeUtil.getCommCodeSelect(comCdFg);
        } else if ( "A".equals(comboType) ) {
            // "전체" 포함
            result = cmmCodeUtil.getCommCode(comCdFg);
        }

        return returnJson(Status.OK, result);
    }


    /**
     * 커스텀 콤보 데이터 조회
     *
     * @param   customComboVO CustomComboVO
     * @param   request HttpServletRequest
     * @param   response HttpServletResponse
     * @param   model model
     * @return  String
     * @author  노현수
     * @since   2018. 11. 15.
     */
    @RequestMapping(value = "/getCustomCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCustomCombo(CustomComboVO customComboVO, HttpServletRequest request, HttpServletResponse response,
        Model model) {

        List<CustomComboVO> result = new ArrayList<CustomComboVO>();
        CustomComboVO value = new CustomComboVO();

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 콤보 타입에 따른 조회
        // "선택" 포함
        if ( "S".equals(customComboVO.getType()) ) {
            value.setValue("");
            value.setName("선택");
            result.add(value);
        // "전체" 포함
        } else if ( "A".equals(customComboVO.getType()) ) {
            value.setValue("");
            value.setName("전체");
            result.add(value);
        }
        // 코드조회
        List<CustomComboVO> list = cmmCodeService.getCustomCombo(customComboVO, sessionInfoVO);
        if ( list.size() < 1 ) {
            value = new CustomComboVO();
            value.setValue("");
            value.setName("데이터가 없습니다.");
            result.add(value);
        } else {
            result.addAll(list);
        }

        return returnJson(Status.OK, result);
    }

}
