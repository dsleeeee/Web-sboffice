package kr.co.solbipos.pos.confg.mainVerManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.mainVerManage.service.MainVerManageService;
import kr.co.solbipos.pos.confg.mainVerManage.service.MainVerManageVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MainVerManageServiceImpl.java
 * @Description : 포스관리 > POS 설정관리 > POS 메인버전관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mainVerManageService")
@Transactional
public class MainVerManageServiceImpl implements MainVerManageService {
    private final MainVerManageMapper mainVerManageMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MainVerManageServiceImpl(MainVerManageMapper mainVerManageMapper){
        this.mainVerManageMapper = mainVerManageMapper;
    }

    /** POS 메인버전관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMainVerManageList(MainVerManageVO mainVerManageVO, SessionInfoVO sessionInfoVO) {

        return mainVerManageMapper.getMainVerManageList(mainVerManageVO);
    }

    /** POS 메인버전관리 - 메인버전 삭제 */
    @Override
    public int getMainVerManageDel(MainVerManageVO mainVerManageVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        mainVerManageVO.setModDt(currentDt);
        mainVerManageVO.setModId(sessionInfoVO.getUserId());

        procCnt = mainVerManageMapper.getMainVerManageDel(mainVerManageVO);

        return procCnt;
    }

    /** 메인버전 등록 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMainVerRegistList(MainVerManageVO mainVerManageVO, SessionInfoVO sessionInfoVO) {

        return mainVerManageMapper.getMainVerRegistList(mainVerManageVO);
    }

    /** 메인버전 등록 팝업 - 등록 */
    @Override
    public int getMainVerRegistSave(MainVerManageVO[] mainVerManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(MainVerManageVO mainVerManageVO : mainVerManageVOs) {
            mainVerManageVO.setModDt(currentDt);
            mainVerManageVO.setModId(sessionInfoVO.getUserId());
            mainVerManageVO.setRegDt(currentDt);
            mainVerManageVO.setRegId(sessionInfoVO.getUserId());

            procCnt = mainVerManageMapper.getMainVerRegistSaveInsert(mainVerManageVO);
        }

        return procCnt;
    }
}
