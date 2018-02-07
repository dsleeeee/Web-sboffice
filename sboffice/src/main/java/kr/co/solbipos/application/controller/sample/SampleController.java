package kr.co.solbipos.application.controller.sample;

import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.domain.sample.SslTrhdrT;
import kr.co.solbipos.application.domain.sample.TbMsStore;
import kr.co.solbipos.application.service.sample.SampleService;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.JavaScriptResult;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

/**
 * 샘플 컨트롤러다.
 * 
 * @author 정용길
 */

@Slf4j
@Controller
public class SampleController {
    @Autowired
    Prop prop;

    @Autowired
    SampleService sampleService;

    @Autowired
    MessageService messageService;

    /**
     * 샘플 메인 페이지
     * 
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sample.sb")
    public String sample(HttpSession session, Model model) {

        String encoding = prop.getEncoding();
        log.error("sample...... : {}", encoding);

        String param = "test";

        List<DefaultMap<Object>> temp = sampleService.selectSample(param);

        log.error("result : {}", temp);

        model.addAttribute("data", temp);

        return "application/sample/sampleView";
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
    public JsonResult json(HttpSession session, Model model) {

        List<DefaultMap<Object>> temp = sampleService.selectDdSum();

        Result result = new Result(Status.OK, temp);

        return new JsonResult(result);
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
    public JsonResult sampleInput2Res(TbMsStore tbMsStore, Model model) {
        List<DefaultMap<Object>> temp = sampleService.selectStore(tbMsStore);
        Result result = new Result(Status.OK, temp);
        return new JsonResult(result);
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
         * SessionInfo sessionInfo = sessionService.getSessionInfo(request);
         * 
         * GridDispItem gridDispItem = new GridDispItem();
         * gridDispItem.setUserId(sessionInfo.getUserId());
         * 
         * GridDispItem result = gsService.selectGridItem(gridDispItem);
         * 
         * String test =
         * Optional.ofNullable(result).map(GridDispItem::getColumnItem).orElse("\"empty\"");
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
     * @param sslTrdtlT
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPageJson.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult exGridPage(SslTrdtlT sslTrdtlT, Model model) {

        // 데이터 조회
        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtlTest(sslTrdtlT);

        sslTrdtlT.setTotalCount(data.get(0).getInt("totCnt"));

        return returnListJson(Status.OK, data, sslTrdtlT);
    }

    /**
     * 
     * @param sslTrhdrT
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPageJson2.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult exGridPage2(SslTrhdrT sslTrhdrT, Model model) {

        // 데이터 조회
        List<DefaultMap<Object>> data = sampleService.selectDdlTrhdrTest(sslTrhdrT);

        sslTrhdrT.setTotalCount(data.get(0).getInt("totCnt"));

        return returnListJson(Status.OK, data, sslTrhdrT);
    }

    /**
     * 
     * @param sslTrhdrT
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridPageDtlJson.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult exGridPageDtl(SslTrhdrT sslTrhdrT, Model model) {

        // 데이터 조회
        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtl2Test(sslTrhdrT);

        sslTrhdrT.setTotalCount(data.get(0).getInt("totCnt"));

        return returnListJson(Status.OK, data, sslTrhdrT);
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
    public JsonResult exGridSave(@RequestBody SslTrdtlT[] sslTrdtlT , Model model) {
        
        int size = sslTrdtlT.length;
        
        for (int i = 0; i < size; i++) {
            SslTrdtlT t = sslTrdtlT[i];
            log.info(t.toString());
        }
        
        return returnJson(Status.OK, null);
    }
}
