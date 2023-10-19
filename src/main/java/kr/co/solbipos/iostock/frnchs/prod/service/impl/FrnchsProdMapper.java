package kr.co.solbipos.iostock.frnchs.prod.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.frnchs.prod.service.FrnchsProdVO;

@Mapper
@Repository
public interface FrnchsProdMapper {
    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getProdFrnchsList(FrnchsProdVO frnchsProdVO);
    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 팝업 리스트 조회 */
    List<DefaultMap<String>> getProdInOutstockInfoList(FrnchsProdVO frnchsProdVO);
    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 엑셀리스트 조회 */
    List<DefaultMap<String>> getProdFrnchsExcelList(FrnchsProdVO frnchsProdVO);

}
