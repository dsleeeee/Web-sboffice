package kr.co.solbipos.sys.admin.adminMedia.service;


import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface AdminMediaService {

    /** 파일타입 콤보박스 조회 */
    List<DefaultMap<String>> getFileTypeComboList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO);

    /** 듀얼모니터영상관리 탭 - 조회 */
    List<DefaultMap<String>> getMediaList(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 삭제 */
    int getMediaDelete(AdminMediaVO[] adminMediaVOS, SessionInfoVO sessionInfoVO);

    /** 파일등록 팝업 - 첨부파일 확장자 체크 */
    String getFileType(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 파일등록 */
    String getRegistMedia(MultipartHttpServletRequest request, SessionInfoVO sessionInfo);

    /** 듀얼모니터영상관리 탭 - 파일수정 */
    String getModifyMedia(MultipartHttpServletRequest request, SessionInfoVO sessionInfo);

    /** 듀얼모니터영상관리 탭 - 파일정보 상세 조회 */
    DefaultMap<String> dtlInfo(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 버전 체크 */
    String chkFileType(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 날짜 체크 */
    String chkDate(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 파일 중복 가능 갯수 확인 */
    String chkDupCnt(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 파일 갯수 확인 */
    int chkFileTypeCnt(SessionInfoVO sessionInfoVO, AdminMediaVO adminMediaVO);

    /** 재생순서관리 탭 - 조회 */
    List<DefaultMap<Object>> getMediaPlaySeqList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO);

    /** 재생순서관리 탭 - 저장 */
    int getMediaPlaySeqSaveUpdate(AdminMediaVO[] adminMediaVOs, SessionInfoVO sessionInfoVO);

    /** 매장등록 - 조회 */
    List<DefaultMap<String>> srchStoreList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO);

    /** 매장 적용 - 파일 등록 */
    int getRegistStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo);

    /** 매장 적용 - 파일 삭제 */
    int getRemoveStore(MediaApplcStoreVO[] applcStores, SessionInfoVO sessionInfo);

    /** 적용버전 - 조회 */
    List<DefaultMap<String>> getSearchRegVersionList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO);

    /** 적용 버전 - 버전 삭제 */
    int getRemoveVersion(AdminMediaVO[] adminMediaVOS, SessionInfoVO sessionInfo);

    /** 적용 버전 - 버전 등록 */
    int getRegistVersion(AdminMediaVO[] adminMediaVOS, SessionInfoVO sessionInfo);

    /** 매장등록 - 등록 매장 조회 */
    List<DefaultMap<String>> srchRegStoreList(AdminMediaVO adminMediaVO, SessionInfoVO sessionInfoVO);
}
