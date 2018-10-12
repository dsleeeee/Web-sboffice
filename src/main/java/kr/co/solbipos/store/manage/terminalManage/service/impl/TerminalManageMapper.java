package kr.co.solbipos.store.manage.terminalManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreManageVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import kr.co.solbipos.store.manage.terminalManage.service.StoreTerminalVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TerminalManageMapper.java
 * @Description : 가맹점관리 > 매장관리 > 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 10.06  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TerminalManageMapper {

    /** 매장 조회 */
    List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO);

    /** 포스/터미널 설정 환경변수 조회 */
    String getTerminalEnv(StoreEnvVO storeEnvVO);

    /** 포스 목록 조회 */
    List<DefaultMap<String>> getPosList(StorePosVO storePosVO);

    /** 코너 목록 조회 */
    List<DefaultMap<String>> getCornerList(StoreCornerVO storeCornerVO);

    /** 포스 터미널 목록 조회 */
    List<DefaultMap<String>> getPosTerminalList(StoreTerminalVO storeTerminalVO);

    /** 코너 터미널 정보 조회 */
    List<DefaultMap<String>> getCornerTerminalList(StoreTerminalVO storeTerminalVO);

    /** 터미널 환경변수 값 저장 */
    int updateTerminalEnvst(StoreEnvVO storeEnvVO);

    /** 포스 터미널 정보 등록 */
    int insertPosTerminalInfo(StoreTerminalVO storeTerminalVO);

    /** 포스 터미널 정보 수정 */
    int updatePosTerminalInfo(StoreTerminalVO storeTerminalVO);

    /** 코너 터미널 정보 등록 */
    int insertCornerTerminalInfo(StoreTerminalVO storeTerminalVO);

    /** 코너 터미널 정보 수정 */
    int updateCornerTerminalInfo(StoreTerminalVO storeTerminalVO);

}

