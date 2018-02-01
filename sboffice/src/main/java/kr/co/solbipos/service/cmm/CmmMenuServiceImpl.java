package kr.co.solbipos.service.cmm;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.convertToJson;
import static org.springframework.util.ObjectUtils.*;
import static org.springframework.util.StringUtils.isEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.cmm.MenuUseHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfoBase;
import kr.co.solbipos.application.enums.resrce.ResrceFg;
import kr.co.solbipos.application.persistence.cmm.CmmMenuMapper;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmmMenuServiceImpl implements CmmMenuService {

    @Autowired
    SessionService sessionService;

    @Autowired
    CmmMenuMapper cmmMenuMapper;

    @Autowired
    Prop prop;


    /**
     * 
     * 메뉴 디비 작업 관련
     * 
     */


    @Override
    public int insertMenuUseHist(MenuUseHist menuUseHist) {
        return cmmMenuMapper.insertMenuUseHist(menuUseHist);
    }

    @Override
    public List<ResrceInfoBase> selectBkmkMenu(SessionInfo sessionInfo) {
        return cmmMenuMapper.selectBkmkMenu(sessionInfo);
    }

    @Override
    public List<ResrceInfoBase> selectFixingMenu(SessionInfo sessionInfo) {
        return cmmMenuMapper.selectFixingMenu(sessionInfo);
    }


    /**
     * 
     * 즐겨 찾기 메뉴 관리
     * 
     */


    @Override
    public int insertMenuUseHist(ResrceInfo resrceInfo, SessionInfo sessionInfo) {
        MenuUseHist muh = new MenuUseHist();
        muh.setUserId(sessionInfo.getUserId());
        muh.setResrceCd(resrceInfo.getResrceCd());
        muh.setUseDate(currentDateString());
        muh.setUseDt(currentTimeMsString());
        return insertMenuUseHist(muh);
    }

    @Override
    public void addHistMenu(ResrceInfo resrceInfo, SessionInfo sessionInfo) {
        ResrceInfoBase resrceInfoBase = new ResrceInfoBase();
        resrceInfoBase.setResrceCd(resrceInfo.getResrceCd());
        resrceInfoBase.setPResrce(resrceInfo.getPResrce());
        resrceInfoBase.setResrceNm(resrceInfo.getResrceNm());
        resrceInfoBase.setUrl(resrceInfo.getUrl());
        addHistMenu(resrceInfoBase, sessionInfo);
    }

    @Override
    public SessionInfo addHistMenu(ResrceInfoBase resrceInfoBase, SessionInfo sessionInfo) {
        String resrceCd = resrceInfoBase.getResrceCd(); // 추가 할려는 리소스
        List<ResrceInfoBase> histMenu = sessionInfo.getHistMenu(); // 히스토리 메뉴
        List<ResrceInfoBase> fixMenu = sessionInfo.getFixMenu(); // 고정 메뉴

        // 고정 메뉴에 선택한 메뉴가 있는지 체크 > 추가하지 않고 고정메뉴 활성화
        int fSize = fixMenu.size();
        if (fSize > 0) {
            for (int i = 0; i < fSize; i++) {
                ResrceInfoBase rb = fixMenu.get(i);
                // 같은게 있으면 히스토리에 추가하지 않고 해당 고정 메뉴 활성화 하고 세션 저장
                if (rb.getResrceCd().equals(resrceCd)) {
                    sessionInfo.setFixMenu(fixMenu);
                    return checkActivation(resrceCd, sessionInfo);
                }
            }
        }

        if (isEmpty(histMenu)) {
            histMenu = new ArrayList<>();
        }
        // 기존 히스토리 메뉴 리스트에 있는지 체크 > 지우고 젤 뒤에 추가
        int hSize = histMenu.size();
        if (hSize == 0) {
            histMenu.add(resrceInfoBase);
            sessionInfo.setHistMenu(histMenu);
            return checkActivation(resrceCd, sessionInfo);
        } else {
            for (int i = 0; i < hSize; i++) {
                ResrceInfoBase rb = histMenu.get(i);
                // 히스토리 메뉴에 같은게 있으면 지우고 젤 뒤에 추가
                if (rb.getResrceCd().equals(resrceCd)) {
                    histMenu.remove(i);
                    histMenu.add(resrceInfoBase);
                    sessionInfo.setHistMenu(histMenu);
                    return checkActivation(resrceCd, sessionInfo);
                }
                // 마지막 까지 같은게 없으면
                if (i == hSize - 1) {
                    // 히스토리 리소스 갯수가 세팅한 갯수를 넘어가면 지우고 추가함
                    if (hSize >= prop.sessionHistMenuSize) {
                        histMenu.remove(0); // 첫번째꺼를 지운다.
                        histMenu.add(resrceInfoBase);
                    }
                    // 세팅한 갯수를 넘어가지 않으면 바로 추가
                    else {
                        histMenu.add(resrceInfoBase);
                    }
                    sessionInfo.setHistMenu(histMenu);
                    return checkActivation(resrceCd, sessionInfo);
                }
            }
        }
        return sessionInfo;
    }

    @Override
    public SessionInfo checkActivation(String resrceCd, SessionInfo sessionInfo) {
        List<ResrceInfoBase> histMenu = sessionInfo.getHistMenu(); // 히스토리 메뉴
        List<ResrceInfoBase> fixMenu = sessionInfo.getFixMenu(); // 고정 메뉴

        if (!isEmpty(fixMenu)) {
            for (ResrceInfoBase resrceInfoBase : fixMenu) {
                if (resrceInfoBase.getResrceCd().equals(resrceCd)) {
                    resrceInfoBase.setActivation(true);
                    sessionInfo.setCurrentMenu(resrceInfoBase); // 현재 메뉴 정보 등록
                } else {
                    resrceInfoBase.setActivation(false);
                }
            }
        }

        if (!isEmpty(histMenu)) {
            for (ResrceInfoBase resrceInfoBase : histMenu) {
                if (resrceInfoBase.getResrceCd().equals(resrceCd)) {
                    resrceInfoBase.setActivation(true);
                    sessionInfo.setCurrentMenu(resrceInfoBase); // 현재 메뉴 정보 등록
                } else {
                    resrceInfoBase.setActivation(false);
                }
            }
        }

        sessionInfo.setFixMenu(fixMenu);
        sessionInfo.setHistMenu(histMenu);

        // 레디스에 수정한 세션정보를 저장
        sessionService.setSessionInfo(sessionInfo);

        return sessionInfo;
    }

    @Override
    public void deleteHistMenu(String resrceCd, SessionInfo sessionInfo) {
        List<ResrceInfoBase> histMenu = sessionInfo.getHistMenu(); // 히스토리 메뉴
        if (!isEmpty(histMenu)) {
            int size = histMenu.size();
            for (int i = 0; i < size; i++) {
                ResrceInfoBase r = histMenu.get(i);
                if (r.getResrceCd().equals(resrceCd)) {
                    histMenu.remove(i);
                    break;
                }
            }
            sessionInfo.setHistMenu(histMenu);
            checkActivation(resrceCd, sessionInfo);
        }
    }

    @Override
    public void deleteFixMenu(String resrceCd, SessionInfo sessionInfo) {
        List<ResrceInfoBase> fixMenu = sessionInfo.getFixMenu();
        if (!isEmpty(fixMenu)) {
            int size = fixMenu.size();
            for (int i = 0; i < size; i++) {
                ResrceInfoBase r = fixMenu.get(i);
                if (r.getResrceCd().equals(resrceCd)) {
                    fixMenu.remove(i);
                    break;
                }
            }
            sessionInfo.setFixMenu(fixMenu);
            checkActivation(resrceCd, sessionInfo);
        }
    }


    /**
     * 
     * 메인 메뉴 관련
     * 
     */

    @Override
    public String makeMenu(SessionInfo sessionInfo) {
        
        List<ResrceInfo> m1 = cmmMenuMapper.selectMenu1(sessionInfo);
        List<ResrceInfo> m2 = cmmMenuMapper.selectMenu2(sessionInfo);
        List<ResrceInfo> m3 = cmmMenuMapper.selectMenu3(sessionInfo);

        List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
        
        for(int i=0; i < m1.size(); i++) {
            
            ResrceInfo r1 = m1.get(i);
            String m1ResrceCd = r1.getResrceCd();
            
            HashMap<String, Object> header = new HashMap<>();
            if(isEmpty(r1.getUrl())) {
                header.put("header", r1.getResrceNm());
                header.put("resrceCd", r1.getResrceCd());
            }
            else {
                String url = "<a href='" + r1.getUrl() + "'>" + r1.getResrceNm() + "</a>";
                header.put("header", url);
                header.put("resrceCd", r1.getResrceCd());
            }
            
            List<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
            
            for (int j = 0; j < m2.size(); j++) {
                
                ResrceInfo r2 = m2.get(j);
                String m2ResrceCd = r2.getResrceCd();
                
                HashMap<String, Object> m2Header = new HashMap<>();
                
                // 상위 메뉴를 찾음
                if(r2.getPResrce().equals(m1ResrceCd)) {
                    if(isEmpty(r2.getUrl())) {
                        m2Header.put("header", r2.getResrceNm());
                        m2Header.put("resrceCd", r2.getResrceCd());
                    }
                    else {
                        String url = "<a href='" + r2.getUrl() + "'>" + r2.getResrceNm() + "</a>";
                        m2Header.put("header", url);
                        m2Header.put("resrceCd", r2.getResrceCd());
                    }
                    
                    List<HashMap<String, Object>> m2items = new ArrayList<HashMap<String, Object>>();
                    for (int k = 0; k < m3.size(); k++) {
                        ResrceInfo r3 = m3.get(k);
                        
                        if(r3.getPResrce().equals(m2ResrceCd)) {
                            HashMap<String, Object> m3Header = new HashMap<>();
                            
                            if(isEmpty(r3.getUrl())) {
                                m3Header.put("header", r3.getResrceNm());
                                m3Header.put("resrceCd", r3.getResrceCd());
                            }
                            else {
                                String url = "<a href='" + r3.getUrl() + "'>" + r3.getResrceNm() + "</a>";
                                m3Header.put("header", url);
                                m3Header.put("resrceCd", r3.getResrceCd());
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
              
              ResrceInfo r3 = m3.get(m);
              String m3ResrceCd = r3.getResrceCd();
              
              HashMap<String, Object> m3Header = new HashMap<>();
              
              if(r3.getPResrce().equals(m1ResrceCd)) {
                  
                  if(isEmpty(r3.getUrl())) {
                      m3Header.put("header", r3.getResrceNm());
                      m3Header.put("resrceCd", r3.getResrceCd());
                  }
                  else {
                      String url = "<a href='" + r3.getUrl() + "'>" + r3.getResrceNm() + "</a>";
                      m3Header.put("header", url);
                      m3Header.put("resrceCd", r3.getResrceCd());
                  }
                  List<HashMap<String, Object>> mitems = new ArrayList<HashMap<String, Object>>();
                  
                  if(r3.getPResrce().equals(m3ResrceCd)) {
                      
                      if(isEmpty(r3.getUrl())) {
                          m3Header.put("header", r3.getResrceNm());
                          m3Header.put("resrceCd", r3.getResrceCd());
                      }
                      else {
                          String url = "<a href='" + r3.getUrl() + "'>" + r3.getResrceNm() + "</a>";
                          m3Header.put("header", url);
                          m3Header.put("resrceCd", r3.getResrceCd());
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

        String menuStr = convertToJson(rList);
        log.error(menuStr);
        
        return menuStr;
    }
}


























