package kr.co.solbipos.iostock.frnchs.slip.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.frnchs.slip.service.SlipVO;

@Mapper
@Repository
public interface SlipMapper {
    /** 본사 전표별 입출고내역 - 전표별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getSlipList(SlipVO slipVO);

    /** 본사 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getSlipDtlList(SlipVO slipVO);

    /** 전표별 입출고내역 - 조회조건 전표구분 콤보 리스트 조회  */
    List<DefaultMap<String>> getSrchSlipFgList(SlipVO slipVO);

    /** 전표별 입출고내역 - 조회조건 전표종류 콤보 리스트 조회  */
    List<DefaultMap<String>> getSrchSlipKindList(SlipVO slipVO);

    /** 전표별 입출고내역 - 조회조건 진행상태 콤보 리스트 조회  */
    List<DefaultMap<String>> getSrchProcFgList(SlipVO slipVO);

    /** 본사 전표별 입출고내역 - 전표별 입출고내역  엑셀리스트 조회 */
	List<DefaultMap<String>> getSlipExcelList(SlipVO slipVO);

	/** 본사 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 엑셀조회 */
	List<DefaultMap<String>> getSlipDtlExcelList(SlipVO slipVO);
}
