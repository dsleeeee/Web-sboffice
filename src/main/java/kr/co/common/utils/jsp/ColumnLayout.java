package kr.co.common.utils.jsp;


import kr.co.common.service.grid.GridSupportService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.common.service.GridDispItemVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author 정용길
 *
 */
@Component("columnLayout")
public class ColumnLayout {

    private final GridSupportService gridSupportService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public ColumnLayout(GridSupportService gridSupportService, SessionService sessionService) {
        this.gridSupportService = gridSupportService;
        this.sessionService = sessionService;
    }

    /**
     * 그리드의 컬럼 레이아웃을 가져온다.
     *
     * @param gridIdx
     * @return
     */
    public String getColumnLayout(Long gridIdx) {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        // 유져의 세션 정보를 가져옴
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        GridDispItemVO gridDispItemVO = new GridDispItemVO();

        gridDispItemVO.setUserId(sessionInfoVO.getUserId());
//        gridDispItemVO.setResrceCd(sessionInfoVO.getCurrentMenu().getResrceCd());
        gridDispItemVO.setGridIdx(gridIdx);

        // 저장한 그리드 레이아웃을 조회한다.
        GridDispItemVO result = gridSupportService.selectGridItem(gridDispItemVO);

        // 저장된 레이아웃이 없으면 기본 레이아웃 조회
        return Optional.ofNullable(result).map(GridDispItemVO::getColumnItem).orElse("\"\"");
    }


}


