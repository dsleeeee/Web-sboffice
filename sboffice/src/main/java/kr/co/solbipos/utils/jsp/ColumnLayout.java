package kr.co.solbipos.utils.jsp;


import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import kr.co.solbipos.application.domain.cmm.GridDispItem;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.grid.GridSupportService;
import kr.co.solbipos.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길
 *
 */
@Slf4j
@Component("columnLayout")
public class ColumnLayout {

    @Autowired
    GridSupportService gsService;

    @Autowired
    SessionService sessionService;

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
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        GridDispItem gridDispItem = new GridDispItem();

        gridDispItem.setUserId(sessionInfo.getUserId());
        gridDispItem.setResrceCd(sessionInfo.getCurrentMenu().getResrceCd());
        gridDispItem.setGridIdx(gridIdx);

        // 저장한 그리드 레이아웃을 조회한다.
        GridDispItem result = gsService.selectGridItem(gridDispItem);
        
        // 저장된 레이아웃이 없으면 기본 레이아웃 조회
        return Optional.ofNullable(result)
                .map(GridDispItem::getColumnItem)
                .orElse("");
    }


}


