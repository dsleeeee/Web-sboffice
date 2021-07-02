package kr.co.solbipos.base.store.tblms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.tblms.service.TblmsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : TblmsMapper.java
* @Description :
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
public interface TblmsMapper {

    /** 창고정보 리스트 조회 */
//    List<DefaultMap<String>> getTblmsList(TblmsVO tblmsVO);

    /** 본사 창고정보 리스트 조회 */
    List<DefaultMap<String>> getHqTblmsList(TblmsVO tblmsVO);

    /** 매장 창고정보 리스트 조회 */
    List<DefaultMap<String>> getStoreTblmsList(TblmsVO tblmsVO);

    /** 임시패스워드 등록 */
    int tblmsOpn(TblmsVO tblmsVO);
}
