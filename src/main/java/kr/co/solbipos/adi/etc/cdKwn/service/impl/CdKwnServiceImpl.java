package kr.co.solbipos.adi.etc.cdKwn.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.cdKwn.service.CdKwnService;
import kr.co.solbipos.adi.etc.cdKwn.service.CdKwnVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CdKwnServiceImpl.java
 * @Description : 광운대 > 공통코드 > 공통코드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.0  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cdKwnService")
public class CdKwnServiceImpl implements CdKwnService {

    // Constructor Injection
    private final CdKwnMapper cdKwnMapper;
    private final MessageService messageService;

    /** 생성자 주입 */
    @Autowired
    public CdKwnServiceImpl(MessageService messageService, CdKwnMapper cdKwnMapper) {
        this.messageService = messageService;
        this.cdKwnMapper = cdKwnMapper;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdKwnList(CdKwnVO cdKwnVO) {
        return cdKwnMapper.getNmcodeGrpCdKwnList(cdKwnVO);
    }

    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdKwnList(CdKwnVO cdKwnVO) {
        return cdKwnMapper.getNmcodeCdKwnList(cdKwnVO);
    }

    /** 코드목록 저장 */
    @Override
    public int getNmcodeCdKwnSave(CdKwnVO[] cdKwnVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdKwnVO cdKwnVO : cdKwnVOs ) {

            cdKwnVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdKwnVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdKwnVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdKwnVO.setRegDt(currentDt);
            cdKwnVO.setRegId(sessionInfoVO.getUserId());
            cdKwnVO.setModDt(currentDt);
            cdKwnVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdKwnVO.getStatus() == GridDataFg.INSERT ) {
                result += cdKwnMapper.getNmcodeCdKwnSaveInsert(cdKwnVO);
            // 수정
            } else if ( cdKwnVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdKwnMapper.getNmcodeCdKwnSaveUpdate(cdKwnVO);
            // 삭제
            } else if ( cdKwnVO.getStatus() == GridDataFg.DELETE ) {
                result += cdKwnMapper.getNmcodeCdKwnSaveDelete(cdKwnVO);
            }
        }

        if ( result == cdKwnVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}