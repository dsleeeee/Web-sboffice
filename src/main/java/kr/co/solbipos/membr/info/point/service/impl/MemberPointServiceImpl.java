package kr.co.solbipos.membr.info.point.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.apache.commons.lang3.time.DateUtils.isSameLocalTime;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.point.service.MemberPointService;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("MemberPointService")
@Transactional
public class MemberPointServiceImpl implements MemberPointService {
  private final MemberPointMapper memberPointMapper;

  public MemberPointServiceImpl(MemberPointMapper memberPointMapper) {
    this.memberPointMapper = memberPointMapper;
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

    String membr_orgn_cd = sessionInfoVO.getOrgnGrpCd();
    String auth_grp_cd = sessionInfoVO.getAuthGrpCd();
    String grp_ogn_nm = sessionInfoVO.getOrgnNm();
    String grp_cd = sessionInfoVO.getOrgnCd();
    System.out.println("=============================");
    System.out.println("=============================");
    System.out.println("membr_orgn_cd" + membr_orgn_cd);
    System.out.println("auth_grp+cd" + auth_grp_cd);
    System.out.println("grp_ogn_nm" + grp_ogn_nm);
    System.out.println("grp_cd" + grp_cd);
    System.out.println("=============================");
    System.out.println("=============================");

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
}