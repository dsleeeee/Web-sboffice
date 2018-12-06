package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : RegistServiceImpl.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018.05.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("registService")
@Transactional
public class RegistServiceImpl implements RegistService {

    private final RegistMapper mapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public RegistServiceImpl(RegistMapper mapper, CmmEnvUtil cmmEnvUtil) {
        this.mapper = mapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 등록매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRegistStore(SessionInfoVO sessionInfoVO) {

        // 회원정보 등록시 등록매장의 콤보박스 내용 조회
        // 본사 : 해당 본사에 소속된 매장만 조회한다.
        // 매장 : 해당 매장만 표시
        HqManageVO hqVO = new HqManageVO();

        hqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getRegistStore(hqVO);
    }

    /** 회원등급 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO) {

        MembrClassVO membrClassVO = new MembrClassVO();

        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getMemberClassList(membrClassVO);
    }

    /** 선택한 회원 정보 조회 */
    @Override
    public DefaultMap<String> getMemberInfo(RegistVO registVO) {
        return mapper.getMemberInfo(registVO);
    }

    /** 회원 목록 조회 */
    @Override
    public List<DefaultMap<String>> getMemberList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if( !StringUtil.isEmpties(registVO.getRegStoreCd())) {
            registVO.setRegStoreCds(registVO.getRegStoreCd().split(","));
        }

        return mapper.getMemberList(registVO);
    }

    /** 회원 등록 */
    @Override
    public int registMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        int result = mapper.registMemberInfo(registVO);
        if(result == 1 && (!StringUtil.isEmpties(registVO.getMembrCardNo()))) {
            // 회원카드 등록
            mapper.insertMembrCard(registVO);
        }
        return result;
    }

    /** 회원 수정 */
    @Override
    public int updateMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        int result = mapper.updateMemberInfo(registVO);
        if(result == 1) {
            // 회원카드 수정
            mapper.updateMembrCard(registVO);
        }
        return result;
    }

    /** 회원 삭제 */
    @Override
    public int deleteMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO  ) {


        registVO.setModId(sessionInfoVO.getUserId());
        registVO.setModDt(DateUtil.currentDateTimeString());

        return mapper.deleteMemberInfo(registVO);
    }

    /***
     * 후불 회원 등록 매장 조회
     * @param postpaidStoreVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getPostpaidStoreLists(PostpaidStoreVO postpaidStoreVO, SessionInfoVO sessionInfoVO) {

        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd()); // 본사에서 조회하는 메뉴
        postpaidStoreVO.setDefaultStoreCd(defaultStoreCd);

        List<DefaultMap<String>> resultList = null;

        // 등록매장 조회
        if(postpaidStoreVO.getRegYn() == UseYn.Y) {
             resultList = mapper.getRegStoreList(postpaidStoreVO);
        }
        // 미등록매장 조회
        else {
            resultList = mapper.getNoRegStoreList(postpaidStoreVO);
        }

        return resultList;
    }

    /** 후불회원 매장 등록 */
    @Override
    public int registPostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(PostpaidStoreVO postpaidStoreVO : postpaidStoreVOs) {

            postpaidStoreVO.setUseYn(UseYn.Y);
            postpaidStoreVO.setRegDt(dt);
            postpaidStoreVO.setRegId(sessionInfoVO.getUserId());
            postpaidStoreVO.setModDt(dt);
            postpaidStoreVO.setModId(sessionInfoVO.getUserId());

            result += mapper.registPostpaidStore(postpaidStoreVO);
        }
        return result;
    }

    /** 후불회원 매장 삭제 */
    @Override
    public int deletePostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(PostpaidStoreVO postpaidStoreVO : postpaidStoreVOs) {

            postpaidStoreVO.setModDt(dt);
            postpaidStoreVO.setModId(sessionInfoVO.getUserId());
            result += mapper.deletePostpaidStore(postpaidStoreVO);
        }
        return result;
    }
}
