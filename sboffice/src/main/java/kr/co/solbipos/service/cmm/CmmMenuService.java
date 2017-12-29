package kr.co.solbipos.service.cmm;

import java.util.List;
import kr.co.solbipos.application.domain.cmm.MenuUseHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfoBase;

/**
 * 
 * 
 * @author 정용길
 */
public interface CmmMenuService {

    /** 
     * 
     * 메뉴 디비 작업 관련
     * 
     * */
    
    /**
     * 리소스 정보로 메뉴 사용 내역 저장
     * 
     * @param resrceInfo 리소스 정보
     * @param sessionInfo
     * @return
     */
    int insertMenuUseHist(ResrceInfo resrceInfo, SessionInfo sessionInfo);

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
     * 
     * 즐겨 찾기 메뉴 관리
     * 
     * */
    
    /**
     * 메뉴 사용 내역 저장
     * 
     * @param menuUseHist
     * @return
     */
    int insertMenuUseHist(MenuUseHist menuUseHist);

    /**
      * 세션에 메뉴 히스토리 추가
      * 
      * @param resrceInfo
      * @param sessionInfo
      * @return
      */
    SessionInfo addHistMenu(ResrceInfoBase resrceInfoBase, SessionInfo sessionInfo);
    
    /**
      * 
      * @param resrceInfo
      * @param sessionInfo
      * @return
      */
    void addHistMenu(ResrceInfo resrceInfo, SessionInfo sessionInfo);
    
    /**
      * 세션 고정메뉴, 히스토리 메뉴 의 활성화 여부 하나만 체크
      * 
      * @param resrceCd 활성화 체크할 리소스 키값
      * @param sessionInfo
      * @return
      */
    SessionInfo checkActivation(String resrceCd, SessionInfo sessionInfo);
    
    /**
      * 히스토리 메뉴 내역을 세션에서 삭제한다.
      * 
      * @param resrceCd
      * @param sessionInfo
      */
    void deleteHistMenu(String resrceCd, SessionInfo sessionInfo);
    
    /**
      * 고정 메뉴를 세션에서 삭제한다. 디비에서 삭제 하는건 아님
      * 
      * @param resrceCd
      * @param sessionInfo
      */
    void deleteFixMenu(String resrceCd, SessionInfo sessionInfo);
    
    
    /**
     * 
     * 메인 메뉴 관련
     * 
     */
    
    String makeMenu(List<ResrceInfo> resrceInfoList);
}



























