package kr.co.solbipos.application.controller.sample;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.sample.TmpBoardT;
import kr.co.solbipos.application.service.sample.SampleService;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.structure.Result.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * 에디터샘플
 * @author 김지은
 */

@Slf4j
@Controller
public class TuiEditorSampleController {

    @Autowired
    SampleService  sampleService;

    @Autowired
    MessageService messageService;
    

    @RequestMapping(value = "editorSampleMain.sb")
    public String editorSampleMain(HttpSession session, Model model) {
        return "application/tuiEditor/editorSampleMain";
    }
    
    
    @RequestMapping(value = "editorSample.sb")
    public String eidtorSample(HttpSession session, Model model) {
        return "application/tuiEditor/editorSample";
    }
    
    
    @RequestMapping(value = "editorSampleSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult editorSampleSave(HttpSession sessions, Model model, @RequestBody TmpBoardT tmpBoardT) {

      log.error("seq : "+ tmpBoardT.getBoardSeqNo());
      log.error("title : "+ tmpBoardT.getBoardTitle());
      log.error("html : "+ tmpBoardT.getBoardContents());
      
      //String boardContents = tmpBoardT.getBoardContents();
      //tmpBoardT.setBoardContents(boardContents.replace("\n","<br>"));
      if(tmpBoardT.getBoardSeqNo().equals("")) {
        
        log.error("111111");
        sampleService.insertBoardSample(tmpBoardT);
      } else {
        log.error("222222");
        sampleService.updateBoardSample(tmpBoardT);
      }
      
      Result result = new Result(Status.OK, tmpBoardT);
      
      return new JsonResult(result);
    }
    
    @RequestMapping(value = "editorSample2.sb")
    public String eidtorSample2(HttpSession session, Model model) {
      
      TmpBoardT tmpBoardT = sampleService.getRecentBoardData();
      
      String boardContents = tmpBoardT.getBoardContents();
      
      log.error("boardContents1 :" + boardContents);
      log.error("boardContents2 :" + boardContents.replace("\n","<br>"));
      log.error("boardContents3 :" + boardContents.replace("\"", "'"));

      tmpBoardT.setBoardContents(boardContents.replace("\n","<br>").replace("\"", "'"));
      
      model.addAttribute("boardData", tmpBoardT);
      
      return "application/tuiEditor/editorSample2";
    }
    
    @RequestMapping(value = "editorSample3.sb")
    public String eidtorSample3(HttpSession session, Model model) {
      
      TmpBoardT tmpBoardT = sampleService.getRecentBoardData();
      
      String boardContents = tmpBoardT.getBoardContents();

      log.error("boardContents1 :" + boardContents);
      log.error("boardContents2 :" + boardContents.replace("\n","<br>"));
      log.error("boardContents3 :" + boardContents.replace("\"", "'"));

      tmpBoardT.setBoardContents(boardContents.replace("\n","<br>").replace("\"", "'"));
      
      model.addAttribute("boardData", tmpBoardT);
      
      return "application/tuiEditor/editorSample3";
    }
}

