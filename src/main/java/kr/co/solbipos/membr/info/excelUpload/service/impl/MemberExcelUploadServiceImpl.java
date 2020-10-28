package kr.co.solbipos.membr.info.excelUpload.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;

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

        for (MemberExcelUploadVO memberExcelUploadVO : memberExcelUploadVOs) {

            memberExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            memberExcelUploadVO.setMembrNo(registMapper.getNewMemberNo(registVO));

            // NOT NULL 컬럼
            memberExcelUploadVO.setLunarYn("N");
            
            // match invalid variable
            String strTelNo = memberExcelUploadVO.getMemberTelNo();
            memberExcelUploadVO.setTelNo(StringUtils.isEmpty(strTelNo) ? "01000000000" : memberExcelUploadVO.getMemberTelNo());

            String strShortNo = memberExcelUploadVO.getMemberShortNo();
            if (StringUtils.isEmpty(strShortNo)) { // 단축번호
                memberExcelUploadVO.setShortNo( (strTelNo.length() > 4) ? StringUtils.right(strTelNo, 4) : "0000");
            } else {
                memberExcelUploadVO.setShortNo(strShortNo);
            }
            memberExcelUploadVO.setEmailAddr(memberExcelUploadVO.getMemberEmail());
            memberExcelUploadVO.setPostNo(memberExcelUploadVO.getMemberPostNo());
            memberExcelUploadVO.setAddr(memberExcelUploadVO.getMemberAddr());
            memberExcelUploadVO.setAddrDtl(memberExcelUploadVO.getMemberAddrDtl());
            // 결혼여부 선택값이 미혼이면 결혼기념일 null
            if(memberExcelUploadVO.getWeddingYn() == WeddingYn.N) {
                memberExcelUploadVO.setWeddingday(null);
            } else {
                memberExcelUploadVO.setWeddingday(memberExcelUploadVO.getWeddingday().replaceAll("-",""));
            }
            memberExcelUploadVO.setRegDt(dt);
            memberExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            memberExcelUploadVO.setModDt(dt);
            memberExcelUploadVO.setModId(sessionInfoVO.getUserId());

            if (memberExcelUploadVO.getStatus() == GridDataFg.INSERT) {
                result = mapper.insertMember(memberExcelUploadVO);

                if (result == 1) {

                    registVO.setMembrCardNo(memberExcelUploadVO.getMembrCardNo());
                    registVO.setMembrNo(memberExcelUploadVO.getMembrNo());
                    registVO.setIssOrgnCd(memberExcelUploadVO.getRegStoreCd());
                    registVO.setIssDate(DateUtil.currentDateString());	//DEFAULT_YMD_FORMAT = "yyyyMMdd";
                    registVO.setRegDt(dt);
                    registVO.setRegId(memberExcelUploadVO.getRegId());
                    registVO.setModDt(dt);
                    registVO.setModId(memberExcelUploadVO.getModId());
                    registVO.setCstCardIssFg("0");

                    // 회원카드번호에 값이 있을때 회원카드 등록
                    if (!StringUtils.isEmpty(memberExcelUploadVO.getMembrCardNo())) {
                        result = registMapper.insertMembrCard(registVO); // 회원카드등록
                    }

                    // 가용포인트에 값이 있을때 포인트히스토리에 등록
                    if (!StringUtils.isEmpty(memberExcelUploadVO.getAvablPoint())) {
                        registVO.setChgDate(DateUtil.currentDateString()); //DEFAULT_YMD_FORMAT = "yyyyMMdd";
                        registVO.setRegStoreCd(memberExcelUploadVO.getRegStoreCd());
                        registVO.setPointChgFg("3");
                        registVO.setChgPoint(Integer.parseInt(memberExcelUploadVO.getAvablPoint())); // 가용포인트
                        registVO.setTotAdjPoint(memberExcelUploadVO.getTotAdjPoint()); // 누적포인트
                        registMapper.insertMembrPointHist(registVO); // 회원포인트 등록
                    }
                }

                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return result;
    }
}
