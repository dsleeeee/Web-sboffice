package kr.co.solbipos.base.store.multiStoreGroup.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.multiStoreGroup.service.MultiStoreGroupService;
import kr.co.solbipos.base.store.multiStoreGroup.service.MultiStoreGroupVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MultiStoreGroupServiceImpl.java
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
@Service("MultiStoreGroupService")
public class MultiStoreGroupServiceImpl implements MultiStoreGroupService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MultiStoreGroupMapper multiStoreGroupMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public MultiStoreGroupServiceImpl(MultiStoreGroupMapper multiStoreGroupMapper,  MessageService messageService, CmmEnvUtil cmmEnvUtil) {

        this.multiStoreGroupMapper = multiStoreGroupMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 그룹조회 */
    @Override
    public List<DefaultMap<Object>> getMultiStoreGroup(MultiStoreGroupVO multiStoreGroupVO, SessionInfoVO sessionInfoVO) {

        multiStoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return multiStoreGroupMapper.getMultiStoreGroup(multiStoreGroupVO);
    }

    /** 그룹저장 */
    @Override
    public int saveMultiStoreGroup(MultiStoreGroupVO[] multiStoreGroupVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( MultiStoreGroupVO multiStoreGroupVO : multiStoreGroupVOs) {

            multiStoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            multiStoreGroupVO.setRegDt(currentDt);
            multiStoreGroupVO.setRegId(sessionInfoVO.getUserId());
            multiStoreGroupVO.setModDt(currentDt);
            multiStoreGroupVO.setModId(sessionInfoVO.getUserId());

            if ( multiStoreGroupVO.getStatus() == GridDataFg.INSERT ) { // 생성

                // 그룹코드 생성
                multiStoreGroupVO.setMultistoreCd(multiStoreGroupMapper.getMultiStoreGroupCode(multiStoreGroupVO));

                result += multiStoreGroupMapper.insertMultiStoreGroup(multiStoreGroupVO);

            } else if ( multiStoreGroupVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += multiStoreGroupMapper.updateMultiStoreGroup(multiStoreGroupVO);

            }
        }

        if ( result == multiStoreGroupVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }









    /** 매장조회 */
    @Override
    public List<DefaultMap<Object>> getStoreList(MultiStoreGroupVO multiStoreGroupVO, SessionInfoVO sessionInfoVO) {

        multiStoreGroupVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return multiStoreGroupMapper.getStoreList(multiStoreGroupVO);
    }
}
