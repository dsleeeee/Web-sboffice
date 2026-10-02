package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.solbipos.base.prod.sidemenu.service.SideMenuCopyReqService;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuCopyReqVO;
import org.springframework.dao.DuplicateKeyException;
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
     * nonce 선점. 먼저 SELECT 로 같은 nonce 가 이미 있는지 확인해 있으면 DuplicateKeyException 을 던지고(차단),
     * 없으면 INSERT 한다. 재전송(같은 nonce)을 PK 충돌 전에 조회로 걸러 ORA-00001 이 catalina 에 남아
     * WAS 오류감시가 울리는 것을 막기 위함. ms 단위 동시요청이 조회를 둘 다 통과하면
     * PK(REQ_NONCE) 충돌로 같은 예외가 전파되므로 차단은 동일하게 동작한다.
     * REQUIRES_NEW 로 즉시 커밋되어야 2차(재전송)가 이 행을 보고 차단된다
     * (여기서 PK 예외를 catch 후 정상리턴하면 rollback-only 트랜잭션 커밋 시 UnexpectedRollbackException 발생하므로 잡지 않음).
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void claim(SideMenuCopyReqVO sideMenuCopyReqVO) {
        String procStatus = sideMenuMapper.getSdselCopyReqProcStatus(sideMenuCopyReqVO);
        if (procStatus != null) {
            throw new DuplicateKeyException("SDSEL_COPY_REQ 중복 nonce=" + sideMenuCopyReqVO.getReqNonce());
        }
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
