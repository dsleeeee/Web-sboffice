package kr.co.solbipos.base.store.view.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewService;
import kr.co.solbipos.base.store.view.service.ViewVO;
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
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.14
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("viewService")
public class ViewServiceImpl implements ViewService {

    @Autowired
    ViewMapper mapper; 
    
    @Autowired
    MessageService messageService;

    /** 매장정보 리스트조회 */
    @Override
    public List<DefaultMap<String>> getViewList(ViewVO viewVO)
    {
        return mapper.getViewList(viewVO);
    }
    
    /** 매장정보 상세조회 */
    @Override
    public DefaultMap<String> getViewDetail(ViewVO viewVO)
    {
        return mapper.getViewDetail(viewVO);
    }
    
    
    /** 밴사설정 정보 조회 */
    @Override
    public List<DefaultMap<String>> getVanconfgList(VanConfigVO vo) {
        return mapper.getVanconfgList(vo);
    }
    
    
    /**  코너별 승인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCornrApproveList(String storeCd) {
        return mapper.getCornrApproveList(storeCd);
    }
    
    /**  포스별 승인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosApproveList(String storeCd) {
        return mapper.getPosApproveList(storeCd);
    }
    
}
