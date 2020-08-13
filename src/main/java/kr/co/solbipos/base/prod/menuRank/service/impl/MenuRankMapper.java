package kr.co.solbipos.base.prod.menuRank.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.kitchenmemo.service.KitchenMemoVO;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.base.prod.menuRank.service.MenuRankVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MenuRankMapper.java
 * @Description : 기초관리 - 상품관리 - 메뉴 순위 표시 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.06  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MenuRankMapper {

    /** 메뉴 순위 표시 사용매장 수 조회(본사사용) */
    List<DefaultMap<String>> getRankCnt(MenuRankVO menuRankVO);

    /** 메뉴 순위 표시 사용/미사용 매장 조회(본사사용) */
    List<DefaultMap<String>> getRegStore(MenuRankVO menuRankVO);

    /** 메뉴 순위 표시 미사용 처리(본사사용) */
    int deleteStore(MenuRankVO menuRankVO);

    /** 메뉴 순위 표시 관리 조회(매장사용) */
    List<DefaultMap<String>> getRankUse(MenuRankVO menuRankVO);

    /** 메뉴 순위 표시 관리 저장(본사/매장사용) */
    int saveRankUse(MenuRankVO menuRankVO);

}
