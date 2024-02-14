package kr.co.solbipos.base.prod.dlvrProdMulti.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DlvrProdMultiService.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 맵핑2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.30  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DlvrProdMultiService {

    /** 배달시스템 상품 명칭 매핑 다중화 - 배달앱구분코드 */
    List<DefaultMap<String>> getDlvrColList(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품목록조회 */
    List<DefaultMap<String>> getProdList(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2  - 전체 엑셀다운로드 */
    List<DefaultMap<String>> getDlvrProdMultiExcelList(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 저장 */
    int saveDlvrProdMultiNm(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 복사 */
    int copyDlvrProdMultiNm(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 팝업 조회 */
    List<DefaultMap<Object>> getDlvrProdMultiNmStoreRegistList(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 저장 */
    int getDlvrProdMultiNmStoreRegistSave(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드 전 상품코드 유효여부 체크 */
    int chkDlvrProdMulti(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 데이터 임시 저장 */
    int getDlvrProdCdSaveInsert(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 중복 체크 */
    String getDlvrProdMultiNmMappingChk(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 입력값 확인 */
    int getChkProdCdChk(DlvrProdMultiVO dlvrProdMultiVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드(기존값 삭제) */
    int excelDeleteDlvrProdNm(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드(저장) */
    int excelUploadsave(DlvrProdMultiVO[] dlvrProdMultiVOs, SessionInfoVO sessionInfoVO);
}
