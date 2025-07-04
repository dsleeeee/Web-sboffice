package kr.co.common.service.treePopup.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.treePopup.TreePopupVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : TreePopupMapper.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface TreePopupMapper {

    /** 상품정보 분류 트리(체크박스) 조회 */
    List<DefaultMap<String>> getProdClassTreeCheck(TreePopupVO treePopupVO);

    /** 상품정보 분류 트리(체크박스) 조회(아티제용) */
    List<DefaultMap<String>> getProdClassTreeCheckArtisee(TreePopupVO treePopupVO);

    /** 상품분류 플랫 조회 */
    String getProdClassCdNmCheck(TreePopupVO treePopupVO);

}
