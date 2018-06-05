package kr.co.solbipos.store.hq.hqmanage.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * 가맹점관리 > 본사정보 > 본사정보관리
 * 
 * @author 김지은
 */
public interface HqManageService {
    
    /**
     * 본사 목록 조회
     * 
     * @param hqManave
     * @return
     */
    List<DefaultMap<String>> list(HqManageVO hqManave);

    /**
     * 본사 상세정보 조회
     * 
     * @param hqManage
     * @return
     */
    DefaultMap<String> dtlInfo(HqManageVO hqManage);

    /**
     * 사업자번호 중복 체크
     * @param hqManage
     * @return
     */
    int chkBizNo(HqManageVO hqManage);

    /**
     * 사업자번호 사용현황 목록
     * @param hqManage
     * @return
     */
    List<DefaultMap<String>>  getBizUseList(HqManageVO hqManage);

    /**
     * 사업자번호 사용현황 상세
     * @param hqManage
     * @return
     */
    DefaultMap<String> getBizInfoDtl(HqManageVO hqManage);
    
    /**
     * 본사 신규 등록
     * @param hqManage
     * @param sessionInfoVO 
     * @return
     */
    int regist(HqManageVO hqManage, SessionInfoVO sessionInfoVO);

    /**
     * 본사 수정
     * @param hqManage
     * @param sessionInfoVO
     * @return
     */
    int modify(HqManageVO hqManage, SessionInfoVO sessionInfoVO);

    /**
     * 권한그룹 목록 조회
     * @return
     */
    List<DefaultMap<String>> authHqList(HqManageVO hqManage);

    /**
     * 사용가능한 메뉴
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
    int copyAuth(HqMenuVO hqMenu, SessionInfoVO sessionInfoVO);

    /**
     * 메뉴 권한 추가 
     * @param hqManage
     * @param sessionInfoVO
     * @return
     */
    int addAuth(HqMenuVO[] hqMenu, SessionInfoVO sessionInfoVO);

    /**
     * 메뉴 권한 삭제
     * @param hqManage
     * @param sessionInfoVO
     * @return
     */
    int removeAuth(HqMenuVO[] hqMenu, SessionInfoVO sessionInfoVO);


    
    
}
