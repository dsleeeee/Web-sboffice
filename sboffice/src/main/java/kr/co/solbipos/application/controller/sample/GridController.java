package kr.co.solbipos.application.controller.sample;

import static kr.co.solbipos.utils.spring.StringUtil.convertToJson;
import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static org.springframework.util.ObjectUtils.*;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.cmm.GridDispItem;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.service.sample.SampleService;
import kr.co.solbipos.service.grid.GridSupportService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * 샘플 컨트롤러다.
 * 
 * @author 정용길
 */

@Slf4j
@Controller
public class GridController {

    @Autowired
    SampleService sampleService;

    @Autowired
    GridSupportService gsService;
    
    @Autowired
    SessionService sessionService;
    
    /**
      * 그리드 컬럼 레이아웃 저장
      * @param gridDispItem
      * @param model
      * @return
      */
    @RequestMapping(value = "setGridItem.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setGridItem(HttpServletRequest request, GridDispItem gridDispItem, Model model) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        gridDispItem.setUserId(sessionInfo.getUserId());
        gridDispItem.setRegDt(currentDateTimeString());
        gridDispItem.setRegId(sessionInfo.getUserId());
        gridDispItem.setModDt(currentDateTimeString());
        gridDispItem.setModId(sessionInfo.getUserId());
        
        GridDispItem gdItem = gsService.selectGridItem(gridDispItem);

        int result = -1;
        
        // 해당 리소스 없음 > 신규 추가
        if(isEmpty(gdItem)) {
            result = gsService.insertGridItem(gridDispItem);
        }
        else {
            result = gsService.updateGridItem(gridDispItem);
        }
        
        return returnJson(Status.OK, result);
    }
    
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
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        GridDispItem gridDispItem = new GridDispItem();
        gridDispItem.setUserId(sessionInfo.getUserId());
        
        GridDispItem result = gsService.selectGridItem(gridDispItem);
        
        String test = Optional.ofNullable(result).map(GridDispItem::getColumnItem).orElse("\"empty\"");
        
        model.addAttribute("columnLayout", test);
        */
        return "sampleWijmo/exGridPage";
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

    /**
     * 그리드 본사 매장 그룹핑 샘플
     * @param rnum
     * @param model
     * @return
     */
   @RequestMapping(value="groupGridSample.sb")
   public String groupGridSample(Model model) {
     List<DefaultMap<Object>> data = sampleService.getgroupGridSample();
     model.addAttribute("shopList", convertToJson(data));
     return "sampleWijmo/groupGridSample";
   }
   
   /**
    * 그리드 본사 매장 그룹핑 샘플
    * @param rnum
    * @param model
    * @return
    */
  @RequestMapping(value="groupGridSample2.sb")
  public String groupGridSample2(Model model) {
     List<DefaultMap<Object>> data = sampleService.getgroupGridSample();
     model.addAttribute("shopList", convertToJson(data));
     return "sampleWijmo/groupGridSample2";
   }
}











