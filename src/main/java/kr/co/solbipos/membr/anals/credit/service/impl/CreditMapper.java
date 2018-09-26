package kr.co.solbipos.membr.anals.credit.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.regist.service.CreditStoreVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CreditMapper {

    /** 후불 회원 외상, 입금 내역 */
    List<DefaultMap<Object>> getCreditMemberList(CreditStoreVO creditStoreVO);
}
