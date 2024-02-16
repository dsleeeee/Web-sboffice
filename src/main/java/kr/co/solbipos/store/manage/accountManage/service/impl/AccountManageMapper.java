package kr.co.solbipos.store.manage.accountManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.accountManage.service.AccountManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AccountManageMapper.java
 * @Description : 기초관리 > 매장정보관리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.14  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface AccountManageMapper {

    /** 계정관리 - 장기미사용 계정리스트 조회 */
    List<DefaultMap<Object>> getLongTermUnusedList(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 계정리스트 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getLongTermUnusedExcelList(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 계정 상태 변경 */
    int updateStatusWebUser(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 계정 사용여부 변경 */
    int updateUseYnWebUser(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 시스템/대리점사원 웹사용여부 변경 */
    int updateWebUseYnCmEmp(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 본사사원 웹사용여부 변경 */
    int updateWebUseYnHqEmp(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 매장사원 웹사용여부 변경 */
    int updateWebUseYnStoreEmp(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 시스템/대리점사원 사용여부 변경 */
    int updateUseYnCmEmp(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 본사사원 사용여부 변경 */
    int updateUseYnHqEmp(AccountManageVO accountManageVO);

    /** 계정관리 - 장기미사용 매장사원 사용여부 변경 */
    int updateUseYnStoreEmp(AccountManageVO accountManageVO);
}
