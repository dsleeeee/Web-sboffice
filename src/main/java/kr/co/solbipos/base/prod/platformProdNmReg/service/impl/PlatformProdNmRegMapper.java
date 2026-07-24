package kr.co.solbipos.base.prod.platformProdNmReg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.platformProdNmReg.service.PlatformProdNmRegVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlatformProdNmRegMapper {

    /** 플랫폼 상품명 등록 리스트 조회 */
    List<DefaultMap<String>> getPlatformProdNmRegList(PlatformProdNmRegVO platformProdNmRegVO);

    /** 플랫폼 상품명 등록 저장 */
    int savePlatformProdNm(PlatformProdNmRegVO platformProdNmRegVO);

}
