package kr.co.solbipos.base.store.dlvrAddr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.dlvrAddr.service.DlvrAddrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DlvrAddrMapper.java
 * @Description : 기초관리 > 매장관리 > 배달권역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface DlvrAddrMapper {

    /** 등록 배달권역 */
    List<DefaultMap<Object>> dlvrAddrList(DlvrAddrVO dlvrAddrVO);

    /** 미등록 배달권역 */
    List<DefaultMap<Object>> dlvrAddrCodeList(DlvrAddrVO dlvrAddrVO);

    /** 배달권역 등록 */
    int addDlvrAddr(DlvrAddrVO dlvrAddrVO);

    /** 배달권역 등록삭제제 */
   int delDlvrAddr(DlvrAddrVO dlvrAddrVO);
   
   /** getMyAreaCd 코드 채번 */
   String getMyAreaCd(DlvrAddrVO dlvrAddrVO);
}