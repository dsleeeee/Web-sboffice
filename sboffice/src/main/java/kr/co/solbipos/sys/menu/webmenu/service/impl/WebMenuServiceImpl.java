package kr.co.solbipos.sys.menu.webmenu.service.impl;

import static kr.co.common.utils.DateUtil.*;
import static kr.co.common.utils.spring.StringUtil.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.validate.WebMenuService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.common.enums.ResrceFg;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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
@Transactional
public class WebMenuServiceImpl implements WebMenuService {

    @Autowired
    WebMenuMapper webMenuMapper;

    @Autowired
    SessionService sessionService;

    @Autowired
    MessageService messageService;

    @Override
    public List<String> selectWebMenu(ResrceInfoVO resrceInfoVO) {
        return webMenuMapper.selectWebMenu(resrceInfoVO);
    }

    @Override
    public List<ResrceInfoVO> selectWebMenuLevel(int level) {
        return webMenuMapper.selectWebMenuLevel(level);
    }

    @Override
    public int insertWebMenu(ResrceInfoVO resrceInfoVO) {
        return webMenuMapper.insertWebMenu(resrceInfoVO);
    }

    @Override
    public int updateWebMenu(ResrceInfoVO resrceInfoVO) {
        // 수정 id, 날짜 세팅
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        resrceInfoVO.setModId(sessionInfoVO.getUserId());
        resrceInfoVO.setModDt(currentDateTimeString());
        return webMenuMapper.updateWebMenu(resrceInfoVO);
    }

    @Override
    public int deleteWebMenu(ResrceInfoVO resrceInfoVO) {
        // 수정 id, 날짜 세팅
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        resrceInfoVO.setModId(sessionInfoVO.getUserId());
        resrceInfoVO.setModDt(currentDateTimeString());
        return webMenuMapper.deleteWebMenuAll(resrceInfoVO);
    }

    @Override
    public List<HashMap<String, Object>> makeupTree() {

        List<ResrceInfoVO> level_1 = selectWebMenuLevel(0);
        List<ResrceInfoVO> level_2 = selectWebMenuLevel(1);
        List<ResrceInfoVO> level_3 = selectWebMenuLevel(2);
        List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
        int l1 = level_1.size();

        for (int i = 0; i < l1; i++) {

            ResrceInfoVO resrceInfoVO1 = level_1.get(i);
            String resrceCd1 = resrceInfoVO1.getResrceCd();
            HashMap<String, Object> m1Header = new HashMap<>();
            m1Header.put("level", 0);
            m1Header.put("header", resrceInfoVO1.getResrceNm());
            m1Header.put("resrceCd", resrceInfoVO1.getResrceCd());
            m1Header.put("url", "");

            List<HashMap<String, Object>> m1items = new ArrayList<HashMap<String, Object>>();

            int l2 = level_2.size();

            for (int j = 0; j < l2; j++) {
                ResrceInfoVO resrceInfoVO2 = level_2.get(j);
                String m2ResrceCd = resrceInfoVO2.getResrceCd();
                HashMap<String, Object> m2Header = new HashMap<>();

                if (resrceInfoVO2.getPResrce().equals(resrceCd1)) {
                    m2Header.put("level", 1);
                    m2Header.put("header", resrceInfoVO2.getResrceNm());
                    m2Header.put("resrceCd", resrceInfoVO2.getResrceCd());
                    m2Header.put("url", "");

                    List<HashMap<String, Object>> m2items =
                            new ArrayList<HashMap<String, Object>>();

                    // 하위 레벨 'TB_WB_RESRCE_INFO.DISP_LEVLE = 2'
                    int l3 = level_3.size();
                    for (int k = 0; k < l3; k++) {
                        ResrceInfoVO resrceInfoVO3 = level_3.get(k);
                        if (resrceInfoVO3.getPResrce().equals(m2ResrceCd)) {

                            HashMap<String, Object> m3Header = new HashMap<>();
                            m3Header.put("level", 2);
                            m3Header.put("header", resrceInfoVO3.getResrceNm());
                            m3Header.put("resrceCd", resrceInfoVO3.getResrceCd());
                            m3Header.put("url",
                                    isEmpty(resrceInfoVO3.getUrl()) ? "" : resrceInfoVO3.getUrl());
                            m2items.add(m3Header);
                        }
                    }
                    m2Header.put("items", m2items);
                    m1items.add(m2Header);
                }
            }

            // 최하위 레벨의 상위 레벨이 최상위 레벨일 경우
            for (int m = 0; m < level_3.size(); m++) {
                ResrceInfoVO resrceInfoVO3 = level_3.get(m);
                HashMap<String, Object> m3Header = new HashMap<>();
                if (resrceInfoVO3.getPResrce().equals(resrceCd1)) {
                    m3Header.put("level", 1);
                    m3Header.put("header", resrceInfoVO3.getResrceNm());
                    m3Header.put("resrceCd", resrceInfoVO3.getResrceCd());
                    m3Header.put("url", isEmpty(resrceInfoVO3.getUrl()) ? "" : resrceInfoVO3.getUrl());

                    m1items.add(m3Header);
                }
            }
            m1Header.put("items", m1items);
            rList.add(m1Header);
        }
        return rList;
    }

    @Override
    public boolean insertMenu(ResrceInfoVO resrceInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        String regId = sessionInfoVO.getUserId();
        String dt = currentDateTimeString();

        resrceInfoVO.setResrceFg(ResrceFg.MENU);

        resrceInfoVO.setRegId(regId);
        resrceInfoVO.setModId(regId);
        resrceInfoVO.setRegDt(dt);
        resrceInfoVO.setModDt(dt);

        int result = 0;

        /*
         *
         * 메뉴 등록
         *
         */

        // 리소스 코드가 없으면 신규 입력
        if (isEmpty(resrceInfoVO.getResrceCd())) {
            resrceInfoVO.setUseYn(UseYn.Y);
            result = webMenuMapper.insertWebMenu(resrceInfoVO);
            if(result == 0) {
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        // 리소스 코드가 있으면 업데이트
        else {
            ResrceInfoVO temp = webMenuMapper.selectWebMenuByResrceCd(resrceInfoVO);

            // 메뉴명, url 중 다른게 있으면 업데이트 한다.
            if (!temp.getResrceNm().equals(resrceInfoVO.getResrceNm())
                    || !equalsNull(temp.getUrl(), resrceInfoVO.getUrl())
                    || !equalsNull(temp.getSpclAuthor(), resrceInfoVO.getSpclAuthor())
                    || temp.getDispIdx() != resrceInfoVO.getDispIdx()) {

                result = webMenuMapper.updateWebMenu(resrceInfoVO);

                if(result == 0) {
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
        }

        /*
         *
         * 기능 등록
         *
         */

        ResrceInfoVO[] f = resrceInfoVO.getResrceInfoArr();
        String pResrce = resrceInfoVO.getResrceCd();
        int procCnt = 0;
        // 기능이 있으면 입력
        if (!isEmpty(f)) {
            for (ResrceInfoVO r : f) {

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
                    procCnt += webMenuMapper.insertWebMenu(r);
                } else if (r.getStatus() == GridDataFg.UPDATE) {
                    log.info("update : {}", r);

                    procCnt += webMenuMapper.updateWebMenu(r);
                    throw new JsonException(Status.FAIL, "aaaaaaaaaaaaaaa");
                } else if (r.getStatus() == GridDataFg.DELETE) {
                    log.info("delete : {}", r);

                    procCnt += webMenuMapper.deleteWebMenu(r);
                }
            }

            if(procCnt == f.length) {
                return true;
            }
            else {
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        return true;
    }

}


