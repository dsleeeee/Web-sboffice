package kr.co.solbipos.accounting.accountingMain.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.accounting.accountingMain.service.AccountingMainVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AccountingMainMapper.java
 * @Description : 벤슨 > 회계관리 > 회계관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface AccountingMainMapper {

    /** 일별전송 탭 - 조회 */
    List<DefaultMap<String>> getAcDayTransferList(AccountingMainVO accountingMainVO);

    /** 월별전송 탭 - 조회 */
    List<DefaultMap<String>> getAcMonthTransferList(AccountingMainVO accountingMainVO);

    /** 매장별 항목관리 탭 - 조회 */
    List<DefaultMap<String>> getAcStoreOptionList(AccountingMainVO accountingMainVO);

    /** 매장별 항목관리 탭 - 저장 */
    int saveAcStoreOption(AccountingMainVO accountingMainVO);

    /** 매장별 항목관리 탭 - 삭제 */
    int delAcStoreOption(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 공통코드(좌측, TB_AC_HQ_NMCODE) 조회 */
    List<DefaultMap<String>> getAcComCodeList(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 상세코드(우측, TB_AC_HQ_NMCODE_DTL) 조회 */
    List<DefaultMap<String>> getAcComCodeDtlList(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 공통코드(좌측) 저장(추가) */
    int saveAcComCodeInsert(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 공통코드(좌측) 저장(수정) */
    int saveAcComCodeUpdate(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 공통코드(좌측) 저장(삭제) */
    int saveAcComCodeDelete(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 공통코드(좌측) 삭제 시 하위 상세코드 일괄삭제 */
    int deleteAcComCodeDtlByNmcodeCd(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 상세코드(우측) 저장(추가) */
    int saveAcComCodeDtlInsert(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 상세코드(우측) 저장(수정) */
    int saveAcComCodeDtlUpdate(AccountingMainVO accountingMainVO);

    /** 공통코드관리 탭 - 상세코드(우측) 저장(삭제) */
    int saveAcComCodeDtlDelete(AccountingMainVO accountingMainVO);

}
