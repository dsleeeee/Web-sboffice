package kr.co.solbipos.service.cmm;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.cmm.MenuUseHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfoBase;
import kr.co.solbipos.application.persistence.cmm.CmmCodeMapper;
import kr.co.solbipos.application.persistence.cmm.CmmMenuMapper;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
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
    CmmCodeMapper cmmCodeMapper;
    
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
        // 추가 할 리소스 생성 및 세팅
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

        if (!isEmpty(fixMenu) && fixMenu.size() > 0) {
            for (ResrceInfoBase resrceInfoBase : fixMenu) {
                if (resrceInfoBase.getResrceCd().equals(resrceCd)) {
                    resrceInfoBase.setActivation(true);
                    sessionInfo.setCurrentMenu(resrceInfoBase); // 현재 메뉴 정보 등록
                } else {
                    resrceInfoBase.setActivation(false);
                }
            }
        }

        if (!isEmpty(histMenu) && histMenu.size() > 0) {
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
    public String makeMenu(SessionInfo sessionInfo, String menuType) {

        log.error("menuType : "+ menuType);
        
        List<DefaultMap<Object>> iconList = cmmCodeMapper.selectCmmCodeList("013"); // 메뉴 아이콘

        List<ResrceInfo> menu1;
        List<ResrceInfo> menu2;
        List<ResrceInfo> menu3;
        
        if(menuType.equals("A")){   // 전체메뉴
            menu1 = cmmMenuMapper.selectMenu1(sessionInfo);
            menu2 = cmmMenuMapper.selectMenu2(sessionInfo);
            menu3 = cmmMenuMapper.selectMenu3(sessionInfo);
        } else {    // 즐겨찾기
            menu1 = cmmMenuMapper.selectBkmkMenu1(sessionInfo); 
            menu2 = cmmMenuMapper.selectBkmkMenu2(sessionInfo);
            menu3 = cmmMenuMapper.selectBkmkMenu3(sessionInfo);
        }

        // 리턴 결과
        List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < menu1.size(); i++) {

            ResrceInfo resrceInfo1 = menu1.get(i);
            String resrceCd1 = resrceInfo1.getResrceCd();

            // 최상위 레벨에 아이콘 세팅 
            String iconNm = "";
            for(int n =0; n<iconList.size(); n++) {
                if(resrceInfo1.getResrceCd().equals(iconList.get(n).get("nmcodeItem1"))) {
                    iconNm = (String) iconList.get(n).get("nmcodeItem2");
                }
            }
            
            HashMap<String, Object> m1Header = new HashMap<>();

            m1Header.put("level1Seq", i);
            m1Header.put("header", resrceInfo1.getResrceNm());
            m1Header.put("resrceCd", resrceInfo1.getResrceCd());
            m1Header.put("url", "");
            m1Header.put("iconNm", iconNm);
            
            List<HashMap<String, Object>> m1items = new ArrayList<HashMap<String, Object>>();

            // 차상위 레벨 'TB_WB_RESRCE_INFO.DISP_LEVLE = 1'
            int level2Seq = 0;
            
            for (int j = 0; j < menu2.size(); j++) {
                ResrceInfo resrceInfo2 = menu2.get(j);
                String m2ResrceCd = resrceInfo2.getResrceCd();

                HashMap<String, Object> m2Header = new HashMap<>();
                
                if (resrceInfo2.getPResrce().equals(resrceCd1)) {
                    
                    m2Header.put("level2Seq", level2Seq);
                    m2Header.put("header", resrceInfo2.getResrceNm());
                    m2Header.put("resrceCd", resrceInfo2.getResrceCd());
                    m2Header.put("url", "");
                    level2Seq++;
                    
                    List<HashMap<String, Object>> m2items = new ArrayList<HashMap<String, Object>>();
                    
                    int level3Seq = 0;

                    // 하위 레벨 'TB_WB_RESRCE_INFO.DISP_LEVLE = 2'
                    for (int k = 0; k < menu3.size(); k++) {
                        ResrceInfo resrceInfo3 = menu3.get(k);
                        
                        if (resrceInfo3.getPResrce().equals(m2ResrceCd)) {
                            
                            HashMap<String, Object> m3Header = new HashMap<>();
                            
                            m3Header.put("level3Seq", level3Seq);
                            m3Header.put("header", resrceInfo3.getResrceNm());
                            m3Header.put("resrceCd", resrceInfo3.getResrceCd());
                            m3Header.put("url", isEmpty(resrceInfo3.getUrl()) ? "" : resrceInfo3.getUrl());
                            m2items.add(m3Header);
                            level3Seq++;
                        }
                    }
                    m2Header.put("items", m2items);
                    m1items.add(m2Header);
                }
            }
            
            // 최하위 레벨의 상위 레벨이 최상위 레벨일 경우
            for (int m = 0; m < menu3.size(); m++) {

                ResrceInfo resrceInfo3 = menu3.get(m);
                HashMap<String, Object> m3Header = new HashMap<>();

                if (resrceInfo3.getPResrce().equals(resrceCd1)) {
                    
                    m3Header.put("level2Seq", level2Seq);
                    m3Header.put("header", resrceInfo3.getResrceNm());
                    m3Header.put("resrceCd", resrceInfo3.getResrceCd());
                    m3Header.put("url", isEmpty(resrceInfo3.getUrl()) ? "" : resrceInfo3.getUrl());
                    level2Seq++;
                   
                    m1items.add(m3Header);
                }
            }
            
            m1Header.put("items", m1items);
            rList.add(m1Header);
        }
        log.error("rList.size() : " + rList.size());

        String menuStr = convertToJson(rList);
        log.error("menuStr : "+menuStr);

        return menuStr;
    }
    
}


