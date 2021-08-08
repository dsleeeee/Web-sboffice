package kr.co.solbipos.sys.menu.webmenu.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.common.enums.ResrceFg;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.menu.webmenu.service.WebMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.equalsNull;
import static kr.co.common.utils.spring.StringUtil.isEmpty;

/**
 *
 * 시스템 관리 > 메뉴관리 > 프로그램 메뉴관리
 *
 * @author 정용길
 *
 */
@Service("webMenuService")
@Transactional
public class WebMenuServiceImpl implements WebMenuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final WebMenuMapper webMenuMapper;
    private final SessionService sessionService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public WebMenuServiceImpl(WebMenuMapper webMenuMapper, SessionService sessionService, MessageService messageService) {
        this.webMenuMapper = webMenuMapper;
        this.sessionService = sessionService;
        this.messageService = messageService;
    }

    /** 리소스 하위 기능 정보 포함 조회 */
    @Override
    public List<String> selectWebMenu(ResrceInfoVO resrceInfoVO) {
        return webMenuMapper.selectWebMenu(resrceInfoVO);
    }

    /** 리소스 트리 레벨로 조회 */
    @Override
    public List<ResrceInfoVO> selectWebMenuLevel(int level) {
        return webMenuMapper.selectWebMenuLevel(level);
    }

    /** 리소스 저장 {@code selectKey} 를 사용해서 추가한 {@code resrceCd} 를 사용할수 있음 */
    @Override
    public int insertWebMenu(ResrceInfoVO resrceInfoVO) {
        return webMenuMapper.insertWebMenu(resrceInfoVO);
    }

    /** 리소스 정보 업데이트 */
    @Override
    public int updateWebMenu(ResrceInfoVO resrceInfoVO) {
        // 수정 id, 날짜 세팅
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        resrceInfoVO.setModId(sessionInfoVO.getUserId());
        resrceInfoVO.setModDt(currentDateTimeString());
        return webMenuMapper.updateWebMenu(resrceInfoVO);
    }

    /** 리소스 정보 삭제 {@code USE_YN} : N 으로 처리 */
    @Override
    public int deleteWebMenu(ResrceInfoVO resrceInfoVO) {
        // 수정 id, 날짜 세팅
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        resrceInfoVO.setModId(sessionInfoVO.getUserId());
        resrceInfoVO.setModDt(currentDateTimeString());
        return webMenuMapper.deleteWebMenuAll(resrceInfoVO);
    }

    /** 위즈모 트리에 사용하는 데이터를 만듬 */
    @Override
    public List<HashMap<String, Object>> makeupTree() {

        List<ResrceInfoVO> level_1 = selectWebMenuLevel(1);
        List<ResrceInfoVO> level_2 = selectWebMenuLevel(2);
        List<ResrceInfoVO> level_3 = selectWebMenuLevel(3);
        List<HashMap<String, Object>> rList = new ArrayList<HashMap<String, Object>>();
        int l1 = level_1.size();

        for (int i = 0; i < l1; i++) {

            ResrceInfoVO resrceInfoVO1 = level_1.get(i);
            String resrceCd1 = resrceInfoVO1.getResrceCd();
            HashMap<String, Object> m1Header = new HashMap<>();
            m1Header.put("level", 1);
            m1Header.put("header", resrceInfoVO1.getResrceNm());
            m1Header.put("resrceCd", resrceInfoVO1.getResrceCd());
            m1Header.put("url", "");

            List<HashMap<String, Object>> m1items = new ArrayList<HashMap<String, Object>>();

            int l2 = level_2.size();

            for (int j = 0; j < l2; j++) {
                ResrceInfoVO resrceInfoVO2 = level_2.get(j);
                String m2ResrceCd = resrceInfoVO2.getResrceCd();
                HashMap<String, Object> m2Header = new HashMap<>();

                if (resrceInfoVO2.getpResrce().equals(resrceCd1)) {
                    m2Header.put("level", 2);
                    m2Header.put("header", resrceInfoVO2.getResrceNm());
                    m2Header.put("resrceCd", resrceInfoVO2.getResrceCd());
                    m2Header.put("url", "");

                    List<HashMap<String, Object>> m2items =
                            new ArrayList<HashMap<String, Object>>();

                    // 하위 레벨 'TB_WB_RESRCE_INFO.DISP_LEVLE = 2'
                    int l3 = level_3.size();
                    for (int k = 0; k < l3; k++) {
                        ResrceInfoVO resrceInfoVO3 = level_3.get(k);
                        if (resrceInfoVO3.getpResrce().equals(m2ResrceCd)) {

                            HashMap<String, Object> m3Header = new HashMap<>();
                            m3Header.put("level", 3);
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
                if (resrceInfoVO3.getpResrce().equals(resrceCd1)) {
                    m3Header.put("level", 2);
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

    /** 리소스 메뉴, 기능을 저장한다. */
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
                    LOGGER.info("insert : {}", r);

                    r.setUseYn(UseYn.Y);
                    r.setpResrce(pResrce);
                    procCnt += webMenuMapper.insertWebMenu(r);
                } else if (r.getStatus() == GridDataFg.UPDATE) {
                    LOGGER.info("update : {}", r);

                    procCnt += webMenuMapper.updateWebMenu(r);
                    throw new JsonException(Status.FAIL, "aaaaaaaaaaaaaaa");
                } else if (r.getStatus() == GridDataFg.DELETE) {
                    LOGGER.info("delete : {}", r);

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


