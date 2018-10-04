package kr.co.solbipos.iostock.order.dstbReq.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DstbReqMapper {
    /** 분배등록 리스트 조회 */
    List<DefaultMap<String>> getDstbReqList(DstbReqVO dstbReqVO);

    /** 분배등록 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbReqDtlList(DstbReqVO dstbReqVO);

    /** 분배등록 상세 리스트 수정 */
    int updateDstbReqDtl(DstbReqVO dstbReqVO);

    /** 분배자료 생성 */
    int insertDstbReqRegist(DstbReqVO dstbReqVO);

    /** 분배등록 분배완료 */
    int updateDstbConfirm(DstbReqVO dstbReqVO);

}
