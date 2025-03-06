package kr.co.solbipos.base.store.empTalk.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.empTalk.service.EmpTalkService;
import kr.co.solbipos.base.store.empTalk.service.EmpTalkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EmpTalkServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 키오스크 직원대화
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.12  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("empTalkService")
@Transactional
public class EmpTalkServiceImpl implements EmpTalkService {

    private final EmpTalkMapper empTalkMapper;
    private final MessageService messageService;

    public EmpTalkServiceImpl(EmpTalkMapper empTalkMapper, MessageService messageService) {
        this.empTalkMapper = empTalkMapper;
        this.messageService = messageService;
    }

    /** 키오스크 직원대화 관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpTalkList(EmpTalkVO empTalkVO, SessionInfoVO sessionInfoVO) {
        empTalkVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        empTalkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        empTalkVO.setStoreCd(sessionInfoVO.getStoreCd());

        return empTalkMapper.getEmpTalkList(empTalkVO);
    }

    /** 키오스크 직원대화 관리 - 저장 */
    @Override
    public int saveEmpTalk(EmpTalkVO[] empTalkVOs, SessionInfoVO sessionInfoVO) {
        int result  = 0;
        String currentDt = currentDateTimeString();

        for ( EmpTalkVO empTalkVO : empTalkVOs) {

            // 소속구분 설정
            empTalkVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            empTalkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            empTalkVO.setStoreCd(sessionInfoVO.getStoreCd());

            empTalkVO.setRegDt(currentDt);
            empTalkVO.setRegId(sessionInfoVO.getUserId());
            empTalkVO.setModDt(currentDt);
            empTalkVO.setModId(sessionInfoVO.getUserId());

            // 추가
            if ( empTalkVO.getStatus() == GridDataFg.INSERT ) {
                empTalkVO.setEmpTextNo(empTalkMapper.getMaxEmpTextNo(empTalkVO));
                empTalkVO.setDispSeq(empTalkMapper.getMaxDispSeq(empTalkVO));

                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    empTalkVO.setRegFg("H");
                }else{
                    empTalkVO.setRegFg("S");
                }

                result += empTalkMapper.mergeEmpTalkList(empTalkVO);
                // 수정
            } else if ( empTalkVO.getStatus() == GridDataFg.UPDATE ) {
                result += empTalkMapper.mergeEmpTalkList(empTalkVO);

                // 삭제
            } else if ( empTalkVO.getStatus() == GridDataFg.DELETE ) {
                result += empTalkMapper.deleteEmpTalkList(empTalkVO);
            }
        }

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    @Override
    public int empTalkRegStore(EmpTalkVO empTalkVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        empTalkVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        empTalkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        empTalkVO.setStoreCd(sessionInfoVO.getStoreCd());
        empTalkVO.setRegDt(currentDt);
        empTalkVO.setRegId(sessionInfoVO.getUserId());
        empTalkVO.setModDt(currentDt);
        empTalkVO.setModId(sessionInfoVO.getUserId());

        result = empTalkMapper.mergeEmpTalkRegStore(empTalkVO);

        return result;
    }
}
