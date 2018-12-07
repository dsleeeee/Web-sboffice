package kr.co.solbipos.base.store.view.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewService;
import kr.co.solbipos.base.store.view.service.ViewVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Class Name : ViewServiceImpl.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.14  김영근      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 변경
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.14
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("viewService")
public class ViewServiceImpl implements ViewService {

    private final ViewMapper viewMapper;
    private final String CORNER_USE_YN_ENVST_CD = "2028"; // 코너사용여부 환경변수


    /** Constructor Injection */
    @Autowired
    public ViewServiceImpl(ViewMapper viewMapper) {
        this.viewMapper = viewMapper;
    }

    /** 매장정보 리스트조회 */
    @Override
    public List<DefaultMap<String>> getViewList(ViewVO viewVO, SessionInfoVO sessionInfoVO){

        viewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return viewMapper.getViewList(viewVO);
    }

    /** 매장정보 상세조회 */
    @Override
    public DefaultMap<String> getViewDetail(ViewVO viewVO)
    {
        return viewMapper.getViewDetail(viewVO);
    }

    /** 코너 사용여부 조회 */
    @Override
    public String getCornerUseYnVal(VanConfigVO vanConfgVO) {

        StoreEnvVO storeEnvVO = new StoreEnvVO();

        storeEnvVO.setStoreCd(vanConfgVO.getStoreCd());
        storeEnvVO.setEnvstCd(CORNER_USE_YN_ENVST_CD);

        return viewMapper.getCornerUseYnVal(storeEnvVO);
    }

    /**  포스별 승인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosTerminalList(VanConfigVO vanConfigVO) {
        return viewMapper.getPosTerminalList(vanConfigVO);
    }

    /**  코너별 승인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCornerTerminalList(VanConfigVO vanConfigVO) {
        return viewMapper.getCornerTerminalList(vanConfigVO);
    }

}
