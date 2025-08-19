package kr.co.solbipos.adi.etc.cdMrpizza.service.impl;


import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.cdMrpizza.service.CdMrpizzaService;
import kr.co.solbipos.adi.etc.cdMrpizza.service.CdMrpizzaVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : CdMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.14  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("cdMrpizzaService")
public class CdMrpizzaServiceImpl implements CdMrpizzaService {

    private final CdMrpizzaMapper cdMrpizzaMapper;
    private final MessageService messageService;

    @Autowired
    public CdMrpizzaServiceImpl(CdMrpizzaMapper cdMrpizzaMapper, MessageService messageService) {
        this.cdMrpizzaMapper = cdMrpizzaMapper;
        this.messageService = messageService;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdMrpizzaList(CdMrpizzaVO cdMrpizzaVO) {
        return cdMrpizzaMapper.getNmcodeGrpCdMrpizzaList(cdMrpizzaVO);
    }

    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdMrpizzaList(CdMrpizzaVO cdMrpizzaVO) {
        return cdMrpizzaMapper.getNmcodeCdMrpizzaList(cdMrpizzaVO);
    }

    /** 시스템 명칭관리 - 저장 */
    @Override
    public int getNmcodeCdMrpizzaSave(CdMrpizzaVO[] cdMrpizzaVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdMrpizzaVO cdMrpizzaVO : cdMrpizzaVOS ) {

            cdMrpizzaVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdMrpizzaVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdMrpizzaVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdMrpizzaVO.setRegDt(currentDt);
            cdMrpizzaVO.setRegId(sessionInfoVO.getUserId());
            cdMrpizzaVO.setModDt(currentDt);
            cdMrpizzaVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdMrpizzaVO.getStatus() == GridDataFg.INSERT ) {
                result += cdMrpizzaMapper.getNmcodeCdMrpizzaSaveInsert(cdMrpizzaVO);
                // 수정
            } else if ( cdMrpizzaVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdMrpizzaMapper.getNmcodeCdMrpizzaSaveUpdate(cdMrpizzaVO);
                // 삭제
            } else if ( cdMrpizzaVO.getStatus() == GridDataFg.DELETE ) {
                result += cdMrpizzaMapper.getNmcodeCdMrpizzaSaveDelete(cdMrpizzaVO);
            }
        }

        if ( result == cdMrpizzaVOS.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
