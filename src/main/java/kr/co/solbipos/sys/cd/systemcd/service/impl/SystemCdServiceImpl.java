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
import org.springframework.stereotype.Service;

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
                
                result += systemCdMapper.insertNmcodeCdList(systemCdVO);
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
