package kr.co.sample.application.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 에디터샘플
 * @author 김지은
 */

@Controller
public class TuiEditorSampleController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SampleService  sampleService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public TuiEditorSampleController(SampleService sampleService, MessageService messageService) {
        this.sampleService = sampleService;
        this.messageService = messageService;
    }

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

        LOGGER.error("seq : "+ tmpBoardTVO.getBoardSeqNo());
      LOGGER.error("title : "+ tmpBoardTVO.getBoardTitle());
      LOGGER.error("html : "+ tmpBoardTVO.getBoardContents());

      //String boardContents = tmpBoardTVO.getBoardContents();
      //tmpBoardTVO.setBoardContents(boardContents.replace("\n","<br>"));
      if(tmpBoardTVO.getBoardSeqNo().equals("")) {

          LOGGER.error("111111");
        sampleService.insertBoardSample(tmpBoardTVO);
      } else {
          LOGGER.error("222222");
        sampleService.updateBoardSample(tmpBoardTVO);
      }

      return new Result(Status.OK, tmpBoardTVO);
    }

    @RequestMapping(value = "editorSample2.sb")
    public String eidtorSample2(HttpSession session, Model model) {

      TmpBoardTVO tmpBoardTVO = sampleService.getRecentBoardData();

      String boardContents = tmpBoardTVO.getBoardContents();

      LOGGER.error("boardContents1 :" + boardContents);
      LOGGER.error("boardContents2 :" + boardContents.replace("\n","<br>"));
      LOGGER.error("boardContents3 :" + boardContents.replace("\"", "'"));

      tmpBoardTVO.setBoardContents(boardContents.replace("\n","<br>").replace("\"", "'"));

      model.addAttribute("boardData", tmpBoardTVO);

      return "application/tuiEditor/editorSample2";
    }

    @RequestMapping(value = "editorSample3.sb")
    public String eidtorSample3(HttpSession session, Model model) {

      TmpBoardTVO tmpBoardTVO = sampleService.getRecentBoardData();

      String boardContents = tmpBoardTVO.getBoardContents();

      LOGGER.error("boardContents1 :" + boardContents);
      LOGGER.error("boardContents2 :" + boardContents.replace("\n","<br>"));
      LOGGER.error("boardContents3 :" + boardContents.replace("\"", "'"));

      tmpBoardTVO.setBoardContents(boardContents.replace("\n","<br>").replace("\"", "'"));

      model.addAttribute("boardData", tmpBoardTVO);

      return "application/tuiEditor/editorSample3";
    }
}

