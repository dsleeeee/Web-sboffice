package kr.co.solbipos.kookmin.base.termInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.base.termInfo.service.TermInfoService;
import kr.co.solbipos.kookmin.base.termInfo.service.TermInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : TermInfoServiceImpl.java
 * @Description : 국민대 > 기초관리 > 학기정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("TermInfoService")
@Transactional
public class TermInfoServiceImpl implements TermInfoService {

    private final TermInfoMapper termInfoMapper;

    @Autowired
    public TermInfoServiceImpl(TermInfoMapper termInfoMapper) {
        this.termInfoMapper = termInfoMapper;
    }


    /** 학기정보 조회 */
    @Override
    public List<DefaultMap<Object>> getTermInfoList(TermInfoVO termInfoVO, SessionInfoVO sessionInfoVO) {
        termInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return termInfoMapper.getTermInfoList(termInfoVO);
    }

    /** 학기정보 저장 */
    @Override
    public int saveTermInfo(TermInfoVO[] termInfoVOS, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(TermInfoVO termInfoVO : termInfoVOS){

            termInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            termInfoVO.setRegId(sessionInfoVO.getUserId());
            termInfoVO.setRegDt(currentDt);
            termInfoVO.setModId(sessionInfoVO.getUserId());
            termInfoVO.setModDt(currentDt);

            procCnt += termInfoMapper.saveTermInfo(termInfoVO);
        }

        return procCnt;
    }
}
