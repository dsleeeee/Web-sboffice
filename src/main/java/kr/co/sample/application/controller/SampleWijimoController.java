package kr.co.sample.application.controller;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.grid.GridSupportService;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.sample.application.domain.CcdCodemTVO;
import kr.co.sample.application.domain.TmpDragtTVO;
import kr.co.sample.application.service.SampleService;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * 샘플 컨트롤러다.
 *
 * @author 정용길
 */

@Controller
public class SampleWijimoController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    private final SampleService sampleService;
    private final MessageService messageService;
    private final GridSupportService gridSupportService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public SampleWijimoController(SampleService sampleService, MessageService messageService,
        GridSupportService gridSupportService, SessionService sessionService) {
        this.sampleService = sampleService;
        this.messageService = messageService;
        this.gridSupportService = gridSupportService;
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "sampleGridMain.sb")
    public String sampleGridMain(HttpSession session, Model model) {
        return "application/sampleWijmo/sampleGridMain";
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
        return "application/sampleWijmo/sampleGrid";
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
        return "application/sampleWijmo/sampleGrid2";
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

        model.addAttribute("columnList", convertToJson(gridSupportService.getGridColumns(data.get(0))));
        model.addAttribute("data", convertToJson(data));

        return "application/sampleWijmo/exGridHeader";
    }

    public String getDdlTrdtlT(Integer rnum) {
        Integer param = Optional.ofNullable(rnum).orElse(100);
        List<DefaultMap<Object>> data = sampleService.selectDdlTrdtlT(param);
        return convertToJson(data);
    }



    @RequestMapping(value = "cmmcode.sb")
    public Result cmmcode(Model model) {

        List<DefaultMap<Object>> temp = sampleService.selectCommonCodeList("001");
        return new Result(Status.OK, temp);
    }


    /**
     * 위즈모 input 샘플
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "exInput.sb")
    public String exInput(Model model) {
        return "application/sampleWijmo/exInput";
    }















    /**
     * 위즈모 트리 샘플
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "exTree.sb")
    public String exTree(HttpServletRequest request, Model model) {

        StopWatch sw = new StopWatch();
        sw.start();

        HashMap<String, String> param = new HashMap<>();
        param.put("level", "0");
//        param.put("userId", sessionService.getSessionInfo(request).getUserId());
        param.put("userId", "m");

        List<ResrceInfoVO> m1 = sampleService.selectMenu1();
        List<ResrceInfoVO> m2 = sampleService.selectMenu2();
        List<ResrceInfoVO> m3 = sampleService.selectMenu3();

        List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();

        for(int i=0; i < m1.size(); i++) {

            ResrceInfoVO r1 = m1.get(i);
            String m1ResrceCd = r1.getResrceCd();

            HashMap<String, Object> header = new HashMap<>();
            if(isEmpty(r1.getUrl())) {
                header.put("header", r1.getResrceNm());
            }
            else {
                String url = "<a href='" + r1.getUrl() + "'>" + r1.getResrceNm() + "</a>";
                header.put("header", url);
            }

            List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
            for (int j = 0; j < m2.size(); j++) {

                ResrceInfoVO r2 = m2.get(j);
                String m2ResrceCd = r2.getResrceCd();

                HashMap<String, Object> m2Header = new HashMap<>();

                // 상위 메뉴를 찾음
                if(r2.getpResrce().equals(m1ResrceCd)) {
                    if(isEmpty(r2.getUrl())) {
                        m2Header.put("header", r2.getResrceNm());
                    }
                    else {
                        String url = "<a href='" + r2.getUrl() + "'>" + r2.getResrceNm() + "</a>";
                        m2Header.put("header", url);
                    }

                    List<HashMap<String, Object>> m2items = new ArrayList<HashMap<String, Object>>();
                    for (int k = 0; k < m3.size(); k++) {
                        ResrceInfoVO r3 = m3.get(k);

                        if(r3.getpResrce().equals(m2ResrceCd)) {
                            HashMap<String, Object> m3Header = new HashMap<>();

                            if(isEmpty(r3.getUrl())) {
                                m3Header.put("header", r3.getResrceNm());
                            }
                            else {
                                String url = "<a href='" + r3.getUrl() + "'>" + r3.getResrceNm() + "</a>";
                                m3Header.put("header", url);
                            }
                            m2items.add(m3Header);
                        }
                    }
                    m2Header.put("items", m2items);
                    items.add(m2Header);
                }
            }

            // 마지막레벨이 2레벨인 경우
            for (int m = 0; m < m3.size(); m++) {

              ResrceInfoVO r3 = m3.get(m);
              String m3ResrceCd = r3.getResrceCd();

              HashMap<String, Object> m3Header = new HashMap<>();

              if(r3.getpResrce().equals(m1ResrceCd)) {

                  if(isEmpty(r3.getUrl())) {
                      m3Header.put("header", r3.getResrceNm());
                  }
                  else {
                      String url = "<a href='" + r3.getUrl() + "'>" + r3.getResrceNm() + "</a>";
                      m3Header.put("header", url);
                  }
                  List<HashMap<String, Object>> mitems = new ArrayList<HashMap<String, Object>>();

                  if(r3.getpResrce().equals(m3ResrceCd)) {

                      if(isEmpty(r3.getUrl())) {
                          m3Header.put("header", r3.getResrceNm());
                      }
                      else {
                          String url = "<a href='" + r3.getUrl() + "'>" + r3.getResrceNm() + "</a>";
                          m3Header.put("header", url);
                      }
                      mitems.add(m3Header);
                  }
                  m3Header.put("items", mitems);
                  items.add(m3Header);
              }
            }
            header.put("items", items);
            rList.add(header);
        }

        sw.stop();

        String test = convertToJson(rList);
        LOGGER.error(test);

        model.addAttribute("treeData", test);
        model.addAttribute("task", sw.getTotalTimeSeconds());

        return "application/sampleWijmo/exTree";
    }





















    /**
     * 위즈모 트리 데이터 로드 테스트 (메뉴 테스트)
     * @param model
     * @return
     */
   @RequestMapping(value="exTreeMenu.sb")
   public String exTreeLoadTest(Model model) {
     List<DefaultMap<Object>> data = sampleService.selectTreeMenu();
     model.addAttribute("menuList", convertToJson(data));
     return "application/sampleWijmo/exTreeMenu";
   }

   /**
     * 위즈모 트리 데이터 로드 (본사/매장)
     * @param model
     * @return
     */
   @RequestMapping(value="exTreeStore.sb")
   public String exTreeStore(Model model) {
     List<DefaultMap<Object>> data = sampleService.getgroupGridSample();
     model.addAttribute("shopList", convertToJson(data));
     return "application/sampleWijmo/exTreeStore";
   }

   /**
    * 위즈모 트리 데이터 로드 (본사/매장) - 매장 단일선택 샘플
    * @param model
    * @return
    */
    @RequestMapping(value="exTreeStore2.sb")
    public String exTreeStore2(Model model) {
      List<DefaultMap<Object>> data = sampleService.getgroupGridSample();
      model.addAttribute("shopList", convertToJson(data));
      return "application/sampleWijmo/exTreeStore2";
    }

  /**
   * drag & drop
   * @param model
   * @return
   */
   @RequestMapping(value="exDragNDrop.sb")
   public String exDragNDrop(Model model) {

     TmpDragtTVO tmpDragtTVO = new TmpDragtTVO();
     tmpDragtTVO.setComFg("1");

     // 등록된 데이터(왼쪽)
     List<DefaultMap<Object>> data1 = sampleService.getDragNDropSample(tmpDragtTVO);
     model.addAttribute("leftData", convertToJson(data1));

     // 미등록 데이터(오른쪽)
     tmpDragtTVO.setComFg("2");
     List<DefaultMap<Object>> data2 = sampleService.getDragNDropSample(tmpDragtTVO);
     model.addAttribute("rightData", convertToJson(data2));

     return "application/sampleWijmo/exDragNDrop";
   }

   /**
    * save Drag & Drop
    * @param model
    * @return
    */
    @RequestMapping(value = "saveDragNDrop.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDragNDrop(HttpSession sessions, Model model,
            @RequestBody TmpDragtTVO[] tmpDragtTVO) {

        LOGGER.error("tmpDragtT.length  : " + tmpDragtTVO.length);

        if (tmpDragtTVO.length > 0) {
            for (int i = 0; i < tmpDragtTVO.length; i++) {

                LOGGER.error(i + " : " + tmpDragtTVO[i]);
                LOGGER.error("comCd : " + tmpDragtTVO[i].getComCd());

                // TODO insert, update, delete 로직 추가
            }
        }

        List<DefaultMap<Object>> temp = sampleService.selectDdSum();
        return new Result(Status.OK, temp);
    }

   /**
    * drag & drop  test
    * @param model
    * @return
    */
   @RequestMapping(value="exDragNDrop2.sb")
   public String exDragNDrop2(Model model) {
     return "application/sampleWijmo/exDragNDrop2";
   }

  /**
   * data insert test
   * @param model
   * @return
   */
   @RequestMapping(value="exGridInsert.sb")
   public String exGridInsert(Model model) {

     // 공통코드
     CcdCodemTVO ccdCodemTVO = new CcdCodemTVO();
     ccdCodemTVO.setComCdFg("012");
     List<DefaultMap<Object>> commList = sampleService.selectCode(ccdCodemTVO);

     // 테스트 리스트
     TmpDragtTVO tmpDragt = new TmpDragtTVO();
     tmpDragt.setComFg("");
     List<DefaultMap<Object>> list = sampleService.getDragNDropSample(tmpDragt);

     model.addAttribute("list", convertToJson(list));
     model.addAttribute("commList", convertToJson(commList));
     return "application/sampleWijmo/exGridInsert";
   }

   /**
    * data insert test
    * @param model
    * @return
    */
   @RequestMapping(value="exGridInsertJson.sb")
   @ResponseBody
   public Result exGridInsertJson(HttpSession session, Model model) {
     // 테스트 리스트
     TmpDragtTVO tmpDragt = new TmpDragtTVO();
     tmpDragt.setComFg("");
     List<DefaultMap<Object>> list = sampleService.getDragNDropSample(tmpDragt);
     return new Result(Status.OK, list);
   }

}


