package kr.co.solbipos.membr.info.point.service.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

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


    @Override
    public List<DefaultMap<Object>> getMemberPointList(MemberPointVO memberPointVO, SessionInfoVO sessionInfoVO) {
//    if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
//      membrPointVO.setStoreCd(sessionInfoVO.getStoreCd());
//    }
        return memberPointMapper.getMemberPointList(memberPointVO);
    }

    @Override
    public int getMemberPointSave(MemberPointVO memberPointVO, SessionInfoVO sessionInfoVO, HttpServletRequest request) {

        List<DefaultMap<Object>> resultList = memberPointMapper.getMemberPointList(memberPointVO);

        int totAjdPoint = Integer.parseInt(request.getParameter("totAjdPoint"));

        int result = 0;

        String dt = currentDateTimeString();
        for (DefaultMap<Object> re : resultList) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String nowDateStr = formatter.format(date);

            re.put("chgSeq", 0);
            re.put("chgDate", nowDateStr);
            re.put("regDt", dt);
            re.put("regId", sessionInfoVO.getUserId());
            re.put("modDt", dt);
            re.put("modId", sessionInfoVO.getUserId());
            re.put("totAjdPoint", totAjdPoint);

            //insert
            result += memberPointMapper.adjustAll(re);
        }
        return result;
    }

    @Override
    public int adjustAll(List<DefaultMap<Object>> result, MemberPointVO[] memberPointVOs, SessionInfoVO sessionInfoVO) {
        int res = 0;
        for (MemberPointVO member : memberPointVOs) {
            for (Object re : result) {
            }
//      result += memberPointMapper.adjustAll(member);
        }
        return res;
    }

    @Override
    public List<MemberPointVO> getMemberPointListChk(MemberPointVO[] memberPointVOs, RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();
        DefaultMap<Object> result = new DefaultMap<>();
        List<MemberPointVO> resultList = new ArrayList<MemberPointVO>();
        for (MemberPointVO memberPointVO : memberPointVOs) {
            memberPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
//            memberPointVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            result = memberPointMapper.getMemberPointListChk(memberPointVO);
            if (result != null) {
                memberPointVO.setMemberResult("검증성공");
                memberPointVO.setMembrClassCd(result.getStr("membrClassCd"));
                memberPointVO.setMembrCardNo(result.getStr("membrCardNo"));
                memberPointVO.setMembrNo(result.getStr("membrNo"));
                memberPointVO.setMembrNm(result.getStr("membrNm"));
                memberPointVO.setMembrOrgnCd(result.getStr("membrOrgnCd"));
                memberPointVO.setAvablPoint(result.getInt("avablPoint"));
            } else {
                memberPointVO.setMemberResult("검증실패");
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
            memberPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            memberPointVO.setModDt(dt);
            memberPointVO.setModId(sessionInfoVO.getUserId());

            if (memberPointVO.getStatus() == GridDataFg.UPDATE) {
                result = memberPointMapper.updateMemberPoint(memberPointVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return result;
    }
}
