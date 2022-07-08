package kr.co.solbipos.stock.product.product.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;
import kr.co.solbipos.stock.product.product.service.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    /** 생산관리 - 생산등록 리스트 조회 */
    List<DefaultMap<String>> getStProductList(ProductVO productVO);

    /** 생산관리 - 생산등록 DTL 전부 삭제 */
    int deleteStAllProductDtl(ProductVO productVO);

    /** 생산관리 - 생산등록 HD 삭제 */
    int deleteStProductHd(ProductVO productVO);

    /** 생산관리 - 생산등록 상품 리스트 조회 */
    List<DefaultMap<String>> getProductRegistList(ProductVO productVO);

    /** 생산관리 - 생산등록 제목 변경(header 정보) */
    int updateProductHdTitle(ProductVO productVO);

    /** 생산관리 - 생산등록 신규 SEQ 조회 */
    String getProductNewSeqNo(ProductVO productVO);

    /** 생산관리 - 생산등록 상품 DTL 삭제 */
    int deleteProductDtl(ProductVO productVO);

    /** 생산관리 - 생산등록 상품 DTL 등록 */
    int insertProductDtl(ProductVO productVO);

    /** 생산관리 - 생산등록 상품 DTL 수정 */
    int updateProductDtl(ProductVO productVO);

    /**  산관리 - 생산등록 HD 등록 */
    int insertProductHd(ProductVO productVO);

    /** 생산관리 - 생산등록 HD 수정 */
    int updateProductHd(ProductVO productVO);

    /** 생산관리 - 생산등록 상세 조회 */
    DefaultMap<String> getProductDtl(ProductVO productVO);

    /** 생산관리 - 생산등록 상세 리스트 조회 */
    List<DefaultMap<String>> getProductDtlList(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 현재 세션ID 와 동일한 데이터 모두 삭제 */
    int deleteUploadProduct(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 임시테이블에 저장 */
    int saveUploadProductTemp(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 임시테이블 바코드로 상품코드 맵핑 */
    int updateUploadProductMapping(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteUploadProductData(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 임시테이블 정보로 생산등록 상품 DTL 등록 */
    int saveUploadProductDtl(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 임시테이블 정보로 생산등록 상품 DTL 등록(수량추가 등록) */
    int saveUploadProductAddDtl(ProductVO productVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 정상 입력된 데이터 임시테이블에서 삭제 */
    int deleteUploadProductCompleteData(ProductVO productVO);

    /** 생산관리 - 생산등록 업로드 실패내역 조회 */
    List<DefaultMap<String>> getUploadErrInfoList(ProductVO productVO);

    /** 생산관리 - 생산등록 상품찾기 */
    List<DefaultMap<String>> getProdInfo(ProductVO productVO);
}
