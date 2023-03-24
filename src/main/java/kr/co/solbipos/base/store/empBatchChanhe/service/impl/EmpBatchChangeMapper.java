package kr.co.solbipos.base.store.empBatchChanhe.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpMenuVO;
import kr.co.solbipos.base.store.empBatchChanhe.service.EmpBatchChangeVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EmpBatchChangeMapper.java
 * @Description : 기초관리 > 사원관리 > 사원정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.16  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.02.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface EmpBatchChangeMapper {

    /** 사원목록 조회 */
    List<DefaultMap<String>> getEmpList(EmpBatchChangeVO empBatchChangeVO);
    List<DefaultMap<String>> getEmpList2(EmpBatchChangeVO empBatchChangeVO);

    /** 사원정보 저장 */
    int getEmpBatchChangeSave(EmpBatchChangeVO empBatchChangeVO);

    /** 사원정보 저장 */
    int getEmpBatchChangeUploadSave(EmpBatchChangeVO empBatchChangeVO);

    /** 검증결과 삭제 */
    int getEmpExcelUploadCheckDeleteAll(EmpBatchChangeVO empBatchChangeVO);

    /** 사원번호 */
    int getEmpNoChk(EmpBatchChangeVO empBatchChangeVO);
    /** 지사코드 */
    String getBranchCdChk(EmpBatchChangeVO empBatchChangeVO);
    /** 본사공통코드 */
    String getHqNmcodeChk(EmpBatchChangeVO empBatchChangeVO);

    /** 검증결과 저장 */
    int getEmpExcelUploadCheckSave(EmpBatchChangeVO empBatchChangeVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getEmpExcelUploadCheckList(EmpBatchChangeVO empBatchChangeVO);

    /** 사원정보 저장 */
    int getSimpleEmpSave(EmpBatchChangeVO empBatchChangeVO);
    int getSimpleEmpInfoSave(EmpBatchChangeVO empBatchChangeVO);

    /** 저장 완료된 검증결과 삭제 */
    int getEmpExcelUploadCheckDelete(EmpBatchChangeVO empBatchChangeVO);


    /** 메뉴권한복사 */
    int copyAuth(EmpBatchChangeVO empBatchChangeVO);

    /** 기존 메뉴권한 예외값 삭제 */
    int removeAuthAll(EmpBatchChangeVO empBatchChangeVO);

    /** 권한예외 복사 시, 복사기준본사의 권한예외 값 조회  */
    List<DefaultMap<String>> exceptMenu(EmpBatchChangeVO empBatchChangeVO);

    /** 권한예외 복사 */
    int copyAuthExcp(EmpBatchChangeVO empBatchChangeVO);

}
