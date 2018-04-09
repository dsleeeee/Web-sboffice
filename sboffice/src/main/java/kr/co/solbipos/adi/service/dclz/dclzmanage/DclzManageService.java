package kr.co.solbipos.adi.service.dclz.dclzmanage;

import java.util.List;
import kr.co.solbipos.adi.domain.dclz.dclzmanage.DclzManage;
import kr.co.solbipos.structure.DefaultMap;

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
     * @param dclzManage
     * @return
     */
    List<DefaultMap<String>> selectDclzManage(DclzManage dclzManage);

    /**
     * 근태 등록
     * 
     * @param dclzManage
     * @param userId
     * @return
     */
    int insertDclzManage(DclzManage dclzManage, String userId);

    /**
     * 근태 수정
     * 
     * @param dclzManage
     * @param userId
     * @return
     */
    int updateDclzManage(DclzManage dclzManage, String userId);

    /**
     * 근태 삭제
     * 
     * @param dclzManage
     * @return
     */
    int deleteDclzManage(DclzManage dclzManage);

    /**
     * 임직원 조회 > 근태 등록시에 해당되는 매장의 근태 등록 가능한 임직원 목록을 조회
     * 
     * @param dclzManage
     * @return
     */
    List<DefaultMap<String>> selectStoreEmployee(DclzManage dclzManage);

    /**
     * 해당 근무일에 근태가 있는지 확인
     * 
     * @param dclzManage
     * @return
     */
    int selectWorkCheck(DclzManage dclzManage);
}
