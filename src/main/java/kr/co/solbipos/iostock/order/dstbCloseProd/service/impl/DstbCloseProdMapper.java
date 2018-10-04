package kr.co.solbipos.iostock.order.dstbCloseProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.dstbCloseProd.service.DstbCloseProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DstbCloseProdMapper {
    /** 분배마감 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdList(DstbCloseProdVO dstbCloseStProd);

    /** 분배마감 리스트 확정 */
    int updateDstbCloseProdConfirm(DstbCloseProdVO dstbCloseStProd);

    /** 분배마감 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdDtlList(DstbCloseProdVO dstbCloseStProd);

    /** 분배마감 상세 리스트 수정 */
    int updateDstbCloseProdDtl(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 상세 리스트 확정 */
    int updateDstbCloseProdDtlConfirm(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 상세 리스트 삭제 */
    int deleteDstbCloseProdDtl(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 추가등록 상품 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdAddProdList(DstbCloseProdVO dstbCloseStProd);

    /** 분배마감 추가등록 분배등록 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdAddRegistList(DstbCloseProdVO dstbCloseStProd);

    /** 분배마감 추가등록 분배등록 리스트 등록 */
    int insertDstbCloseProdAddRegist(DstbCloseProdVO dstbCloseProdVO);

}
