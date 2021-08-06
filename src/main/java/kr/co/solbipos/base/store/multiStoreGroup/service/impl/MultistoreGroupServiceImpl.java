package kr.co.solbipos.base.store.multistoreGroup.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.multistoreGroup.service.MultistoreGroupService;
import kr.co.solbipos.base.store.multistoreGroup.service.MultistoreGroupVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MultistoreGroupServiceImpl.java
 * @Description : 기초관리 - 매장관리 - 다중매장그룹관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.07.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MultistoreGroupService")
public class MultistoreGroupServiceImpl implements MultistoreGroupService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MultistoreGroupMapper multistoreGroupMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public MultistoreGroupServiceImpl(MultistoreGroupMapper multistoreGroupMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {

        this.multistoreGroupMapper = multistoreGroupMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 그룹조회 */
    @Override
    public List<DefaultMap<Object>> getMultistoreGroup(MultistoreGroupVO multistoreGroupVO, SessionInfoVO sessionInfoVO) {

        multistoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return multistoreGroupMapper.getMultistoreGroup(multistoreGroupVO);
    }

    /** 그룹저장 */
    @Override
    public int saveMultistoreGroup(MultistoreGroupVO[] multistoreGroupVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( MultistoreGroupVO multistoreGroupVO : multistoreGroupVOS) {

            multistoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            multistoreGroupVO.setRegDt(currentDt);
            multistoreGroupVO.setRegId(sessionInfoVO.getUserId());
            multistoreGroupVO.setModDt(currentDt);
            multistoreGroupVO.setModId(sessionInfoVO.getUserId());

            if ( multistoreGroupVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 그룹코드 생성
                multistoreGroupVO.setMultistoreCd(multistoreGroupMapper.getMultistoreGroupCode(multistoreGroupVO));

                result += multistoreGroupMapper.insertMultistoreGroup(multistoreGroupVO);

            } else if ( multistoreGroupVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += multistoreGroupMapper.updateMultistoreGroup(multistoreGroupVO);

            }
        }

        if ( result == multistoreGroupVOS.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 등록매장조회 */
    @Override
    public List<DefaultMap<Object>> getMultiStoreList(MultistoreGroupVO multistoreGroupVO, SessionInfoVO sessionInfoVO) {

        multistoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return multistoreGroupMapper.getMultiStoreList(multistoreGroupVO);
    }

    /** 매장조회 */
    @Override
    public List<DefaultMap<Object>> getStoreList(MultistoreGroupVO multistoreGroupVO, SessionInfoVO sessionInfoVO) {

        multistoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return multistoreGroupMapper.getStoreList(multistoreGroupVO);
    }

    /** 매장등록, 삭제*/
    @Override
    public int saveStoreMapping(MultistoreGroupVO[] multistoreGroupVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(MultistoreGroupVO multistoreGroupVO : multistoreGroupVOs) {

            multistoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            multistoreGroupVO.setRegDt(dt);
            multistoreGroupVO.setRegId(sessionInfoVO.getUserId());
            multistoreGroupVO.setModDt(dt);
            multistoreGroupVO.setModId(sessionInfoVO.getUserId());

            if ( multistoreGroupVO.getStatus() == GridDataFg.INSERT ) { // 등록
                result += multistoreGroupMapper.saveStoreMapping(multistoreGroupVO);

            } else if ( multistoreGroupVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += multistoreGroupMapper.deleteStoreMapping(multistoreGroupVO);
            }
        }

        if (result == multistoreGroupVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 매장 기능사용자 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getMultiStoreUserList(MultistoreGroupVO multistoreGroupVO, SessionInfoVO sessionInfoVO) {

        multistoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return multistoreGroupMapper.getMultiStoreUserList(multistoreGroupVO);
    }
}
