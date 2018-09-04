package kr.co.solbipos.iostock.loan.storeLoanInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.loan.storeLoanInfo.service.StoreLoanInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreLoanInfoMapper {
    /** 매장별여신상세현황 목록 조회 */
    List<DefaultMap<String>> getStoreLoanInfoList(StoreLoanInfoVO storeLoanInfoVO);
}
