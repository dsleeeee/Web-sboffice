package kr.co.solbipos.iostock.cmm.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface IostockCmmService {

    /** 수불&재고관련 공통 - 매장선택 리스트 조회 */
    List<DefaultMap<String>> selectStoreList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> selectStoreMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> selectProdMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> selectBrandMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 거래처 선택모듈 리스트 조회 */
    List<DefaultMap<String>> getVendrList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 창고선택모듈 리스트 조회 */
    List<DefaultMap<String>> selectStorageList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 공통 명칭 콤보조회 */
    List<DefaultMap<String>> selectCmmCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 본사 명칭 콤보조회 */
    List<DefaultMap<String>> selectHqCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 매장 명칭 콤보조회 */
    List<DefaultMap<String>> selectStoreCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 본사/매장 명칭 콤보조회 (본사인 경우 본사, 매장인 경우 매장의 명칭 콤보조회) */
    List<DefaultMap<String>> selectOrgnCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 수불&재고관련 공통 - 다이나믹 콤보조회 */
    List<DefaultMap<String>> selectDynamicCodeList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<String>> selectHqNmcodeMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 지사 콤보박스 조회 */
    List<DefaultMap<String>> selectBranchMomsList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);
}
