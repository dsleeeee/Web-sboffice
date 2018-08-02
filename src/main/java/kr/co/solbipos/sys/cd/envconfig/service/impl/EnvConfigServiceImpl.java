package kr.co.solbipos.sys.cd.envconfig.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.envconfig.service.EnvConfigService;
import kr.co.solbipos.sys.cd.envconfig.service.EnvstDtlVO;
import kr.co.solbipos.sys.cd.envconfig.service.EnvstVO;

/**
 * @Class Name : EnvConfigService.java
 * @Description : 시스템관리 > 코드관리 > 환경설정관리
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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("envConfigService")
public class EnvConfigServiceImpl implements EnvConfigService {
    
    @Autowired
    EnvConfigMapper envConfigMapper;
    @Autowired
    MessageService messageService;
    
    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getEnvstList(EnvstVO envstVO) {
        return envConfigMapper.getEnvstList(envstVO);
    }
    
    /** 대표명칭 코드 저장 */
    @Override
    public int saveEnvstList(EnvstVO[] envstVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( EnvstVO envstVO : envstVOs ) {
            
            envstVO.setRegDt(currentDt);
            envstVO.setRegId(sessionInfoVO.getUserId());
            envstVO.setModDt(currentDt);
            envstVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( envstVO.getStatus() == GridDataFg.INSERT ) {
                
                result += envConfigMapper.insertEnvst(envstVO);
            // 수정
            } else if ( envstVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += envConfigMapper.updateEnvst(envstVO);
            // 삭제
            } else if ( envstVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == envstVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getEnvstDtlList(EnvstDtlVO envstDtlVO) {
        return envConfigMapper.getEnvstDtlList(envstDtlVO);
    }
    
    /** 세부명칭 코드 저장 */
    @Override
    public int saveEnvstDtlList(EnvstDtlVO[] envstDtlVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( EnvstDtlVO envstDtlVO : envstDtlVOs ) {
            
            envstDtlVO.setRegDt(currentDt);
            envstDtlVO.setRegId(sessionInfoVO.getUserId());
            envstDtlVO.setModDt(currentDt);
            envstDtlVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( envstDtlVO.getStatus() == GridDataFg.INSERT ) {
                
                result += envConfigMapper.insertEnvstDtl(envstDtlVO);
            // 수정
            } else if ( envstDtlVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += envConfigMapper.updateEnvstDtl(envstDtlVO);
            // 삭제
            } else if ( envstDtlVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == envstDtlVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
}
