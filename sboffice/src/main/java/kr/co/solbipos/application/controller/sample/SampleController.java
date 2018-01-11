package kr.co.solbipos.application.controller.sample;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

        return "sample/sampleView";
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
        return "sample/sampleView2";
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
    @RequestMapping(value = "samplepop.sb")
    public String samplepop(HttpSession session, Model model) {
        return "sample/pop:samplePop";
    }
    

    /**
      * input 샘플
      * @param session
      * @param model
      * @return
      */
    @RequestMapping(value = "sampleInput.sb")
    public String sampleInput(HttpSession session, Model model) {
        return "sample/sampleInput";
    }
    

    /**
      * input 샘플2
      * @param session
      * @param model
      * @return
      */
    @RequestMapping(value = "sampleInput2.sb")
    public String sampleInput2(HttpSession session, Model model) {
        return "sample/sampleInput2";
    }
    

    /**
     * json sample
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
}
