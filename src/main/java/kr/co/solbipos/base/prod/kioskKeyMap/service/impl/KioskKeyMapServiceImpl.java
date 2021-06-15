package kr.co.solbipos.base.prod.kioskKeyMap.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskKeyMapServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 키오스크키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("kioskKeyMapService")
public class KioskKeyMapServiceImpl implements KioskKeyMapService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KioskKeyMapMapper kioskKeyMapMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public KioskKeyMapServiceImpl(KioskKeyMapMapper kioskKeyMapMapper,  MessageService messageService, CmmEnvUtil cmmEnvUtil) {

        this.kioskKeyMapMapper = kioskKeyMapMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 키오스크용 포스 조회 */
    @Override
    public List<DefaultMap<String>> getKioskPosList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return kioskKeyMapMapper.getKioskPosList(kioskKeyMapVO);
    }

    /** 키오스크 키맵그룹 조회 */
    @Override
    public List<DefaultMap<String>> getKioskTuClsTypeList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskTuClsTypeList(kioskKeyMapVO);
    }

    /** 키오스크 카테고리(분류) 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskCategory(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskCategory(kioskKeyMapVO);
    }

    /** 키오스크 카테고리(분류) 저장 */
    @Override
    public int saveKioskCategory(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(kioskKeyMapVO.getIndexNo());
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 4) + 1)));

            if ( kioskKeyMapVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 분류코드 생성
                kioskKeyMapVO.setTuClsCd(kioskKeyMapMapper.getKioskCategoryCode(kioskKeyMapVO));

                result += kioskKeyMapMapper.insertKioskCategory(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += kioskKeyMapMapper.updateKioskCategory(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += kioskKeyMapMapper.deleteKioskCategory(kioskKeyMapVO);

                // 해당 카테고리(분류)에 해당하는 상품도 삭제
                kioskKeyMapMapper.deleteAllKioskKeyMap(kioskKeyMapVO);
            }
        }

        if ( result == kioskKeyMapVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 키오스크 키맵 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskKeyMap(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskKeyMap(kioskKeyMapVO);
    }

    /** 키오스크 키맵 수정 */
    @Override
    public int updateKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 페이지 수 계산
            int indexNo = Integer.parseInt(kioskKeyMapVO.getIndexNo());
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += kioskKeyMapMapper.updateKioskKeyMap(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += kioskKeyMapMapper.deleteKioskKeyMap(kioskKeyMapVO);
            }
        }

        if ( result == kioskKeyMapVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /**  키오스크 상품 조회 */
    @Override
    public List<DefaultMap<String>> getKioskProdList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskProdList(kioskKeyMapVO);
    }

    /** 키오스크 키맵 등록 */
    @Override
    public int saveKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int procCnt = 0;

        for(KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            kioskKeyMapVO.setClsFg("K"); // K: KIOSK
            kioskKeyMapVO.setRegDt(dt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(dt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 키오스크 키 관련 코드 조회
            DefaultMap<String> keyValue = kioskKeyMapMapper.getKioskKeyMapCode(kioskKeyMapVO);

            kioskKeyMapVO.setTuKeyCd(keyValue.get("tuKeyCd"));
            kioskKeyMapVO.setIndexNo(String.valueOf(keyValue.get("indexNo")));

            // 페이지 수 계산
            int indexNo = Integer.parseInt(String.valueOf(keyValue.get("indexNo")));
            kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 16) + 1)));

            int result = kioskKeyMapMapper.saveKioskKeyMap(kioskKeyMapVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return procCnt;

    }

    /** 키오스크 키맵 신규그룹추가 */
    @Override
    public int createKioskTuClsType(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        kioskKeyMapVO.setClsFg("K"); // K: KIOSK
        kioskKeyMapVO.setRegDt(currentDt);
        kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
        kioskKeyMapVO.setModDt(currentDt);
        kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

        // 키맵 새 그룹코드 생성
        kioskKeyMapVO.setTuClsType(kioskKeyMapMapper.getKiosTuClsTypeCode(kioskKeyMapVO));

        // 카테고리 새 분류코드 생성
        kioskKeyMapVO.setTuClsCd(kioskKeyMapMapper.getKioskCategoryCode(kioskKeyMapVO));

        // 페이지 수 계산
        int indexNo = Integer.parseInt(kioskKeyMapVO.getIndexNo());
        kioskKeyMapVO.setTuPage(Integer.toString((int)(Math.floor((indexNo - 1) / 4) + 1)));

        result = kioskKeyMapMapper.insertKioskCategory(kioskKeyMapVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 키오스크 키맵 그룹복제 */
    @Override
    public int copyKioskTuClsType(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        kioskKeyMapVO.setRegDt(currentDt);
        kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
        kioskKeyMapVO.setModDt(currentDt);
        kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

        // 새 키맵그룹 코드 생성
        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
        kioskKeyMapVO.setTuClsType(kioskKeyMapMapper.getKiosTuClsTypeCode(kioskKeyMapVO));

        // 기존 키맵그룹으로 새 키맵그룹 등록(복제)
        result = kioskKeyMapMapper.copyKioskCategory(kioskKeyMapVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 기존 키맵그룹에 맵핑된 상품이 있으면 상품도 새 키맵그룹에 등록(복제)
        result = kioskKeyMapMapper.copyKioskKeyMap(kioskKeyMapVO);
        if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 키오스크 키맵매장적용 - 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return kioskKeyMapMapper.getStoreList(kioskKeyMapVO);
    }

    /** 키오스크 키맵매장적용 */
    @Override
    public int saveKioskKeyMapStore(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 기존 카테고리(분류) 삭제
            kioskKeyMapMapper.deleteStoreTuClsType(kioskKeyMapVO);

            // 기존 키맵상품삭제
            kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd(kioskKeyMapVO);

            // posNo 조회
            List<DefaultMap<String>> posList = kioskKeyMapMapper.getKioskPosList(kioskKeyMapVO);

            if(posList.size() > 0){

                for (int i = 0; i < posList.size(); i++) {

                    kioskKeyMapVO.setPosNo(posList.get(i).getStr("value"));

                    // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                    result = kioskKeyMapMapper.insertKioskCategoryStoreReg(kioskKeyMapVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                    result = kioskKeyMapMapper.copyKioskKeyMapStoreReg(kioskKeyMapVO);
                    if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                }
            }
        }

        return result;
    }

    /** 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 리스트 조회 */
    public List<DefaultMap<String>> getStoreKioskPosList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return kioskKeyMapMapper.getStoreKioskPosList(kioskKeyMapVO);
    }

    /** 키오스크 매장적용(매장/포장) - 본사/매장 환경설정값 저장 */
    @Override
    public int saveHqStoreKioskPosEnv(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        // 본사 환경설정값 저장 (매장 일괄 적용 시, 항상 본사 환경설정도 update 해준다)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {

            KioskKeyMapVO hqKioskKeyMapVO = new KioskKeyMapVO();
            hqKioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqKioskKeyMapVO.setEnvstCd(kioskKeyMapVOs[0].getEnvstCd());
            hqKioskKeyMapVO.setEnvstVal(kioskKeyMapVOs[0].getEnvstVal());
            hqKioskKeyMapVO.setDirctInYn("Y");
            hqKioskKeyMapVO.setUseYn("Y");
            hqKioskKeyMapVO.setRegDt(currentDt);
            hqKioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            hqKioskKeyMapVO.setModDt(currentDt);
            hqKioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            result = kioskKeyMapMapper.insertHqKioskEnv(hqKioskKeyMapVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // 매장 키오스크 포스 환경설정값 일괄 저장
        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setDirctInYn("Y");
            kioskKeyMapVO.setPosFg("W"); // WEB
            kioskKeyMapVO.setUseYn("Y");
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 매장 키오스크 포스 환경설정값 저장
            result = kioskKeyMapMapper.insertStoreKioskPosEnv(kioskKeyMapVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 키맵매장적용
            if("Y".equals(kioskKeyMapVO.getChkTuClsTypeStore())){

                // 기존 카테고리(분류) 삭제
                kioskKeyMapMapper.deleteStoreTuClsType(kioskKeyMapVO);

                // 기존 키맵상품삭제
                kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd(kioskKeyMapVO);

                // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                result = kioskKeyMapMapper.insertKioskCategoryStoreReg(kioskKeyMapVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                result = kioskKeyMapMapper.copyKioskKeyMapStoreReg(kioskKeyMapVO);
                if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            }

        }

        return result;
    }

    /** 키오스크 매장적용(매장/포장) - 키오스크 환경설정 값 가져오기 */
    @Override
    public String getKioskEnv(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO){

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskEnv(kioskKeyMapVO);
    }

}
