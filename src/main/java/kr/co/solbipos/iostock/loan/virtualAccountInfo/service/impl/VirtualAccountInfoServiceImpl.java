package kr.co.solbipos.iostock.loan.virtualAccountInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.virtualAccountInfo.service.VirtualAccountInfoService;
import kr.co.solbipos.iostock.loan.virtualAccountInfo.service.VirtualAccountInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : VirtualAccountInfoController.java
 * @Description : 수불관리 > 주문관리 > 가상계좌-기초정보등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("virtualAccountInfoService")
@Transactional
public class VirtualAccountInfoServiceImpl implements VirtualAccountInfoService {
    private final VirtualAccountInfoMapper virtualAccountInfoMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public VirtualAccountInfoServiceImpl(VirtualAccountInfoMapper virtualAccountInfoMapper) {
        this.virtualAccountInfoMapper = virtualAccountInfoMapper;
    }

    /** 가상계좌-기초정보등록 - 조회 */
    @Override
    public List<DefaultMap<Object>> getVirtualAccountInfoList(VirtualAccountInfoVO virtualAccountInfoVO, SessionInfoVO sessionInfoVO) {

        return virtualAccountInfoMapper.getVirtualAccountInfoList(virtualAccountInfoVO);
    }

    /** 가상계좌-기초정보등록 - 저장 */
    @Override
    public int getVirtualAccountInfoSave(VirtualAccountInfoVO[] virtualAccountInfoVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(VirtualAccountInfoVO virtualAccountInfoVO : virtualAccountInfoVOs) {

            virtualAccountInfoVO.setRegDt(currentDt);
            virtualAccountInfoVO.setRegId(sessionInfoVO.getUserId());
            virtualAccountInfoVO.setModDt(currentDt);
            virtualAccountInfoVO.setModId(sessionInfoVO.getUserId());

            procCnt = virtualAccountInfoMapper.getVirtualAccountInfoSaveMerge(virtualAccountInfoVO);
        }

        return procCnt;
    }
}