package kr.co.solbipos.excclc.excclc.saleRegistKw.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.saleRegistKw.service.SaleRegistKwVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleRegistKwMapper.java
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
public interface SaleRegistKwMapper {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistKwList(SaleRegistKwVO saleRegistKwVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistKwVO saleRegistKwVO);

    /** 매출처 팝업 조회 */
    List<DefaultMap<String>> getMembrKwList(SaleRegistKwVO saleRegistKwVO);

    /** 영수번호 채번 */
    String getBillNo(SaleRegistKwVO saleRegistKwVO);

    /** DTL 저장 */
    int getSaleDtl(SaleRegistKwVO saleRegistKwVO);
    int getSaleDtlInfo(SaleRegistKwVO saleRegistKwVO);
    int getSaleDtlPay(SaleRegistKwVO saleRegistKwVO);
    int getSaleDtlDc(SaleRegistKwVO saleRegistKwVO);

    /** HDR 저장 */
    int getSaleHdr(SaleRegistKwVO saleRegistKwVO);
    int getSaleHdrInfo(SaleRegistKwVO saleRegistKwVO);
    int getSaleHdrPay(SaleRegistKwVO saleRegistKwVO);
    int getSaleHdrDc(SaleRegistKwVO saleRegistKwVO);

    /** PAY 저장 */
    int getSalePayCash(SaleRegistKwVO saleRegistKwVO);
    int getSalePayCard(SaleRegistKwVO saleRegistKwVO);


    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistKwVO saleRegistKwVO);
    List<DefaultMap<String>> getCashAmt(SaleRegistKwVO saleRegistKwVO);
    List<DefaultMap<String>> getHdrInfo(SaleRegistKwVO saleRegistKwVO);
    String getSaleFg(SaleRegistKwVO saleRegistKwVO);

    /** HDR 삭제 */
    int delSaleHdr(SaleRegistKwVO saleRegistKwVO);
}
