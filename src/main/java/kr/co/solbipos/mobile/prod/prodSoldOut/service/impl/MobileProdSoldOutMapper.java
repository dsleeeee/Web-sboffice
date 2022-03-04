package kr.co.solbipos.mobile.prod.prodSoldOut.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.prod.prodSoldOut.service.MobileProdSoldOutVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileProdSoldOutMapper.java
 * @Description : 상품관리 > 품절관리(상품)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MobileProdSoldOutMapper {

    /** 조회 */
    List<DefaultMap<String>> getMobileProdSoldOutList(MobileProdSoldOutVO mobileProdSoldOutVO);

    /** 저장 */
    int getMobileProdSoldOutSave(MobileProdSoldOutVO mobileProdSoldOutVO);

}
