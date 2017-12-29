package kr.co.solbipos.application.controller.sample;

import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.sample.CommonCode;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.service.sample.SampleService;
import kr.co.solbipos.service.grid.GridSupportService;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.structure.Result.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * 샘플 컨트롤러다.
 * 
 * @author 정용길
 */

@Slf4j
@Controller
public class SampleWijimoController {

    @Autowired
    SampleService sampleService;

    @Autowired
    MessageService messageService;

    @Autowired
    GridSupportService gsService;

    @RequestMapping(value = "sampleGridMain.sb")
    public String sampleGridMain(HttpSession session, Model model) {
        return "sampleWijmo/sampleGridMain";
    }

    /**
     * 그리드 샘플
     * 
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "sampleGrid.sb")
    public String sample4(HttpSession session, Model model) {
        List<DefaultMap<Object>> temp = sampleService.selectDdSum();
        model.addAttribute("data", temp);
        return "sampleWijmo/sampleGrid";
    }

    /**
     * 그냥 테스트용
     * 
     * @param rnum
     * @param model
     * @return
     */
    @RequestMapping(value = "sampleGrid2.sb")
    public String sample5(Integer rnum, Model model) {
        model.addAttribute("data", getDdlTrdtlT(rnum).toString());
        return "sampleWijmo/sampleGrid2";
    }

    /**
     * 그리드 헤더 번역용...
     * 
     * @param rnum
     * @param model
     * @return
     */
    @RequestMapping(value = "exGridHeader.sb")
    public String exGridHeader(Integer rnum, Model model) {

        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtlT(rnum);

        // List<String> columns = Arrays.asList("dcmSaleAmt", "prodCd", "prmProcYn");

        model.addAttribute("columnList", convertToJson(gsService.getGridColumns(data.get(0))));
        model.addAttribute("data", convertToJson(data));

        return "sampleWijmo/exGridHeader";
    }

    public String getDdlTrdtlT(Integer rnum) {
        Integer param = Optional.ofNullable(rnum).orElse(100);
        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtlT(param);
        return convertToJson(data);
    }



    @RequestMapping(value = "cmmcode.sb")
    public JsonResult cmmcode(Model model) {

        List<DefaultMap<Object>> temp = sampleService.selectCommonCodeList("001");

        Result result = new Result(Status.OK, temp);

        return new JsonResult(result);
    }


    /**
     * 위즈모 input 샘플
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "exInput.sb")
    public String exInput(Model model) {
        return "sampleWijmo/exInput";
    }

    /**
     * 위즈모 트리 샘플
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "exTree.sb")
    public String exTree(Model model) {
        return "sampleWijmo/exTree";
    }

}


