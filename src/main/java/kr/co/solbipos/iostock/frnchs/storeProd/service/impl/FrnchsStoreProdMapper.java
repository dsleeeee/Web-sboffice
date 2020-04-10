package kr.co.solbipos.iostock.frnchs.storeProd.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.frnchs.storeProd.service.FrnchsStoreProdVO;

@Mapper
@Repository
public interface FrnchsStoreProdMapper {
	/** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreProdList(FrnchsStoreProdVO frnchsStoreProdVO);
    /** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreProdDtlList(FrnchsStoreProdVO frnchsStoreProdVO);

}
