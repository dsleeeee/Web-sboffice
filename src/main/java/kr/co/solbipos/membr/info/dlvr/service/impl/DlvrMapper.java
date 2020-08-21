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

  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO);

  List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO);

  int deleteDlvr(DlvrVO dlvrVO);

  int deleteDlvrTel(DlvrVO dlvrVO);

    List<DefaultMap<String>> getDlvrLzoneList(DlvrVO dlvrVO);

  List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

  List getDlvrMzoneList(DlvrVO dlvrVO);

  int updateDlvrTel(DlvrVO dlvrVO);

  int updateDlvr(DlvrVO dlvrVO);
}