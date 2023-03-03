package kr.co.solbipos.base.price.salePrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SalePriceService.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SalePriceService {

    /** 상품별 가격정보 조회 */
    DefaultMap<String> getProdInfo(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 상품별 매장 판매가 조회 */
    List<DefaultMap<String>> getProdSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 상품별 매장 판매가 저장 */
    int saveProdSalePrice(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장별 가격정보 조회 */
    List<DefaultMap<String>> getStoreSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 본사 가격정보 조회 */
    List<DefaultMap<String>> getHqSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 본사 판매가 저장 */
    int saveHqProdSalePrice(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);


    /** 엑셀업로드 탭 - 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getSalePriceExcelUploadSampleList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getSalePriceExcelUploadCheckList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 전체 삭제 */
    int getSalePriceExcelUploadCheckDeleteAll(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getSalePriceExcelUploadCheckDelete(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);

    /** 업로드시 임시테이블 저장 */
    int getSalePriceExcelUploadCheckSave(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getSalePriceExcelUploadCheckSaveAdd(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);

    /** 본사판매가관리 엑셀업로드 탭 - 판매가 저장 */
    int getHqSalePriceExcelUploadSave(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장판매가관리 엑셀업로드 탭 - 판매가 저장 */
    int getStoreSalePriceExcelUploadSave(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO);

}
