package kr.co.solbipos.store.manage.envConfgBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.envConfgBatchChange.service.EnvConfgBatchChangeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : EnvConfgBatchChangeMapper.java
 * @Description : 기초관리 > 매장정보관리 > 환경변수일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.02.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface EnvConfgBatchChangeMapper {

    /** 본사환경탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeHqList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /**  환경설정 조회 팝업 - 조회(본사환경) */
    List<DefaultMap<Object>> getSearchEnvConfgList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 환경설정 조회 팝업 - 조회(매장환경) */
    List<DefaultMap<Object>> getSearchEnvConfgStoreList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 환경설정 조회 팝업 - 조회(매장포스환경) */
    List<DefaultMap<Object>> getSearchEnvConfgStorePosList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 본사환경탭 - 환경변수값 콤보박스 조회 */
    List<DefaultMap<Object>> getEnvstValComboList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 본사환경탭 - 저장 insert, update */
    int getEnvConfgBatchChangeHqSave(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 매장환경탭 - 저장 insert, update */
    int getEnvConfgBatchChangeHqStoreSave(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 본사환경탭 - 매장코드 조회 */
    List<DefaultMap<String>> getStoreCdList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 매장환경탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeStoreList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 매장포스환경탭 - 조회 */
    List<DefaultMap<Object>> getEnvConfgBatchChangeStorePosList(EnvConfgBatchChangeVO envConfgBatchChangeVO);

    /** 매장포스환경탭 - 저장 insert, update */
    int getEnvConfgBatchChangeStorePosSave(EnvConfgBatchChangeVO envConfgBatchChangeVO);
}