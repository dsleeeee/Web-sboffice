package kr.co.solbipos.store.manage.migDataMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.migDataMapping.service.MigDataMappingVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MigDataMappingMapper.java
 * @Description : 기초관리 > 매장정보관리 > OKPOS-KCP 데이터 이관
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface MigDataMappingMapper {

    /** OKPOS-KCP 데이터 이관 조회 */
    List<DefaultMap<Object>> getMigDataMappingList(MigDataMappingVO migDataMappingVO);

    /** OKPOS-KCP 사용자정보 조회 */
    DefaultMap<String> getOkposUserInfoList(MigDataMappingVO migDataMappingVO);

    /** OKPOS-KCP 매장 조회 */
    List<DefaultMap<Object>> getMigDataMappingInfoList(MigDataMappingVO migDataMappingVO);

    /** OKPOS-KCP 데이터 이관 저장 */
    int getMigDataMappingInfoSaveInsert(MigDataMappingVO migDataMappingVO);

    /** SOLBI 매장코드 조회 */
    DefaultMap<String> getMigDataMappingSolbiStoreCdList(MigDataMappingVO migDataMappingVO);

    /** 매출재이관 */
    int getMigDataMappingSaleAgainSave(MigDataMappingVO migDataMappingVO);
}