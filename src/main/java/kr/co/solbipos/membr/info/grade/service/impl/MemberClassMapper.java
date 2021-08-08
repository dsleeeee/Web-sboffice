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

    /** 회원등급 리스트 조회 */
    List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

    /** 등급포인트 적립 저장 */
    List<DefaultMap<String>> getMemberClassPoint(MembrClassVO membrClassVO);

    /** 회원등급 상세 */
   DefaultMap<String> getMemberClassDetail(MembrClassVO membrClassVO);

   /** 회원등급 등록 */
    int insertClassInfo(MembrClassVO membrClassVO);

    /** 등급체크 */
    int classInfoChk(MembrClassVO membrClassVO);

    /** 회원등급 수정 */
    int updateClassInfo(MembrClassVO membrClassVO);

    /** 회원등급 삭제 */
    int deleteClassInfo(MembrClassVO membrClassVO);

    /** 회원등급Point적립 중복여부 */
    int classPointInfoChk(MembrClassPointVO membrClassPointVO);

    /** 회원등급Point적립 수정 */
    int updateClassPointInfo(MembrClassPointVO membrClassPointVO);

    /** 회원등급Point적립 등록 */
    int insertClassPointInfo(MembrClassPointVO membrClassPointVO);

    /** 회원Point적립 삭제 */
    int deleteClassPointInfo(MembrClassPointVO membrClassPointVO);

    int defaultUpdateClassInfo(MembrClassVO membrClassVO);

    /** 회원Point적립 삭제 */
    int deleteClassInitPointInfo(MembrClassPointVO membrClassPointVO);

    /** 회원등급 기본여부 */
    String classDefltChk(MembrClassVO membrClassVO);

    /** 회원등급 기본여부(기존에 저장된 기본이 있는지) */
    int classDefltChkList(MembrClassVO membrClassVO);

    /** 회원정보 삭제 */
    int deleteClassChk(MembrClassVO membrClassVO);

    /** 회원등급 사용여부 */
    int updateClassPayRateInfo(MembrClassVO membrClassVO);

    /** 회원등급 포인트삭제 */
    int deleteClassPayRateInfo(MembrClassVO membrClassVO);
}
