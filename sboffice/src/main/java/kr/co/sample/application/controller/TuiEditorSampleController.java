package kr.co.sample.application.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.sample.application.domain.TmpBoardTVO;
import kr.co.sample.application.service.SampleService;
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
    public Result editorSampleSave(HttpSession sessions, Model model, @RequestBody TmpBoardTVO tmpBoardTVO) {

      log.error("seq : "+ tmpBoardTVO.getBoardSeqNo());
      log.error("title : "+ tmpBoardTVO.getBoardTitle());
      log.error("html : "+ tmpBoardTVO.getBoardContents());

      //String boardContents = tmpBoardTVO.getBoardContents();
      //tmpBoardTVO.setBoardContents(boardContents.replace("\n","<br>"));
      if(tmpBoardTVO.getBoardSeqNo().equals("")) {

        log.error("111111");
        sampleService.insertBoardSample(tmpBoardTVO);
      } else {
        log.error("222222");
        sampleService.updateBoardSample(tmpBoardTVO);
      }

      return new Result(Status.OK, tmpBoardTVO);
    }

    @RequestMapping(value = "editorSample2.sb")
    public String eidtorSample2(HttpSession session, Model model) {

      TmpBoardTVO tmpBoardTVO = sampleService.getRecentBoardData();

      String boardContents = tmpBoardTVO.getBoardContents();

      log.error("boardContents1 :" + boardContents);
      log.error("boardContents2 :" + boardContents.replace("\n","<br>"));
      log.error("boardContents3 :" + boardContents.replace("\"", "'"));

      tmpBoardTVO.setBoardContents(boardContents.replace("\n","<br>").replace("\"", "'"));

      model.addAttribute("boardData", tmpBoardTVO);

      return "application/tuiEditor/editorSample2";
    }

    @RequestMapping(value = "editorSample3.sb")
    public String eidtorSample3(HttpSession session, Model model) {

      TmpBoardTVO tmpBoardTVO = sampleService.getRecentBoardData();

      String boardContents = tmpBoardTVO.getBoardContents();

      log.error("boardContents1 :" + boardContents);
      log.error("boardContents2 :" + boardContents.replace("\n","<br>"));
      log.error("boardContents3 :" + boardContents.replace("\"", "'"));

      tmpBoardTVO.setBoardContents(boardContents.replace("\n","<br>").replace("\"", "'"));

      model.addAttribute("boardData", tmpBoardTVO);

      return "application/tuiEditor/editorSample3";
    }
}

