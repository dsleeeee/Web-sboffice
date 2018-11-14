package kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service.HqSetProdAdjVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HqSetProdAdjMapper {
    /** 세트재고조정 - 세트재고조정 리스트 조회 */
    List<DefaultMap<String>> getHqSetProdAdjList(HqSetProdAdjVO hqSetProdAdjVO);

    /** 세트재고조정 - 세트재고 DTL 전부 삭제 */
    int deleteAllHqSetProdAdjDtl(HqSetProdAdjVO hqSetProdAdjVO);

    /** 세트재고조정 - 세트재고 HD 삭제 */
    int deleteHqSetProdAdjHd(HqSetProdAdjVO hqSetProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회 */
    List<DefaultMap<String>> getHqSetProdAdjRegistList(HqSetProdAdjVO hqSetProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회 */
    List<DefaultMap<String>> getHqSetProdAdjRegistCompstList(HqSetProdAdjVO hqSetProdAdjVO);

    /** 세트재고조정 - 세트재고조정 DTL 등록 */
    int insertHqSetProdAdjDtl(HqSetProdAdjVO hqSetProdAdjVO);

    /** 세트재고조정 - 세트재고조정 HD 등록 */
    int insertHqSetProdAdjHd(HqSetProdAdjVO hqSetProdAdjVO);

}
