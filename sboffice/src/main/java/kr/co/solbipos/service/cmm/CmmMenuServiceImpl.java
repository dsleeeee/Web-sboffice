package kr.co.solbipos.service.cmm;

import static kr.co.solbipos.utils.DateUtil.currentDateString;
import static kr.co.solbipos.utils.DateUtil.currentTimeMsString;
import static org.springframework.util.StringUtils.isEmpty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.cmm.MenuUseHist;
import kr.co.solbipos.application.domain.cmm.Store;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.Menu;
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
     * 레이어 팝업 매장 조회
     * 
     * */
    
    @Override
    public List<Store> selectStore(Store store) {
        return cmmMenuMapper.selectStore(store);
    }
    
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
    
    @Override
    public List<Menu> makeMenu(SessionInfo sessionInfo, String menuType) {

        DefaultMap<String> map = new DefaultMap<String>();
        map.put("grpCd", sessionInfo.getGrpCd());
        map.put("userId", sessionInfo.getUserId());

        //Tree 생성을 위한 모든 리소스(Menu 포함) 조회
        //로그인 아이디의 그룹 기준 권한이 있는 것 체크
        List<DefaultMap<String>> list = cmmMenuMapper.selectAllResrce(map);
        
        //로그인 아이디 기준 권한이 있는 기능의 탐색 경로 조회
        //Tree의 기준 데이터에서 권한이 없는 리소스를 빼기 위해 사용
        List<DefaultMap<String>> authedList = new ArrayList<DefaultMap<String>>();
        if(menuType.equals("A")) {
            authedList = cmmMenuMapper.selectAuthedResrce(map);
        }
        else if(menuType.equals("F")) {
            authedList = cmmMenuMapper.selectAuthedBkmk(map);
        }
        
        return makeTreeData(list, authedList);

    }


    @Override
    public List<String> selectStoreCdList(String hqOfficeCd) {
        return cmmMenuMapper.selectStoreCdList(hqOfficeCd);
    }
    
    @Override
    public List<Menu> makeTreeData(List<DefaultMap<String>> list, 
            List<DefaultMap<String>> authedList) {
        
        List<Menu> input = new ArrayList<Menu>();
        Menu m = new Menu();
        for(DefaultMap<String> item : list) {
            m = new Menu();
            m.setCd(item.getStr("cd"));
            m.setPcd(item.getStr("pcd"));
            m.setNm(item.getStr("nm"));
            m.setUrl(item.getStr("url"));
            m.setIcon(item.getStr("icon"));
            m.setItems(new ArrayList<Menu>());
            input.add(m);
        }
        //권한이 있는 기능에서 상위 리소스 정보 생성
        //상위 리소스 맵에 없는 리소스는 권한이 없는 것으로 판단.
        DefaultMap<String> authedResrce = new DefaultMap<String>(); 
        if(authedList != null && authedList.size() > 0) {
            String resrcePath = "";
            String[] arrayRes;
            for(DefaultMap<String> item : authedList) {
                resrcePath = item.getStr("resrcePath").substring(1);
                arrayRes = resrcePath.split("\\/");
                for(String res : arrayRes) {
                    authedResrce.put(res, "");
                }
            }
            log.debug(authedResrce.toString());
        }

        Map<String, Menu> hm = new LinkedHashMap<String, Menu>();
        Menu child;
        Menu mmdParent;
        
        if(authedResrce.size() > 0) {
            for(Menu menu : input) {
                
                if(!hm.containsKey(menu.getCd())) {
                    if(authedResrce.size() > 0) {
                        if(authedResrce.containsKey(menu.getCd())) {
                            hm.put(menu.getCd(), menu);
                        }
                    }
                    else {
                        hm.put(menu.getCd(), menu);
                    }
                }
                child = hm.get(menu.getCd());
                
                if(!menu.getPcd().equals("") && !menu.getPcd().equals("000000")) {
                    if(hm.containsKey(menu.getPcd())) {
                        mmdParent = hm.get(menu.getPcd());
                        mmdParent.getItems().add(child);
                    }
                }
                
            }
            //log.debug( hm.toString() );
        }
        
        List<Menu> retrunData = new ArrayList<Menu>();
        for (Menu mmd : hm.values()) {
            if (mmd.getPcd() == null || mmd.getPcd().equals("")
                    || mmd.getPcd().equals("000000")) {
                retrunData.add(mmd);
            }
        }
        log.debug( retrunData.toString() );
        return retrunData;
    }
}


