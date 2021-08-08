package kr.co.solbipos.membr.info.dlvr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrMapper {
  /** 배달주소지 */
  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO);

  /** 배달 전호번호 */
  List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO);

  /** 배달지 삭제 */
  int deleteDlvr(DlvrVO dlvrVO);

  /** 배달지 전화번호 삭제 */
  int deleteDlvrTel(DlvrVO dlvrVO);

  /** 배달대분류구역 조회  */
  List<DefaultMap<String>> getDlvrLzoneList(DlvrVO dlvrVO);

  /** 회원등급 리스트 조회 */
  List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

  /** 중분류구역 조회 */
  List getDlvrMzoneList(DlvrVO dlvrVO);

  /** 배달지 저장 */
  int updateDlvrTel(DlvrVO dlvrVO);

  /** 배달 전화번호 저장 */
  int updateDlvr(DlvrVO dlvrVO);
}