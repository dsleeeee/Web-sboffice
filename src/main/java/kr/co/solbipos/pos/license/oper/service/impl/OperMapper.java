package kr.co.solbipos.pos.license.oper.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.license.oper.service.OperVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OperMapper {

    /** 매출매장현황탭 - 러닝매장현황조회 */
    List<DefaultMap<Object>> getRunSaleStoreList(OperVO operVO);

    /** 매출매장현황탭 - 매출매장현황조회 */
    List<DefaultMap<Object>> getSaleStoreList(OperVO operVO);

    /** 대리점인증현황탭 - 대리점인증현황조회*/
    List<DefaultMap<Object>> getAgencyAuthList(OperVO operVO);
}
