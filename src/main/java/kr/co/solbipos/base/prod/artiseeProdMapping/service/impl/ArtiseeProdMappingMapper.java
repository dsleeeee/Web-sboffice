package kr.co.solbipos.base.prod.artiseeProdMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.ArtiseeProdMappingVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : ArtiseeProdMappingMapper.java
 * @Description : 보나비 - 상품관리 - 아티제상품코드맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.27  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ArtiseeProdMappingMapper {

    /** 매핑정보 - 조회 */
    List<DefaultMap<String>> getMapStrList(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 상품정보 - 조회 */
    List<DefaultMap<String>> getProdList(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 매핑정보 - 삭제 */
    int getDeleteMappingProd(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 상품정보 - 등록 */
    int getSaveMappingProd(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 상품정보 - 엑셀다운로드 */
    List<DefaultMap<String>> getProdExcelList(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 맵핑스트링 저장 */
    String insertMappingString(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 맵핑정보 임시테이블 삭제1 */
    int deleteMappingTmp01(ArtiseeProdMappingVO artiseeProdMappingVO);

    /** 맵핑정보 임시테이블 삭제2 */
    int deleteMappingTmp02(ArtiseeProdMappingVO artiseeProdMappingVO);
}
