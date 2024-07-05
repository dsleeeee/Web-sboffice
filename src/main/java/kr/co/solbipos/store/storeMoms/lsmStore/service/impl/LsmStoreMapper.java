package kr.co.solbipos.store.storeMoms.lsmStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : LsmStoreMapper.java
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

@Mapper
@Repository
public interface LsmStoreMapper {

    /** 터치키 리스트 조회 */
    List<DefaultMap<String>> getLsmStoreList(LsmStoreVO lsmStoreVO);

    /** 터치키 엑셀 조회 */
    List<DefaultMap<String>> getLsmStoreExcelList(LsmStoreVO lsmStoreVO);

    /** 키오스크 리스트 조회 */
    List<DefaultMap<String>> getLsmKioskStoreList(LsmStoreVO lsmStoreVO);
    
    /** 키오스크 엑셀 조회 */
    List<DefaultMap<String>> getLsmKioskStoreExcelList(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 -  임시 저장 데이터 삭제 */
    int getKioskKeyTempDeleteAll(LsmStoreVO lsmStoreVO);

    /** 상품코드 유효여부 체크 */
    int getChkProdCd(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 - 데이터 임시 저장 */
    int getKioskKeyTempInsertAll(LsmStoreVO lsmStoreVO);

    /** 키오스크 키 관련 코드 조회 */
    DefaultMap<String> getKioskKeyMapCode(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 - 키오스크키맵 삭제 */
    int getDeleteKioskKey(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 - 데이터 중 LSM '사용' 데이터 수 조회 */
    int getKioskKeyCnt(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 - 저장 */
    int getInsertKioskKey(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 임시 저장 데이터 삭제 */
    int getTukeyTempDeleteAll(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장(셀 사이즈) */
    int getTukeyTempInsert01(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장(상품명) */
    int getTukeyTempInsert02(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장(가격) */
    int getTukeyTempInsert03(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 터치키 삭제 */
    int getDeleteTukey(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 중 LSM '사용' 데이터 수 조회 */
    int getTukeyCnt(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장(셀 사이즈) */
    int getTukeyInsert01(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장(상품명) */
    int getTukeyInsert02(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 데이터 임시 저장(가격) */
    int getTukeyInsert03(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 매장코드 조회 */
    List<DefaultMap<String>> getSrchStoreCd(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인 */
    String getKioskChkUseYn(LsmStoreVO lsmStoreVO);

    /** 키오스크 탭 엑셀 업로드 - 카테고리별 상품수 확인 */
    String getKioskChkProdCnt(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 단종/미사용 상품 유무 확인 */
    String getTukeyChkUseYn(LsmStoreVO lsmStoreVO);

    /** 터치키 탭 엑셀 업로드 - 분류별 상품수 확인 */
    String getTukeyChkProdCnt(LsmStoreVO lsmStoreVO);
}
