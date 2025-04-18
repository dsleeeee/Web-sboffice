package kr.co.sample.application.controller;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.JavaScriptResult;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.sample.application.domain.SslTrdtlTVO;
import kr.co.sample.application.domain.SslTrhdrTVO;
import kr.co.sample.application.domain.TbMsStoreVO;
import kr.co.sample.application.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * 샘플 컨트롤러다.
 *
 * @author 정용길
 */

@Controller
public class SampleController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    private final SampleService sampleService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public SampleController(SampleService sampleService, MessageService messageService) {
        this.sampleService = sampleService;
        this.messageService = messageService;
    }

    /**
     * 샘플 메인 페이지
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sample.sb")
    public String sample(HttpSession session, Model model) {

        String encoding = BaseEnv.ENCODING;
        LOGGER.error("sample...... : {}", encoding);

        String param = "test";
        /* m2m개발용 임시 주석 
        List<DefaultMap<Object>> temp = sampleService.selectSample(param);
        LOGGER.error("result : {}", temp);
        model.addAttribute("data", temp);
        return "application/sample/sampleView"; 	
 */
        return "application/sample/sampleViewM2mDev";
    }

    // m2m 개발을 위한 샘플 페이지
    @RequestMapping(value = "sample10.sb")
    public String sample10(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mCss";
    }         
    @RequestMapping(value = "sample11.sb")
    public String sample11(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mLayout";
    }     
    @RequestMapping(value = "sample12.sb")
    public String sample12(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mForm";
    }
    @RequestMapping(value = "sample13.sb")
    public String sample13(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mBtn";
    }
    @RequestMapping(value = "sample14.sb")
    public String sample14(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mLyTab";
    }
    
    @RequestMapping(value = "sample20.sb")
    public String sample20(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mMonCal";
    }     
    @RequestMapping(value = "sample21.sb")
    public String sample21(HttpSession session, Model model) {
        return "application/sample/sampleViewM2mAgt";
    }  
    @RequestMapping(value = "sample22.sb")
    public String sample22(HttpSession session, Model model) {
    	return "application/sample/sampleViewM2mPsh";
    }  
    @RequestMapping(value = "sample23.sb")
    public String sample23(HttpSession session, Model model) {
    	return "application/sample/sampleViewM2mTouch";
    }  
    
    // m2mglobal 샘플 페이지 - tonymory
    @RequestMapping(value = "sample40.sb")
    public String sample40(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm01";
    }     
    @RequestMapping(value = "sample41.sb")
    public String sample41(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm02";
    }  
    @RequestMapping(value = "sample42.sb")
    public String sample42(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm03";
    }  
    @RequestMapping(value = "sample43.sb")
    public String sample43(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm04";
    }  
    @RequestMapping(value = "sample44.sb")
    public String sample44(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm05";
    }     
    @RequestMapping(value = "sample45.sb")
    public String sample45(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm06";
    }  
    @RequestMapping(value = "sample46.sb")
    public String sample46(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm07";
    }  
    @RequestMapping(value = "sample47.sb")
    public String sample47(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm08";
    }  
    @RequestMapping(value = "sample48.sb")
    public String sample48(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm09";
    }     
    @RequestMapping(value = "sample49.sb")
    public String sample49(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm10";
    }  
    @RequestMapping(value = "sample50.sb")
    public String sample50(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm11";
    }  
    @RequestMapping(value = "sample51.sb")
    public String sample51(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm12";
    }  
    
    /**
     * json sample
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "samplejson.sb")
    @ResponseBody
    public Result json(HttpSession session, Model model) {

        List<DefaultMap<Object>> temp = sampleService.selectDdSum();

        return new Result(Status.OK, temp);
    }

    /**
     * 샘플 페이지 2
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sample2.sb")
    public String sample2(HttpSession session, Model model) {
        return "application/sample/sampleView2";
    }

    /**
     * 메세지, 페이지 이동 샘플
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sample3.sb")
    public Object sample3(HttpSession session, Model model) {

        String msg = messageService.get("label.insertOk");

        return new JavaScriptResult(
                String.format("alert('%s'); location.href=\"sample2.sb\"", msg));
    }



    /**
     * 팝업 샘플
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "samplepop2.sb")
    public String samplepop2(HttpSession session, Model model) {
        return "application/pop:samplePop2";
    }

    @RequestMapping(value = "samplepop3.sb")
    public String samplepop3(HttpSession session, Model model) {
        return "application/sample/pop:samplePop3";
    }

    @RequestMapping(value = "samplepop4.sb")
    public String samplepop4(HttpSession session, Model model) {
        return "application/sample/sample/pop:samplePop4";
    }



    /**
     * input 샘플
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sampleInput.sb")
    public String sampleInput(HttpSession session, Model model) {
        return "application/sample/sampleInput";
    }


    /**
     * input 샘플2
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sampleInput2.sb")
    public String sampleInput2(HttpSession session, Model model) {
        return "application/sample/sampleInput2";
    }


    /**
     * json sample
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sampleInput2Res.sb")
    @ResponseBody
    public Result sampleInput2Res(TbMsStoreVO tbMsStoreVO, Model model) {
        List<DefaultMap<Object>> temp = sampleService.selectStore(tbMsStoreVO);
        return new Result(Status.OK, temp);
    }


    /**
     *
     * 그리드 관련 테스트 페이지
     *
     */

    /**
     * 그리드 페이징 샘플
     *
     * @param rnum
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPage.sb", method = RequestMethod.GET)
    public String exGridPage(HttpServletRequest request, Model model) {
        /*
         * SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
         *
         * GridDispItemVO gridDispItemVO = new GridDispItemVO();
         * gridDispItemVO.setUserId(sessionInfoVO.getUserId());
         *
         * gridDispItemVO result = gsService.selectGridItem(gridDispItemVO);
         *
         * String test =
         * Optional.ofNullable(result).map(GridDispItemVO::getColumnItem).orElse("\"empty\"");
         *
         * model.addAttribute("columnLayout", test);
         */
        return "application/sampleWijmo/exGridPage";
    }

    /**
     * 그리드 페이징 샘플
     *
     * @param rnum
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPage2.sb", method = RequestMethod.GET)
    public String exGridPage2(HttpServletRequest request, Model model) {
        return "application/sampleWijmo/exGridPage2";
    }

    /**
     *
     * @param sslTrdtlTVO
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPageJson.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result exGridPage(SslTrdtlTVO sslTrdtlTVO, Model model) {

        // 데이터 조회
        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtlTest(sslTrdtlTVO);

        sslTrdtlTVO.setTotalCount(data.get(0).getInt("totCnt"));

        return returnListJson(Status.OK, data, sslTrdtlTVO);
    }

    /**
     *
     * @param sslTrhdrTVO
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPageJson2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result exGridPage2(SslTrhdrTVO sslTrhdrTVO, Model model) {

        // 데이터 조회
        List<DefaultMap<Object>> data = sampleService.selectDdlTrhdrTest(sslTrhdrTVO);

        sslTrhdrTVO.setTotalCount(data.get(0).getInt("totCnt"));

        return returnListJson(Status.OK, data, sslTrhdrTVO);
    }

    /**
     *
     * @param sslTrhdrTVO
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPageDtlJson.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result exGridPageDtl(SslTrhdrTVO sslTrhdrTVO, Model model) {

        // 데이터 조회
        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtl2Test(sslTrhdrTVO);

        sslTrhdrTVO.setTotalCount(data.get(0).getInt("totCnt"));

        return returnListJson(Status.OK, data, sslTrhdrTVO);
    }

    /**
     * 그리드 본사 매장 그룹핑 샘플
     *
     * @param rnum
     * @param model
     * @return
     */
    @RequestMapping(value = "groupGridSample.sb")
    public String groupGridSample(Model model) {
        List<DefaultMap<Object>> data = sampleService.getgroupGridSample();
        model.addAttribute("shopList", convertToJson(data));
        return "application/sampleWijmo/groupGridSample";
    }

    /**
     * 그리드 본사 매장 그룹핑 샘플
     *
     * @param rnum
     * @param model
     * @return
     */
    @RequestMapping(value = "groupGridSample2.sb")
    public String groupGridSample2(Model model) {
        List<DefaultMap<Object>> data = sampleService.getgroupGridSample();
        model.addAttribute("shopList", convertToJson(data));
        return "application/sampleWijmo/groupGridSample2";
    }

    @RequestMapping(value = "exGridSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result exGridSave(@RequestBody SslTrdtlTVO[] sslTrdtlTVO , Model model) {

        int size = sslTrdtlTVO.length;

        for (int i = 0; i < size; i++) {
            SslTrdtlTVO t = sslTrdtlTVO[i];
            LOGGER.info(t.toString());
        }

        return returnJson(Status.OK, null);
    }
}
