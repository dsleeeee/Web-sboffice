package kr.co.solbipos.sys.bill.kind.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.bill.kind.service.KindService;
import kr.co.solbipos.sys.bill.kind.service.KindVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KindServiceImpl.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 종류
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
@Service("kindService")
public class KindServiceImpl implements KindService {
    
    private final KindMapper kindMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public KindServiceImpl(KindMapper kindMapper, MessageService messageService) {
        this.kindMapper = kindMapper;
        this.messageService = messageService;
    }

    /** 출력물종류 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintList(KindVO kindVO) {
        return kindMapper.getPrintList(kindVO);
    }
    
    /** 출력물종류 목록 저장 */
    @Override
    public int savePrintList(KindVO[] kindVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( KindVO kindVO : kindVOs ) {
            
            kindVO.setRegDt(currentDt);
            kindVO.setRegId(sessionInfoVO.getUserId());
            kindVO.setModDt(currentDt);
            kindVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( kindVO.getStatus() == GridDataFg.INSERT ) {
                result += kindMapper.insertPrintList(kindVO);
            // 수정
            } else if ( kindVO.getStatus() == GridDataFg.UPDATE ) {
                result += kindMapper.updatePrintList(kindVO);
            // 삭제
            } else if ( kindVO.getStatus() == GridDataFg.DELETE ) {
                result += kindMapper.deletePrintList(kindVO);
            }
            
        }
        
        if ( result == kindVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
    /** 출력물매핑 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPrintMapngList(KindVO kindVO) {
        return kindMapper.getPrintMapngList(kindVO);
    }

    /** 출력물매핑 목록 팝업 조회 */
    @Override
    public List<DefaultMap<String>> getPrintMapngUnUsedList(KindVO kindVO) {
        return kindMapper.getPrintMapngUnUsedList(kindVO);
    }
    
    /** 출력물매핑 목록 저장 */
    @Override
    public int savePrintMapngList(KindVO[] kindVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( KindVO kindVO : kindVOs ) {
            
            kindVO.setRegDt(currentDt);
            kindVO.setRegId(sessionInfoVO.getUserId());
            kindVO.setModDt(currentDt);
            kindVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( kindVO.getStatus() == GridDataFg.INSERT ) {
                result += kindMapper.insertPrintMapngList(kindVO);
            // 수정
            } else if ( kindVO.getStatus() == GridDataFg.UPDATE ) {
                result += kindMapper.updatePrintMapngList(kindVO);
            // 삭제
            } else if ( kindVO.getStatus() == GridDataFg.DELETE ) {
                result += kindMapper.deletePrintMapngList(kindVO);
            }
            
        }
        
        if ( result == kindVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }

}
