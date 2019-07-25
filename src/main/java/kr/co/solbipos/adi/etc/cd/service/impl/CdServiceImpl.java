package kr.co.solbipos.adi.etc.cd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.cd.service.CdService;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CdServiceImpl.java
 * @Description : 부가서비스 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.13  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cdService")
public class CdServiceImpl implements CdService {

    // Constructor Injection
    private final CdMapper cdMapper;
    private final MessageService messageService;

    /** 생성자 주입 */
    @Autowired
    public CdServiceImpl(MessageService messageService, CdMapper cdMapper) {
        this.messageService = messageService;
        this.cdMapper = cdMapper;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdList(CdVO cdVO) {
        return cdMapper.getNmcodeGrpCdList(cdVO);
    }
    
    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdList(CdVO cdVO) {
        return cdMapper.getNmcodeCdList(cdVO);
    }
    
    /** 코드목록 저장 */
    @Override
    public int saveNmcodeCdList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO) {
        
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdVO cdVO : cdVOs ) {

            cdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdVO.setRegDt(currentDt);
            cdVO.setRegId(sessionInfoVO.getUserId());
            cdVO.setModDt(currentDt);
            cdVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdVO.getStatus() == GridDataFg.INSERT ) {
                result += cdMapper.insertNmcodeCdList(cdVO);
                // 수정
            } else if ( cdVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdMapper.updateNmcodeCdList(cdVO);
                // 삭제
            } else if ( cdVO.getStatus() == GridDataFg.DELETE ) {
                result += cdMapper.deleteNmcodeCdList(cdVO);
            }

        }

        if ( result == cdVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        
    }
    
}
