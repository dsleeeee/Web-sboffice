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


    List<DefaultMap<String>> getProdClassTreeArtisee(TreePopupTwoVO treePopupTwoVO);

    List<DefaultMap<String>> getProdClassTreeTwo(TreePopupTwoVO treePopupTwoVO);

    String getProdClassCdNm(TreePopupTwoVO treePopupTwoVO);
}
