package kr.co.solbipos.iostock.loan.storeLoanManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.loan.storeLoanManage.service.StoreLoanManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreLoanManageMapper {
    /** 매장여신관리 목록 조회 */
    List<DefaultMap<String>> getStoreLoanManageList(StoreLoanManageVO storeLoanManageVO);

    /** 매장여신 수정 */
    int updateStoreLoanManage(StoreLoanManageVO storeLoanManageVO);

    /** 매장여신 등록 */
    int insertStoreLoanManage(StoreLoanManageVO storeLoanManageVO);
    
    /** 매장여신 삭제 */
    int deleteStoreLoanManage(StoreLoanManageVO storeLoanManageVO);

    /** 매장여신 상세 현황 조회 */
    List<DefaultMap<String>> getStoreLoanManageDtlList(StoreLoanManageVO storeLoanManageVO);
}
