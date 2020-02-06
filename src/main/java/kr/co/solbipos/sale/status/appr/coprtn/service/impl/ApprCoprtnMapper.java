package kr.co.solbipos.sale.status.appr.coprtn.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.coprtn.service.ApprCoprtnVO;

@Mapper
@Repository
public interface ApprCoprtnMapper {
    /** 코너별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprCoprtnList(ApprCoprtnVO apprCoprtnVO);
}
