package kr.co.solbipos.sale.pay.payDtlBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.pay.payDtlBenson.service.PayDtlBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PayDtlBensonMapper.java
 * @Description : 벤슨 > 결제수단매출 > 결제수단상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PayDtlBensonMapper {

    /** 결제수단상세 리스트 조회 */
    List<DefaultMap<Object>> getPayDtlBensonList(PayDtlBensonVO payDtlBensonVO);

    /** 결제수단상세 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getPayDtlBensonExcelDivisionList(PayDtlBensonVO payDtlBensonVO);
}
