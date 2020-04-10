package kr.co.solbipos.iostock.frnchs.storeProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface FrnchsStoreProdService {
	/** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreProdList(FrnchsStoreProdVO FrnchsStoreProdVO, SessionInfoVO sessionInfoVO);
    /** 매장-상품별 입출고내역 - 매장-상품별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreProdDtlList(FrnchsStoreProdVO FrnchsStoreProdVO, SessionInfoVO sessionInfoVO);

}
