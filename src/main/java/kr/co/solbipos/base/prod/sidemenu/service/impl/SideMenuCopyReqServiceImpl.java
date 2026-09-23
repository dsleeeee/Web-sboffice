package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.solbipos.base.prod.sidemenu.service.SideMenuCopyReqService;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuCopyReqVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class Name : SideMenuCopyReqServiceImpl.java
 * @Description : 사이드메뉴 복사요청 멱등처리 구현
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026. 09. 22.
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sideMenuCopyReqService")
public class SideMenuCopyReqServiceImpl implements SideMenuCopyReqService {

    private final SideMenuMapper sideMenuMapper;

    /** Constructor Injection */
    public SideMenuCopyReqServiceImpl(SideMenuMapper sideMenuMapper) {
        this.sideMenuMapper = sideMenuMapper;
    }

    /**
     * nonce 선점 INSERT. REQUIRES_NEW 로 즉시 커밋되어야 2차(재전송)가 이 행을 보고 차단된다.
     * PK(REQ_NONCE) 중복이면 DuplicateKeyException(ORA-00001) 이 그대로 호출측으로 전파된다
     * (여기서 catch 후 정상리턴하면 rollback-only 트랜잭션 커밋 시 UnexpectedRollbackException 발생하므로 잡지 않음).
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void claim(SideMenuCopyReqVO sideMenuCopyReqVO) {
        sideMenuMapper.insertSdselCopyReq(sideMenuCopyReqVO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void finish(String reqNonce, String status) {
        SideMenuCopyReqVO vo = new SideMenuCopyReqVO();
        vo.setReqNonce(reqNonce);
        vo.setProcStatus(status);
        sideMenuMapper.updateSdselCopyReqStatus(vo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markResend(String reqNonce) {
        SideMenuCopyReqVO vo = new SideMenuCopyReqVO();
        vo.setReqNonce(reqNonce);
        sideMenuMapper.updateSdselCopyReqResend(vo);
    }

    @Override
    public String getProcStatus(String reqNonce) {
        SideMenuCopyReqVO vo = new SideMenuCopyReqVO();
        vo.setReqNonce(reqNonce);
        return sideMenuMapper.getSdselCopyReqProcStatus(vo);
    }
}
