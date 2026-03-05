package kr.co.solbipos.sys.admin.posLogCollectMgmt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.admin.posLogCollectMgmt.service.PosLogCollectMgmtVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : PosLogCollectMgmtMapper.java
 * @Description : 시스템관리 > 관리자기능 > POS로그수집관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface PosLogCollectMgmtMapper {

    /** POS로그수집관리 - 조회 */
    List<DefaultMap<Object>> getSearchPosLogList(PosLogCollectMgmtVO posLogCollectMgmtVO);

    /** POS로그수집등록 팝업 - 조회 */
    List<DefaultMap<Object>> getSearchStoreList(PosLogCollectMgmtVO posLogCollectMgmtVO);

    /** POS로그수집등록 팝업 - POS로그 SEQ값 조회 */
    String getMaxSeqNo(PosLogCollectMgmtVO posLogCollectMgmtVO);
    
    /** POS로그수집등록 팝업 - POS로그 저장 */
    int savePosLog(PosLogCollectMgmtVO posLogCollectMgmtVO);

}
