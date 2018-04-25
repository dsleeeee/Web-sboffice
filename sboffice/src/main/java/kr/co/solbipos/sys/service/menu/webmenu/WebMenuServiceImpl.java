package kr.co.solbipos.sys.service.menu.webmenu;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.enums.grid.GridDataFg;
import kr.co.solbipos.application.enums.resrce.ResrceFg;
import kr.co.solbipos.enums.UseYn;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.sys.persistence.menu.webmenu.WebMenuMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 * 
 * @author 정용길
 *
 */
@Slf4j
@Service
public class WebMenuServiceImpl implements WebMenuService {

    @Autowired
    WebMenuMapper webMenuMapper;

    @Autowired
    SessionService sessionService;

    @Override
    public List<String> selectWebMenu(ResrceInfo resrceInfo) {
        return webMenuMapper.selectWebMenu(resrceInfo);
    }

    @Override
    public List<ResrceInfo> selectWebMenuLevel(int level) {
        return webMenuMapper.selectWebMenuLevel(level);
    }

    @Override
    public int insertWebMenu(ResrceInfo resrceInfo) {
        return webMenuMapper.insertWebMenu(resrceInfo);
    }

    @Override
    public int updateWebMenu(ResrceInfo resrceInfo) {
        // 수정 id, 날짜 세팅
        SessionInfo si = sessionService.getSessionInfo();
        resrceInfo.setModId(si.getUserId());
        resrceInfo.setModDt(currentDateTimeString());
        return webMenuMapper.updateWebMenu(resrceInfo);
    }

    @Override
    public int deleteWebMenu(ResrceInfo resrceInfo) {
        // 수정 id, 날짜 세팅
        SessionInfo si = sessionService.getSessionInfo();
        resrceInfo.setModId(si.getUserId());
        resrceInfo.setModDt(currentDateTimeString());
        return webMenuMapper.deleteWebMenuAll(resrceInfo);
    }

    @Override
    public List<HashMap<String, Object>> makeupTree() {

        List<ResrceInfo> level_1 = selectWebMenuLevel(0);
        List<ResrceInfo> level_2 = selectWebMenuLevel(1);
        List<ResrceInfo> level_3 = selectWebMenuLevel(2);
        List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
        int l1 = level_1.size();

        for (int i = 0; i < l1; i++) {

            ResrceInfo resrceInfo1 = level_1.get(i);
            String resrceCd1 = resrceInfo1.getResrceCd();
            HashMap<String, Object> m1Header = new HashMap<>();
            m1Header.put("level", 0);
            m1Header.put("header", resrceInfo1.getResrceNm());
            m1Header.put("resrceCd", resrceInfo1.getResrceCd());
            m1Header.put("url", "");

            List<HashMap<String, Object>> m1items = new ArrayList<HashMap<String, Object>>();

            int l2 = level_2.size();

            for (int j = 0; j < l2; j++) {
                ResrceInfo resrceInfo2 = level_2.get(j);
                String m2ResrceCd = resrceInfo2.getResrceCd();
                HashMap<String, Object> m2Header = new HashMap<>();

                if (resrceInfo2.getPResrce().equals(resrceCd1)) {
                    m2Header.put("level", 1);
                    m2Header.put("header", resrceInfo2.getResrceNm());
                    m2Header.put("resrceCd", resrceInfo2.getResrceCd());
                    m2Header.put("url", "");

                    List<HashMap<String, Object>> m2items =
                            new ArrayList<HashMap<String, Object>>();

                    // 하위 레벨 'TB_WB_RESRCE_INFO.DISP_LEVLE = 2'
                    int l3 = level_3.size();
                    for (int k = 0; k < l3; k++) {
                        ResrceInfo resrceInfo3 = level_3.get(k);
                        if (resrceInfo3.getPResrce().equals(m2ResrceCd)) {

                            HashMap<String, Object> m3Header = new HashMap<>();
                            m3Header.put("level", 2);
                            m3Header.put("header", resrceInfo3.getResrceNm());
                            m3Header.put("resrceCd", resrceInfo3.getResrceCd());
                            m3Header.put("url",
                                    isEmpty(resrceInfo3.getUrl()) ? "" : resrceInfo3.getUrl());
                            m2items.add(m3Header);
                        }
                    }
                    m2Header.put("items", m2items);
                    m1items.add(m2Header);
                }
            }

            // 최하위 레벨의 상위 레벨이 최상위 레벨일 경우
            for (int m = 0; m < level_3.size(); m++) {
                ResrceInfo resrceInfo3 = level_3.get(m);
                HashMap<String, Object> m3Header = new HashMap<>();
                if (resrceInfo3.getPResrce().equals(resrceCd1)) {
                    m3Header.put("level", 1);
                    m3Header.put("header", resrceInfo3.getResrceNm());
                    m3Header.put("resrceCd", resrceInfo3.getResrceCd());
                    m3Header.put("url", isEmpty(resrceInfo3.getUrl()) ? "" : resrceInfo3.getUrl());

                    m1items.add(m3Header);
                }
            }
            m1Header.put("items", m1items);
            rList.add(m1Header);
        }
        return rList;
    }

    @Override
    public boolean insertMenu(ResrceInfo resrceInfo) {

        SessionInfo si = sessionService.getSessionInfo();
        String regId = si.getUserId();
        String dt = currentDateTimeString();

        resrceInfo.setResrceFg(ResrceFg.MENU);

        resrceInfo.setRegId(regId);
        resrceInfo.setModId(regId);
        resrceInfo.setRegDt(dt);
        resrceInfo.setModDt(dt);

        int result = 0;

        /*
         * 
         * 메뉴 등록
         * 
         */

        // 리소스 코드가 없으면 신규 입력
        if (isEmpty(resrceInfo.getResrceCd())) {
            resrceInfo.setUseYn(UseYn.Y);
            result = webMenuMapper.insertWebMenu(resrceInfo);
        }
        // 리소스 코드가 있으면 업데이트
        else {
            ResrceInfo temp = webMenuMapper.selectWebMenuByResrceCd(resrceInfo);

            // 메뉴명, url 중 다른게 있으면 업데이트 한다.
            if (!temp.getResrceNm().equals(resrceInfo.getResrceNm())
                    || !equalsNull(temp.getUrl(), resrceInfo.getUrl())
                    || !equalsNull(temp.getSpclAuthor(), resrceInfo.getSpclAuthor())
                    || temp.getDispIdx() != resrceInfo.getDispIdx()) {
                result = webMenuMapper.updateWebMenu(resrceInfo);
            }
        }

        /*
         * 
         * 기능 등록
         * 
         */

        ResrceInfo[] f = resrceInfo.getResrceInfoArr();
        String pResrce = resrceInfo.getResrceCd();

        // 기능이 있으면 입력
        if (!isEmpty(f)) {
            for (int i = 0; i < f.length; i++) {
                ResrceInfo r = f[i];

                // 기능 기본 정보 세팅
                r.setResrceFg(ResrceFg.FUNC);
                r.setRegId(regId);
                r.setModId(regId);
                r.setRegDt(dt);
                r.setModDt(dt);

                // 그리드에 추가된 정보
                if (r.getStatus() == GridDataFg.INSERT) {
                    log.info("insert : {}", r);

                    r.setUseYn(UseYn.Y);
                    r.setpResrce(pResrce);
                    int insert = webMenuMapper.insertWebMenu(r);
                } else if (r.getStatus() == GridDataFg.UPDATE) {
                    log.info("update : {}", r);

                    int update = webMenuMapper.updateWebMenu(r);
                } else if (r.getStatus() == GridDataFg.DELETE) {
                    log.info("delete : {}", r);

                    int delete = webMenuMapper.deleteWebMenu(r);
                }
            }
        }
        return true;
    }

}


