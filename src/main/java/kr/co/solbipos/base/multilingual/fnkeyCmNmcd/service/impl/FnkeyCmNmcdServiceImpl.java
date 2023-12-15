package kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.FnkeyCmNmcdService;
import kr.co.solbipos.base.multilingual.fnkeyCmNmcd.service.FnkeyCmNmcdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : FnkeyCmNmcdServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(기능키/공통코드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.12.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("FnkeyCmNmcdService")
public class FnkeyCmNmcdServiceImpl implements FnkeyCmNmcdService {

    private final FnkeyCmNmcdMapper fnkeyCmNmcdMapper;
    private final MessageService messageService;
    private final PopupMapper popupMapper;

    @Autowired
    public FnkeyCmNmcdServiceImpl(FnkeyCmNmcdMapper fnkeyCmNmcdMapper, MessageService messageService, PopupMapper popupMapper) {

        this.fnkeyCmNmcdMapper = fnkeyCmNmcdMapper;
        this.messageService = messageService;
        this.popupMapper = popupMapper;
    }

    /** 기능키(공통) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCmPosFnkeyList(FnkeyCmNmcdVO fnkeyCmNmcdVO, SessionInfoVO sessionInfoVO){

        return fnkeyCmNmcdMapper.getCmPosFnkeyList(fnkeyCmNmcdVO);
    }

    /** 기능키(공통) 탭 영문, 중문, 일문 저장 */
    @Override
    public int saveCmPosFnkey(FnkeyCmNmcdVO[] fnkeyCmNmcdVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (FnkeyCmNmcdVO fnkeyCmNmcdVO : fnkeyCmNmcdVOs) {

            fnkeyCmNmcdVO.setRegDt(dt);
            fnkeyCmNmcdVO.setRegId(sessionInfoVO.getUserId());
            fnkeyCmNmcdVO.setModDt(dt);
            fnkeyCmNmcdVO.setModId(sessionInfoVO.getUserId());

            result += fnkeyCmNmcdMapper.saveCmPosFnkey(fnkeyCmNmcdVO);
        }

        if (result == fnkeyCmNmcdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 기능키(매장) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreFnkeyList(FnkeyCmNmcdVO fnkeyCmNmcdVO, SessionInfoVO sessionInfoVO){

        fnkeyCmNmcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(fnkeyCmNmcdVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(fnkeyCmNmcdVO.getStoreCd(), 3900));
            fnkeyCmNmcdVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return fnkeyCmNmcdMapper.getStoreFnkeyList(fnkeyCmNmcdVO);
    }

    /** 기능키(공통) 탭 영문, 중문, 일문 저장 */
    @Override
    public int saveStoreFnkey(FnkeyCmNmcdVO[] fnkeyCmNmcdVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (FnkeyCmNmcdVO fnkeyCmNmcdVO : fnkeyCmNmcdVOs) {

            fnkeyCmNmcdVO.setRegDt(dt);
            fnkeyCmNmcdVO.setRegId(sessionInfoVO.getUserId());
            fnkeyCmNmcdVO.setModDt(dt);
            fnkeyCmNmcdVO.setModId(sessionInfoVO.getUserId());

            result += fnkeyCmNmcdMapper.saveStoreFnkey(fnkeyCmNmcdVO);
        }

        if (result == fnkeyCmNmcdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 공통코드 탭 공통코드 그룹코드 조회(콤보박스용) */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdList(){

        return fnkeyCmNmcdMapper.getNmcodeGrpCdList();
    }

    /** 공통코드 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCmNmcdList(FnkeyCmNmcdVO fnkeyCmNmcdVO, SessionInfoVO sessionInfoVO){

        return fnkeyCmNmcdMapper.getCmNmcdList(fnkeyCmNmcdVO);
    }

    /** 공통코드 탭 영문, 중문, 일문 저장 */
    @Override
    public int saveCmNmcd(FnkeyCmNmcdVO[] fnkeyCmNmcdVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (FnkeyCmNmcdVO fnkeyCmNmcdVO : fnkeyCmNmcdVOs) {

            fnkeyCmNmcdVO.setRegDt(dt);
            fnkeyCmNmcdVO.setRegId(sessionInfoVO.getUserId());
            fnkeyCmNmcdVO.setModDt(dt);
            fnkeyCmNmcdVO.setModId(sessionInfoVO.getUserId());

            result += fnkeyCmNmcdMapper.saveCmNmcd(fnkeyCmNmcdVO);
        }

        if (result == fnkeyCmNmcdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
