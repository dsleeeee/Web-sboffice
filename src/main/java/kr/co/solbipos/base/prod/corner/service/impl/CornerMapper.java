package kr.co.solbipos.base.prod.corner.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.corner.service.CornerVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CornerController.java
 * @Description : 기초관리 - 상품관리 - 코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.27  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 02.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface CornerMapper {

    /** 상품별 코너변경 리스트 조회 */
    List<DefaultMap<String>> getProdCornerList(CornerVO cornerVO);

    /** 상품별 코너 이동 */
    int changeProdCorner(CornerVO cornerVO);

    /** 코너 목록 조회 */
    List<DefaultMap<String>> getCornerList(StoreCornerVO storeCornerVO);

    /** 상품별 코너변경 - 전체 엑셀다운로드 */
    List<DefaultMap<String>> getexcelList(CornerVO cornerVO);
}
