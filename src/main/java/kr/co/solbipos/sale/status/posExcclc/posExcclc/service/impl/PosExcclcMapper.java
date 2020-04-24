package kr.co.solbipos.sale.status.posExcclc.posExcclc.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.posExcclc.posExcclc.service.PosExcclcVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosExcclcMapper {
	/** 포스별매출 일자별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getPosExcclcList(PosExcclcVO posExcclcVO);

    /** 포스별매출 일자별 탭 - 리스트 조회 */
    DefaultMap<String> getPosExcclcDetailInfo(PosExcclcVO posExcclcVO);
    
    /** 포스별매출 일자별 탭 - 매장 및 포스 전체 엑셀 리스트 조회 */
    List<DefaultMap<String>> getPosExcclcExcelList(PosExcclcVO posExcclcVO);
}