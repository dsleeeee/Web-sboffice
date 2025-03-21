package kr.co.solbipos.sys.admin.adminMedia.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.sys.admin.adminMedia.service.AdminMediaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMediaMapper {

    /** 파일타입 콤보박스 조회 */
    List<DefaultMap<String>> getFileTypeComboList(AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 조회 */
    List<DefaultMap<String>> getMediaList(AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 삭제 */
    int getMediaDelete(AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 적용매장 삭제 */
    int getMediaHqStoreDelete(AdminMediaVO adminMediaVO);

    /** 파일등록 팝업 - 첨부파일 확장자 체크 */
    String getFileType(AdminMediaVO adminMediaVO);

    /** 파일 코드 채번 */
    String getFileCd(AdminMediaVO mediaVO);

    /** 파일 확장자명 조회 */
    String getFileTypeNm(AdminMediaVO mediaVO);

    /** 동영상순서 자동채번 */
    String getDispSeq(AdminMediaVO mediaVO);

    /** 파일 등록 */
    int verRegist(AdminMediaVO mediaVO);

    /** 파일 수정 */
    int verModify(AdminMediaVO adminMediaVO);

    /** 파일 상세 정보 조회 */
    DefaultMap<String> dtlInfo2(AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 파일정보 상세 조회 */
    DefaultMap<String> dtlInfo(AdminMediaVO adminMediaVO);

    /** 날짜 체크 */
    String chkDate(AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 파일 중복 가능 갯수 확인 */
    String chkDupCnt(AdminMediaVO adminMediaVO);

    /** 듀얼모니터영상관리 탭 - 파일 갯수 확인 */
    int chkFileTypeCnt(AdminMediaVO adminMediaVO);

    /** 재생순서관리 탭 - 조회 */
    List<DefaultMap<Object>> getMediaPlaySeqList(AdminMediaVO adminMediaVO);

    /** 재생순서관리 탭 - 저장 */
    int getMediaPlaySeqSaveUpdate(AdminMediaVO adminMediaVO);

    /** 중복허용여부 조회 */
    String chkFileType(AdminMediaVO adminMediaVO);

    /** 매장 등록 - 조회 */
    List<DefaultMap<String>> srchStoreList(AdminMediaVO adminMediaVO);

    /** 매장 적용 - 파일 등록 */
    int getRegistStore(MediaApplcStoreVO applcStore);

    /** 매장 적용 - 파일 삭제 */
    int getRemoveStore(MediaApplcStoreVO applcStore);
}
