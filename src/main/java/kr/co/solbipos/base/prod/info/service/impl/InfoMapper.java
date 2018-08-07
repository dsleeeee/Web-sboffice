package kr.co.solbipos.base.prod.info.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : InfoMapper.java
 * @Description : 기초관리 > 상품관리 > 분류코드등록(상품기초정보등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface InfoMapper {

    /** 본사 분류 조회 */
    List<DefaultMap<String>> getHqProdClsList(ProductClassVO prodClsVO);

    /** 매장 분류 조회 */
    List<DefaultMap<String>> getStoreProdClsList(ProductClassVO prodClsVO);

    /** 신규 분류 코드 조회 */
    String getClsCd(ProductClassVO productClassVO);

    /** 상위분류 코드 조회 */
    String getPProdClsCd(ProductClassVO productClassVO);

    /** 분류 등록 */
    int insertCls(ProductClassVO productClassVO);

    /** 본사 분류 수정 */
    int updateHqCls(ProductClassVO productClassVO);

    /** 본사 분류 수정 */
    int updateStoreCls(ProductClassVO productClassVO);

    /** 해당 분류로 등록된 상품 조회 */
    int chkProdCnt(ProductClassVO productClassVO);

    /** 분류 삭제 */
    int deleteCls(ProductClassVO productClassVO);
}
