package kr.co.solbipos.base.prod.dlvrProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayVO;

import java.util.List;

/**
 * @Class Name : DlvrProdService.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 10. 14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DlvrProdService {

    /** 배달시스템 상품 명칭 매핑 - 배달앱구분코드 */
    List<DefaultMap<String>> getDlvrColList(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑 - 상품목록조회 */
    List<DefaultMap<String>> getProdList(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 저장 */
    int saveDlvrProdNm(DlvrProdVO[] dlvrProdVOs, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 복사 */
    int copyDlvrProdNm(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO);

    /** 배달시스템 상품 명칭 매핑 - 상품명칭 엑셀 업로드 전 상품코드 유효여부 체크 */
    int chkDlvrProd(DlvrProdVO dlvrProdVO, SessionInfoVO sessionInfoVO);

}
