package kr.co.solbipos.store.hq.hqmanage.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqMenuVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqPrintTemplVO;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : HqManageMapper.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface HqManageMapper {

    /** 본사 목록 조회 */
    List<DefaultMap<String>> getHqOfficeList(HqManageVO hqManage);

    /** 본사 상세정보조회 */
    DefaultMap<String> dtlInfo(HqManageVO hqManage);

    /** 사업자번호 중복체크 */
    int chkBizNo(HqManageVO hqManage);

    /** 사업자번호 사용현황 목록 */
    List<DefaultMap<String>> getBizUseList(HqManageVO hqManage);

    /** 사업자번호 사용현황 상세 */
    DefaultMap<String> getBizInfoDtl(HqManageVO hqManage);

    /** 본사 코드 조회 */
    String getHqOfficeCd(HqManageVO hqManage);

    /** 본사 신규등록 */
    int regist(HqManageVO hqManage);

    /** 본사 사원등록 */
    int registEmployee(HqManageVO hqManage);

    /** 웹 사용자 등록 */
    int registWebUser(HqManageVO hqManage);

    /** 본사 공통코드 등록 */
    int cmmCodeReg(HqNmcodeVO nmcodeVO);

    /** 공통코드 내려받기 */
    String copyCmmNameCode(HqNmcodeVO hqNmcodeVO);

//    /** TID 복사 프로시져 */
//    String copyTid(HqNmcodeVO nmcodeVO);

//    /** 공통코드 복사 */
//    int copyCmmCode(HqNmcodeVO nmcodeVO);

    /** 본사 포스 출력물 등록 */
    int hqPrintTempReg(HqPrintTemplVO printTempVO);

    /** 본사 수정 */
    int modify(HqManageVO hqManage);

    /** 권한그룹 목록조회 */
    List<DefaultMap<String>> authHqList(HqManageVO hqManage);

    /** 사용메뉴 조회 */
    List<DefaultMap<String>> avlblMenu(HqManageVO hqManage);

    /** 미사용 메뉴 조회 */
    List<DefaultMap<String>> beUseMenu(HqManageVO hqManage);

    /** 메뉴권한복사 */
    int copyAuth(HqMenuVO hqMenuVO);

    /** 권한예외 복사 시, 복사기준본사의 권한예외 값 조회  */
    List<DefaultMap<String>> exceptMenu(HqMenuVO hqMenuVO);

    /** 권한예외 복사 */
    int copyAuthExcp(HqMenuVO hqMenuVO);

    /** 권한확인 */
    int isAuth(HqMenuVO hqMenus);

    /** 메뉴권한 추가*/
    int addAuth(HqMenuVO hqMenus);

    /** 권한 삭제 */
    int removeAuth(HqMenuVO hqMenus);

    /** 환경설정 조회 */
    List<DefaultMap<String>> getConfigList(HqManageVO hqManageVO);

    /** 환경설정 등록 */
    int insertConfig(HqEnvstVO hqEnvst);

    /** 환경설정 수정 */
    int updateConfig(HqEnvstVO hqEnvst);

    /** 환경설정 수정 - 매장 */
    int updateConfigStore(HqEnvstVO hqEnvst);

    /** 권한 전체 삭제 */
    int removeAuthAll(HqMenuVO hqMenus);

    /** 업체 목록 조회 */
    List<DefaultMap<String>> getAgencyCd(HqManageVO hqManage);

}
