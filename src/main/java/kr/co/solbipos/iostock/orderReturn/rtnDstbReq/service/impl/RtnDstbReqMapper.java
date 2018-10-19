package kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service.RtnDstbReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnDstbReqMapper {
    /** 반품등록 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbReqList(RtnDstbReqVO rtnDstbReqVO);

    /** 반품등록 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbReqDtlList(RtnDstbReqVO rtnDstbReqVO);

    /** 반품등록 상세 리스트 수정 */
    int updateRtnDstbReqDtl(RtnDstbReqVO rtnDstbReqVO);

    /** 분배자료 생성 */
    int insertRtnDstbReqRegist(RtnDstbReqVO rtnDstbReqVO);

    /** 반품등록 분배완료 */
    int updateDstbConfirm(RtnDstbReqVO rtnDstbReqVO);
}
