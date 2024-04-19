package kr.co.solbipos.store.manage.closeStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreService;
import kr.co.solbipos.store.manage.closeStore.service.CloseStoreVO;
import kr.co.solbipos.store.manage.storeCloseExcept.service.StoreCloseExceptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : CloseStoreServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 폐점예정매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.22  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("CloseStoreService")
public class CloseStoreServiceImpl implements CloseStoreService {

    private final CloseStoreMapper closeStoreMapper;
    private final AuthMapper authMapper;

    /** Constructor Injection */
    @Autowired
    public CloseStoreServiceImpl(CloseStoreMapper closeStoreMapper, AuthMapper authMapper) {
        this.closeStoreMapper = closeStoreMapper;
        this.authMapper = authMapper;
    }

    /** 밴 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getVanComboList() {
        return closeStoreMapper.getVanComboList();
    }

    /** 폐점예정매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCloseStoreList(CloseStoreVO closeStoreVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.AGENCY)){
            closeStoreVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }
        return closeStoreMapper.getCloseStoreList(closeStoreVO);
    }

    @Override
    public int saveCloseStore(CloseStoreVO[] closeStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (CloseStoreVO closeStoreVO : closeStoreVOs) {
            closeStoreVO.setModDt(dt);
            closeStoreVO.setModId(sessionInfoVO.getUserId());
            result = closeStoreMapper.saveCloseStore(closeStoreVO);
        }

        return result;
    }

    /** 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(CloseStoreVO closeStoreVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.AGENCY)){
            closeStoreVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }
        return closeStoreMapper.getStoreList(closeStoreVO);
    }

    /** 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCloseExceptList(CloseStoreVO closeStoreVO, SessionInfoVO sessionInfoVO) {
/*
        try{
            System.out.println("시간지연으로 타임아웃 체크중 kjs try sleep:");
            Thread.sleep(1000 * 120);       // 120초 지연 처리
        }catch(InterruptedException e){
            System.out.println("시간지연으로 타임아웃 체크중 kjs try sleep: catch :InterruptedException 발생 e: "+ e);
            e.printStackTrace();
        }

        System.out.println("시간지연으로 타임아웃 체크중 kjs try out:");
*/
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.AGENCY)){
            closeStoreVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return closeStoreMapper.getStoreCloseExceptList(closeStoreVO);
    }

    /** 폐점제외매장 등록 */
    @Override
    public int saveStoreCloseExcept(CloseStoreVO[] closeStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for (CloseStoreVO closeStoreVO : closeStoreVOs) {
            closeStoreVO.setRegDt(dt);
            closeStoreVO.setRegId(sessionInfoVO.getUserId());
            closeStoreVO.setModDt(dt);
            closeStoreVO.setModId(sessionInfoVO.getUserId());
            result = closeStoreMapper.saveStoreCloseExcept(closeStoreVO);
        }

        return result;
    }

    /** 폐점제외매장 삭제 */
    @Override
    public int deleteStoreCloseExcept(CloseStoreVO[] closeStoreVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for (CloseStoreVO closeStoreVO : closeStoreVOs) {
            result = closeStoreMapper.deleteStoreCloseExcept(closeStoreVO);
        }

        return result;
    }
}
