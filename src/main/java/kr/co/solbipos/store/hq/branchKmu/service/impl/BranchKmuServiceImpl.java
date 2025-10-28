package kr.co.solbipos.store.hq.branchKmu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.branchKmu.service.BranchKmuService;
import kr.co.solbipos.store.hq.branchKmu.service.BranchKmuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BranchKmuServiceImpl.java
 * @Description : 국민대 > 매장관리 > 그룹관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class BranchKmuServiceImpl implements BranchKmuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final BranchKmuMapper branchKmuMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public BranchKmuServiceImpl(BranchKmuMapper branchKmuMapper, MessageService messageService) {
        this.branchKmuMapper = branchKmuMapper;
        this.messageService = messageService;
    }

    /** 목록 조회 */
    @Override
    public List<DefaultMap<String>> getBranchKmuList(BranchKmuVO branchKmuVO, SessionInfoVO sessionInfoVO) {

        branchKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return branchKmuMapper.getBranchKmuList(branchKmuVO);
    }

    /** 등록 */
    @Override
    public int saveBranchKmu(BranchKmuVO branchKmuVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        branchKmuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        branchKmuVO.setRegDt(dt);
        branchKmuVO.setModDt(dt);
        branchKmuVO.setRegId(sessionInfoVO.getUserId());
        branchKmuVO.setModId(sessionInfoVO.getUserId());

        if(branchKmuVO.getBranchCd().equals("자동채번")){
            branchKmuVO.setBranchCd(branchKmuMapper.setBranchCd(branchKmuVO));
        }

        result = branchKmuMapper.saveBranchKmu(branchKmuVO);

        return result;
    }

}