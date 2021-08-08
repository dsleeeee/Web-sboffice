package kr.co.solbipos.membr.info.dlvr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrService {
  /** 배달주소지 */
  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  /** 배달 전호번호 */
  List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  /** 배달지 삭제 */
  int deleteDlvr(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);

  /** 배달지 전화번호 삭제 */
  int deleteDlvrTel(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);

  /** 배달대분류구역 조회  */
  List getDlvrLzoneList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  /** 회원등급 리스트 조회 */
  List getMembrClassList(SessionInfoVO sessionInfoVO);

  /** 중분류구역 조회 */
  DefaultMap<Object> getDlvrMzoneList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  /** 배달지 저장 */
  int saveDlvr(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);

  /** 배달 전화번호 저장 */
  int saveDlvrTel(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);
}
