package kr.co.solbipos.sys.admin.saleChk.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.admin.logSend.service.LogSendVO;
import kr.co.solbipos.sys.admin.saleChk.service.SaleChkVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleChkMapper.java
 * @Description : 시스템관리 > 관리자기능 > 매출점검
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.07  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface SaleChkMapper {

    /** 조회 */
    List<DefaultMap<String>> getSaleList(SaleChkVO saleChkVO);

    /** RESULT_MSG 업데이트 */
    int updateResultMsg(SaleChkVO saleChkVO);
}
