package kr.co.solbipos.adi.service.dclz.dclzmanage;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManageVO;

/**
 * 부가서비스 > 근태관리 > 근태관리
 *
 * @author 정용길
 *
 */
public interface DclzManageService {

    /**
     * 근태 관리 리스트 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> selectDclzManage(DclzManageVO dclzManageVO);

    /**
     * 근태 등록
     *
     * @param dclzManageVO
     * @param userId
     * @return
     */
    int insertDclzManage(DclzManageVO dclzManageVO, String userId);

    /**
     * 근태 수정
     *
     * @param dclzManageVO
     * @param userId
     * @return
     */
    int updateDclzManage(DclzManageVO dclzManageVO, String userId);

    /**
     * 근태 삭제
     *
     * @param dclzManageVO
     * @return
     */
    int deleteDclzManage(DclzManageVO dclzManageVO);

    /**
     * 임직원 조회 > 근태 등록시에 해당되는 매장의 근태 등록 가능한 임직원 목록을 조회
     *
     * @param dclzManageVO
     * @return
     */
    List<DefaultMap<String>> selectStoreEmployee(DclzManageVO dclzManageVO);

    /**
     * 해당 근무일에 근태가 있는지 확인
     *
     * @param dclzManageVO
     * @return
     */
    int selectWorkCheck(DclzManageVO dclzManageVO);
}
