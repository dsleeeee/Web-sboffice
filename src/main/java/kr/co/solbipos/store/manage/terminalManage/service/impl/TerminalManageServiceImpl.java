package kr.co.solbipos.store.manage.terminalManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreTerminalVO;
import kr.co.solbipos.store.manage.terminalManage.service.TerminalManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TerminalManageServiceImpl.java
 * @Description : 가맹점관리 > 매장관리 > 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 10.06  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class TerminalManageServiceImpl implements TerminalManageService{


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final TerminalManageMapper mapper;
    private final String POS_ENVST_CD = "2028"; // 코너, VAN 설정 환경변수

    /** Constructor Injection */
    @Autowired
    public TerminalManageServiceImpl(TerminalManageMapper mapper) {

        this.mapper = mapper;
    }

    /** 벤더 조회 */
    @Override
    public List<DefaultMap<String>> getVendorList() {

        return mapper.getVendorList();
    }

    /** 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeManageVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.getStoreList(storeManageVO);
    }

    /** 포스/터미널 설정 환경변수 조회 */
    @Override
    public String getTerminalEnv(StoreEnvVO storeEnvVO) {

        storeEnvVO.setEnvstCd(POS_ENVST_CD);

        return mapper.getTerminalEnv(storeEnvVO);
    }

    /** 포스 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosList(StorePosVO storePosVO) {
        return mapper.getPosList(storePosVO);
    }

    /** 코너 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCornerList(StoreCornerVO storeCornerVO) {
        return mapper.getCornerList(storeCornerVO);
    }

    /** 포스 터미널 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosTerminalList(StoreTerminalVO storeTerminalVO) {
        return mapper.getPosTerminalList(storeTerminalVO);
    }

    /** 코너 터미널 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCornerTerminalList(StoreTerminalVO storeTerminalVO) {
        return mapper.getCornerTerminalList(storeTerminalVO);
    }

    /** 터미널 환경변수 값 저장*/
    @Override
    public int updateTerminalEnvst(StoreEnvVO storeEnvVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeEnvVO.setEnvstCd(POS_ENVST_CD);
        storeEnvVO.setRegDt(dt);
        storeEnvVO.setRegId(sessionInfoVO.getUserId());
        storeEnvVO.setModDt(dt);
        storeEnvVO.setModId(sessionInfoVO.getUserId());

        return mapper.updateTerminalEnvst(storeEnvVO);
    }

    /** 포스 터미널 정보 저장 */
    @Override
    public int savePosTerminalInfo(StoreTerminalVO[] storeTerminalVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(StoreTerminalVO storeTerminalVO :  storeTerminalVOs) {
            storeTerminalVO.setRegDt(dt);
            storeTerminalVO.setRegId(sessionInfoVO.getUserId());
            storeTerminalVO.setModDt(dt);
            storeTerminalVO.setModId(sessionInfoVO.getUserId());

            if(storeTerminalVO.getStatus() == GridDataFg.INSERT) {

                result += mapper.insertPosTerminalInfo(storeTerminalVO);

            } else if(storeTerminalVO.getStatus() == GridDataFg.UPDATE) {

                result += mapper.updatePosTerminalInfo(storeTerminalVO);
            }
        }
        return result;
    }


    /** 코너 터미널 정보 저장 */
    @Override
    public int saveCornerTerminalInfo(StoreTerminalVO[] storeTerminalVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(StoreTerminalVO storeTerminalVO : storeTerminalVOs) {

            storeTerminalVO.setRegDt(dt);
            storeTerminalVO.setRegId(sessionInfoVO.getUserId());
            storeTerminalVO.setModDt(dt);
            storeTerminalVO.setModId(sessionInfoVO.getUserId());

            if(storeTerminalVO.getStatus() == GridDataFg.INSERT) {

                result += mapper.insertCornerTerminalInfo(storeTerminalVO);

            } else if (storeTerminalVO.getStatus() == GridDataFg.UPDATE) {

                result += mapper.updateCornerTerminalInfo(storeTerminalVO);
            }
        }

        return result;
    }

}
