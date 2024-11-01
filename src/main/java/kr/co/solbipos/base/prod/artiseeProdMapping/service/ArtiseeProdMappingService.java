package kr.co.solbipos.base.prod.artiseeProdMapping.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : ArtiseeProdMappingService.java
 * @Description : 보나비 - 상품관리 - 아티제상품코드맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.27  김유승       최초생성
 *
 * @author  솔비포스 WEB개발팀 김유승
 * @since   2024.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ArtiseeProdMappingService {


    /** 매핑정보 - 조회 */
    List<DefaultMap<String>> getMapStrList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO);

    /** 상품정보 - 조회 */
    List<DefaultMap<String>> getProdList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO);

    /** 매핑정보 - 삭제 */
    int getDeleteMappingProd(ArtiseeProdMappingVO[] artiseeProdMappingVOs, SessionInfoVO sessionInfoVO);

    /** 상품정보 - 등록 */
    int getRegProd(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO);

    /** 매핑정보 - 엑셀다운로드 */
    List<DefaultMap<String>> getMapStrExcelList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO);

    /** 상품정보 - 엑셀다운로드 */
    List<DefaultMap<String>> getProdExcelList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO);
}
