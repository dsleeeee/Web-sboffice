package kr.co.solbipos.membr.info.excelUpload.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("memberExcelUploadService")
@Transactional(rollbackFor = {Exception.class})
public class MemberExcelUploadServiceImpl implements MemberExcelUploadService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberExcelUploadMapper mapper;
    private final RegistMapper registMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public MemberExcelUploadServiceImpl(MemberExcelUploadMapper memberExcelUploadMapper, RegistMapper registMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = memberExcelUploadMapper;
        this.registMapper = registMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    @Override
    public List<DefaultMap<Object>> getMemberExcelList(MemberExcelUploadVO memberExcelUploadVO, SessionInfoVO sessionInfoVO) {

        return mapper.getMemberExcelList(memberExcelUploadVO);
    }

    @Override
    public int memberExcelSave(MemberExcelUploadVO[] memberExcelUploadVOs, RegistVO registVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String dt = currentDateTimeString();
//        String membrNo = "";

        LOGGER.debug("memberExcelUploadVOs: {}", memberExcelUploadVOs);
        for (MemberExcelUploadVO memberExcelUploadVO : memberExcelUploadVOs) {

            memberExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            memberExcelUploadVO.setMembrNo(registMapper.getNewMemberNo(registVO));

            memberExcelUploadVO.setShortNo(memberExcelUploadVO.getTelNo().substring(memberExcelUploadVO.getTelNo().length() - 4, memberExcelUploadVO.getTelNo().length()));
            memberExcelUploadVO.setRegDt(dt);
            memberExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            memberExcelUploadVO.setModDt(dt);
            memberExcelUploadVO.setModId(sessionInfoVO.getUserId());

            // 생일 특문 제거
            memberExcelUploadVO.setBirthday(memberExcelUploadVO.getBirthday().replaceAll("-",""));
            // 결혼여부 선택값이 미혼이면 결혼기념일 null
            if(memberExcelUploadVO.getWeddingYn() == WeddingYn.N) {
                memberExcelUploadVO.setWeddingday(null);
            } else {
                memberExcelUploadVO.setWeddingday(memberExcelUploadVO.getWeddingday().replaceAll("-",""));
            }

            if (memberExcelUploadVO.getStatus() == GridDataFg.INSERT) {
                result = mapper.insertMember(memberExcelUploadVO);
//                if (result == 1 ) {
//                    membrNo = registVO.getMembrNo();
//                    memberExcelUploadVO.getMembrNo();
//                }
//                result = mapper.insertMemberAddr(memberExcelUploadVO);
//                result = mapper.insertMemberPoint(memberExcelUploadVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return result;
    }
}
