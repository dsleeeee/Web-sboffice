package kr.co.solbipos.base.store.storeType.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreTypeServiceImpl.java
 * @Description : 기초관리 - 매장관리 - 매장타입관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("StoreTypeService")
public class StoreTypeServiceImpl implements StoreTypeService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final StoreTypeMapper storeTypeMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public StoreTypeServiceImpl(StoreTypeMapper storeTypeMapper,  MessageService messageService, CmmEnvUtil cmmEnvUtil) {

        this.storeTypeMapper = storeTypeMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 매장타입관리 - 매장타입조회 */
    @Override
    public List<DefaultMap<Object>> getStoreType(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreType(storeTypeVO);
    }

    /** 매장타입관리 - 매장타입저장 */
    @Override
    public int saveStoreType(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setRegDt(currentDt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(currentDt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            if ( storeTypeVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 매장타입코드 생성
                storeTypeVO.setStoreTypeCd(storeTypeMapper.getStoreTypeCode(storeTypeVO));

                result += storeTypeMapper.insertStoreType(storeTypeVO);

            } else if ( storeTypeVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += storeTypeMapper.updateStoreType(storeTypeVO);

            }
        }

        if ( result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 매장타입관리 - 매장연결조회 */
    @Override
    public List<DefaultMap<Object>> getStoreMapping(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreMapping(storeTypeVO);
    }

    /** 매장타입관리 - 매장연결삭제 */
    @Override
    public int deleteStoreMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO){

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setRegDt(dt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(dt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            result += storeTypeMapper.deleteStoreMapping(storeTypeVO);

            // 매장타입자동적용(1106) 사용여부에 따라 매장타입적용 관리(상품/가격 매장적용) 테이블에 INSERT
            if("1".equals(storeTypeVO.getStoreTypeAutoEnvstVal())){

                procResult = storeTypeMapper.saveStoreTypeApplyStore(storeTypeVO);
                if(procResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        if ( result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 매장타입관리 - 매장조회 */
    @Override
    public List<DefaultMap<Object>> getStoreList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreList(storeTypeVO);
    }

    /** 매장타입관리 - 매장연결등록 */
    @Override
    public int saveStoreMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setUseYn("Y");
            storeTypeVO.setRegDt(dt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(dt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            // 이미 다른 매장타입에 등록되어 있는 매장인지 확인
            if(storeTypeMapper.getStoreMappingYn(storeTypeVO) == 0) {

                // 매장연결등록
                result += storeTypeMapper.saveStoreMapping(storeTypeVO);

                // 매장타입자동적용(1106) 사용여부에 따라 매장타입적용 관리(상품/가격 매장적용) 테이블에 INSERT
                if ("1".equals(storeTypeVO.getStoreTypeAutoEnvstVal())) {

                    procResult = storeTypeMapper.saveStoreTypeApplyStore(storeTypeVO);
                    if (procResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
        }

        if ( result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 매장타입관리 - 메뉴그룹연결조회 */
    @Override
    public List<DefaultMap<Object>> getMenuGroupMapping(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getMenuGroupMapping(storeTypeVO);
    }

    /** 매장타입관리 - 메뉴그룹연결삭제 */
    @Override
    public int deleteMenuGroupMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO){

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            result += storeTypeMapper.deleteMenuGroupMapping(storeTypeVO);
        }

        // 매장타입자동적용(1106) 사용여부에 따라 매장타입적용 관리(상품/가격 매장적용) 테이블에 INSERT
        StoreTypeVO storeTypeApplyVO = storeTypeVOs[0];

        if("1".equals(storeTypeApplyVO.getStoreTypeAutoEnvstVal())){

            storeTypeApplyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeApplyVO.setRegDt(dt);
            storeTypeApplyVO.setRegId(sessionInfoVO.getUserId());
            storeTypeApplyVO.setModDt(dt);
            storeTypeApplyVO.setModId(sessionInfoVO.getUserId());

            procResult = storeTypeMapper.saveStoreTypeApplyStoreMenuGroup(storeTypeApplyVO);
            if(procResult < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        if ( result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 매장타입관리 - 메뉴그룹조회 */
    @Override
    public List<DefaultMap<Object>> getMenuGroupList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getMenuGroupList(storeTypeVO);
    }

    /** 매장타입관리 - 메뉴그룹연결등록 */
    @Override
    public int saveMenuGroupMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setUseYn("Y");
            storeTypeVO.setRegDt(dt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(dt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            result += storeTypeMapper.saveMenuGroupMapping(storeTypeVO);
        }

        // 매장타입자동적용(1106) 사용여부에 따라 매장타입적용 관리(상품/가격 매장적용) 테이블에 INSERT
        StoreTypeVO storeTypeApplyVO = storeTypeVOs[0];

        if("1".equals(storeTypeApplyVO.getStoreTypeAutoEnvstVal())){

            storeTypeApplyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeApplyVO.setRegDt(dt);
            storeTypeApplyVO.setRegId(sessionInfoVO.getUserId());
            storeTypeApplyVO.setModDt(dt);
            storeTypeApplyVO.setModId(sessionInfoVO.getUserId());

            procResult = storeTypeMapper.saveStoreTypeApplyStoreMenuGroup(storeTypeApplyVO);
            if(procResult < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        if ( result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 메뉴그룹관리 - 메뉴그룹조회 */
    @Override
    public List<DefaultMap<Object>> getMenuGroup(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getMenuGroup(storeTypeVO);
    }

    /** 메뉴그룹관리 - 메뉴그룹저장 */
    @Override
    public int saveMenuGroup(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setRegDt(currentDt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(currentDt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            if ( storeTypeVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 메뉴그룹코드 생성
                storeTypeVO.setStoreGroupCd(storeTypeMapper.getMenuGroupCode(storeTypeVO));

                result += storeTypeMapper.insertMenuGroup(storeTypeVO);

            } else if ( storeTypeVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += storeTypeMapper.updateMenuGroup(storeTypeVO);

            }
        }

        if ( result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 메뉴그룹관리 - 상품연결조회 */
    @Override
    public List<DefaultMap<Object>> getProdMapping(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getProdMapping(storeTypeVO);
    }

    /** 메뉴그룹관리 - 상품조회 */
    @Override
    public List<DefaultMap<Object>> getProdList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getProdList(storeTypeVO);
    }

    /** 메뉴그룹관리 - 상품연결저장 */
    @Override
    public int saveProdMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setUseYn("Y");
            storeTypeVO.setRegDt(dt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(dt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            if ( storeTypeVO.getStatus() == GridDataFg.INSERT ) { // 등록
                result += storeTypeMapper.saveProdMapping(storeTypeVO);

            } else if ( storeTypeVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += storeTypeMapper.updateProdMapping(storeTypeVO);

            } else if ( storeTypeVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += storeTypeMapper.deleteProdMapping(storeTypeVO);
            }
        }

        // 매장타입자동적용(1106) 사용여부에 따라 매장타입적용 관리(상품/가격 매장적용) 테이블에 INSERT
        StoreTypeVO storeTypeApplyVO = storeTypeVOs[0];

        if("1".equals(storeTypeApplyVO.getStoreTypeAutoEnvstVal())) {

            storeTypeApplyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeApplyVO.setRegDt(dt);
            storeTypeApplyVO.setRegId(sessionInfoVO.getUserId());
            storeTypeApplyVO.setModDt(dt);
            storeTypeApplyVO.setModId(sessionInfoVO.getUserId());

            procResult = storeTypeMapper.saveStoreTypeApplyStoreProd(storeTypeApplyVO);
            if(procResult < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        if (result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 메뉴그룹관리 - 브랜드조회(콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getBrandList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeTypeVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return storeTypeMapper.getBrandList(storeTypeVO);
    }

    /** 매장타입관리 - 매장타입 매장적용 팝업 매장리스트 조회 */
    public List<DefaultMap<Object>> getStoreTypeApplyStoreList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreTypeApplyStoreList(storeTypeVO);
    }

    /** 매장타입관리 - 매장타입조회(콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getStoreTypeCombo(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreTypeCombo(storeTypeVO);
    }

    /** 매장타입관리 - 매장타입 매장적용 팝업 매장적용(매장타입적용관리 테이블에 등록) */
    @Override
    public int saveStoreTypeApplyStore(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int result = 0;

        for(StoreTypeVO storeTypeVO : storeTypeVOs) {

            storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeTypeVO.setUseYn("Y");
            storeTypeVO.setRegDt(dt);
            storeTypeVO.setRegId(sessionInfoVO.getUserId());
            storeTypeVO.setModDt(dt);
            storeTypeVO.setModId(sessionInfoVO.getUserId());

            if(storeTypeVO.getApplyDt() == null || "".equals(storeTypeVO.getApplyDt())){
                storeTypeVO.setApplyDt(dt);
            }

            result += storeTypeMapper.saveStoreTypeApplyStore(storeTypeVO);

        }

        if (result == storeTypeVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 매장타입관리 - 매장타입적용관리 테이블 조회하여 본사상품 > 매장등록 PKG 호출 (스케쥴러에서 사용, PKG_HQ_STORE_TYPE_APP_ALL -> PKG_HQ_STORE_TYPE_APP 호출) */
    @Override
    public String insertHqProductToStoreAll(StoreTypeVO storeTypeVO) {
        return storeTypeMapper.insertHqProductToStoreAll(storeTypeVO);
    }

    /** 매장타입관리 - 메뉴그룹조회(콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getStoreGroupCombo(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreGroupCombo(storeTypeVO);
    }

    /** 매장타입관리 - 매장타입변경이력조회 */
    @Override
    public List<DefaultMap<Object>> getStoreTypeChgHist(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getStoreTypeChgHist(storeTypeVO);
    }

    /** 매장타입관리 - 메뉴그룹변경이력조회 */
    @Override
    public List<DefaultMap<Object>> getMenuGroupChgHist(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO) {

        storeTypeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return storeTypeMapper.getMenuGroupChgHist(storeTypeVO);
    }

}
