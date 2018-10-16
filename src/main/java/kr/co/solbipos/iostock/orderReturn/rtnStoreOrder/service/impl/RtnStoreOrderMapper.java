package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderDtlVO;
import kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service.RtnStoreOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnStoreOrderMapper {
    /** 반품등록 HD 리스트 조회 */
    List<DefaultMap<String>> getRtnStoreOrderList(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 DT 리스트 조회 */
    List<DefaultMap<String>> getRtnStoreOrderDtlList(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 상품추가 리스트 조회 */
    List<DefaultMap<String>> getRtnStoreOrderRegistList(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 반품상품 수정 */
    int updateRtnStoreOrderDtl(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 반품상품 등록 */
    int insertRtnStoreOrderDtl(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 반품상품 삭제 */
    int deleteRtnStoreOrderDtl(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 반품상품HD 수정 */
    int updateRtnStoreOrder(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 반품상품HD 등록 */
    int insertRtnStoreOrder(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 반품상품HD 삭제 */
    int deleteRtnStoreOrder(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품요청일의 상품건수 조회 */
    int getDtlCnt(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품요청일의 HD 내용이 존재하는지 여부 조회 */
    String getHdExist(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 반품진행구분 조회 */
    DefaultMap<String> getOrderProcFgCheck(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 출고요청가능일 조회 */
    String getReqDate(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 확정 - MD 수량을 반품수량으로 수정 */
    int updateOrderQtyMdQty(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 확정 - 분배자료 생성 */
    int insertDstbRegist(DstbReqVO dstbReqVO);
}
