package kr.co.solbipos.base.store.view.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @Class Name : ViewMapper.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.13  김영근      최초생성
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.13
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
public interface ViewMapper { 

    /** 매장정보 목록 조회 */
    List<DefaultMap<String>> getViewList(ViewVO viewVO);
    
    
    /** 매장정보 상세 조회 */
    DefaultMap<String> getViewDetail(ViewVO viewVO);
    
    /** 밴사설정 정보 조회 */
    List<DefaultMap<String>> getVanconfgList(VanConfigVO vanConfigVO);
    
    /** 코너별 승인 목록 조회 */
    List<DefaultMap<String>> getCornrApproveList(String storeCd);
    
    /** 포스별 승인 목록 조회*/
    List<DefaultMap<String>> getPosApproveList(String storeCd);
    
}
