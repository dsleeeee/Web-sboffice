package kr.co.solbipos.iostock.order.dstmn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DstmnService {
    /** 거래명세표 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(DstmnVO dstmnVO);

    /** 거래명세표 리스트 조회 */
    List<DefaultMap<String>> getDstmnList(DstmnVO dstmnVO);

    /** 거래명세표 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(DstmnVO dstmnVO);

    /** 거래명세표 - 거래명세표 상세 리스트 조회 */
    List<DefaultMap<String>> getDstmnDtlList(DstmnVO dstmnVO);

    /** 거래명세표 - 거래명세표 상세 리스트 저장 */
    int saveDstmnDtl(DstmnVO[] dstmnVOs, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 출고확정 이후 저장 */
    int saveOutstockAfter(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 세금계산서 공급자 조회 */
    DefaultMap<String> getSupplierInfo(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 세금계산서 전표 내역 조회 */
    List<DefaultMap<String>> getTaxReportInfoList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 거래명세표 전표 내역 조회 */
    List<DefaultMap<String>> getTransReportInfoList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 분배지시서(상품) 상품 리스트 조회 */
    List<DefaultMap<String>> getDstbProdReportList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 분배지시서(상품-매장) 상품 리스트 조회 */
    List<DefaultMap<String>> getDstbProdStoreReportList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 분배지시서(매장-상품) 상품 리스트 조회 */
    List<DefaultMap<String>> getDstbStoreProdReportList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 분배지시서(기사) 배송기사 리스트 조회 */
    List<DefaultMap<String>> getDstbDlvrList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

    /** 거래명세표 - 분배지시서(기사) 배송기사별 상품 리스트 조회 */
    List<DefaultMap<String>> getDstbDlvrReportList(DstmnVO dstmnVO, SessionInfoVO sessionInfoVO);

}
