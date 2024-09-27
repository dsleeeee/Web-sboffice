package kr.co.solbipos.base.prod.kioskKeyMap.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.tools.StandardLocation;
import java.util.ArrayList;
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

    /** 키오스크 키맵그룹 조회 */
    @Override
    public List<DefaultMap<String>> getStoreModGrpList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getStoreModGrpList(kioskKeyMapVO);
    }

    /** 키오스크 키맵그룹 저장 */
    @Override
    public int saveStoreModGrp(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs ) {

            // 소속구분 설정
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 기본입력정보 설정
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 매장에 터치키 XML 정보 업데이트
            result += kioskKeyMapMapper.saveStoreModGrp(kioskKeyMapVO);

        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 키오스크 터치키그룹 조회 */
    @Override
    public List<DefaultMap<String>> getClsTypeList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());

        return kioskKeyMapMapper.getClsTypeList(kioskKeyMapVO);
    }

    /** 키오스크 터치키그룹 저장 */
    @Override
    public int saveClsType(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs ) {
            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            // 소속구분 설정
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            // 기본입력정보 설정
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 매장에 터치키 XML 정보 업데이트
            result += kioskKeyMapMapper.saveClsType(kioskKeyMapVO);

            System.out.println("본사"+sessionInfoVO.getOrgnFg().getCode());
            // 그룹명 추가 및 수정
            if(sessionInfoVO.getOrgnFg().getCode() != null && sessionInfoVO.getOrgnFg().getCode() == "H"){
                result = kioskKeyMapMapper.updateStoreGrpNm(kioskKeyMapVO);
            }

        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
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
                // 중분류 사용
                if(kioskKeyMapVO.getTuMClsFg().equals("2")) {
                    // 카테고리 삭제 시, (중분류 사용) 카테고리에 속한 상품도 삭제
                    kioskKeyMapMapper.deleteAllKioskKeyMapM(kioskKeyMapVO);

                    // 카테고리 삭제 시, (중분류 사용) 카테고리에 속한 중분류도 삭제
                    kioskKeyMapMapper.deleteAllKioskCategoryM(kioskKeyMapVO);
                }
                // 중분류 미사용
                else if (kioskKeyMapVO.getTuMClsFg().equals("0")) {
                    // 카테고리 삭제 시, 카테고리에 속한 키맵도 삭제
                    kioskKeyMapMapper.deleteAllKioskKeyMap(kioskKeyMapVO);
                }
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
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

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
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

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (kioskKeyMapVO.getProdHqBrandCd() == "" || kioskKeyMapVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskKeyMapVO.getUserProdBrands() != null && !"".equals(kioskKeyMapVO.getUserProdBrands())) {
                    String[] userBrandList = kioskKeyMapVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskKeyMapVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
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

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
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

        kioskKeyMapMapper.saveClsType(kioskKeyMapVO);

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

        kioskKeyMapMapper.copyKioskTuClsTypeNm(kioskKeyMapVO);

        // 기존 키맵그룹으로 새 키맵그룹 등록(현재 포스로 복제)
        result = kioskKeyMapMapper.copyKioskCategory(kioskKeyMapVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 중분류 키맵그룹 row count 조회
        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();
        selectList = kioskKeyMapMapper.getKioskMClsCount(kioskKeyMapVO);
        if(Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
            // 중분류도 복제
            result = kioskKeyMapMapper.copyKioskCategoryM(kioskKeyMapVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // 기존 키맵그룹에 맵핑된 상품이 있으면 상품도 새 키맵그룹에 등록(현재 포스로 복제)
        result = kioskKeyMapMapper.copyKioskKeyMap(kioskKeyMapVO);
        if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    @Override
    public int copyStoreKioskTuClsType(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        kioskKeyMapVO.setRegDt(currentDt);
        kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
        kioskKeyMapVO.setModDt(currentDt);
        kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

        // 기존 키맵 삭제
        result = kioskKeyMapMapper.deletePosStoreKioskCategory(kioskKeyMapVO);
        result = kioskKeyMapMapper.deletePosStoreTuClsType(kioskKeyMapVO);

        // 선택한 키맵 저장
        // 기존 키맵그룹으로 새 키맵그룹 등록(다른 포스로 복제)
        result = kioskKeyMapMapper.copyPosKioskCategory(kioskKeyMapVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        kioskKeyMapMapper.copyKioskTuClsTypeNm(kioskKeyMapVO);

        // 기존 키맵그룹에 맵핑된 상품이 있으면 상품도 새 키맵그룹에 등록(다른 포스로 복제)
        result = kioskKeyMapMapper.copyPosKioskKeyMap(kioskKeyMapVO);
        if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 키오스크 키맵매장적용 - 매장리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (kioskKeyMapVO.getStoreHqBrandCd() == "" || kioskKeyMapVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskKeyMapVO.getUserBrands() != null && !"".equals(kioskKeyMapVO.getUserBrands())) {
                    String[] userBrandList = kioskKeyMapVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskKeyMapVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

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

            sessionInfoVO.setStoreCd(kioskKeyMapVO.getStoreCd());

            // KIOSK-매장수정여부(0:미사용, 1:사용, 2:사용(특정카테고리))
            String envstVal1249 = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1249"), "0");

            // 매장적용할 키맵그룹 코드를 담을 list 형태의 변수
            List<DefaultMap<Object>> tuClsTypeEnvList;

            if("00".equals(kioskKeyMapVO.getTuClsType())) { // [00]사용중인키맵매장적용을 선택한 경우
                // 매장 키오스크포스에서 사용중인 [매장/포장]키맵그룹 코드 조회
                tuClsTypeEnvList = kioskKeyMapMapper.getTuClsTypeEnvList(kioskKeyMapVO);
            } else {
                // 선택한 키맵그룹을 list 변수에 담기
                List<DefaultMap<Object>> list = new ArrayList<DefaultMap<Object>>();
                DefaultMap<Object> m = new DefaultMap<>();
                m.put("env40684069", kioskKeyMapVO.getTuClsType());
                list.add(m);

                tuClsTypeEnvList = list;
            }

            for (int i = 0; i < tuClsTypeEnvList.size(); i++) {

                // 키맵그룹 코드 셋팅
                kioskKeyMapVO.setTuClsType(tuClsTypeEnvList.get(i).getStr("env40684069"));

                // 맘스터치는 기존로직 적용(delete -> insert-select), 나머지 본사는 새 로직 적용(merge -> delete) (20240909)
                if(kioskKeyMapVO.getHqOfficeCd().equals("DS021") || kioskKeyMapVO.getHqOfficeCd().equals("DS034") || kioskKeyMapVO.getHqOfficeCd().equals("H0393")){

                    // 기존 키맵분류명 삭제
                    kioskKeyMapMapper.deleteStoreTuClsTypeNm(kioskKeyMapVO);

                    // 기존 카테고리(분류) 삭제
                    kioskKeyMapMapper.deleteStoreTuClsType(kioskKeyMapVO);

                    // 1249 0/1이면 전체 삭제
                    // 1249 2이면 229 Y 제외 삭제
                    if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                        // 기존 키맵상품삭제
                        kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd(kioskKeyMapVO);
                        // 기존 카테고리(중분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeM(kioskKeyMapVO);
                    } else  if(envstVal1249.equals("2")) {
                        // 기존 키맵상품삭제
                        kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd2(kioskKeyMapVO);
                        // 기존 카테고리(중분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeM2(kioskKeyMapVO);
                    }


                    // posNo 조회
                    //            List<DefaultMap<String>> posList = kioskKeyMapMapper.getKioskPosList(kioskKeyMapVO);

                    //            if(posList.size() > 0){

                    //                for (int i = 0; i < posList.size(); i++) {

                    //                    kioskKeyMapVO.setPosNo(posList.get(i).getStr("value"));

                    // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                    result = kioskKeyMapMapper.insertKioskCategoryStoreReg(kioskKeyMapVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    result = kioskKeyMapMapper.copyKioskStoreTuClsTypeNm(kioskKeyMapVO);

                    // 1249 0/1이면 전체 삭제
                    // 1249 2이면 229 Y 제외 삭제
                    if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.copyKioskKeyMapStoreReg(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 중분류 키맵그룹 row count 조회
                        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.insertKioskCategoryStoreRegM(kioskKeyMapVO);
                            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                        //                }
                        //            }
                    } else  if(envstVal1249.equals("2")) {

                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.copyKioskKeyMapStoreReg2(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 중분류 키맵그룹 row count 조회
                        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount2(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.insertKioskCategoryStoreRegM2(kioskKeyMapVO);
                            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    }

                }else{

                    // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                    result = kioskKeyMapMapper.mergeKioskCategoryStoreReg(kioskKeyMapVO);
                    if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    result = kioskKeyMapMapper.mergeKioskStoreTuClsTypeNm(kioskKeyMapVO);

                    // 중분류 사용여부 조회를 위한 변수
                    List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

                    // 1249 0/1이면 전체 삭제
                    // 1249 2이면 229 Y 제외 삭제
                    if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.mergeKioskKeyMapStoreReg(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 중분류 키맵그룹 row count 조회
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.mergeKioskCategoryStoreRegM(kioskKeyMapVO);
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                        //                }
                        //            }
                    } else  if(envstVal1249.equals("2")) {

                        // 현재는 대분류만 사용할 경우만 가능, 중분류 사용은 '매장수정허용카테고리' 먼저 수정 후 개발 예정

                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.mergeKioskKeyMapStoreReg2(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        /*
                        // 중분류 키맵그룹 row count 조회
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount2(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.mergeKioskCategoryStoreRegM2(kioskKeyMapVO);
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                        */
                    }

                    // 기존 데이터 중, 불필요한 매장 카테고리(분류) 정보 삭제
                    kioskKeyMapMapper.deleteNotUseKioskCategoryToStore(kioskKeyMapVO);
                    if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                        // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                        kioskKeyMapMapper.deleteNotUseKioskKeyMapToStore(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 삭제
                            kioskKeyMapMapper.deleteNotUseKioskCategoryToStoreM(kioskKeyMapVO);
                        }
                    } else  if(envstVal1249.equals("2")) {

                        // 현재는 대분류만 사용할 경우만 가능, 중분류 사용은 '매장수정허용카테고리' 먼저 수정 후 개발 예정

                        // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                        kioskKeyMapMapper.deleteNotUseKioskKeyMapToStore2(kioskKeyMapVO);

                        /*
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 삭제
                            kioskKeyMapMapper.deleteNotUseKioskCategoryToStoreM2(kioskKeyMapVO);
                        }
                        */
                    }
                }
            }

            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
        }

        return result;
    }

    /** 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 리스트 조회 */
    public List<DefaultMap<String>> getStoreKioskPosList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (kioskKeyMapVO.getStoreHqBrandCd() == "" || kioskKeyMapVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskKeyMapVO.getUserBrands() != null && !"".equals(kioskKeyMapVO.getUserBrands())) {
                    String[] userBrandList = kioskKeyMapVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskKeyMapVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return kioskKeyMapMapper.getStoreKioskPosList(kioskKeyMapVO);
    }

    /** 키오스크 매장적용(매장/포장) - 본사/매장 환경설정값 저장 */
    @Override
    public int saveHqStoreKioskPosEnv(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        // 본사 환경설정값 저장 (매장 일괄 적용 시, 항상 본사 환경설정도 update 해준다)
        /*if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {

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

            // 20230406 키맵적용 기능 중 TB_HQ_ENVST 4068,4069 저장하는 로직 제거
            //result = kioskKeyMapMapper.insertHqKioskEnv(hqKioskKeyMapVO);
            //if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        // 매장 키오스크 포스 환경설정값 일괄 저장
        // 맘스 : [매장/포장] 키맵적용 본사권한만 가능, 맘스제외 : [매장/포장] 키맵적용 본사권한 가능, 매장권한은 환경설정값만 바꿀 수 있음(2024.09.26)
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
            if("Y".equals(kioskKeyMapVO.getChkTuClsTypeStore())) {
                /*if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){*/

                sessionInfoVO.setStoreCd(kioskKeyMapVO.getStoreCd());

                // KIOSK-매장수정여부(0:미사용, 1:사용, 2:사용(특정카테고리))
                String envstVal1249 = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1249"), "0");

                // 맘스터치는 기존로직 적용(delete -> insert-select), 나머지 본사는 새 로직 적용(merge -> delete) (20240909)
                if (kioskKeyMapVO.getHqOfficeCd().equals("DS021") || kioskKeyMapVO.getHqOfficeCd().equals("DS034") || kioskKeyMapVO.getHqOfficeCd().equals("H0393")) {

                    // 기존 카테고리(분류) 삭제
                    kioskKeyMapMapper.deleteStoreTuClsType(kioskKeyMapVO);
                    // 1249 0/1이면 전체 삭제
                    // 1249 2이면 229 Y 제외 삭제
                    if (envstVal1249.equals("0") || envstVal1249.equals("1")) {

                        // 기존 키맵상품삭제
                        kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd(kioskKeyMapVO);

                        // 기존 카테고리(중분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeM(kioskKeyMapVO);
                    } else if (envstVal1249.equals("2")) {
                        // 기존 키맵상품삭제
                        kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd2(kioskKeyMapVO);

                        // 기존 카테고리(중분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeM2(kioskKeyMapVO);
                    }

                    if (sessionInfoVO.getOrgnFg().getCode() != null && sessionInfoVO.getOrgnFg().getCode() == "H") {
                        System.out.println("본사" + sessionInfoVO.getOrgnFg().getCode());
                        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                        // 매장그룹명 INSERT
                        result = kioskKeyMapMapper.insertStoreGrpNmReg(kioskKeyMapVO);
                        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }

                    // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                    result = kioskKeyMapMapper.insertKioskCategoryStoreReg(kioskKeyMapVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // 1249 0/1이면 전체 삭제
                    // 1249 2이면 229 Y 제외 삭제
                    if (envstVal1249.equals("0") || envstVal1249.equals("1")) {
                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.copyKioskKeyMapStoreReg(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

                        // 중분류 키맵그룹 row count 조회
                        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.insertKioskCategoryStoreRegM(kioskKeyMapVO);
                            if (result <= 0)
                                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    } else if (envstVal1249.equals("2")) {
                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.copyKioskKeyMapStoreReg2(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

                        // 중분류 키맵그룹 row count 조회
                        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount2(kioskKeyMapVO);
                        if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.insertKioskCategoryStoreRegM2(kioskKeyMapVO);
                            if (result <= 0)
                                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    }
                    /*} else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){

                        // 기존 카테고리(분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsType(kioskKeyMapVO);

                        // 기존 키맵상품삭제
                        kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd(kioskKeyMapVO);

                        // 기존 카테고리(중분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeM(kioskKeyMapVO);

                        // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                        result = kioskKeyMapMapper.insertKioskCategoryStoreReg(kioskKeyMapVO);
                        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.copyKioskKeyMapStoreReg(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

                        // 중분류 키맵그룹 row count 조회
                        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();
                        kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                        selectList = kioskKeyMapMapper.getKioskMClsCount(kioskKeyMapVO);
                        if(Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.insertKioskCategoryStoreRegM(kioskKeyMapVO);
                            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    }*/
                    }else{

                        if (sessionInfoVO.getOrgnFg().getCode() != null && sessionInfoVO.getOrgnFg().getCode() == "H") {
                            System.out.println("본사" + sessionInfoVO.getOrgnFg().getCode());
                            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                            // 매장그룹명 INSERT
                            result = kioskKeyMapMapper.insertStoreGrpNmReg(kioskKeyMapVO);
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }

                        // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                        result = kioskKeyMapMapper.mergeKioskCategoryStoreReg(kioskKeyMapVO);
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 중분류 사용여부 조회를 위한 변수
                        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

                        // 1249 0/1이면 전체 삭제
                        // 1249 2이면 229 Y 제외 삭제
                        if (envstVal1249.equals("0") || envstVal1249.equals("1")) {
                            // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                            result = kioskKeyMapMapper.mergeKioskKeyMapStoreReg(kioskKeyMapVO);
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

                            // 중분류 키맵그룹 row count 조회
                            kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                            selectList = kioskKeyMapMapper.getKioskMClsCount(kioskKeyMapVO);
                            if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                                // 중분류도 복제
                                result = kioskKeyMapMapper.mergeKioskCategoryStoreRegM(kioskKeyMapVO);
                                if (result < 0)
                                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                            }
                        } else if (envstVal1249.equals("2")) {

                            // 현재는 대분류만 사용할 경우만 가능, 중분류 사용은 '매장수정허용카테고리' 먼저 수정 후 개발 예정

                            // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                            result = kioskKeyMapMapper.mergeKioskKeyMapStoreReg2(kioskKeyMapVO);
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

                            /*
                            // 중분류 키맵그룹 row count 조회
                            kioskKeyMapVO.setOrgTuClsType(kioskKeyMapVO.getTuClsType());
                            selectList = kioskKeyMapMapper.getKioskMClsCount2(kioskKeyMapVO);
                            if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                                // 중분류도 복제
                                result = kioskKeyMapMapper.mergeKioskCategoryStoreRegM2(kioskKeyMapVO);
                                if (result <= 0)
                                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                            }
                            */
                        }

                        // 기존 데이터 중, 불필요한 매장 카테고리(분류) 정보 삭제
                        kioskKeyMapMapper.deleteNotUseKioskCategoryToStore(kioskKeyMapVO);
                        if(envstVal1249.equals("0") || envstVal1249.equals("1")) {
                            // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                            kioskKeyMapMapper.deleteNotUseKioskKeyMapToStore(kioskKeyMapVO);
                            if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                                // 중분류도 삭제
                                kioskKeyMapMapper.deleteNotUseKioskCategoryToStoreM(kioskKeyMapVO);
                            }
                        } else  if(envstVal1249.equals("2")) {

                            // 현재는 대분류만 사용할 경우만 가능, 중분류 사용은 '매장수정허용카테고리' 먼저 수정 후 개발 예정

                            // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                            kioskKeyMapMapper.deleteNotUseKioskKeyMapToStore2(kioskKeyMapVO);

                            /*
                            if (Integer.parseInt(selectList.get(0).get("rowCount").toString()) > 0) {
                                // 중분류도 삭제
                                kioskKeyMapMapper.deleteNotUseKioskCategoryToStoreM2(kioskKeyMapVO);
                            }
                            */
                        }
                    }

                // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
                kioskKeyMapVO.setTuClsType("");
                kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);
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

    /** 키오스크 추천메뉴 - 추천메뉴코드 가져오기 */
    @Override
    public List<DefaultMap<Object>> getRecmd(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getRecmd(kioskKeyMapVO);
    }

    @Override
    public int saveRecmd(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        // 매장 키오스크 포스 환경설정값 일괄 저장
        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {
            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
                kioskKeyMapVO.setRegFg("S");
            }
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            if(kioskKeyMapVO.getRecmdProdCd().equals("선택불가") || kioskKeyMapVO.getRecmdProdCd().equals("선택")){
                kioskKeyMapVO.setRecmdProdCd(null);
            }

            if(kioskKeyMapVO.getStatus() == GridDataFg.INSERT){

                kioskKeyMapVO.setRegDt(currentDt);
                kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
                // 자동채번
                String recmdCd = kioskKeyMapMapper.getRecmdCd(kioskKeyMapVO);

                kioskKeyMapVO.setRecmdCd(recmdCd);

                result = kioskKeyMapMapper.saveRecmd(kioskKeyMapVO);
            } else if(kioskKeyMapVO.getStatus() == GridDataFg.UPDATE){

                result = kioskKeyMapMapper.saveRecmd(kioskKeyMapVO);

            } else if(kioskKeyMapVO.getStatus() == GridDataFg.DELETE){

                result = kioskKeyMapMapper.deleteRecmd(kioskKeyMapVO);
                result = kioskKeyMapMapper.deleteRecmdProd(kioskKeyMapVO);

            }

        }

        return result;
    }

    /** 키오스크 추천메뉴 - 추천상품 조회 */
    @Override
    public List<DefaultMap<Object>> getRecmdProd(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getRecmdProd(kioskKeyMapVO);
    }

    /** 키오스크 추천메뉴 - 추천상품으로 등록할 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getRecmdProdList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (kioskKeyMapVO.getProdHqBrandCd() == "" || kioskKeyMapVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskKeyMapVO.getUserProdBrands() != null && !"".equals(kioskKeyMapVO.getUserProdBrands())) {
                    String[] userBrandList = kioskKeyMapVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskKeyMapVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return kioskKeyMapMapper.getRecmdProdList(kioskKeyMapVO);
    }

    @Override
    public int saveRecmdProd(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        // 추천메뉴 일괄저장
        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {
            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
                kioskKeyMapVO.setRegFg("S");
            }
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setIndex(kioskKeyMapMapper.getIndex(kioskKeyMapVO));

            result = kioskKeyMapMapper.addRecmdProd(kioskKeyMapVO);

        }

        return result;
    }

    @Override
    public int deleteRecmdProd(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        int result = 0;

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        if (kioskKeyMapMapper.getIndex(kioskKeyMapVO) != "1") {
            result = kioskKeyMapMapper.deleteRecmdProd(kioskKeyMapVO);
        }

        return result;
    }


    /** 키오스크 추천메뉴 - 추천메뉴 저장 */
    @Override
    public int addRecmdProd(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        // 추천메뉴 저장
        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {
            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
                kioskKeyMapVO.setRegFg("S");
            }
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            kioskKeyMapVO.setIndex(kioskKeyMapMapper.getIndex(kioskKeyMapVO));

            result = kioskKeyMapMapper.addRecmdProd(kioskKeyMapVO);
        }

        return result;
    }

    /** 키오스크 추천메뉴 매장적용 */
    @Override
    public int saveRecmdStore(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 매장에서 사용중인 기존 메뉴추천정보 삭제
            kioskKeyMapMapper.deleteStoreRecmd(kioskKeyMapVO);

            // 매장에서 사용중인 기존 메뉴추천리스트 삭제
            kioskKeyMapMapper.deleteStoreRecmdProd(kioskKeyMapVO);

            // 본사에서 사용중인 메뉴추천정보 매장등록
            result = kioskKeyMapMapper.insertStoreHqRecmd(kioskKeyMapVO);
            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 본사에서 사용중인 메뉴추천리스트 매장등록
            result = kioskKeyMapMapper.insertStoreHqRecmdProd(kioskKeyMapVO);
            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }

        return result;
    }

    /** 키오스크 카테고리(중분류) 조회 */
    public List<DefaultMap<Object>> getKioskCategoryM(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskCategoryM(kioskKeyMapVO);
    }

    /** 키오스크 카테고리(중분류) 저장 */
    @Override
    public int saveKioskCategoryM(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {
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

                // 중분류코드 생성
                kioskKeyMapVO.setTuMClsCd(kioskKeyMapMapper.getKioskCategoryCodeM(kioskKeyMapVO));

                result += kioskKeyMapMapper.insertKioskCategoryM(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += kioskKeyMapMapper.updateKioskCategoryM(kioskKeyMapVO);

            } else if ( kioskKeyMapVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += kioskKeyMapMapper.deleteKioskCategoryM(kioskKeyMapVO);

                // 해당 카테고리(분류)에 해당하는 상품도 삭제
                kioskKeyMapVO.setTuClsCd(kioskKeyMapVO.getTuMClsCd());
                kioskKeyMapMapper.deleteAllKioskKeyMap(kioskKeyMapVO);
            }
        }

        if ( result == kioskKeyMapVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 키맵그룹에 KIOSK중분류사용 조회 */
    @Override
    public List<DefaultMap<Object>> getKioskKeyMapGroupTuMClsFg(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getKioskKeyMapGroupTuMClsFg(kioskKeyMapVO);
    }

    /** 키오스크키맵 매장복사 - 매장 키오스크 포스 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCopyKioskPosList(@RequestBody KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (kioskKeyMapVO.getStoreHqBrandCd() == "" || kioskKeyMapVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskKeyMapVO.getUserBrands() != null && !"".equals(kioskKeyMapVO.getUserBrands())) {
                    String[] userBrandList = kioskKeyMapVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        kioskKeyMapVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return kioskKeyMapMapper.getStoreCopyKioskPosList(kioskKeyMapVO);
    }

    /** 키오스크키맵 매장복사 - 기준매장 키맵그룹 및 키맵정보를 적용매장에 복사 */
    @Override
    public int saveKioskKeyMapStoreCopy(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        // 매장 키오스크 포스 환경설정값 일괄 저장
        for (KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapVO.setDirctInYn("Y");
            kioskKeyMapVO.setPosFg("W"); // WEB
            kioskKeyMapVO.setUseYn("Y");
            kioskKeyMapVO.setRegDt(currentDt);
            kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
            kioskKeyMapVO.setModDt(currentDt);
            kioskKeyMapVO.setModId(sessionInfoVO.getUserId());

            // 매장에서 매장으로 복사하는 쿼리라 "S"값으로 고정(getKioskMClsCount, getKioskMClsCount2에서 사용)
            kioskKeyMapVO.setOrgnFg("S");

            // 매장, 포장 환경설정값
            String [] arrEnvCd = kioskKeyMapVO.getEnvstCd().split(",");
            String [] arrEnvVal = kioskKeyMapVO.getEnvstVal().split(",");

            for(int i = 0; i < arrEnvCd.length; i++) {

                if (!"".equals(arrEnvVal[i])) {

                    kioskKeyMapVO.setEnvstCd(arrEnvCd[i]);
                    kioskKeyMapVO.setEnvstVal(arrEnvVal[i]);
                    kioskKeyMapVO.setTuClsType(arrEnvVal[i]);

                    // 매장 키오스크 포스 환경설정값 저장
                    result = kioskKeyMapMapper.insertStoreKioskPosEnv(kioskKeyMapVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    sessionInfoVO.setStoreCd(kioskKeyMapVO.getStoreCd());

                    // 맘스터치는 기존로직 적용(delete -> insert-select), 나머지 본사는 새 로직 적용(merge -> delete) (20240909)
                    if (kioskKeyMapVO.getHqOfficeCd().equals("DS021") || kioskKeyMapVO.getHqOfficeCd().equals("DS034") || kioskKeyMapVO.getHqOfficeCd().equals("H0393")) {

                        // 기존 키맵분류명 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeNm(kioskKeyMapVO);

                        // 기존 카테고리(분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsType(kioskKeyMapVO);

                        // 기존 키맵상품삭제
                        kioskKeyMapMapper.deleteKioskKeyMapByTuClsCd(kioskKeyMapVO);

                        // 기존 카테고리(중분류) 삭제
                        kioskKeyMapMapper.deleteStoreTuClsTypeM(kioskKeyMapVO);

                        // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                        result = kioskKeyMapMapper.copyStoreTuCls(kioskKeyMapVO); // 매장용
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        result = kioskKeyMapMapper.copyStoreTuClsType(kioskKeyMapVO); // 매장용
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.copyStoreTuKey(kioskKeyMapVO); // 매장용
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 중분류 키맵그룹 row count 조회
                        int cnt = kioskKeyMapMapper.getStoreTuClsCount(kioskKeyMapVO);
                        if (cnt > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.copyStoreTuClsM(kioskKeyMapVO); // 매장용
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    }else{

                        // 새 키맵그룹과 카테고리(분류)코드로 INSERT
                        result = kioskKeyMapMapper.mergeKioskCategoryStoreRegStore(kioskKeyMapVO); // 매장용
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        result = kioskKeyMapMapper.mergeKioskStoreTuClsTypeNmStore(kioskKeyMapVO); // 매장용
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 기존 카테고리(분류)에 맵핑된 상품이 있으면 상품도 INSERT
                        result = kioskKeyMapMapper.mergeKioskKeyMapStoreRegStore(kioskKeyMapVO); // 매장용
                        if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                        // 중분류 키맵그룹 row count 조회
                        int cnt = kioskKeyMapMapper.getStoreTuClsCount(kioskKeyMapVO);
                        if (cnt > 0) {
                            // 중분류도 복제
                            result = kioskKeyMapMapper.mergeKioskCategoryStoreRegMStore(kioskKeyMapVO); // 매장용
                            if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }

                        // 기존 데이터 중, 불필요한 매장 카테고리(분류) 정보 삭제
                        kioskKeyMapMapper.deleteNotUseKioskCategoryToStoreStore(kioskKeyMapVO);

                        // 기존 데이터 중, 불필요한 매장 맵핑상품 정보 삭제
                        kioskKeyMapMapper.deleteNotUseKioskKeyMapToStoreStore(kioskKeyMapVO);
                        if (cnt > 0) {
                            // 중분류도 삭제
                            kioskKeyMapMapper.deleteNotUseKioskCategoryToStoreMStore(kioskKeyMapVO);
                        }
                    }
                }
            }

            // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
            kioskKeyMapVO.setTuClsType("");
            kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);

        }
        return result;
    }

    /** 키맵 미리보기 키맵조회 */
    @Override
    public List<DefaultMap<Object>> getTuKeyList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return kioskKeyMapMapper.getTuKeyList(kioskKeyMapVO);
    }

    /** 본사판매가관리 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getHqSalePrice(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskKeyMapVO.setUserId(sessionInfoVO.getUserId());

        return kioskKeyMapMapper.getHqSalePrice(kioskKeyMapVO);
    }

    /** 매장판매가관리 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreSalePrice(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskKeyMapVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (kioskKeyMapVO.getProdHqBrandCd() == "" || kioskKeyMapVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (kioskKeyMapVO.getUserBrands() != null && !"".equals(kioskKeyMapVO.getUserBrands())) {
                    // 사용자별 브랜드 array 값 세팅
                    String[] userBrandList = kioskKeyMapVO.getUserBrands().split(",");

                    if (userBrandList.length > 0) {
                        kioskKeyMapVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return kioskKeyMapMapper.getStoreSalePrice(kioskKeyMapVO);
    }

    /** 판매가관리 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSalePrice(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO) {

        kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
        kioskKeyMapVO.setUserId(sessionInfoVO.getUserId());

        return kioskKeyMapMapper.getSalePrice(kioskKeyMapVO);
    }

    @Override
    public int getKioskKeyDelete(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(KioskKeyMapVO kioskKeyMapVO : kioskKeyMapVOs) {

            KioskKeyMapVO kioskKeyMapClassVO = new KioskKeyMapVO();
            kioskKeyMapClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskKeyMapClassVO.setPosNo(kioskKeyMapVO.getPosNo());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                kioskKeyMapClassVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            kioskKeyMapClassVO.setTuClsType(kioskKeyMapVO.getTuClsType());
            kioskKeyMapMapper.deleteAllKioskKeyClass(kioskKeyMapClassVO);

            kioskKeyMapVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                kioskKeyMapVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            kioskKeyMapMapper.deleteKioskkeyGrp(kioskKeyMapVO);
            kioskKeyMapMapper.deleteAllKioskKey(kioskKeyMapVO);
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                kioskKeyMapMapper.deleteKioskKeyLsm(kioskKeyMapVO);
            }
        }

        return procCnt;
    }
}
