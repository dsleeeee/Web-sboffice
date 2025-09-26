package kr.co.solbipos.base.multilingual.storeProdLang.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.storeProdLang.service.StoreProdLangService;
import kr.co.solbipos.base.multilingual.storeProdLang.service.StoreProdLangVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreProdLangServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(상품)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.25  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("StoreProdLangService")
public class StoreProdLangServiceImpl implements StoreProdLangService {

    private final StoreProdLangMapper storeProdLangMapper;

    @Autowired
    public StoreProdLangServiceImpl(StoreProdLangMapper storeProdLangMapper) {

        this.storeProdLangMapper = storeProdLangMapper;
    }

    /** 상품명 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreProdNmList(StoreProdLangVO storeProdLangVO, SessionInfoVO sessionInfoVO){

        storeProdLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeProdLangVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeProdLangVO.setUserId(sessionInfoVO.getUserId());

        return storeProdLangMapper.getStoreProdNmList(storeProdLangVO);
    }

    /** 상품명 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreProdNm(StoreProdLangVO[] storeProdLangVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreProdLangVO storeProdLangVO : storeProdLangVOs) {

            storeProdLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeProdLangVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeProdLangVO.setRegDt(dt);
            storeProdLangVO.setRegId(sessionInfoVO.getUserId());
            storeProdLangVO.setModDt(dt);
            storeProdLangVO.setModId(sessionInfoVO.getUserId());

            result += storeProdLangMapper.saveStoreProdNm(storeProdLangVO);
        }

        /*if (result == prodLangVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 상품설명 탭 리스트 조회(매장) */
    @Override
    public List<DefaultMap<String>> getStoreProdInfoList(StoreProdLangVO storeProdLangVO, SessionInfoVO sessionInfoVO){

        storeProdLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        storeProdLangVO.setStoreCd(sessionInfoVO.getStoreCd());
        storeProdLangVO.setUserId(sessionInfoVO.getUserId());

        return storeProdLangMapper.getStoreProdInfoList(storeProdLangVO);
    }

    /** 상품설명 영문, 중문, 일문 저장(매장) */
    @Override
    public int saveStoreProdInfo(StoreProdLangVO[] storeProdLangVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (StoreProdLangVO storeProdLangVO : storeProdLangVOs) {

            storeProdLangVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeProdLangVO.setStoreCd(sessionInfoVO.getStoreCd());
            storeProdLangVO.setRegDt(dt);
            storeProdLangVO.setRegId(sessionInfoVO.getUserId());
            storeProdLangVO.setModDt(dt);
            storeProdLangVO.setModId(sessionInfoVO.getUserId());

            result += storeProdLangMapper.saveStoreProdInfo(storeProdLangVO);
        }

        /*if (result == prodLangVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }
}
