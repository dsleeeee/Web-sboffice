package kr.co.solbipos.pos.confg.verAddr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
* @Class Name : VerAddrService.java
* @Description : 포스관리 > POS 설정관리 > 주소 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.10  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.05.10
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface VerAddrService {

    /** 포스버전 목록 조회 */
    List<DefaultMap<String>> list(AddrVerInfoVO verInfo);

    /** 포스버전정보 상세 조회 */
    DefaultMap<String> dtlInfo(AddrVerInfoVO verInfo);

    /** 매장목록 조회 */
    List<DefaultMap<String>> storeList(AddrVerInfoVO verInfo);

    /** 버전 삭제 */
    int verDelete(AddrVerInfoVO verInfo);

    /** 버전 시리얼넘버 중복 체크 */
    int chkVerSerNo(AddrVerInfoVO verInfo);

    /** 버전 등록 */
    boolean regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);

    /** 버전 수정 */
    boolean modify(MultipartHttpServletRequest request, SessionInfoVO sessionInfo);

    /** 매장검색 (매장추가용) */
    List<DefaultMap<String>> srchStoreList(AddrApplcStoreVO applcStore);

    /** 버전 적용 매장 등록 */
    int registStore(AddrApplcStoreVO[] applcStores, SessionInfoVO sessionInfo);

    /** 버전 적용 매장 삭제 */
    int removeStore(AddrApplcStoreVO[] applcStores, SessionInfoVO sessionInfo);
}
