package kr.co.solbipos.membr.info.point.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.point.service.MemberPointService;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;

@Service("MemberPointService")
@Transactional
public class MemberPointServiceImpl implements MemberPointService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final MemberPointMapper memberPointMapper;
    private final RegistMapper registMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    public MemberPointServiceImpl(MemberPointMapper memberPointMapper, RegistMapper registMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.memberPointMapper = memberPointMapper;
        this.registMapper = registMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

//    @Override
//    public List<DefaultMap<Object>> getMemberPointList(MemberPointVO memberPointVO, SessionInfoVO sessionInfoVO) {
////    if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
////      membrPointVO.setStoreCd(sessionInfoVO.getStoreCd());
////    }
//        return memberPointMapper.getMemberPointList(memberPointVO);
//    }

    @Override
    public int getMemberPointSave(MemberPointVO memberPointVO, SessionInfoVO sessionInfoVO, HttpServletRequest request) {

//        List<DefaultMap<Object>> resultList = memberPointMapper.getMemberPointList(memberPointVO);

        int totAdjPoint = Integer.parseInt(request.getParameter("totAjdPoint"));
        String remark = request.getParameter("remark");

        int result = 0;
        String currentDt = currentDateTimeString();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String nowDateStr = formatter.format(date);

        memberPointVO.setOrgnFg(memberPointVO.getOrgnFg());
        memberPointVO.setHqOfficeCd(memberPointVO.getHqOfficeCd());
        memberPointVO.setStoreCd(memberPointVO.getStoreCd());
        memberPointVO.setModDt(currentDt);
        memberPointVO.setModId(sessionInfoVO.getUserId());
        memberPointVO.setRegDt(currentDt);
        memberPointVO.setRegId(sessionInfoVO.getUserId());

        memberPointVO.setTotAdjPoint(totAdjPoint);
        memberPointVO.setChgDate(nowDateStr);
        memberPointVO.setRemark(remark);

        //insert
        result += memberPointMapper.adjustAll(memberPointVO);

        return result;
    }

//    @Override
//    public int adjustAll(List<DefaultMap<Object>> result, MemberPointVO[] memberPointVOs, SessionInfoVO sessionInfoVO) {
//        int res = 0;
//        for (MemberPointVO member : memberPointVOs) {
//            for (Object re : result) {
//            }
////      result += memberPointMapper.adjustAll(member);
//        }
//        return res;
//    }

    @Override
    public List<MemberPointVO> getMemberPointListChk(MemberPointVO[] memberPointVOs, RegistVO registVO, SessionInfoVO sessionInfoVO) {

        LOGGER.debug("sessionInfoVO.getOrgnFg(: {}", sessionInfoVO.getOrgnFg());
        LOGGER.debug("sessionInfoVO.getOrgnGrpCd: {}", sessionInfoVO.getOrgnGrpCd());
        LOGGER.debug("sessionInfoVO.getOrgnCd(): {}", sessionInfoVO.getOrgnCd());
        LOGGER.debug("sessionInfoVO.getHqOfficeCd: {}", sessionInfoVO.getHqOfficeCd());

        DefaultMap<Object> result = new DefaultMap<>();
        List<MemberPointVO> resultList = new ArrayList<MemberPointVO>();

        for (MemberPointVO memberPointVO : memberPointVOs) {

            // 상품코드
            if (memberPointVO.getMembrNo() != null && !"".equals(memberPointVO.getMembrNo())) {
                if(memberPointVO.getMembrNo().contains("'")) {
                    memberPointVO.setMembrNo(memberPointVO.getMembrNo().replaceAll("'",""));
                }
            }

            memberPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            String pattern = "^[0-9]*$"; //숫자만
            //String val = "123456789"; //대상문자열

            if (memberPointVO.getTmpTotAdjPoint() != null && !memberPointVO.getTmpTotAdjPoint().equals("")) {
                boolean regex = Pattern.matches(pattern, memberPointVO.getTmpTotAdjPoint());
                if (!regex) {
                    memberPointVO.setTotAdjPoint(0);
                } else {
                    memberPointVO.setTotAdjPoint(Integer.parseInt(memberPointVO.getTmpTotAdjPoint()));
                }
            } else {
                memberPointVO.setTotAdjPoint(0);
            }
            result = memberPointMapper.getMemberPointListChk(memberPointVO);

            if (result != null) {
                if(resultList.size() > 0) {
                   for (int i = 0; i < resultList.size(); i++) {
                        if (resultList.get(i).getMembrNo().equals(memberPointVO.getMembrNo())) {
                            memberPointVO.setMemberResult("회원번호중복");
                            resultList.get(i).setMemberResult("회원번호중복");
                            break;
                        } else {
                            memberPointVO.setMemberResult("검증성공");
                        }
                   }
                }else{
                    memberPointVO.setMemberResult("검증성공");
                }
                memberPointVO.setMembrClassCd(result.getStr("membrClassCd"));
                memberPointVO.setMembrCardNo(result.getStr("membrCardNo"));
                memberPointVO.setMembrNo(result.getStr("membrNo"));
                memberPointVO.setMembrNm(result.getStr("membrNm"));
                memberPointVO.setMembrOrgnCd(result.getStr("membrOrgnCd"));
                memberPointVO.setAvablPoint(result.getInt("avablPoint"));
            } else {
                memberPointVO.setMemberResult("회원번호없음");
            }

            resultList.add(memberPointVO);
        }

        LOGGER.debug("resultList {}", resultList);
        return resultList;
    }

    @Override
    public int memberPointSave(MemberPointVO[] memberPointVOs, RegistVO registVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
//        String membrNo = "";
        LOGGER.debug("memberExcelUploadVOs: {}", memberPointVOs);

        for (MemberPointVO memberPointVO : memberPointVOs) {

            memberPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            memberPointVO.setRegDt(dt);
            memberPointVO.setRegId(sessionInfoVO.getUserId());
            memberPointVO.setModDt(dt);
            memberPointVO.setModId(sessionInfoVO.getUserId());
            memberPointVO.setChgDate(memberPointVO.getRegDt().substring(0, memberPointVO.getRegDt().length() - 6));

            result = memberPointMapper.insertMemberPointHist(memberPointVO);
        }

        return result;
    }
}