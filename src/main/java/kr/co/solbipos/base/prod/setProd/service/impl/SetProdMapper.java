package kr.co.solbipos.base.prod.setProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.setProd.service.SetProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SetProdMapper.java
 * @Description : 기초관리 > 상품관리 > 세트메뉴구성(BBQ전용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.04  노현수      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.09.04
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SetProdMapper {
    // 상품 조회
    List<DefaultMap<String>> getProdList(SetProdVO setProdVO);

    // 상품 사이드상품여부 저장
    int saveSideProdYn(SetProdVO setProdVO);

    // 상품 선택그룹 조회
    List<DefaultMap<String>> getSdselGrpList(SetProdVO setProdVO);
}
