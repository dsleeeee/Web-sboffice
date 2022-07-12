package kr.co.solbipos.stock.product.product.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProductService {

    /** 생산관리 - 생산등록 리스트 조회 */
    List<DefaultMap<String>> getStProductList(ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 삭제 */
    int deleteProduct(ProductVO[] productVOs, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 싱품 리스트 조회 */
    List<DefaultMap<String>> getProductRegistList(ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 제목 변경(header 정보) */
    int updateProductHdTitle(ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 상품 저장*/
    String saveProductRegist(ProductVO[] productVOs, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 상세 조회 */
    DefaultMap<String> getProductDtl(ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 상세 리스트 조회 */
    List<DefaultMap<String>> getProductDtlList(ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 상세 상품 저장 */
    int saveProductDtl(ProductVO[] productVOs, SessionInfoVO sessionInfoVO);

    /** 생산등록 리더기자료 텍스트 업로드시, 현재 세션ID 와 동일한 데이터 모두 삭제 */
    int deleteUploadProduct(ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드 */
    String saveUploadProduct(ProductVO[] productVOs, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 업로드 실패내역 조회 */
    List<DefaultMap<String>> getUploadErrInfoList (ProductVO productVO, SessionInfoVO sessionInfoVO);

    /** 생산관리 - 생산등록 상품찾기 */
    DefaultMap<String> getProdInfo(ProductVO productVO, SessionInfoVO sessionInfoVO);

}
