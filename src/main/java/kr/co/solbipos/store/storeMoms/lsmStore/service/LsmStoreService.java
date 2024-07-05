package kr.co.solbipos.store.storeMoms.lsmStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;

import java.util.List;

/**
 * @Class Name : LsmStoreService.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface LsmStoreService {

    /** 터치키 리스트 조회 */
    List<DefaultMap<String>> getLsmStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);
    /** 터치키 엑셀 조회 */
    List<DefaultMap<String>> getLsmStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 리스트 조회 */
    List<DefaultMap<String>> getLsmKioskStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 엑셀 조회 */
    List<DefaultMap<String>> getLsmKioskStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 데이터 임시저장 */
    int getKioskKeyTempInsert(LsmStoreVO[] lsmStoreVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 키오스크키맵 삭제 */
    int getDeleteKioskKey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 데이터 중 LSM사용인 데이터 수 조회 */
    int getKioskKeyCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 저장 */
    int getInsertKioskKey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장 */
    int getTukeyTempInsert(LsmStoreVO[] lsmStoreVOs, SessionInfoVO sessionInfoVO);

    /** 터치키 탭 엑셀 업로드 - 키오스크키맵 삭제 */
    int getDeleteTukey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 데이터 중 LSM사용인 데이터 수 조회 */
    int getTukeyCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 터치키 탭 엑셀 업로드 - 저장 */
    int getInsertTukey(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인 */
    String getKioskChkUseYn(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 탭 엑셀 업로드 - 카테고리별 상품수 확인 */
    String getKioskChkProdCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 터치키 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인 */
    String getTukeyChkUseYn(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 터치키 탭 엑셀 업로드 - 분류별 상품수 확인 */
    String getTukeyChkProdCnt(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);
}
