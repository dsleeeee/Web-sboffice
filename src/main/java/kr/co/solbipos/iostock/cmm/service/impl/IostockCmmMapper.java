package kr.co.solbipos.iostock.cmm.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IostockCmmMapper {
    /** 수불&재고관련 공통 - 매장선택 리스트 조회 */
    List<DefaultMap<String>> selectStoreList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 거래처 선택모듈 리스트 조회 */
    List<DefaultMap<String>> getVendrList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 공통 명칭 콤보조회 */
    List<DefaultMap<String>> selectCmmCodeList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 본사 명칭 콤보조회 */
    List<DefaultMap<String>> selectHqCodeList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 매장 명칭 콤보조회 */
    List<DefaultMap<String>> selectStoreCodeList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 다이나믹 콤보조회 */
    List<DefaultMap<String>> selectDynamicCodeList(IostockCmmVO iostockCmmVO);

}
