package kr.co.solbipos.base.prod.artiseeProdMapping.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.ArtiseeProdMappingService;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.ArtiseeProdMappingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : ArtiseeProdMappingController.java
 * @Description : 보나비 - 상품관리 - 아티제상품코드맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.27  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/artiseeProdMapping")
public class ArtiseeProdMappingController {

    private final SessionService sessionService;
    private final ArtiseeProdMappingService artiseeProdMappingService;

    @Autowired
    public ArtiseeProdMappingController(SessionService sessionService, ArtiseeProdMappingService artiseeProdMappingService) {
        this.sessionService = sessionService;
        this.artiseeProdMappingService = artiseeProdMappingService;
    }

    /**
     * 아티제상품코드맵핑 메인화면
     *
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2024.09.27
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/prod/artiseeProdMapping/artiseeProdMapping";
    }

    /**
     * 아티제상품코드맵핑 매핑정보 조회
     *
     * @param   artiseeProdMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2024.09.27
     * @return
     */
    @RequestMapping(value = "/artiseeProdMapping/getMapStrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMapStrList(ArtiseeProdMappingVO artiseeProdMappingVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseeProdMappingService.getMapStrList(artiseeProdMappingVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseeProdMappingVO);
    }

    /**
     * 아티제상품코드맵핑 상품정보 조회
     *
     * @param   artiseeProdMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2024.09.30
     * @return
     */
    @RequestMapping(value = "/artiseeProdMapping/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ArtiseeProdMappingVO artiseeProdMappingVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseeProdMappingService.getProdList(artiseeProdMappingVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseeProdMappingVO);
    }

    /**
     *  매핑정보 - 삭제
     *
     * @param   artiseeProdMappingVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024.09.30
     */
    @RequestMapping(value = "/artiseeProdMapping/getDeleteMappingProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteMappingProd(@RequestBody ArtiseeProdMappingVO[] artiseeProdMappingVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = artiseeProdMappingService.getDeleteMappingProd(artiseeProdMappingVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     *  상품정보 - 저장
     *
     * @param   artiseeProdMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024.09.30
     */
    @RequestMapping(value = "/artiseeProdMapping/getRegProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegProd(@RequestBody ArtiseeProdMappingVO artiseeProdMappingVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = artiseeProdMappingService.getRegProd(artiseeProdMappingVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 아티제상품코드맵핑 상품정보 엑셀다운로드
     *
     * @param   artiseeProdMappingVO
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2024.09.27
     * @return
     */
    @RequestMapping(value = "/artiseeProdMapping/getProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdExcelList(ArtiseeProdMappingVO artiseeProdMappingVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseeProdMappingService.getProdExcelList(artiseeProdMappingVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseeProdMappingVO);
    }
}
