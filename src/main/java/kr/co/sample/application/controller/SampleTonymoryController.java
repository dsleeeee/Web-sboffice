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
 * 토니모리 샘플 컨트롤러
 *
 * @author 
 */

@Controller
@RequestMapping("/sample/tonymory")
public class SampleTonymoryController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());    

    /**
     * 샘플 메인 페이지
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/Login.sb")
    public String sampleTonyMoryLogin(HttpSession session, Model model) {

        String encoding = BaseEnv.ENCODING;
        LOGGER.error("sample...... : {}", encoding);

        String param = "test";
        /* m2m개발용 임시 주석 
        List<DefaultMap<Object>> temp = sampleService.selectSample(param);
        LOGGER.error("result : {}", temp);
        model.addAttribute("data", temp);
        return "application/sample/sampleView"; 	
 */
        return "application/sample/tonymory/sampleViewTm01";
    }
    
    // m2mglobal 샘플 페이지 - tonymory
    @RequestMapping(value = "/sample40.sb")
    public String sample40(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm01";
    }     
    @RequestMapping(value = "/sample41.sb")
    public String sample41(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm02";
    }  
    @RequestMapping(value = "/sample42.sb")
    public String sample42(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm03";
    }  
    @RequestMapping(value = "/sample43.sb")
    public String sample43(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm04";
    }  
    @RequestMapping(value = "/sample44.sb")
    public String sample44(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm05";
    }     
    @RequestMapping(value = "/sample45.sb")
    public String sample45(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm06";
    }  
    @RequestMapping(value = "/sample46.sb")
    public String sample46(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm07";
    }  
    @RequestMapping(value = "/sample47.sb")
    public String sample47(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm08";
    }  
    @RequestMapping(value = "/sample48.sb")
    public String sample48(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm09";
    }     
    @RequestMapping(value = "/sample49.sb")
    public String sample49(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm10";
    }  
    @RequestMapping(value = "/sample50.sb")
    public String sample50(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm11";
    }  
    @RequestMapping(value = "/sample51.sb")
    public String sample51(HttpSession session, Model model) {
    	return "application/sample/tonymory/sampleViewTm12";
    }  
    
    
}
