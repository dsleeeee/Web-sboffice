package kr.co.solbipos.sale.benson.payFgBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.benson.payFgBenson.service.PayFgBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PayFgBensonMapper.java
 * @Description : 벤슨 > 결제수단별 매출 > 결제수단별 일 매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PayFgBensonMapper {

    /** 조회 */
    List<DefaultMap<Object>> getPayFgBensonList(PayFgBensonVO payFgBensonVO);

    /** 엑셀 조회 */
    List<DefaultMap<Object>> getPayFgBensonExcelList(PayFgBensonVO payFgBensonVO);
}
