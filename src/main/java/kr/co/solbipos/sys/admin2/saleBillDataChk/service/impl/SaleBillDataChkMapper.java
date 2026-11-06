package kr.co.solbipos.sys.admin2.saleBillDataChk.service.impl;

import kr.co.solbipos.sys.admin2.saleBillDataChk.service.SaleBillDataChkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : SaleBillDataChkMapper.java
 * @Description : 시스템관리 > 관리자기능2 > 매출자료검증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.11.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleBillDataChkMapper {

    /** 매출자료검증 - 영수증 1건 정합성 체크 (SP_SALE_BILL_DATA_CHECK_S02 호출) */
    String getSaleBillDataChk(SaleBillDataChkVO saleBillDataChkVO);
}
