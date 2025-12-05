package kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service.AcquireSlipRegistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name  : AcquireSlipRegistMapper.java
 * @Description : 국민대 > 매입관리 > 매입전표등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface AcquireSlipRegistMapper {

    /** 매입전표등록 - 거래처 입고/반출 리스트 조회 */
    List<DefaultMap<String>> getSearchInOutStockList(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 상세 조회 */
    DefaultMap<String> getInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출 전표번호 조회 */
    String getNewSlipNo(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 저장 */
    int insertVendrInstock(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 수정 */
    int updateVendrInstock(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 기존상태 조회 */
    String getInOutStockProcFg(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 입고/반출 삭제시 상품이 있는지 여부 조회 */
    String getInOutStockProdExist(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 삭제 */
    int deleteInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 상품 삭제 */
    int deleteInOutStockProdInfo(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출정보 진행상태 변경 */
    int updateInOutStockProcFg(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 거래처 정산 데이터 등록 */
    int insertVendrExact(AcquireSlipRegistVO acquireSlipRegistVO);

    int deleteVendrInstockProdConfm(AcquireSlipRegistVO acquireSlipRegistVO);

    int mergeVendrInstockProdConfm(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 입고 확정 시 원가단가 상품 마스터 수정 */
    int updateLastCostUprc(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 창고 정보 조회 */
    String getOutStorageCd(AcquireSlipRegistVO acquireSlipRegistVO);

    int deleteVendrExact(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 진행구분 조회 */
    DefaultMap<String> getChkProcFg(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출상품 리스트 조회 */
    List<DefaultMap<String>> getSearchInOutStockProdList(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출상품 등록 리스트 저장 */
    int insertVendrInstockDtl(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출상품 등록 리스트 수정 */
    int updateVendrInstockDtl(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출상품 등록 리스트 삭제 */
    int deleteVendrInstockDtl(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 상품 공급가 수정 */
    int updateProdSplyUprc(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매장 상품 공급가 수정 */
    int updateStProdSplyUprc(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 입고/반출정보 DTL의 집계정보 HD에 수정 */
    int updateVendrInstockDtlSumHd(AcquireSlipRegistVO acquireSlipRegistHdVO);

    /** 매입전표등록 - 입고/반출상품 등록 리스트 조회 */
    List<DefaultMap<String>> getSearchInOutStockRegList(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매장 리스트 조회 */
    List<DefaultMap<String>> getAcquireSelectStoreList(SelectStoreVO selectStoreVO);

    /** 거래처 리스트 조회 */
    List<DefaultMap<String>> getAcquireSelectVendrList(IostockCmmVO iostockCmmVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 TEMP 저장 */
    int insertExcelUpload(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    int updateExcelUploadProdCd(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 바코드 업데이트 */
    int updateExcelUploadBarcdCd(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 판매단가 업데이트 */
    int updateExcelUploadUprc(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteInOutStockToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 한 수량을 입고수량으로 입력 */
    int insertInOutStockToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 엑셀업로드 - 엑셀업로드 TEMP 삭제 */
    int deleteExcelUploadCompleteData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 매입전표등록 - 반출서 반출정보 조회(반출처, 공급자 정보) */
    DefaultMap<String> getInOutStockInfo(AcquireSlipRegistVO acquireSlipRegistVO);

    /** 매입전표등록 - 입고/반출상품 리스트 조회 */
    List<DefaultMap<String>> getInOutStockProdList(AcquireSlipRegistVO acquireSlipRegistVO);
}
