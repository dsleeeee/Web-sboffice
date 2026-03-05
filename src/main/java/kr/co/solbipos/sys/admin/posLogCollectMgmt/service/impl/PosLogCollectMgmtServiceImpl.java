package kr.co.solbipos.sys.admin.posLogCollectMgmt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtService;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : PosLogCollectMgmtServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > POS로그수집관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("PosLogCollectMgmtService")
@Transactional
public class PosLogCollectMgmtServiceImpl implements PosLogCollectMgmtService {

    private final PosLogCollectMgmtMapper posLogCollectMgmtMapper;

    /**
     *  Constructor Injection
     */
    @Autowired
    public PosLogCollectMgmtServiceImpl(PosLogCollectMgmtMapper posLogCollectMgmtMapper) {
        this.posLogCollectMgmtMapper = posLogCollectMgmtMapper;
    }

    /** POS로그수집관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchPosLogList(PosLogCollectMgmtVO posLogCollectMgmtVO, SessionInfoVO sessionInfoVO) {
        return posLogCollectMgmtMapper.getSearchPosLogList(posLogCollectMgmtVO);
    }

    /** POS로그수집등록 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchStoreList(PosLogCollectMgmtVO posLogCollectMgmtVO, SessionInfoVO sessionInfoVO) {
        return posLogCollectMgmtMapper.getSearchStoreList(posLogCollectMgmtVO);
    }

    /** POS로그수집등록 팝업 - POS로그 저장 */
    @Override
    public int savePosLog(PosLogCollectMgmtVO[] posLogCollectMgmtVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();
        String currentDay = currentDateString();

        for(PosLogCollectMgmtVO posLogCollectMgmtVO : posLogCollectMgmtVOs){

            posLogCollectMgmtVO.setSaleDate(currentDay);
            posLogCollectMgmtVO.setRegDt(currentDt);
            posLogCollectMgmtVO.setRegId(sessionInfoVO.getUserId());
            posLogCollectMgmtVO.setModDt(currentDt);
            posLogCollectMgmtVO.setModId(sessionInfoVO.getUserId());

            posLogCollectMgmtVO.setSeq(posLogCollectMgmtMapper.getMaxSeqNo(posLogCollectMgmtVO));

            result = posLogCollectMgmtMapper.savePosLog(posLogCollectMgmtVO);
        }

        return result;
    }
}
