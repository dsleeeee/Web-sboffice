package kr.co.solbipos.membr.info.memberFg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.memberFg.service.MemberFgService;
import kr.co.solbipos.membr.info.memberFg.service.MemberFgVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MemberFgServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 선불/후불회원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("memberFgService")
@Transactional
public class MemberFgServiceImpl implements MemberFgService {


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberFgMapper memberFgMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MemberFgServiceImpl(MemberFgMapper memberFgMappe, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.memberFgMapper = memberFgMappe;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 회원등급 리스트 조회
     */
    @Override
    public List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO) {

        MembrClassVO membrClassVO = new MembrClassVO();

        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
        membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        List<DefaultMap<String>> resultList = memberFgMapper.getMemberClassList(membrClassVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "기본등급");
            resultList.add(tmpList);
        }
        return resultList;
    }

    /** 선불 회원 조회 */
    @Override
    public List<DefaultMap<String>> getMemberPrepaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO) {
        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }
        memberFgVO.setDefaultStoreCd(defaultStoreCd);

        memberFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        memberFgVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        memberFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(memberFgVO.getStoreCd()).equals("")) {
                memberFgVO.setArrStoreCd(memberFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            memberFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> returnList = new ArrayList<DefaultMap<String>>();

        returnList = memberFgMapper.getMemberPrepaid(memberFgVO);

        return returnList;
    }

    /** 미선불 회원 조회 */
    @Override
    public List<DefaultMap<String>> getMemberNoPrepaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO) {
        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }
        memberFgVO.setDefaultStoreCd(defaultStoreCd);

        memberFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        memberFgVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        memberFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(memberFgVO.getStoreCd()).equals("")) {
                memberFgVO.setArrStoreCd(memberFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            memberFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> returnList = new ArrayList<DefaultMap<String>>();

        returnList = memberFgMapper.getMemberNoPrepaid(memberFgVO);

        return returnList;
    }

    /** 후불 회원 조회 */
    @Override
    public List<DefaultMap<String>> getMemberPostpaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO) {
        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }
        memberFgVO.setDefaultStoreCd(defaultStoreCd);

        memberFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        memberFgVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        memberFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(memberFgVO.getStoreCd()).equals("")) {
                memberFgVO.setArrStoreCd(memberFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            memberFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> returnList = new ArrayList<DefaultMap<String>>();

        returnList = memberFgMapper.getMemberPostpaid(memberFgVO);

        return returnList;
    }

    /** 미후불 회원 조회 */
    @Override
    public List<DefaultMap<String>> getMemberNoPostpaid(MemberFgVO memberFgVO, SessionInfoVO sessionInfoVO) {
        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }
        memberFgVO.setDefaultStoreCd(defaultStoreCd);

        memberFgVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        memberFgVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        memberFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(!StringUtil.getOrBlank(memberFgVO.getStoreCd()).equals("")) {
                memberFgVO.setArrStoreCd(memberFgVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            memberFgVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> returnList = new ArrayList<DefaultMap<String>>();

        returnList = memberFgMapper.getMemberNoPostpaid(memberFgVO);

        return returnList;
    }

    /** 선불 회원 등록 */
    @Override
    public int regPrepaid(MemberFgVO[] memberFgVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for ( MemberFgVO memberFgVO : memberFgVOs) {

            memberFgVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            memberFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd()); //프차:본사코드 단독매장:매장코드
            memberFgVO.setRegDt(dt);                                  //등록일시
            memberFgVO.setRegId(sessionInfoVO.getUserId());           //등록아이디
            memberFgVO.setModDt(dt);                                  //수정일시
            memberFgVO.setModId(sessionInfoVO.getUserId());           //수정아이디

            result = memberFgMapper.regPrepaid(memberFgVO);
        }

        return result;
    }

    /** 후불 회원 등록 */
    @Override
    public int regPostpaid(MemberFgVO[] memberFgVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for ( MemberFgVO memberFgVO : memberFgVOs) {

            memberFgVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            memberFgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            memberFgVO.setRegDt(dt);                                  //등록일시
            memberFgVO.setRegId(sessionInfoVO.getUserId());           //등록아이디
            memberFgVO.setModDt(dt);                                  //수정일시
            memberFgVO.setModId(sessionInfoVO.getUserId());           //수정아이디

            result = memberFgMapper.regPostpaid(memberFgVO);
        }

        return result;
    }

}