package kr.co.solbipos.sale.status.appr.payMethod.payco.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.payco.service.ApprPaycoVO;

@Mapper
@Repository
public interface ApprPaycoMapper {
    /** 승인현황 승인현황 페이코 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprPaycoList(ApprPaycoVO apprPaycoVO);
    /** 승인현황 승인현황 페이코 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprPaycoExcelList(ApprPaycoVO apprPaycoVO);

	List<DefaultMap<String>> getCornerNmList(ApprPaycoVO apprPaycoVO);
}
