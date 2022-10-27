package kr.co.solbipos.adi.etc.cdMoms.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.etc.cdMoms.service.CdMomsService;
import kr.co.solbipos.adi.etc.cdMoms.service.CdMomsVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CdMomsServiceImpl.java
 * @Description : 맘스터치 > 기타관리 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cdMomsService")
public class CdMomsServiceImpl implements CdMomsService {

    // Constructor Injection
    private final CdMomsMapper cdMomsMapper;
    private final MessageService messageService;

    /** 생성자 주입 */
    @Autowired
    public CdMomsServiceImpl(MessageService messageService, CdMomsMapper cdMomsMapper) {
        this.messageService = messageService;
        this.cdMomsMapper = cdMomsMapper;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdMomsList(CdMomsVO cdMomsVO) {
        return cdMomsMapper.getNmcodeGrpCdMomsList(cdMomsVO);
    }

    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdMomsList(CdMomsVO cdMomsVO) {
        return cdMomsMapper.getNmcodeCdMomsList(cdMomsVO);
    }

    /** 코드목록 저장 */
    @Override
    public int getNmcodeCdMomsSave(CdMomsVO[] cdMomsVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdMomsVO cdMomsVO : cdMomsVOs ) {

            cdMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdMomsVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdMomsVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdMomsVO.setRegDt(currentDt);
            cdMomsVO.setRegId(sessionInfoVO.getUserId());
            cdMomsVO.setModDt(currentDt);
            cdMomsVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdMomsVO.getStatus() == GridDataFg.INSERT ) {
                result += cdMomsMapper.getNmcodeCdMomsSaveInsert(cdMomsVO);
            // 수정
            } else if ( cdMomsVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdMomsMapper.getNmcodeCdMomsSaveUpdate(cdMomsVO);
            // 삭제
            } else if ( cdMomsVO.getStatus() == GridDataFg.DELETE ) {
                result += cdMomsMapper.getNmcodeCdMomsSaveDelete(cdMomsVO);
            }
        }

        if ( result == cdMomsVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}