package kr.co.solbipos.sys.etc.vancard.service.impl;

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
import kr.co.solbipos.sys.etc.vancard.service.CardCmpnyVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCardService;
import kr.co.solbipos.sys.etc.vancard.service.VanCardVO;
import kr.co.solbipos.sys.etc.vancard.service.VanCmpnyVO;

/**
 * @Class Name : VanCardServiceImpl.java
 * @Description : 시스템관리 > VAN/CARD사 관리
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
@Service("vanCardService")
public class VanCardServiceImpl implements VanCardService {
    
    @Autowired
    VanCardMapper vanCardMapper;
    @Autowired
    MessageService messageService;
    
    /** VAN사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getVanCmpnyList(VanCmpnyVO vanCmpnyVO) {
        return vanCardMapper.getVanCmpnyList(vanCmpnyVO);
    }
    
    /** VAN사 목록 저장 */
    @Override
    public int saveVanCmpnyList(VanCmpnyVO[] vanCmpnyVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( VanCmpnyVO vanCmpnyVO : vanCmpnyVOs ) {
            
            vanCmpnyVO.setRegDt(currentDt);
            vanCmpnyVO.setRegId(sessionInfoVO.getUserId());
            vanCmpnyVO.setModDt(currentDt);
            vanCmpnyVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( vanCmpnyVO.getStatus() == GridDataFg.INSERT ) {
                
                result += vanCardMapper.insertVanCmpnyList(vanCmpnyVO);
            // 수정
            } else if ( vanCmpnyVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += vanCardMapper.updateVanCmpnyList(vanCmpnyVO);
            // 삭제
            } else if ( vanCmpnyVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == vanCmpnyVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
    /** CARD사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCardCmpnyList(CardCmpnyVO cardCmpnyVO) {
        return vanCardMapper.getCardCmpnyList(cardCmpnyVO);
    }
    
    /** CARD사 목록 저장 */
    @Override
    public int saveCardCmpnyList(CardCmpnyVO[] cardCmpnyVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( CardCmpnyVO cardCmpnyVO : cardCmpnyVOs ) {
            
            cardCmpnyVO.setRegDt(currentDt);
            cardCmpnyVO.setRegId(sessionInfoVO.getUserId());
            cardCmpnyVO.setModDt(currentDt);
            cardCmpnyVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( cardCmpnyVO.getStatus() == GridDataFg.INSERT ) {
                
                result += vanCardMapper.insertCardCmpnyList(cardCmpnyVO);
            // 수정
            } else if ( cardCmpnyVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += vanCardMapper.updateCardCmpnyList(cardCmpnyVO);
            // 삭제
            } else if ( cardCmpnyVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == cardCmpnyVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
    /** VAN/CARD사 매핑 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMappingList(VanCardVO vanCardVO) {
        return vanCardMapper.getMappingList(vanCardVO);
    }
    
    /** VAN/CARD사 매핑 목록 저장 */
    @Override
    public int saveMappingList(VanCardVO[] vanCardVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();
        
        for ( VanCardVO vanCardVO : vanCardVOs ) {
            
            vanCardVO.setRegDt(currentDt);
            vanCardVO.setRegId(sessionInfoVO.getUserId());
            vanCardVO.setModDt(currentDt);
            vanCardVO.setModId(sessionInfoVO.getUserId());
            
            // 추가
            if ( vanCardVO.getStatus() == GridDataFg.INSERT ) {
                
                result += vanCardMapper.insertMappingList(vanCardVO);
            // 수정
            } else if ( vanCardVO.getStatus() == GridDataFg.UPDATE ) {
                
                result += vanCardMapper.updateMappingList(vanCardVO);
            // 삭제
            } else if ( vanCardVO.getStatus() == GridDataFg.DELETE ) {
                
            }
            
        }
        
        if ( result == vanCardVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    

    
    
}
