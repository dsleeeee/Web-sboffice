package kr.co.solbipos.iostock.volmErr.volmErr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.volmErr.volmErr.service.VolmErrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VolmErrMapper {
    /** 물량오류관리 - 물량오류관리 리스트 조회 */
    List<DefaultMap<String>> getVolmErrList(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류관리 상세 리스트 조회 */
    List<DefaultMap<String>> getVolmErrDtlList(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류관리 DTL 수정 */
    int updateVolmErrDtl(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류관리 Hd 수정 */
    int updateVolmErrHd(VolmErrVO volmErrVO);

    /** 물량오류관리 - 신규전표번호 조회 */
    String getNewSlipNo(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류관리 신규전표번호 수정 */
    int updateVolmErrNewSlipNo(VolmErrVO volmErrVO);

    /** 물량오류관리 - 출고수량을 입고수량으로 수정 */
    int updateOutToIn(VolmErrVO volmErrVO);

    /** 물량오류관리 - 입고수량을 출고수량으로 수정 */
    int updateInToOut(VolmErrVO volmErrVO);

    /** 물량오류관리 - 출고정보 HD 집계 수정 */
    int updateVolmErrHdSum(VolmErrVO volmErrVO);

    /** 물량오류관리 - 출고정보 DTL 등록 */
    int insertVolmErrOutstockDtl(VolmErrVO volmErrVO);

    /** 물량오류관리 - 출고정보 HD 등록 */
    int insertVolmErrOutstockHd(VolmErrVO volmErrVO);

    /** 물량오류관리 - 본사 조정 DTL 등록 */
    int insertVolmErrHqAdjustDtl(VolmErrVO volmErrVO);

    /** 물량오류관리 - 본사 조정 HD 등록 */
    int insertVolmErrHqAdjustHd(VolmErrVO volmErrVO);

    /** 물량오류관리 - 본사 조정 HD 확정업데이트 */
    int updateVolmErrHqAdjustHd(VolmErrVO volmErrVO);

    /** 물량오류관리 - 매장 조정 DTL 등록 */
    int insertVolmErrStoreAdjustDtl(VolmErrVO volmErrVO);

    /** 물량오류관리 - 매장 조정 HD 등록 */
    int insertVolmErrStoreAdjustHd(VolmErrVO volmErrVO);

    /** 물량오류관리 - 매장 조정 HD 확정업데이트 */
    int updateVolmErrStoreAdjustHd(VolmErrVO volmErrVO);

    /** 조정관리 - 조정 신규 SEQ 조회(본사) */
    String getHqNewSeqNo(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류확정 프로시져 호출 */
    String saveConfirmVolmErr(VolmErrVO volmErrVO);

}
