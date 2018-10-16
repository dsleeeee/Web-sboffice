package kr.co.common.utils.jsp;

import kr.co.common.data.domain.EnvCodeVO;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @Class Name : CmmEnvUtil.java
 * @Description : 환경변수 관련 유틸
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.22  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Component("cmmEnvUtil")
public class CmmEnvUtil {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvService cmmEnvService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public CmmEnvUtil(CmmCodeUtil cmmCodeUtil, CmmEnvService cmmEnvService, SessionService sessionService) {
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvService = cmmEnvService;
        this.sessionService = sessionService;
    }

    /**
     * 환경변수 공통코드 조회 (TB_CM_ENVST_DTL)
     *
     * @param envstCd
     * @return
     */
    public String getEnvCode(String envstCd) {

        EnvCodeVO envCodeVO = getEnvCodeData(envstCd);
        if(envCodeVO == null) {
            return cmmCodeUtil.assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return cmmCodeUtil.assmblObj(envCodeVO.getCodeList(), "envstValNm", "envstValCd", UseYn.ALL);
    }

    /**
     * 환경변수 공통코드 조회  "ALL" 제외 (TB_CM_ENVST_DTL)
     *
     * @param envstCd
     * @return
     */
    public String getEnvCodeExcpAll(String envstCd) {

        EnvCodeVO envCodeVO = getEnvCodeData(envstCd);
        if(envCodeVO == null) {
            return cmmCodeUtil.assmblEmptyCombo();
        }
        // 결과 형태를 만들어서 json 으로 리턴
        return cmmCodeUtil.assmblObj(envCodeVO.getCodeList(), "envstValNm", "envstValCd", UseYn.N);
    }

    /**
     * 환경변수 코드 조회 (select box용 list 데이터)
     *
     * @param envstCd (TB_CM_ENVST.ENVST_CD)
     * @return EnvCodeVO (TB_CM_ENVST_DTL)
     */
    private EnvCodeVO getEnvCodeData(String envstCd) {

        EnvCodeVO envCodeVO = new EnvCodeVO();

        envCodeVO.setEnvstCd(envstCd);

        List<DefaultMap<String>> codeList = null;

        codeList = cmmEnvService.selectEnvCodeList(envstCd);

        if (isEmpty(codeList)) { // 조회 결과 없으면 데이터 없음 리턴
            return null;
        }

        envCodeVO.setCodeList(codeList);

        return envCodeVO;
    }

    /**
     * 본사 환경변수 테이블에 등록된 값 조회
     * (시스템 권한 : 가맹점관리 > 본사관리 > 환경설정에서 환경변수 값 저장 후 사용)
     *
     * @param sessionInfoVO, envstCd
     * @return String (TB_HQ_ENVST)
     */
    public String getHqEnvst(SessionInfoVO sessionInfoVO, String envstCd) {

        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd(envstCd);

        return cmmEnvService.getHqEnvst(hqEnvstVO);
    }

    /**
     * 매장 환경변수 테이블에 등록된 값 조회
     *
     * @param sessionInfoVO, envstCd
     * @return String (TB_MS_STORE_ENVST)
     */
    public String getStoreEnvst(SessionInfoVO sessionInfoVO, String envstCd) {

        StoreEnvVO storeEnvVO = new StoreEnvVO();
        storeEnvVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeEnvVO.setEnvstCd(envstCd);

        return cmmEnvService.getStoreEnvst(storeEnvVO);
    }

    /** 환경변수명 조회 */
    public String getEnvNm(EnvstVO envstVO) {
        return cmmEnvService.getEnvNm(envstVO);
    }

}
