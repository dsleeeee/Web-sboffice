package kr.co.solbipos.membr.info.excelUpload.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberExcelUploadService")
@Transactional
public class MemberExcelUploadServiceImpl implements MemberExcelUploadService {

  private final MemberExcelUploadMapper memberExcelUploadMapper;

  @Autowired
  public MemberExcelUploadServiceImpl(MemberExcelUploadMapper memberExcelUploadMapper) {
    this.memberExcelUploadMapper = memberExcelUploadMapper;
  }

  @Override
  public List<DefaultMap<Object>> getMemberExcelList(MemberExcelUploadVO memberExcelUploadVO, SessionInfoVO sessionInfoVO) {
    return memberExcelUploadMapper.getMemberExcelList(memberExcelUploadVO);
  }
}
