package kr.co.solbipos.stock.setProdAdj.setProdAdj.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.setProdAdj.setProdAdj.service.SetProdAdjVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SetProdAdjMapper {
    /** 세트재고조정 - 세트재고조정 리스트 조회(본사) */
    List<DefaultMap<String>> getHqSetProdAdjList(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 리스트 조회(매장) */
    List<DefaultMap<String>> getStSetProdAdjList(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고 DTL 전부 삭제(본사) */
    int deleteHqAllSetProdAdjDtl(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고 DTL 전부 삭제(매장) */
    int deleteStAllSetProdAdjDtl(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고 HD 삭제(본사) */
    int deleteHqSetProdAdjHd(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고 HD 삭제(매장) */
    int deleteStSetProdAdjHd(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqSetProdAdjRegistList(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStSetProdAdjRegistList(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회(본사) */
    List<DefaultMap<String>> getHqSetProdAdjRegistCompstList(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 세트구성상품 리스트 조회(매장) */
    List<DefaultMap<String>> getStSetProdAdjRegistCompstList(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 등록시 신규 SEQ 조회(본사) */
    String getHqNewSeqNo(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 등록시 신규 SEQ 조회(매장) */
    String getStNewSeqNo(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 DTL 등록(본사) */
    int insertHqSetProdAdjDtl(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 DTL 등록(매장) */
    int insertStSetProdAdjDtl(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 HD 등록(본사) */
    int insertHqSetProdAdjHd(SetProdAdjVO setProdAdjVO);

    /** 세트재고조정 - 세트재고조정 HD 등록(매장) */
    int insertStSetProdAdjHd(SetProdAdjVO setProdAdjVO);
}
