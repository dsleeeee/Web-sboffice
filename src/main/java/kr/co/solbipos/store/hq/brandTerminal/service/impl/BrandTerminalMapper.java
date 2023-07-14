package kr.co.solbipos.store.hq.brandTerminal.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.hq.brandTerminal.service.BrandTerminalVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BrandTerminalMapper.java
 * @Description : 기초관리 > 본사정보관리 > 브랜드별 매장터미널관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.06
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface BrandTerminalMapper {

    /** 벤더 목록 조회 */
    List<DefaultMap<String>> getVendorList();

    /** 브랜드 조회 */
    List<DefaultMap<String>> getBrandList(BrandTerminalVO brandTerminalVO);

    /** 포스 터미널 목록 조회 */
    List<DefaultMap<String>> getTerminalList(BrandTerminalVO brandTerminalVO);

    /** 터미널 환경변수 값 저장 */
    int updateTerminalEnvst(StoreEnvVO storeEnvVO);

    /** 터미널 정보 등록 */
    int insertTerminalInfo(BrandTerminalVO brandTerminalVO);

    /** 터미널 정보 수정 */
    int updateTerminalInfo(BrandTerminalVO brandTerminalVO);

    /** 터미널 정보 삭제 */
    int deleteTerminalInfo(BrandTerminalVO brandTerminalVO);

    /** 브랜드 터미널 정보 매장에 저장 */
    int insertStoreTerminalInfo(BrandTerminalVO brandTerminalVO);
    /** 브랜드 터미널 정보 매장에 삭제 */
    int deleteStoreTerminalInfo(BrandTerminalVO brandTerminalVO);
}

