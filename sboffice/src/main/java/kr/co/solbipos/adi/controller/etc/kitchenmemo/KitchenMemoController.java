package kr.co.solbipos.adi.controller.etc.kitchenmemo;

import static kr.co.solbipos.utils.grid.ReturnUtil.returnJson;
import static kr.co.solbipos.utils.spring.StringUtil.convertToJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.adi.domain.etc.kitchenmemo.KitchenMemo;
import kr.co.solbipos.adi.service.etc.kitchenmemo.KitchenMemoService;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;


/**
 * 부가서비스 > 주방메모관리
 * 
 * @author 김지은
 */
@Controller
@RequestMapping(value = "/adi/etc/kitchenmemo/kitchenmemo/")
public class KitchenMemoController {
    
    @Autowired
    KitchenMemoService kitchenMemoService;
    
    @Autowired
    SessionService sessionService;

    @Autowired
    MessageService messageService;
    
    /**
     * 부가서비스 > 주방메모관리
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String kitchenmemoList(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        
        SessionInfo sessionInfo = sessionService.getSessionInfo();        
        List<DefaultMap<Object>> list = kitchenMemoService.selectKitchenMemo(sessionInfo);
        model.addAttribute("kitchenMemoList", convertToJson(list));
        
        return "adi/etc/kitchenmemo/kitchenMemo";
    }

    /**
     * 저장
     * @param kitchenMemo
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result kitchenmemoSave(@RequestBody KitchenMemo[] kitchenMemo , Model model) {
        for(int i=0; i<kitchenMemo.length; i++ ){
            KitchenMemo memo = kitchenMemo[i];
            if(memo.getStatus() == GridDataFg.INSERT){
                int cnt = kitchenMemoService.selectKitchenMemoCnt(memo);
                if(cnt > 0) {
                    return returnJson(Status.FAIL, "msg", messageService.get("error.duplicate.args", new String[]{"주방메모코드("+memo.getKitchnMemoCd()+")"} ,null));
                }
                kitchenMemoService.insertKitchenMemo(memo);
            }else if(memo.getStatus() == GridDataFg.UPDATE){
                kitchenMemoService.updateKitchenMemo(memo);
            }else if(memo.getStatus() == GridDataFg.DELETE){
                kitchenMemoService.deleteKitchenMemo(memo);
            }
        }
        return returnJson(Status.OK, null);
    }
    
}
