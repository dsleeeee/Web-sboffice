package kr.co.solbipos.base.store.tblpt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.tblpt.service.TblptVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
* @Class Name : TblptMapper.java
* @Description : 기초관리 > 매장관리 > 테이블속성
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @
*
* @author
* @since
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface TblptMapper {
    /** 임시패스워드 등록 */
    int tblptOpn(TblptVO tblptVO);
}
