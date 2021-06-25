package kr.co.solbipos.base.store.media.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
* @Class Name : MediaService.java
* @Description : 기초관리 > 매장관리 > 미디어관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.06.09  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.06.09
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface MediaService {

    /** 포스버전 목록 조회 */
    List<DefaultMap<String>> list(SessionInfoVO sessionInfo, MediaVO mediaVO);

    /** 포스버전정보 상세 조회 */
    DefaultMap<String> dtlInfo(SessionInfoVO sessionInfo, MediaVO mediaVO);

    /** 매장목록 조회 */
    List<DefaultMap<String>> storeList(MediaVO mediaVO);

    /** 버전 삭제 */
    int verDelete(MediaVO mediaVO);

    /** 버전 체크 */
    String chkFileType(SessionInfoVO sessionInfoVO, MediaVO mediaVO);

    /** 파일 확장자 */
    String getFileType(SessionInfoVO sessionInfoVO, MediaVO mediaVO);

    /** 날짜 체크 */
    String chkDate(SessionInfoVO sessionInfoVO, MediaVO mediaVO);

    /** 버전 등록 */
    String regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);

    /** 버전 수정 */
    String modify(MultipartHttpServletRequest request, SessionInfoVO sessionInfo);

    /** 매장검색 (매장추가용) */
    List<DefaultMap<String>> srchStoreList(MediaApplcStoreVO applcStore, SessionInfoVO sessionInfoVO);

    /** 버전 적용 매장 등록 */
    int registStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo);

    /** 버전 적용 매장 삭제 */
    int removeStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo);
}
