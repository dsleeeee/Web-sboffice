package kr.co.solbipos.membr.info.excelUpload.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
            if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
                registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                registVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                memberExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            } else { // 본사/매장
                registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
                memberExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            }
            memberExcelUploadVO.setMembrNo(registMapper.getNewMemberNo(registVO));
            
            // NOT NULL 컬럼
            memberExcelUploadVO.setLunarYn("N");
            
            // match invalid variable
            String strTelNo = (memberExcelUploadVO.getMemberTelNo() == null || "".equals(memberExcelUploadVO.getMemberTelNo())) ? "01000000000" : memberExcelUploadVO.getMemberTelNo();
            memberExcelUploadVO.setTelNo(strTelNo);

            String strShortNo = (memberExcelUploadVO.getMemberShortNo() == null || "".equals(memberExcelUploadVO.getMemberShortNo())) ? "0000" : memberExcelUploadVO.getMemberShortNo();
            if ("0000".equals(strShortNo)) { // 단축번호
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

                    // 포인트 처리
                    registVO.setRegStoreCd(memberExcelUploadVO.getRegStoreCd());
                    registVO.setMembrClassCd(memberExcelUploadVO.getMembrClassCd());
                    registVO.setChgDate(DateUtil.currentDateString()); //DEFAULT_YMD_FORMAT = "yyyyMMdd";
                    // 1. 회원등급에 따른 신규가입포인트 처리
                    int newJoinSavePoint = registMapper.newJoinSavePointInfo(registVO);
                    if ( newJoinSavePoint > 0 ) {
                        // 신규가입 적립 Point
                        registVO.setPointChgFg("1");
                        registVO.setRemark("신규가입");
                        registVO.setChgPoint(newJoinSavePoint);
                        registVO.setRegDt(currentDateTimeString());
                        registMapper.insertMembrPointHist(registVO); // 회원포인트 등록
                    }
                    // 2. 가용포인트 = (누적포인트 - 가용포인트)를 사용 처리한 값
                    int totAdjPoint = StringUtils.isEmpty(memberExcelUploadVO.getTotAdjPoint()) ? 0 : Integer.parseInt( memberExcelUploadVO.getTotAdjPoint() );
                    int usedPoint = StringUtils.isEmpty(memberExcelUploadVO.getAvablPoint()) ? 0 : totAdjPoint - Integer.parseInt( memberExcelUploadVO.getAvablPoint() );
                    if (usedPoint != 0) {
                        registVO.setPointChgFg("6");
                        registVO.setRemark("사용포인트");
                        registVO.setChgPoint(usedPoint); // 사용포인트
                        String nowDt = DateFormatUtils.format(new Date(System.currentTimeMillis() + 1000L), "yyyyMMddHHmmss");
                        registVO.setRegDt(nowDt);
                        registVO.setModDt(nowDt);
                        registMapper.insertMembrPointHist(registVO); // 회원포인트 등록
                    }
                    // 3. 누적포인트 처리
                    if (totAdjPoint != 0) {
                        registVO.setPointChgFg("3");
                        registVO.setRemark("누적포인트");
                        registVO.setChgPoint(totAdjPoint); // 누적포인트
                        String nowDt = DateFormatUtils.format(new Date(System.currentTimeMillis() + 2000L), "yyyyMMddHHmmss");
                        registVO.setRegDt(nowDt);
                        registVO.setModDt(nowDt);
                        registMapper.insertMembrPointHist(registVO); // 회원포인트 등록
                    }
                }

                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return result;
    }
}
