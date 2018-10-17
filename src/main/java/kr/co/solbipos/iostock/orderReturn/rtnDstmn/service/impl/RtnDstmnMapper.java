package kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.orderReturn.rtnDstmn.service.RtnDstmnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RtnDstmnMapper {
    /** 반품명세표 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 리스트 조회 */
    List<DefaultMap<String>> getRtnDstmnList(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstmnDtlList(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 출고확정 DTL 수정 */
    int updateOutstockDtl(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 출고확정 HD 수정 */
    int updateOutstockHd(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 출고확정시 자동입고 환경변수 조회 */
    String getEnv176(RtnDstmnVO rtnDstmnVO);

    /** 출고확정 - 출고확정 DTL 수정 */
    int updateOutstockDtlConfirm(RtnDstmnVO rtnDstmnVO);

    /** 출고확정 - 출고확정 HD 수정 */
    int updateOutstockConfirm(RtnDstmnVO rtnDstmnVO);

    /** 출고확정 - 출고확정 자동입고 DTL 수정*/
    int updateAutoInstockDtl(RtnDstmnVO rtnDstmnVO);

    /** 출고확정 - 출고확정 자동입고 HD 수정*/
    int updateAutoInstock(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 반품매장출고 이후 저장시 HD 수정 */
    int updateOutstockAfterHd(RtnDstmnVO rtnDstmnVO);
}
