package kr.co.solbipos.iostock.order.dstmn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.dstmn.service.DstmnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface DstmnMapper {
    /** 거래명세표 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(DstmnVO dstmnVO);

    /** 거래명세표 리스트 조회 */
    List<DefaultMap<String>> getDstmnList(DstmnVO dstmnVO);

    /** 거래명세표 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(DstmnVO dstmnVO);

    /** 거래명세표 상세 리스트 조회 */
    List<DefaultMap<String>> getDstmnDtlList(DstmnVO dstmnVO);

    /** 거래명세표 - 출고확정 DTL 수정 */
    int updateOutstockDtl(DstmnVO dstmnVO);

    /** 거래명세표 - 출고확정 HD 수정 */
    int updateOutstockHd(DstmnVO dstmnVO);

    /** 거래명세표 - 출고확정시 자동입고 환경변수 조회 */
    String getEnv176(DstmnVO dstmnVO);

    /** 출고확정 - 출고확정 DTL 수정 */
    int updateOutstockDtlConfirm(DstmnVO dstmnVO);

    /** 출고확정 - 출고확정 HD 수정 */
    int updateOutstockConfirm(DstmnVO dstmnVO);

    /** 출고확정 - 출고확정 자동입고 DTL 수정*/
    int updateAutoInstockDtl(DstmnVO dstmnVO);

    /** 출고확정 - 출고확정 자동입고 HD 수정*/
    int updateAutoInstock(DstmnVO dstmnVO);

    /** 거래명세표 - 출고확정 이후 저장시 HD 수정 */
    int updateOutstockAfterHd(DstmnVO dstmnVO);

    /** 거래명세표 - 세금계산서 공급자 조회 */
    DefaultMap<String> getSupplierInfo(DstmnVO dstmnVO);

    /** 거래명세표 - 세금계산서 전표 내역 조회 */
    List<DefaultMap<String>> getTaxReportInfoList(DstmnVO dstmnVO);

    /** 거래명세표 - 거래명세표 전표 내역 조회 */
    List<DefaultMap<String>> getTransReportInfoList(DstmnVO dstmnVO);

}
