package kr.co.solbipos.dlvr.info.regist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrRegistMapper {
    List<DefaultMap<String>> getDlvrManageList(DlvrRegistVO dlvrRegistVO);

    String getNewDlvrLzoneCd(DlvrRegistVO dlvrRegistVO);

    int insertDlvrRegistInfo(DlvrRegistVO dlvrRegistVO);

    int updateDlvrRegistInfo(DlvrRegistVO dlvrRegistVO);

    int deleteDlvrRegistInfo(DlvrRegistVO dlvrRegistVO);

    int deleteDlvrDetailInfo(DlvrRegistVO dlvrRegistVO);

    String getEmpNoSelect(DlvrRegistVO dlvrRegistVO);

    List<DefaultMap<String>> dlvrDetailList(DlvrRegistVO dlvrRegistVO);

    int insertDlvrDetailRegistInfo(DlvrRegistVO dlvrRegistVO);

    int updateDlvrDetailRegistInfo(DlvrRegistVO dlvrRegistVO);

    int deleteDlvrDetailRegistInfo(DlvrRegistVO dlvrRegistVO);

    String getNewDlvrMzoneCd(DlvrRegistVO dlvrRegistVO);
}
