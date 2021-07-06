package kr.co.solbipos.base.store.guestManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.guestManage.service.GuestManageService;
import kr.co.solbipos.base.store.guestManage.service.GuestManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : GuestManageServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 객층관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.07.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("guestManageService")
@Transactional
public class GuestManageServiceImpl implements GuestManageService {
    private final GuestManageMapper guestManageMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public GuestManageServiceImpl(GuestManageMapper guestManageMapper) {
        this.guestManageMapper = guestManageMapper;
    }

    /** 객층관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getGuestManageList(GuestManageVO guestManageVO, SessionInfoVO sessionInfoVO) {

        guestManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        guestManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            guestManageVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return guestManageMapper.getGuestManageList(guestManageVO);
    }

    /** 객층관리 - 저장 */
    @Override
    public int getGuestManageSave(GuestManageVO[] guestManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(GuestManageVO guestManageVO : guestManageVOs) {

            guestManageVO.setRegDt(currentDt);
            guestManageVO.setRegId(sessionInfoVO.getUserId());
            guestManageVO.setModDt(currentDt);
            guestManageVO.setModId(sessionInfoVO.getUserId());

            guestManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            guestManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                guestManageVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            if (guestManageVO.getStatus() == GridDataFg.INSERT) {
                procCnt = guestManageMapper.getGuestManageSaveInsert(guestManageVO);

            } else if (guestManageVO.getStatus() == GridDataFg.DELETE) {
                procCnt = guestManageMapper.getGuestManageSaveDelete(guestManageVO);
            }
        }

        return procCnt;
    }
}