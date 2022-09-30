package kr.co.solbipos.store.hq.branchMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.branchMoms.service.BranchMomsService;
import kr.co.solbipos.store.hq.branchMoms.service.BranchMomsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BranchMomsServiceImpl.java
 * @Description : 가맹점관리 > 본사정보 > 본사-지사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.23  권지현      최초생성
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2022.09.23
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class BranchMomsServiceImpl implements BranchMomsService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final BranchMomsMapper branchMomsMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public BranchMomsServiceImpl(BranchMomsMapper branchMomsMapper, MessageService messageService) {
        this.branchMomsMapper = branchMomsMapper;
        this.messageService = messageService;
    }

    /** 목록 조회 */
    @Override
    public List<DefaultMap<String>> getBranchMomsList(BranchMomsVO branchMomsVO, SessionInfoVO sessionInfoVO) {

        branchMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return branchMomsMapper.getBranchMomsList(branchMomsVO);
    }

    /** 등록 */
    @Override
    public int saveBranchMoms(BranchMomsVO branchMomsVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        branchMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        branchMomsVO.setRegDt(dt);
        branchMomsVO.setModDt(dt);
        branchMomsVO.setRegId(sessionInfoVO.getUserId());
        branchMomsVO.setModId(sessionInfoVO.getUserId());

        if(branchMomsVO.getBranchCd().equals("자동채번")){
            branchMomsVO.setBranchCd(branchMomsMapper.setBranchCd(branchMomsVO));
        }

        result = branchMomsMapper.saveBranchMoms(branchMomsVO);

        return result;
    }

}
