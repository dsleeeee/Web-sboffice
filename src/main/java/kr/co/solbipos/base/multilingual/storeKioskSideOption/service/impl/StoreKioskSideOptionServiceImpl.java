package kr.co.solbipos.base.multilingual.storeKioskSideOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
import kr.co.solbipos.base.multilingual.storeKioskSideOption.service.StoreKioskSideOptionService;
import kr.co.solbipos.base.multilingual.storeKioskSideOption.service.StoreKioskSideOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreKioskSideOptionServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("StoreKioskSideOptionService")
public class StoreKioskSideOptionServiceImpl implements StoreKioskSideOptionService {

    private final StoreKioskSideOptionMapper storeKioskSideOptionMapper;

    @Autowired
    public StoreKioskSideOptionServiceImpl(StoreKioskSideOptionMapper storeKioskSideOptionMapper) {

        this.storeKioskSideOptionMapper = storeKioskSideOptionMapper;
    }

    /** 키오스크(카테고리명) 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreKioskCategoryList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO) {

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreKioskCategoryList(storeKioskSideOptionVO);
    }

    /** 키오스크(카테고리명) 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreKioskCategory(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreKioskSideOptionVO storeKioskSideOptionVO : storeKioskSideOptionVOs) {

            storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeKioskSideOptionVO.setRegDt(dt);
            storeKioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            storeKioskSideOptionVO.setModDt(dt);
            storeKioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += storeKioskSideOptionMapper.saveStoreKioskCategory(storeKioskSideOptionVO);
        }

        return result;
    }

    /** 키오스크 포스 조회(중분류 사용 포스만 조회) */
    @Override
    public List<DefaultMap<String>> getKioskPosComboList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getKioskPosComboList(storeKioskSideOptionVO);
    }

    /** 키오스크중분류(카테고리명) 카테고리(대분류) 콤보박스 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreKioskCategoryComboList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreKioskCategoryComboList(storeKioskSideOptionVO);
    }

    /** 키오스크중분류(카테고리명) 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreKioskMClsList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreKioskMClsList(storeKioskSideOptionVO);
    }

    /** 키오스크중분류(카테고리명) 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreKioskMCls(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreKioskSideOptionVO storeKioskSideOptionVO : storeKioskSideOptionVOs) {

            storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeKioskSideOptionVO.setRegDt(dt);
            storeKioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            storeKioskSideOptionVO.setModDt(dt);
            storeKioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += storeKioskSideOptionMapper.saveStoreKioskMCls(storeKioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 사이드(선택그룹명) 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreSideSdselGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreSideSdselGrpList(storeKioskSideOptionVO);
    }

    /** 사이드(선택그룹명) 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreSideSdselGrp(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreKioskSideOptionVO storeKioskSideOptionVO : storeKioskSideOptionVOs) {

            storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeKioskSideOptionVO.setRegDt(dt);
            storeKioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            storeKioskSideOptionVO.setModDt(dt);
            storeKioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += storeKioskSideOptionMapper.saveStoreSideSdselGrp(storeKioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 사이드(선택분류명) 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreSideSdselClassList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreSideSdselClassList(storeKioskSideOptionVO);
    }

    /** 사이드(선택분류명) 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreSideSdselClass(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreKioskSideOptionVO storeKioskSideOptionVO : storeKioskSideOptionVOs) {

            storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeKioskSideOptionVO.setRegDt(dt);
            storeKioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            storeKioskSideOptionVO.setModDt(dt);
            storeKioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += storeKioskSideOptionMapper.saveStoreSideSdselClass(storeKioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 옵션(그룹명) 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreOptionGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreOptionGrpList(storeKioskSideOptionVO);
    }

    /** 옵션(그룹명) 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreOptionGrp(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreKioskSideOptionVO storeKioskSideOptionVO : storeKioskSideOptionVOs) {

            storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeKioskSideOptionVO.setRegDt(dt);
            storeKioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            storeKioskSideOptionVO.setModDt(dt);
            storeKioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += storeKioskSideOptionMapper.saveStoreOptionGrp(storeKioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 옵션(옵션명) 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreOptionValList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO){

        storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
        return storeKioskSideOptionMapper.getStoreOptionValList(storeKioskSideOptionVO);
    }

    /** 옵션(옵션명) 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreOptionVal(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreKioskSideOptionVO storeKioskSideOptionVO : storeKioskSideOptionVOs) {

            storeKioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeKioskSideOptionVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeKioskSideOptionVO.setRegDt(dt);
            storeKioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            storeKioskSideOptionVO.setModDt(dt);
            storeKioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += storeKioskSideOptionMapper.saveStoreOptionVal(storeKioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

}
