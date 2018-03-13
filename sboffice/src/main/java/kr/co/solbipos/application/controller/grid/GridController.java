package kr.co.solbipos.application.controller.grid;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static org.springframework.util.ObjectUtils.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.cmm.GridDispItem;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.grid.GridSupportService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * 그리드 관련 공통 기능 컨트롤러
 * 
 * @author 정용길
 */

@Slf4j
@Controller
public class GridController {

    @Autowired
    GridSupportService gsService;

    @Autowired
    SessionService sessionService;

    /**
     * 그리드 컬럼 레이아웃 저장
     * 
     * @param gridDispItem
     * @param model
     * @return
     */
    @RequestMapping(value = "setGridItem.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result setGridItem(HttpServletRequest request, GridDispItem gridDispItem,
            Model model) {
        // 유져 정보 조회
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        // 그리드 레이아웃 저장 정보 세팅
        gridDispItem.setUserId(sessionInfo.getUserId());
        gridDispItem.setRegDt(currentDateTimeString());
        gridDispItem.setRegId(sessionInfo.getUserId());
        gridDispItem.setModDt(currentDateTimeString());
        gridDispItem.setModId(sessionInfo.getUserId());
        
        // 기존에 저장 내역 있는지 조회
        GridDispItem gdItem = gsService.selectGridItem(gridDispItem);

        int result = -1;

        // 해당 리소스 없음 > 신규 추가
        if (isEmpty(gdItem)) {
            result = gsService.insertGridItem(gridDispItem);
        } else {
            // 해당 리소스 있음 > 업데이트
            result = gsService.updateGridItem(gridDispItem);
        }

        return returnJson(Status.OK, result);
    }
}


