package kr.co.solbipos.application.persistence.cmm;

import java.util.List;
import kr.co.solbipos.application.domain.cmm.MenuUseHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfoBase;

/**
 * 
 * @author 정용길
 *
 */
public interface CmmMenuMapper {
    
    /**
      * 메뉴 사용 내역 저장
      * 
      * @param menuUseHist
      * @return
      */
    int insertMenuUseHist(MenuUseHist menuUseHist);
    
    /**
      * 즐겨찾기 메뉴 조회
      * 
      * @param sessionInfo
      * @return
      */
    List<ResrceInfoBase> selectBkmkMenu(SessionInfo sessionInfo);
    
    /**
      * 고정 메뉴 조회
      * 
      * @param sessionInfo
      * @return
      */
    List<ResrceInfoBase> selectFixingMenu(SessionInfo sessionInfo);

    /**
     * 메뉴 조회
     * 
     * @return ResrceInfo
     */
    List<ResrceInfo> selectMenu1(SessionInfo sessionInfo);
    List<ResrceInfo> selectMenu2(SessionInfo sessionInfo);
    List<ResrceInfo> selectMenu3(SessionInfo sessionInfo);
    List<ResrceInfo> selectMenu1Icon(SessionInfo sessionInfo);
    
}
