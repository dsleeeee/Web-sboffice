package kr.co.solbipos.sys.bill.kind.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.bill.kind.service.KindVO;

/**
 * @Class Name : KindMapper.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 종류
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface KindMapper {
    
    /** 출력물종류 목록 조회 */
    List<DefaultMap<String>> getPrintList(KindVO kindVO);
    
    /** 출력물종류 목록 생성 */
    int insertPrintList(KindVO kindVO);
    
    /** 출력물종류 목록 수정 */
    int updatePrintList(KindVO kindVO);
    
    /** 출력물종류 목록 삭제 */
    int deletePrintList(KindVO kindVO);
    
    /** 출력물매핑 목록 조회 */
    List<DefaultMap<String>> getPrintMapngList(KindVO kindVO);
    
    /** 출력물매핑 목록 생성 */
    int insertPrintMapngList(KindVO kindVO);
    
    /** 출력물매핑 목록 수정 */
    int updatePrintMapngList(KindVO kindVO);
    
    /** 출력물매핑 목록 삭제 */
    int deletePrintMapngList(KindVO kindVO);
    
    
}
