package kr.co.solbipos.store.manage.terminalManage.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosVO;
import kr.co.solbipos.store.manage.storemanage.service.impl.StoreManageMapper;
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
    private final StoreManageMapper storeManageMapper;
    private final String POS_ENVST_CD = "2028"; // 코너, VAN 설정 환경변수

    /** Constructor Injection */
    @Autowired
    public TerminalManageServiceImpl(TerminalManageMapper mapper, StoreManageMapper storeManageMapper) {

        this.mapper = mapper;
        this.storeManageMapper = storeManageMapper;
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
    public List<DefaultMap<String>> getPosTerminalList(StoreTerminalVO storeTerminalVO, SessionInfoVO sessionInfoVO) {
        storeTerminalVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeTerminalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
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

                // 포스 대표 터미널 정보 수정시 코너 대표 터미널에 정보 merge
                if("Y".equals(storeTerminalVO.getBaseVanYn())) {

                    storeTerminalVO.setPosNo("01");
                    storeTerminalVO.setCornrCd("01");
                    storeTerminalVO.setVendorFg("01");
                    result += storeManageMapper.insertCornerTerminal(storeTerminalVO);
                }

            } else if(storeTerminalVO.getStatus() == GridDataFg.DELETE) {

                result += mapper.deletePosTerminalInfo(storeTerminalVO);
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

            // 코너 정보 셋팅
            StoreCornerVO storeCornerVO = new StoreCornerVO();
            storeCornerVO.setStoreCd(storeTerminalVO.getStoreCd());
            storeCornerVO.setCornrCd(storeTerminalVO.getCornrCd());
            storeCornerVO.setCornrNm(storeTerminalVO.getCornrNm());
            storeCornerVO.setOwnerNm(storeTerminalVO.getOwnerNm());
            storeCornerVO.setBizNo(storeTerminalVO.getBizNo());
            storeCornerVO.setTelNo(storeTerminalVO.getTelNo());
            storeCornerVO.setUseYn(UseYn.Y.getCode());
            storeCornerVO.setBaseYn(storeTerminalVO.getBaseYn());
            storeCornerVO.setRegDt(dt);
            storeCornerVO.setRegId(sessionInfoVO.getUserId());
            storeCornerVO.setModDt(dt);
            storeCornerVO.setModId(sessionInfoVO.getUserId());

            if(storeTerminalVO.getStatus() == GridDataFg.INSERT) {

                if (storeCornerVO.getCornrCd().isEmpty()) {

                    // 코너 코드 생성
                    storeCornerVO.setCornrCd(mapper.getCornerCd(storeTerminalVO));
                    result += mapper.insertCorner(storeCornerVO); // 코너 등록

                    // 터미널 정보에도 코너 코트 셋팅
                    storeTerminalVO.setCornrCd(storeCornerVO.getCornrCd());
                }

                result += mapper.insertCornerTerminalInfo(storeTerminalVO);

            } else if (storeTerminalVO.getStatus() == GridDataFg.UPDATE) {

                if("1".equals(storeTerminalVO.getCornrRnum())) {
                    result += mapper.updateCorner(storeCornerVO); // 코너 수정
                }

                result += mapper.updateCornerTerminalInfo(storeTerminalVO);

                // 코너 대표 터미널 정보 수정시 포스 대표 터미널에 정보 merge
                if("1".equals(storeTerminalVO.getCornrRnum()) && "Y".equals(storeTerminalVO.getBaseVanYn())) {

                    storeTerminalVO.setPosNo("01");
                    storeTerminalVO.setCornrCd("01");
                    storeTerminalVO.setVendorFg("01");
                    result += mapper.mergePosTerminalInfo(storeTerminalVO);
                }

            } else if(storeTerminalVO.getStatus() == GridDataFg.DELETE) {

                result += mapper.deleteCornerTerminalInfo(storeTerminalVO);

                if("1".equals(storeTerminalVO.getCornrRnum()) && !"Y".equals(storeTerminalVO.getBaseVanYn())) {
                    result += mapper.deleteCorner(storeCornerVO); // 코너 삭제
                }
            }
        }

        return result;
    }

    /** 코너 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCornerDtlList(StoreCornerVO storeCornerVO) {
        return mapper.getCornerDtlList(storeCornerVO);
    }

    /** 코너 저장 */
    @Override
    public int insertCorner(StoreCornerVO[] storeCornerVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(StoreCornerVO storeCornerVO : storeCornerVOs) {

            storeCornerVO.setUseYn(UseYn.Y.getCode());
            storeCornerVO.setRegDt(dt);
            storeCornerVO.setRegId(sessionInfoVO.getUserId());
            storeCornerVO.setModDt(dt);
            storeCornerVO.setModId(sessionInfoVO.getUserId());

            if(storeCornerVO.getStatus() == GridDataFg.INSERT) {
                result += mapper.insertCorner(storeCornerVO); // 등록

            } else if (storeCornerVO.getStatus() == GridDataFg.UPDATE) {
                result += mapper.updateCorner(storeCornerVO); // 수정

            } else if(storeCornerVO.getStatus() == GridDataFg.DELETE) {
                result += mapper.deleteCorner(storeCornerVO); // 삭제
            }
        }

        return result;
    }

    /** 매장터미널관리 조회 */
    @Override
    public List<DefaultMap<Object>> getTerminalManageList(StoreTerminalVO storeTerminalVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        storeTerminalVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            storeTerminalVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            storeTerminalVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        return mapper.getTerminalManageList(storeTerminalVO);
    }


    /** 터미널 정보 복사 */
    @Override
    public int copyTerminalInfo(StoreTerminalVO storeTerminalVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        storeTerminalVO.setRegDt(dt);
        storeTerminalVO.setRegId(sessionInfoVO.getUserId());
        storeTerminalVO.setModDt(dt);
        storeTerminalVO.setModId(sessionInfoVO.getUserId());

        // 기존터미널정보 삭제
        result += mapper.deleteTerminalInfo(storeTerminalVO);
        
        // 터미널정보 복사
        result += mapper.copyTerminalInfo(storeTerminalVO);

        return result;
    }

    /** 터미널 콤보박스(코너사용설정) 선택값에 따른 터미널 환경설정 저장 */
    @Override
    public int chgTerminalEnv(StoreEnvVO storeEnvVO, SessionInfoVO sessionInfoVO){

        int procCnt = 0;
        String dt = currentDateTimeString();

        storeEnvVO.setRegDt(dt);
        storeEnvVO.setRegId(sessionInfoVO.getUserId());
        storeEnvVO.setModDt(dt);
        storeEnvVO.setModId(sessionInfoVO.getUserId());
        // [2028] 코너사용설정 저장
        procCnt += mapper.updateTerminalEnvst(storeEnvVO);

        // [1337] 다중사업자사용여부 저장
        storeEnvVO.setEnvstCd("1337");
        if("2".equals(storeEnvVO.getEnvstVal())) {
            storeEnvVO.setEnvstVal("1"); // 사용
        }else if("3".equals(storeEnvVO.getEnvstVal())){
            storeEnvVO.setEnvstVal("0"); // 미사용
        }
        procCnt += mapper.updateTerminalEnvst(storeEnvVO);


        // [1337] 다중사업자사용여부 '사용' 시
        if("1".equals(storeEnvVO.getEnvstVal())){

            // 매장 사업자번호 조회
            StoreManageVO storeManageVO = new StoreManageVO();
            storeManageVO.setHqOfficeCd(storeEnvVO.getHqOfficeCd());
            storeManageVO.setStoreCd(storeEnvVO.getStoreCd());
            DefaultMap<String> storeDtlInfo = storeManageMapper.getStoreDetail(storeManageVO);

            // 01번 대표코너 생성
            StoreCornerVO storeCornerVO = new StoreCornerVO();
            storeCornerVO.setStoreCd(storeEnvVO.getStoreCd());
            storeCornerVO.setCornrCd("01");
            storeCornerVO.setCornrNm("대표코너");
            storeCornerVO.setBizNo(storeDtlInfo.getStr("bizNo"));
            storeCornerVO.setUseYn(UseYn.Y.getCode());
            storeCornerVO.setBaseYn("Y");
            storeCornerVO.setRegDt(dt);
            storeCornerVO.setRegId(sessionInfoVO.getUserId());
            storeCornerVO.setModDt(dt);
            storeCornerVO.setModId(sessionInfoVO.getUserId());
            procCnt += storeManageMapper.insertBaseCorner(storeCornerVO);

            // 01번 코너터미널 생성
            StoreTerminalVO storeTerminalVO = new StoreTerminalVO();
            storeTerminalVO.setStoreCd(storeEnvVO.getStoreCd());
            storeTerminalVO.setPosNo("01");
            storeTerminalVO.setCornrCd("01");
            storeTerminalVO.setVendorFg("01");
            storeTerminalVO.setRegDt(dt);
            storeTerminalVO.setRegId(sessionInfoVO.getUserId());
            storeTerminalVO.setModDt(dt);
            storeTerminalVO.setModId(sessionInfoVO.getUserId());
            storeTerminalVO.setBaseVanYn("Y");
            procCnt += storeManageMapper.insertCornerTerminal(storeTerminalVO);
        }

        return procCnt;
    }

}
