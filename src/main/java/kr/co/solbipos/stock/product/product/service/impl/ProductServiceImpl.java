package kr.co.solbipos.stock.product.product.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.stock.product.product.service.ProductService;
import kr.co.solbipos.stock.product.product.service.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service("productServiceImpl")
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final MessageService messageService;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, MessageService messageService) {
        this.productMapper = productMapper;
        this.messageService = messageService;
    }

    /** 생산관리 - 생산등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStProductList(ProductVO productVO, SessionInfoVO sessionInfoVO) {

        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return productMapper.getStProductList(productVO);
    }

    /** 생산관리 - 생산등록 삭제 */
    @Override
    public int deleteProduct(ProductVO[] productVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (ProductVO productVO : productVOs) {

            productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                productVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            productVO.setRegId(sessionInfoVO.getUserId());
            productVO.setRegDt(currentDt);
            productVO.setModId(sessionInfoVO.getUserId());
            productVO.setModDt(currentDt);

            // DTL 삭제
            result = productMapper.deleteStAllProductDtl(productVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // HD 삭제
            result = productMapper.deleteStProductHd(productVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }

    /** 생산관리 - 생산등록 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProductRegistList(ProductVO productVO, SessionInfoVO sessionInfoVO) {

        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return productMapper.getProductRegistList(productVO);
    }

    /** 생산관리 - 생산등록 제목 변경(header 정보) */
    public int updateProductHdTitle(ProductVO productVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        productVO.setRegId(sessionInfoVO.getUserId());
        productVO.setRegDt(currentDt);
        productVO.setModId(sessionInfoVO.getUserId());
        productVO.setModDt(currentDt);

        result = productMapper.updateProductHdTitle(productVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 생산관리 - 생산등록 상품 저장 */
    @Override
    public int saveProductRegist(ProductVO[] productVOs, SessionInfoVO sessionInfoVO) {

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        // header 테이블 저장용
        ProductVO productHdVO = new ProductVO();
        String seqNo = "";

        for (ProductVO productVO : productVOs) {

            productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                productVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            productVO.setRegId(sessionInfoVO.getUserId());
            productVO.setRegDt(currentDt);
            productVO.setModId(sessionInfoVO.getUserId());
            productVO.setModDt(currentDt);

            // 차수가 없는 경우, seqNo 생성
            if(isEmpty(productVO.getSeqNo())){
                seqNo = productMapper.getProductNewSeqNo(productVO);
                productVO.setSeqNo(seqNo);
            }

            // header 테이블 저장을 위한 set
            if(i == 0) {
                productHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                productHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    productHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                productHdVO.setRegId(sessionInfoVO.getUserId());
                productHdVO.setRegDt(currentDt);
                productHdVO.setModId(sessionInfoVO.getUserId());
                productHdVO.setModDt(currentDt);

                productHdVO.setProductTitle(productVO.getProductTitle());
                productHdVO.setProductDate(productVO.getProductDate());
                productHdVO.setProductFg(productVO.getProductFg());
                productHdVO.setStorageCd(productVO.getStorageCd());
                productHdVO.setProductStorageCd(productVO.getProductStorageCd());
                productHdVO.setProcFg("0"); // 0: 등록

                if(!isEmpty(seqNo)){
                    productHdVO.setSeqNo(seqNo);
                }else{
                    productHdVO.setSeqNo(productVO.getSeqNo());
                }
            }

            // 삭제
            if(StringUtil.getOrBlank(productVO.getStatus()).equals("DELETE")) {

                result = productMapper.deleteProductDtl(productVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            } else if(StringUtil.getOrBlank(productVO.getStatus()).equals("UPDATE")) {

                // 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                if(StringUtil.getOrBlank(productVO.getProductProdStatus()).equals("I")) {
                    result = productMapper.insertProductDtl(productVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                if(StringUtil.getOrBlank(productVO.getProductProdStatus()).equals("U")) {
                    result = productMapper.updateProductDtl(productVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // header 테이블 등록
        if(!isEmpty(seqNo)){
            result = productMapper.insertProductHd(productHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }else{
            result = productMapper.updateProductHd(productHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }


        return returnResult;
    }

    /** 생산관리 - 생산등록 상세 조회 */
    public DefaultMap<String> getProductDtl(ProductVO productVO, SessionInfoVO sessionInfoVO){

        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return productMapper.getProductDtl(productVO);
    }

    /** 생산관리 - 생산등록 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProductDtlList(ProductVO productVO, SessionInfoVO sessionInfoVO) {

        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return productMapper.getProductDtlList(productVO);
    }

    /** 생산관리 - 생산등록 상세 상품 저장 */
    @Override
    public int saveProductDtl(ProductVO[] productVOs, SessionInfoVO sessionInfoVO) {

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

        // header 테이블 저장용
        ProductVO productHdVO = new ProductVO();
        String confirmFg = "";

        for (ProductVO productVO : productVOs) {

            productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                productVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            productVO.setRegId(sessionInfoVO.getUserId());
            productVO.setRegDt(currentDt);
            productVO.setModId(sessionInfoVO.getUserId());
            productVO.setModDt(currentDt);

            // HD 저장을 위한 파라미터 세팅
            if(i == 0) {
                confirmFg = StringUtil.getOrBlank(productVO.getConfirmFg());

                // 확정인 경우 procFg 를 1로 변경
                if(confirmFg.equals("Y")) {
                    productHdVO.setProcFg("1");
                }
                else {
                    productHdVO.setProcFg("0");
                }

                productHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                productHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    productHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                productHdVO.setRegId(sessionInfoVO.getUserId());
                productHdVO.setRegDt(currentDt);
                productHdVO.setModId(sessionInfoVO.getUserId());
                productHdVO.setModDt(currentDt);

                productHdVO.setProductTitle(productVO.getProductTitle());
                productHdVO.setProductDate(productVO.getProductDate());
                productHdVO.setProductFg(productVO.getProductFg());
                productHdVO.setSeqNo(productVO.getSeqNo());
                productHdVO.setStorageCd(productVO.getStorageCd());
                productHdVO.setProductStorageCd(productVO.getProductStorageCd());
                productHdVO.setStatus(productVO.getStatus());
            }

            // 삭제
            if(StringUtil.getOrBlank(productVO.getStatus()).equals("DELETE")) {

                result = productMapper.deleteProductDtl(productVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            } else if(StringUtil.getOrBlank(productVO.getStatus()).equals("UPDATE")) {

                // 상품의 상태(등록이 되어있지 않은 경우 I, 이미 등록된 경우 U)
                if(StringUtil.getOrBlank(productVO.getProductProdStatus()).equals("I")) {
                    result = productMapper.insertProductDtl(productVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                if(StringUtil.getOrBlank(productVO.getProductProdStatus()).equals("U")) {
                    result = productMapper.updateProductDtl(productVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // header 테이블 수정
        result = productMapper.updateProductHd(productHdVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return returnResult;
    }

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 현재 세션ID 와 동일한 데이터 모두 삭제 */
    @Override
    public int deleteUploadProduct(ProductVO productVO, SessionInfoVO sessionInfoVO) {

        productVO.setSessionId(sessionInfoVO.getSessionId());

        int result = productMapper.deleteUploadProduct(productVO);

        return result;
    }

    /** 생산관리 - 생산등록 리더기자료 텍스트 업로드 */
    @Override
    public int saveUploadProduct(ProductVO[] productVOs, SessionInfoVO sessionInfoVO) {

        int returnResult = 0;
        int result = 0;
        int i = 0;
        String currentDt = currentDateTimeString();

       // header 테이블 저장용
       ProductVO productHdVO = new ProductVO();
       // dtl 테이블 저장용
       ProductVO productDtlVO = new ProductVO();

       //
       String seqNo = "";
       String insFg = "";

       // 임시테이블에 저장
       for (ProductVO productVO : productVOs) {

            // header, dtl 테이블 저장을 위한 set
            if(i == 0) {

                seqNo = StringUtil.getOrBlank(productVO.getSeqNo());
                insFg = (seqNo.equals("") ? "I" : "U");

                // dtl
                productDtlVO.setSessionId(sessionInfoVO.getSessionId());
                productDtlVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                productDtlVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    productDtlVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                productDtlVO.setRegId(sessionInfoVO.getUserId());
                productDtlVO.setRegDt(currentDt);
                productDtlVO.setModId(sessionInfoVO.getUserId());
                productDtlVO.setModDt(currentDt);

                productDtlVO.setProductDate(productVO.getProductDate());
                productDtlVO.setProductFg(productVO.getProductFg());
                productDtlVO.setHqBrandCd(productVO.getHqBrandCd());
                productDtlVO.setStorageCd(productVO.getStorageCd());
                productDtlVO.setProductStorageCd(productVO.getProductStorageCd());
                productDtlVO.setProcFg("0"); // 0: 등록
                productDtlVO.setAddQtyFg(productVO.getAddQtyFg());

                // header
                productHdVO.setSessionId(sessionInfoVO.getSessionId());
                productHdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                productHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    productHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                productHdVO.setRegId(sessionInfoVO.getUserId());
                productHdVO.setRegDt(currentDt);
                productHdVO.setModId(sessionInfoVO.getUserId());
                productHdVO.setModDt(currentDt);

                productHdVO.setProductTitle(productVO.getProductTitle());
                productHdVO.setProductDate(productVO.getProductDate());
                productHdVO.setProductFg(productVO.getProductFg());
                productHdVO.setStorageCd(productVO.getStorageCd());
                productHdVO.setProductStorageCd(productVO.getProductStorageCd());
                productHdVO.setProcFg("0"); // 0: 등록
                productHdVO.setAddQtyFg(productVO.getAddQtyFg());
            }

            productVO.setSessionId(sessionInfoVO.getSessionId());
            productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                productVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            productVO.setRegId(sessionInfoVO.getUserId());
            productVO.setRegDt(currentDt);
            productVO.setModId(sessionInfoVO.getUserId());
            productVO.setModDt(currentDt);
            productVO.setSeq(++i);

            result = productMapper.saveUploadProductTemp(productVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            returnResult += result;
        }

       if(productVOs.length > 0){

           // 임시테이블 바코드로 상품코드 맵핑
           productMapper.updateUploadProductMapping(productDtlVO);

           if(!isEmpty(seqNo)){

               productDtlVO.setSeqNo(seqNo);
               productHdVO.setSeqNo(seqNo);

               if(StringUtil.getOrBlank(productDtlVO.getAddQtyFg()).equals("apply")) {
                   // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
                    result = productMapper.deleteUploadProductData(productDtlVO);
               }

           }else{
               // seqNo 생성
               seqNo = productMapper.getProductNewSeqNo(productDtlVO);
               productDtlVO.setSeqNo(seqNo);
               productHdVO.setSeqNo(seqNo);
           }

           // 임시테이블 정보로 생산등록 상품 DTL 등록
           if(StringUtil.getOrBlank(insFg).equals("I")) {
               result = productMapper.saveUploadProductDtl(productDtlVO);
               if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
           }else{
               if(StringUtil.getOrBlank(productDtlVO.getAddQtyFg()).equals("add")) { // 수량추가
                   result = productMapper.saveUploadProductAddDtl(productDtlVO);
                   if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
               }else{ // 수량적용
                   result = productMapper.saveUploadProductDtl(productDtlVO);
                   if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
               }
           }

           // 임시테이블 정보로 생산등록 HD 등록
           if(StringUtil.getOrBlank(insFg).equals("I")) {
              result = productMapper.insertProductHd(productHdVO);
              if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
           }else{
              result = productMapper.updateProductHd(productHdVO);
              if (result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
           }

           // 정상 입력된 데이터 임시테이블에서 삭제
           result = productMapper.deleteUploadProductCompleteData(productDtlVO);
       }

       return returnResult;
    }

    /** 생산관리 - 생산등록 업로드 실패내역 조회 */
    @Override
    public List<DefaultMap<String>> getUploadErrInfoList(ProductVO productVO, SessionInfoVO sessionInfoVO) {

        productVO.setSessionId(sessionInfoVO.getSessionId());
        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return productMapper.getUploadErrInfoList(productVO);
    }

    /** 생산관리 - 생산등록 상품찾기 */
    @Override
    public DefaultMap<String> getProdInfo(ProductVO productVO, SessionInfoVO sessionInfoVO){

        productVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            productVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        DefaultMap<String> returnData = null;

        result = productMapper.getProdInfo(productVO);

       if(!result.isEmpty()) {
           returnData = result.get(0);
       }

       return returnData;
    }

}
