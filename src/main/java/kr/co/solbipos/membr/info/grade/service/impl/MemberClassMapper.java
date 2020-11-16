package kr.co.solbipos.membr.info.grade.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberClassMapper {
    List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

    List<DefaultMap<String>> getMemberClassPoint(MembrClassVO membrClassVO);

    DefaultMap<String> getMemberClassDetail(MembrClassVO membrClassVO);

    int insertClassInfo(MembrClassVO membrClassVO);

    int classInfoChk(MembrClassVO membrClassVO);

    int updateClassInfo(MembrClassVO membrClassVO);

    int deleteClassInfo(MembrClassVO membrClassVO);

    int classPointInfoChk(MembrClassPointVO membrClassPointVO);

    int updateClassPointInfo(MembrClassPointVO membrClassPointVO);

    int insertClassPointInfo(MembrClassPointVO membrClassPointVO);

    int deleteClassPointInfo(MembrClassPointVO membrClassPointVO);

    int defaultUpdateClassInfo(MembrClassVO membrClassVO);

    int deleteClassInitPointInfo(MembrClassPointVO membrClassPointVO);

    String classDefltChk(MembrClassVO membrClassVO);

    int classDefltChkList(MembrClassVO membrClassVO);

    int deleteClassChk(MembrClassVO membrClassVO);

    int updateClassPayRateInfo(MembrClassVO membrClassVO);

    int deleteClassPayRateInfo(MembrClassVO membrClassVO);
}
