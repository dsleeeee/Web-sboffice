package kr.co.solbipos.membr.info.excelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberExcelUploadMapper {

  /** 회원 엑셀 조회 */
  List<DefaultMap<Object>> getMemberExcelList(MemberExcelUploadVO memberExcelUploadVO);

  /** 회원 엑셀 저장 */
  int insertMember(MemberExcelUploadVO memberExcelUploadVO);

  int insertMemberAddr(MemberExcelUploadVO memberExcelUploadVO);

  int insertMemberPoint(MemberExcelUploadVO memberExcelUploadVO);
}
