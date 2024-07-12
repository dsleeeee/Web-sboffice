package kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnInstockConfmStore.service.RtnInstockConfmStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnInstockConfmStoreMapper {

    /**  반품본사입고현황 - 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmStoreList(RtnInstockConfmStoreVO rtnInstockConfmStoreVO);

    /**  반품본사입고현황 - 전표 상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnInstockConfmStoreVO rtnInstockConfmStoreVO);

    /** 반품본사입고현황 - 전표 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmStoreDtlList(RtnInstockConfmStoreVO rtnInstockConfmStoreVO);
}
