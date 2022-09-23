package kr.co.solbipos.store.manage.status.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.store.manage.status.service.StoreStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreStatusMapper {

    /** 매장탭 - 매장정보조회*/
    List<DefaultMap<Object>> getStatusStoreList(StoreStatusVO storeStatusVO);
    List<DefaultMap<Object>> getStatusStoreExcelList(StoreStatusVO storeStatusVO);

    /** 매장탭 - 코너 상세조회*/
    List<DefaultMap<Object>> getStatusStoreCornerList(StoreStatusVO storeStatusVO);

    /** 관리업체탭 - 관리업체 조회*/
    List<DefaultMap<Object>> getStatusAgencyList(StoreStatusVO storeStatusVO);

    /** 관리업체탭 - 관리업체 상세조회*/
    List<DefaultMap<Object>> getStatusAgencyDetailList(StoreStatusVO storeStatusVO);

    /** VAN사탭 - VAN사 조회*/
    List<DefaultMap<Object>> getStatusVanList(StoreStatusVO storeStatusVO);

    /** VAN사탭 - VAN사 상세조회*/
    List<DefaultMap<Object>> getStatusVanDetailList(StoreStatusVO storeStatusVO);

    /** POS설치현황탭 - POS설치현황 조회*/
    List<DefaultMap<Object>> getStatusPosInstallList(StoreStatusVO storeStatusVO);
    List<DefaultMap<Object>> getStatusPosInstallExcelList(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 승인내역 리스트 조회 */
    List<DefaultMap<String>> getStatusApprList(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 카드승인현황 조회 */
    List<DefaultMap<String>> getCardAppr(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 현금승인현황 조회 */
    List<DefaultMap<String>> getCashAppr(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 매출상세내역 */
    DefaultMap<String> getSaleDtlInfo(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 매출정보_신용카드결제내역 */
    List<DefaultMap<String>> getCardPayInfo(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 매출정보_현금결제내역 */
    List<DefaultMap<String>> getCashPayInfo(StoreStatusVO storeStatusVO);

    /** 매장현황 탭 - 관리매장 매출정보_상품내역 */
    List<DefaultMap<String>> getSaleProductInfo(StoreStatusVO storeStatusVO);
}
