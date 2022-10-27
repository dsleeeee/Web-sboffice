package kr.co.solbipos.adi.etc.cdKwu.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.cdKwu.service.CdKwuService;
import kr.co.solbipos.adi.etc.cdKwu.service.CdKwuVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CdKwuServiceImpl.java
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
@Service("cdKwuService")
public class CdKwuServiceImpl implements CdKwuService {

    // Constructor Injection
    private final CdKwuMapper cdKwuMapper;
    private final MessageService messageService;

    /** 생성자 주입 */
    @Autowired
    public CdKwuServiceImpl(MessageService messageService, CdKwuMapper cdKwuMapper) {
        this.messageService = messageService;
        this.cdKwuMapper = cdKwuMapper;
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdKwuList(CdKwuVO cdKwuVO) {
        return cdKwuMapper.getNmcodeGrpCdKwuList(cdKwuVO);
    }

    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getNmcodeCdKwuList(CdKwuVO cdKwuVO) {
        return cdKwuMapper.getNmcodeCdKwuList(cdKwuVO);
    }

    /** 코드목록 저장 */
    @Override
    public int getNmcodeCdKwuSave(CdKwuVO[] cdKwuVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( CdKwuVO cdKwuVO : cdKwuVOs ) {

            cdKwuVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                cdKwuVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                cdKwuVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            cdKwuVO.setRegDt(currentDt);
            cdKwuVO.setRegId(sessionInfoVO.getUserId());
            cdKwuVO.setModDt(currentDt);
            cdKwuVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( cdKwuVO.getStatus() == GridDataFg.INSERT ) {
                result += cdKwuMapper.getNmcodeCdKwuSaveInsert(cdKwuVO);
                // 수정
            } else if ( cdKwuVO.getStatus() == GridDataFg.UPDATE ) {
                result += cdKwuMapper.getNmcodeCdKwuSaveUpdate(cdKwuVO);
                // 삭제
            } else if ( cdKwuVO.getStatus() == GridDataFg.DELETE ) {
                result += cdKwuMapper.getNmcodeCdKwuSaveDelete(cdKwuVO);
            }
        }

        if ( result == cdKwuVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}