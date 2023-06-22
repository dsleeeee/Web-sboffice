package kr.co.solbipos.adi.etc.alimtalk.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.etc.alimtalk.service.AlimtalkService;
import kr.co.solbipos.adi.etc.alimtalk.service.AlimtalkVO;
import kr.co.solbipos.adi.etc.alimtalk.service.impl.AlimtalkMapper;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AlimtalkServiceImpl.java
 * @Description : 맘스터치 > 기타관리 > 매출트레킹수신자목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.06.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("alimtalkService")
public class AlimtalkServiceImpl implements AlimtalkService {

    // Constructor Injection
    private final AlimtalkMapper alimtalkMapper;
    private final MessageService messageService;

    /** 생성자 주입 */
    @Autowired
    public AlimtalkServiceImpl(MessageService messageService, AlimtalkMapper alimtalkMapper) {
        this.messageService = messageService;
        this.alimtalkMapper = alimtalkMapper;
    }

    @Override
    public List<DefaultMap<String>> getAlimtalkFgList(SessionInfoVO sessionInfoVO) {
        return alimtalkMapper.getAlimtalkFgList(sessionInfoVO);
    }

    /** 세부명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getAlimtalkList(AlimtalkVO alimtalkVO, SessionInfoVO sessionInfoVO) {
        alimtalkVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        return alimtalkMapper.getAlimtalkList(alimtalkVO);
    }

    /** 코드목록 저장 */
    @Override
    public int getAlimtalkSave(AlimtalkVO[] alimtalkVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for ( AlimtalkVO alimtalkVO : alimtalkVOs ) {

            alimtalkVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            alimtalkVO.setRegDt(currentDt);
            alimtalkVO.setRegId(sessionInfoVO.getUserId());
            alimtalkVO.setModDt(currentDt);
            alimtalkVO.setModId(sessionInfoVO.getUserId());

            if ( alimtalkVO.getStatus() == GridDataFg.DELETE ) {
                result += alimtalkMapper.getAlimtalkDelete(alimtalkVO);
            } else {
                result += alimtalkMapper.getAlimtalkSave(alimtalkVO);
            }
        }

        if ( result == alimtalkVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}