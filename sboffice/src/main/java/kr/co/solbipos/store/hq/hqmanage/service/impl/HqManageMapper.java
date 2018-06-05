package kr.co.solbipos.store.hq.hqmanage.service.impl;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqMenuVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqPrintTemplVO;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
public interface HqManageMapper {

    /**
     * 본사 목록 조회
     * 
     * @param hqManage
     * @return
     */
    List<DefaultMap<String>> list(HqManageVO hqManage);

    /**
     * 본사 상세정보조회
     * 
     * @param hqManage
     * @return
     */
    DefaultMap<String> dtlInfo(HqManageVO hqManage);

    /**
     * 사업자번호 중복체크
     * @param hqManage
     * @return
     */
    int chkBizNo(HqManageVO hqManage);

    /**
     * 사업자번호 사용현황 목록
     * @param hqManage
     * @return
     */
    List<DefaultMap<String>> getBizUseList(HqManageVO hqManage);
    
    /**
     * 사업자번호 사용현황 상세
     * @param hqManage
     * @return
     */
    DefaultMap<String> getBizInfoDtl(HqManageVO hqManage);
    
    /**
     * 본사 코드 조회
     * @param hqManage
     * @return
     */
    String getHqOfficeCd(HqManageVO hqManage);
    
    /**
     * 본사 신규등록
     * @param hqManage
     * @return
     */
    int regist(HqManageVO hqManage);

    /**
     * 본사 사원등록
     * @param hqManage
     * @return
     */
    int registEmployee(HqManageVO hqManage);
    
    /**
     * 웹 사용자 등록
     * @param hqManage
     * @return
     */
    int registWebUser(HqManageVO hqManage);

    /**
     * 본사 공통코드 등록
     * @param nmcodeVO
     * @return
     */
    int cmmCodeReg(HqNmcodeVO nmcodeVO);

    /**
     * 본사 포스 출력물 등록
     * @param printTempVO
     * @return
     */
    int hqPrintTempReg(HqPrintTemplVO printTempVO);

    /**
     * 본사 수정
     * @param hqManage
     * @return
     */
    int modify(HqManageVO hqManage);

    /**
     * 권한그룹 목록조회
     * @return
     */
    List<DefaultMap<String>> authHqList(HqManageVO hqManage);

    /**
     * 사용가능 메뉴
     * @param hqManage
     * @return
     */
    List<DefaultMap<String>> avlblMenu(HqManageVO hqManage);

    /**
     * 사용중인 메뉴
     * @param hqManage
     * @return
     */
    List<DefaultMap<String>> beUseMenu(HqManageVO hqManage);

    /**
     * 메뉴권한복사
     * @param hqManage
     * @param sessionInfoVO
     * @return
     */
    int copyAuth(HqMenuVO hqMen, SessionInfoVO sessionInfoVO);

    /**
     * 권한예외 복사
     * @param hqManage
     * @param sessionInfoVO
     * @return
     */
    int copyAuthExcp(HqMenuVO hqMen, SessionInfoVO sessionInfoVO);

    /**
     * 권한확인
     * @param hqManage
     * @return
     */
    int isAuth(HqMenuVO hqMenus);

    /**
     * 메뉴권한 추가
     * @param hqManage
     * @return
     */
    int addAuth(HqMenuVO hqMenus);

    /**
     * 권한 삭제
     * @param hqManage
     * @return
     */
    int removeAuth(HqMenuVO hqMenus);

    


}
