package kr.co.solbipos.sys.cd.systemcd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.systemcd.service.SystemCdService;
import kr.co.solbipos.sys.cd.systemcd.service.SystemCdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SystemCdService.java
 * @Description : 시스템관리 > 코드관리 > 시스템 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("systemCdService")
public class SystemCdServiceImpl implements SystemCdService {

    // Constructor Injection
    private final SystemCdMapper systemCdMapper;
    private final MessageService messageService;

    @Autowired
    public SystemCdServiceImpl(MessageService messageService, SystemCdMapper systemCdMapper) {
        this.messageService = messageService;
        this.systemCdMapper = systemCdMapper;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdList(SystemCdVO systemCdVO) {
        return systemCdMapper.getNmcodeGrpCdList(systemCdVO);
    }
    
    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdList(SystemCdVO systemCdVO) {
        return systemCdMapper.getNmcodeCdList(systemCdVO);
    }
    
    /** 코드목록 저장 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveNmcodeCdList(SystemCdVO[] systemCdVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( SystemCdVO systemCdVO : systemCdVOs ) {
            
            systemCdVO.setRegDt(currentDt);
            systemCdVO.setRegId(sessionInfoVO.getUserId());
            systemCdVO.setModDt(currentDt);
            systemCdVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( systemCdVO.getStatus() == GridDataFg.INSERT ) {

                // 동일 그룹/코드 존재 시 명시적 실패 처리 (중복 등록 방지)
                if ( systemCdMapper.getNmcodeCdDupCnt(systemCdVO) > 0 ) {
                    throw new JsonException(Status.SERVER_ERROR, "[" + systemCdVO.getNmcodeCd() + "] " + messageService.get("systemCd.dupNmcodeCd"));
                }

                try {
                    result += systemCdMapper.insertNmcodeCdList(systemCdVO);
                } catch (DuplicateKeyException e) {
                    // 사전 체크와 INSERT 사이에 동시 등록된 경우
                    throw new JsonException(Status.SERVER_ERROR, "[" + systemCdVO.getNmcodeCd() + "] " + messageService.get("systemCd.dupNmcodeCd"));
                }
            // 수정
            } else if ( systemCdVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += systemCdMapper.updateNmcodeCdList(systemCdVO);
            // 삭제
            } else if ( systemCdVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == systemCdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
}
