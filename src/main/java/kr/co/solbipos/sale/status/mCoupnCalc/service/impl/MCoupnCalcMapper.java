package kr.co.solbipos.sale.status.mCoupnCalc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.mCoupnCalc.service.MCoupnCalcVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MCoupnCalcMapper.java
 * @Description : 맘스터치 > 매출분석2 > 모바일쿠폰 정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.19  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MCoupnCalcMapper {

    /** 모바일쿠폰 정산 조회 */
    List <DefaultMap<Object>> getMCoupnCalcList(MCoupnCalcVO mCoupnCalcVO);

    /** 모바일쿠폰 정산 조회조건 엑셀다운로드 */
    List <DefaultMap<Object>> getMCoupnCalcExcelList(MCoupnCalcVO mCoupnCalcVO);

    /** 모바일쿠폰 정산 상세화면 조회 */
    List <DefaultMap<Object>> getMCoupnCalcDtlList(MCoupnCalcVO mCoupnCalcVO);

    /** 모바일쿠폰 조회(콤보박스용) */
    List<DefaultMap<Object>> getMCoupnComboList(SessionInfoVO sessionInfoVO);
}
