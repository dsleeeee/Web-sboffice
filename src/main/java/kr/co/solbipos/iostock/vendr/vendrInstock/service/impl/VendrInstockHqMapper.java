package kr.co.solbipos.iostock.vendr.vendrInstock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VendrInstockHqMapper {
    /** 거래처 입고/반출등록 - 거래처 입고/반출 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 거래처 입고/반출 등록시 발주번호 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockOrderSlipList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 상세 조회 */
    DefaultMap<String> getSlipInfo(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 신규전표 조회 */
    String getNewSlipNo(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 HD 등록 */
    int insertVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 HD 수정 */
    int updateVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 HD 삭제 */
    int deleteVendrInstockHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 PROD 삭제 */
    int deleteVendrInstockProdInfo(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 삭제시 상품이 있는지 여부 조회 */
    String getDtlProdExist(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 현재 진행상태 값 조회 */
    String getVendrInstockProcFg (VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경 */
    int updateProcFg(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 DT 내역의 입고관련 정보 초기화 */
    int updateDefaultVendrOrderDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 DT 내역의 입고관련 정보 갱신 */
    int updateVendrInstockToOrderDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 입고DT에 있으면서 발주 DT 내역에 없는 내역은 신규로 생성 */
    int insertVendrInstockToOrderDtl(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 DT 내역의 집계정보 HD에 수정 */
    int updateVendrOrderDtlSumHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 발주 테이블의 진행구분 수정 */
    int updateVendrOrderProcFg(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockProdList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 진행구분 조회 */
    DefaultMap<String> getProcFgCheck(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockProdRegList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 DTL 등록 */
    int insertVendrInstockDtl(VendrInstockVO vendrInstockVO);
    
    /** 거래처 입고/반출등록 - 입고/반출상품 PROD 등록 */
    int insertVendrInstockProd(VendrInstockVO vendrInstockVO);
    
    int mergeVendrInstockProd(VendrInstockVO vendrInstockVO);
    
    int mergeVendrInstockProdConfm(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 상품마스터 최종판매단가 update */
    int updateLastCostUprc(VendrInstockVO vendrInstockVO);
    
    /** 거래처 입고/반출등록 - 입고/반출상품 DTL 수정 */
    int updateVendrInstockDtl(VendrInstockVO vendrInstockVO);
    
    /** 거래처 입고/반출등록 - 입고/반출상품 PROD 수정 */
    int updateVendrInstockProd(VendrInstockVO vendrInstockVO);
    
    /** 거래처 입고/반출등록 - 입고/반출상품 DTL 삭제 */
    int deleteVendrInstockDtl(VendrInstockVO vendrInstockVO);
    
    /** 거래처 입고/반출등록 - 입고/반출상품 PROD 삭제 */
    int deleteVendrInstockProd(VendrInstockVO vendrInstockVO);    
    
    int deleteVendrInstockProdConfm(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 상품 공급가 수정 */
    int updateProdSplyUprc(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출 상품 공급가 수정(매장) */
    int updateStProdSplyUprc(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 DTL의 집계정보 HD에 수정 */
    int updateVendrInstockDtlSumHd(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 발주내역으로 세팅 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockOrderInfoRegList(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 거래처정산 데이터 등록 */
    int insertVendrExact(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경시 거래처정산 데이터 삭제 */
    int deleteVendrExact(VendrInstockVO vendrInstockVO);

    /** 거래처 입고/반출등록 엑셀업로드 - 엑셀업로드 수량추가 */
    int insertExcelUploadAddQty(ExcelUploadMPSVO excelUploadMPSVO);

    /** 거래처 입고/반출등록 엑셀업로드 - 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteVendrInstockToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 거래처 입고/반출등록 엑셀업로드 - 엑셀업로드 한 수량을 입고수량으로 입력 */
    int insertVendrInstockToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 거래처 입고/반출등록 엑셀업로드 - 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 거래처 입고/반출등록 - 반출서 반출정보 조회(반출처, 공급자 정보) */
    DefaultMap<String> getVendrInstockReportInfo(VendrInstockVO vendrInstockVO);
    
    /** TB_PO_HQ_VENDR_INSTOCK_PROD 확정여부 'Y' */
    int saveVendrInstockProd(VendrInstockVO vendrInstockVO);
    
    /** TB_PO_HQ_VENDR_INSTOCK_PROD - 창고 조회 */
    String getOutStorageCd(VendrInstockVO vendrInstockVO);
    
    
}
