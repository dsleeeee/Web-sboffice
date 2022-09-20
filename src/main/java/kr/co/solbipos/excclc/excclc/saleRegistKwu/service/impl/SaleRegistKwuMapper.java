package kr.co.solbipos.excclc.excclc.saleRegistKwu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.saleRegistKwu.service.SaleRegistKwuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleRegistKwuMapper.java
 * @Description : 광운대 > 후방매출수기등록 > 후방매출수기등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.31  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.08.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface SaleRegistKwuMapper {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistKwuList(SaleRegistKwuVO saleRegistKwuVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistKwuVO saleRegistKwuVO);

    /** 매출처 팝업 조회 */
    List<DefaultMap<String>> getMembrKwList(SaleRegistKwuVO saleRegistKwuVO);

    /** 영수번호 채번 */
    String getBillNo(SaleRegistKwuVO saleRegistKwuVO);

    /** DTL 저장 */
    int getSaleDtl(SaleRegistKwuVO saleRegistKwuVO);
    int getSaleDtlInfo(SaleRegistKwuVO saleRegistKwuVO);
    int getSaleDtlPay(SaleRegistKwuVO saleRegistKwuVO);
    int getSaleDtlDc(SaleRegistKwuVO saleRegistKwuVO);

    /** HDR 저장 */
    int getSaleHdr(SaleRegistKwuVO saleRegistKwuVO);
    int getSaleHdrInfo(SaleRegistKwuVO saleRegistKwuVO);
    int getSaleHdrPay(SaleRegistKwuVO saleRegistKwuVO);
    int getSaleHdrDc(SaleRegistKwuVO saleRegistKwuVO);

    /** PAY 저장 */
    int getSalePayCash(SaleRegistKwuVO saleRegistKwuVO);
    int getSalePayCard(SaleRegistKwuVO saleRegistKwuVO);


    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistKwuVO saleRegistKwuVO);
    List<DefaultMap<String>> getCashAmt(SaleRegistKwuVO saleRegistKwuVO);
    List<DefaultMap<String>> getHdrInfo(SaleRegistKwuVO saleRegistKwuVO);
    String getSaleFg(SaleRegistKwuVO saleRegistKwuVO);

    /** HDR 삭제 */
    int delSaleHdr(SaleRegistKwuVO saleRegistKwuVO);
}
