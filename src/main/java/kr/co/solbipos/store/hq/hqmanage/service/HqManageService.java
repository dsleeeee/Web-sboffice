package kr.co.solbipos.store.hq.hqmanage.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;

/**
 * @Class Name : HqManageService.java
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

public interface HqManageService {

    /** 본사 목록 조회 */
    List<DefaultMap<String>> list(HqManageVO hqManageVO);

    /** 본사 상세정보 조회 */
    DefaultMap<String> getHqDetailInfo(HqManageVO hqManageVO);

    /** 사업자번호 중복 체크 */
    int chkBizNo(HqManageVO hqManage);

    /** 사업자번호 사용현황 목록 */
    List<DefaultMap<String>>  getBizUseList(HqManageVO hqManageVO);

    /** 사업자번호 사용현황 상세 */
    DefaultMap<String> getBizInfoDtl(HqManageVO hqManageVO);

    /** 본사 신규 등록 */
    int regist(HqManageVO hqManageVO, SessionInfoVO sessionInfoVO);

    /** 본사 수정 */
    int modify(HqManageVO hqManageVO, SessionInfoVO sessionInfoVO);

    /** 권한그룹 목록 조회 */
    List<DefaultMap<String>> authHqList(HqManageVO hqManageVO);

    /** 사용가능한 메뉴 */
    List<DefaultMap<String>> avlblMenu(HqManageVO hqManageVO);

    /** 사용중인 메뉴 */
    List<DefaultMap<String>> beUseMenu(HqManageVO hqManageVO);

    /** 메뉴권한복사 */
    int copyAuth(HqMenuVO hqMenuVO, SessionInfoVO sessionInfoVO);

    /** 메뉴 권한 추가 */
    int addAuth(HqMenuVO[] hqMenuVOs, SessionInfoVO sessionInfoVO);

    /** 메뉴 권한 삭제 */
    int removeAuth(HqMenuVO[] hqMenuVOs, SessionInfoVO sessionInfoVO);

    /** 환경설정 조회 */
    List<DefaultMap<String>> getConfigList(HqManageVO hqManageVO);

    /** 환경설정 저장 */
    int saveConfig(HqEnvstVO[] hqEnvstsVOs, SessionInfoVO sessionInfoVO);
}
