package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnDstbCloseStore.service.RtnDstbCloseStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnDstbCloseStoreMapper {
    /** 반품마감 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseStoreList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 리스트 확정 */
    int updateDstbCloseConfirm(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseStoreDtlList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 상세 리스트 수정 */
    int updateDstbCloseDtl(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 상세 리스트 확정 */
    int updateDstbCloseDtlConfirm(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 상세 리스트 삭제 */
    int deleteDstbCloseDtl(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배시 주문가능여부 조회 */
    DefaultMap<String> getOrderFg(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbAddList(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배 등록 */
    int insertDstbAdd(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배 수정 */
    int updateDstbAdd(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);

    /** 반품마감 - 추가분배 삭제 */
    int deleteDstbAdd(RtnDstbCloseStoreVO rtnDstbCloseStoreVO);
}
