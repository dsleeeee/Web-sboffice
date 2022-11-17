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
    List<DefaultMap<String>> selectStoreMomsList(IostockCmmVO iostockCmmVO);
    List<DefaultMap<String>> selectProdMomsList(IostockCmmVO iostockCmmVO);
    List<DefaultMap<String>> selectBrandMomsList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 거래처 선택모듈 리스트 조회 */
    List<DefaultMap<String>> getVendrList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 창고선택모듈 리스트 조회 */
    List<DefaultMap<String>> selectStorageList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 공통 명칭 콤보조회 */
    List<DefaultMap<String>> selectCmmCodeList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 본사 명칭 콤보조회 */
    List<DefaultMap<String>> selectHqCodeList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 매장 명칭 콤보조회 */
    List<DefaultMap<String>> selectStoreCodeList(IostockCmmVO iostockCmmVO);

    /** 수불&재고관련 공통 - 다이나믹 콤보조회 */
    List<DefaultMap<String>> selectDynamicCodeList(IostockCmmVO iostockCmmVO);

    /** 사용자별 브랜드 사용 조회 */
    String getUserBrandCdList(IostockCmmVO iostockCmmVO);

    /** 사용자별 코드별 공통코드 조회 */
    String getUserHqNmcodeCdList(IostockCmmVO iostockCmmVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<String>> selectHqNmcodeMomsList(IostockCmmVO iostockCmmVO);

    /** 사용자별 지사 조회 */
    String getUserBranchCdList(IostockCmmVO iostockCmmVO);

    /** 사용자별 지사 콤보박스 조회 */
    List<DefaultMap<String>> selectBranchMomsList(IostockCmmVO iostockCmmVO);
}
