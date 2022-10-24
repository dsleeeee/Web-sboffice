package kr.co.solbipos.base.store.media.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.media.service.MediaApplcStoreVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : MediaMapper.java
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
@Mapper
@Repository
public interface MediaMapper {

    /** 포스버전 목록 조회 */
    List<DefaultMap<String>> getList(MediaVO mediaVO);

    /** 포스버전정보 상세 조회 */
    DefaultMap<String> dtlInfo(MediaVO mediaVO);
    DefaultMap<String> dtlInfo2(MediaVO mediaVO);

    /** 버전 삭제 */
    int verDelete(MediaVO mediaVO);

    /** 등록 매장 목록 */
    List<DefaultMap<String>> storeList(MediaVO mediaVO);

    /** 일련번호 중복 체크 */
    int chkVerSerNo(MediaVO mediaVO);

    /** 파일 코드 채번 */
    String getFileCd(MediaVO mediaVO);

    /** 버전 등록 */
    int verRegist(MediaVO mediaVO);

    /** 버전 수정 */
    int verModify(MediaVO mediaVO);

    /** 파일타입체크 */
    String chkFileType(MediaVO mediaVO);

    /** 파일 확장자 */
    String getFileType(MediaVO mediaVO);

    /** 파일 확장자 */
    String getFileTypeNm(MediaVO mediaVO);

    /** 날짜 체크 */
    String chkDate(MediaVO mediaVO);

    /** 추가가능한 매장 목록 조회  */
    List<DefaultMap<String>> srchStoreList(MediaApplcStoreVO applcStore);

    /** 버전 적용 매장 등록 */
    int registStore(MediaApplcStoreVO applcStore);

    /** 버전 적용 매장 삭제 */
    int removeStore(MediaApplcStoreVO applcStore);

    /** 동영상출력순서 자동채번 */
    String getDispSeq(MediaVO mediaVO);

    /** 듀얼모니터영상관리 탭 - 삭제 */
    int getMediaDelete(MediaVO mediaVO);

    /** 듀얼모니터영상관리 탭 - 적용매장 삭제 */
    int getMediaHqStoreDelete(MediaVO mediaVO);

    /** 재생순서관리 탭 - 조회 */
    List<DefaultMap<Object>> getMediaPlaySeqList(MediaVO mediaVO);

    /** 재생순서관리 탭 - 저장 */
    int getMediaPlaySeqSaveUpdate(MediaVO mediaVO);
}
