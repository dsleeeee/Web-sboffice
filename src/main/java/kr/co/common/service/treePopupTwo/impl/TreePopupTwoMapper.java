package kr.co.common.service.treePopupTwo.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.treePopupTwo.TreePopupTwoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : TreePopupTwoMapper.java
 * @Description : 상품분류모듈 팝업 관련
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.27  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface TreePopupTwoMapper {

    /** 상품정보 분류 트리(체크박스) 조회2(아티제용) */
    List<DefaultMap<String>> getProdClassTreeArtiseeCheck2(TreePopupTwoVO treePopupTwoVO);

    /** 상품정보 분류 트리(체크박스) 조회2 */
    List<DefaultMap<String>> getProdClassTreeCheck2(TreePopupTwoVO treePopupTwoVO);

    /** 상품분류 플랫 조회2 */
    String getProdClassCdNmCheck2(TreePopupTwoVO treePopupTwoVO);
}
