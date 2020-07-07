package kr.co.solbipos.membr.info.excelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberExcelUploadMapper {
  List<DefaultMap<Object>> getMemberExcelList(MemberExcelUploadVO memberExcelUploadVO);
}
