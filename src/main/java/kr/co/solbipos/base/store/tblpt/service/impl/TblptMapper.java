package kr.co.solbipos.base.store.tblpt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.tblpt.service.TblptVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
* @Class Name : TblptMapper.java
* @Description : 기초관리 > 매장관리 > 창고관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2020.03.24  조동훤      최초생성
*
* @author 조동훤
* @since 2020.03.24
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface TblptMapper {

    /** 창고정보 리스트 조회 */
//    List<DefaultMap<String>> getTblptList(TblptVO tblptVO);

    /** 본사 창고정보 리스트 조회 */
    List<DefaultMap<String>> getHqTblptList(TblptVO tblptVO);

    /** 매장 창고정보 리스트 조회 */
    List<DefaultMap<String>> getStoreTblptList(TblptVO tblptVO);

    /** 임시패스워드 등록 */
    int tblptOpn(TblptVO tblptVO);
}
