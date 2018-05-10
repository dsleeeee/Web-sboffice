package kr.co.solbipos.application.controller.grid;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static org.springframework.util.ObjectUtils.isEmpty;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.grid.GridSupportService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.domain.cmm.GridDispItemVO;
import kr.co.solbipos.application.domain.login.SessionInfoVO;

/**
 * 그리드 관련 공통 기능 컨트롤러
 *
 * @author 정용길
 */

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
    public Result setGridItem(HttpServletRequest request, GridDispItemVO gridDispItemVO,
            Model model) {
        // 유져 정보 조회
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 그리드 레이아웃 저장 정보 세팅
        gridDispItemVO.setUserId(sessionInfoVO.getUserId());
        gridDispItemVO.setRegDt(currentDateTimeString());
        gridDispItemVO.setRegId(sessionInfoVO.getUserId());
        gridDispItemVO.setModDt(currentDateTimeString());
        gridDispItemVO.setModId(sessionInfoVO.getUserId());

        // 기존에 저장 내역 있는지 조회
        GridDispItemVO gdItem = gsService.selectGridItem(gridDispItemVO);

        int result = -1;

        // 해당 리소스 없음 > 신규 추가
        if (isEmpty(gdItem)) {
            result = gsService.insertGridItem(gridDispItemVO);
        } else {
            // 해당 리소스 있음 > 업데이트
            result = gsService.updateGridItem(gridDispItemVO);
        }

        return returnJson(Status.OK, result);
    }
}


