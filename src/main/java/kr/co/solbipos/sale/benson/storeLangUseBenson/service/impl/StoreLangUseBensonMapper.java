package kr.co.solbipos.sale.benson.storeLangUseBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.storeLangUseBenson.service.StoreLangUseBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreLangUseBensonMapper.java
 * @Description : 벤슨 > 매장분석 > 다국어사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreLangUseBensonMapper {

    /** 다국어사용현황 조회 */
    List<DefaultMap<String>> getStoreLangUseBensonList(StoreLangUseBensonVO storeLangUseBensonVO);
}
