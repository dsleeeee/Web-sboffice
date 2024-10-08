package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.MemberMappingVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @author NHN한국사이버결제 KCP 정용길
 * @version 1.0
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @Class Name : RegistServiceImpl.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 * @ 2018.08.02  이다솜      회원등록 시 회원단축번호 추가
 * @since 2018.05.01
 */
@Service("registService")
@Transactional
public class RegistServiceImpl implements RegistService {


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RegistMapper mapper;
    private final PopupMapper popupMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public RegistServiceImpl(RegistMapper mapper, PopupMapper popupMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.popupMapper = popupMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 등록매장 리스트 조회
     */
    @Override
    public List<DefaultMap<String>> getRegistStore(SessionInfoVO sessionInfoVO) {

        // 회원정보 등록시 등록매장의 콤보박스 내용 조회
        // 본사 : 해당 본사에 소속된 매장만 조회한다.
        // 매장 : 해당 매장만 표시
        HqManageVO hqVO = new HqManageVO();

        hqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getRegistStore(hqVO);
    }

    /**
     * 등록매장 리스트 조회 2
     */
    @Override
    public List<DefaultMap<String>> getRegistStore2(SessionInfoVO sessionInfoVO) {

        // 회원정보 등록시 등록매장의 콤보박스 내용 조회
        // 본사 : 해당 본사에 소속된 매장만 조회한다.
        // 매장 : 해당 매장만 표시
        HqManageVO hqVO = new HqManageVO();

        hqVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        hqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            hqVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getRegistStore2(hqVO);
    }

    /**
     * 회원등급 리스트 조회
     */
    @Override
    public List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO) {

        // 회원등급 관리구분
        String membrClassManageFg = CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1");

        MembrClassVO membrClassVO = new MembrClassVO();

        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
        membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        membrClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            membrClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        membrClassVO.setMembrClassManageFg(membrClassManageFg);

        List<DefaultMap<String>> resultList = mapper.getMemberClassList(membrClassVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "기본등급");
            resultList.add(tmpList);
        }
        return resultList;
    }

    /**
     * 선택한 회원 정보 조회
     */
    @Override
    public DefaultMap<String> getMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // ERP 연동과 관련, 본사코드에 의해 조회하는 DB가 다름 (이다솜_2020.01.28)
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getMemberInfo(registVO);
    }

    /**
     * 회원 목록 조회
     */
    @Override
    public List<DefaultMap<String>> getMemberList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        LOGGER.debug("sessionInfoVO.getHqOfficeCd(): {}", sessionInfoVO.getHqOfficeCd());

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            StoreVO storeVO = new StoreVO();
            if(!StringUtil.getOrBlank(registVO.getRegStoreCd()).equals("")) {
               storeVO.setArrSplitStoreCd(CmmUtil.splitText(registVO.getRegStoreCd(), 3900));
               registVO.setRegStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }

            if(!StringUtil.getOrBlank(registVO.getRegUseStoreCd()).equals("")) {
               storeVO.setArrSplitStoreCd(CmmUtil.splitText(registVO.getRegUseStoreCd(), 3900));
               registVO.setRegUseStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getMemberList(registVO);
    }

    /**
     * 회원 목록 조회(Excel 용)
     */
    @Override
    public List<DefaultMap<String>> getMemberListExcel(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            StoreVO storeVO = new StoreVO();
            if(!StringUtil.getOrBlank(registVO.getRegStoreCd()).equals("")) {
               storeVO.setArrSplitStoreCd(CmmUtil.splitText(registVO.getRegStoreCd(), 3900));
               registVO.setRegStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }

            if(!StringUtil.getOrBlank(registVO.getRegUseStoreCd()).equals("")) {
               storeVO.setArrSplitStoreCd(CmmUtil.splitText(registVO.getRegUseStoreCd(), 3900));
               registVO.setRegUseStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (registVO.getExcelGubun().equals("T")) { // 전체 엑셀다운로드시(T) 조회조건 날림
            registVO.setPeriodType(null);
            registVO.setPeriodStartDate(null);
            registVO.setPeriodEndDate(null);
            registVO.setAnvType(null);
            registVO.setAnvStartDate(null);
            registVO.setAnvEndDate(null);
            registVO.setStartSaveSale(null);
            registVO.setEndSaveSale(null);
            registVO.setStartAvablPoint(null);
            registVO.setEndAvablPoint(null);
            registVO.setShortNo(null);
            registVO.setWeddingYn(null);
            registVO.setPhoneNo(null);
            registVO.setMembrClassCd(null);
            registVO.setMembrNo(null);
            registVO.setMembrNm(null);
            registVO.setMembrEngNm(null);
            registVO.setStoreMembr(null);
            registVO.setVisitStoreMembr(null);
            registVO.setRegUseStoreCds(null);
            registVO.setRegUseStoreCd(null);
            registVO.setRegStoreCds(null);
            registVO.setRegStoreCd(null);
            registVO.setTelNo(null);
            registVO.setMembrCardNo(null);
            registVO.setCstCardUseFg(null);
            registVO.setEmailAddr(null);
            registVO.setEmailRecvYn(null);
            registVO.setSmsRecvYn(null);
            registVO.setGendrFg(null);
            registVO.setMemberSaleFg(null);
            registVO.setMemberPointFg(null);
            registVO.setUseYn(null);
            if(CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1").equals("1")){
                registVO.setMembrClassManageFg(null);
            }

            if(registVO.getExcelPw() != null && registVO.getExcelPw().equals("c08001")){
                registVO.setUserId(sessionInfoVO.getUserId());
                registVO.setvUserId(sessionInfoVO.getvUserId());
                /** 마스킹 없는 엑셀다운로드 로그 저장 */
                mapper.saveMemberExcelLog(registVO);
            }
        } else {
            registVO.setExcelPw(null);
        }

        return mapper.getMemberListExcel(registVO);
    }

    /**
     * 회원 등록
     */
    @Override
    public String registMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        registVO.setMembrNo(mapper.getNewMemberNo(registVO));

        if(registVO.getMembrNm() == null || registVO.getMembrNm() == ""){
            registVO.setMembrNm(registVO.getMembrNo());
        }

        // 회원단축번호 추가_2019.08.02 추가 이다솜 -> 테스트중 에러나서 주석처리 2020.10.27 김설아(확인 김중선)
        // .js 단에서 처리하고 있음 2020.11.02 이다솜 -> 에러나서 수정함 2020.11.12 김설아
//        registVO.setShortNo(registVO.getTelNo().substring(registVO.getTelNo().length() - 4, registVO.getTelNo().length()));

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        registVO.setChgDate(registVO.getRegDt().substring(0, registVO.getRegDt().length()-6));
        LOGGER.debug("registVO.getRegDt()(): {}", registVO.getRegDt());
        LOGGER.debug("registVO.setChgDate(): {}", registVO.getChgDate());

        // 신규가입 적립 POINT
        int newJoinSavePoint = mapper.newJoinSavePointInfo(registVO);

        // 회원등록
        int result = mapper.registMemberInfo(registVO);
        String membrNo = "";

        if (result == 1) {
            membrNo = registVO.getMembrNo();
//            // 회원카드 등록
//            if (mapper.insertMembrCard(registVO) <= 0) {
//                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }

            // 회원등급에 따른 신규가입 적립포인트
//            if (newJoinSavePoint > 0) {
//                registVO.setPointChgFg("1");
//                registVO.setChgPoint(newJoinSavePoint);
//                registVO.setRemark("신규가입");
//                mapper.insertMembrPointHist(registVO);
//            }

            // 이전포인트
            if(registVO.getMovePoint() > 0){
                registVO.setPointChgFg("3");
                registVO.setChgPoint(registVO.getMovePoint());
                registVO.setRemark("이전포인트");
                mapper.insertMembrPointHist(registVO);
            }

            if ( registVO.getMembrCardNo() != null && !"".equals(registVO.getMembrCardNo())) {
                registVO.setCstCardIssFg("0");//신규발급
                mapper.insertMembrCard(registVO);
            }
        }
        // 선불회원 등록 (자점회원)
        result = mapper.registMemberPrepaid(registVO);
        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 회원-거래처 매핑코드 등록 (보나비)
        if (registVO.getCdCompany() != null && registVO.getCdCompany() != ""){
                if(registVO.getCdPartner() != null && registVO.getCdPartner() != "") {
                    result = mapper.registMemberMappingCode(registVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
        }

//        System.out.println("test1111");
        LOGGER.info("회원등록 >>> 날짜 : " + dt + ", 본사코드 : " + registVO.getMembrOrgnCd() + ", 매장코드 : " + registVO.getRegStoreCd() + ", 회원코드 : " + registVO.getMembrNo());
        // 회원정보 등록,수정시 본사코드 A0007만
        if(("A0007").equals(registVO.getMembrOrgnCd())) {
            // 프로시저 결과
            String sResult = "";

            LOGGER.info("회원등록 >>> SP_NEOE_POSLINK_PTN 호출 : " + dt);
            sResult = mapper.registPoslinkPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원등록 >>> SP_NEOE_POSLINK_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/

            LOGGER.info("회원등록 >>> SP_NEOE_SPOS_BILL_PTN 호출 : " + dt);
            sResult = mapper.registSposBillPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원등록 >>> SP_NEOE_SPOS_BILL_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/
        }

        // [1246 광운대아이스링크] 환경설정값 조회
        String kwuEnvstVal = "0";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            kwuEnvstVal = CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1246"), "0");
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            kwuEnvstVal = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1246"), "0");
        }
        System.out.println("kwuEnvstVal : " + kwuEnvstVal);
        if(("1").equals(kwuEnvstVal)) {
            result = mapper.mergeMemberInfoAddKwu(registVO);
        }


        return membrNo;
    }

    /**
     * 회원 수정
     */
    @Override
    public int updateMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원 등록
        int result = mapper.updateMemberInfo(registVO);
        if (result == 1) {
            // 회원카드 수정
            mapper.updateMembrCard(registVO);
        }

        // 선불회원 등록 (자점회원)
        result = mapper.registMemberPrepaid(registVO);
        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 회원-거래처 매핑코드 등록 (보나비)
        if (registVO.getCdCompany() != null && registVO.getCdCompany() != ""){
            if(registVO.getCdPartner() != null && registVO.getCdPartner() != "") {
                result = mapper.registMemberMappingCode(registVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        LOGGER.info("회원수정 >>> 날짜 : " + dt + ", 본사코드 : " + registVO.getMembrOrgnCd() + ", 매장코드 : " + registVO.getRegStoreCd() + ", 회원코드 : " + registVO.getMembrNo());
        // 회원정보 등록,수정시 본사코드 A0007만
            if(("A0007").equals(registVO.getMembrOrgnCd())) {
                // 프로시저 결과
                String sResult = "";

                LOGGER.info("회원수정 >>> SP_NEOE_POSLINK_PTN 호출 : " + dt);
                sResult = mapper.registPoslinkPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원수정 >>> SP_NEOE_POSLINK_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/

                LOGGER.info("회원수정 >>> SP_NEOE_SPOS_BILL_PTN 호출 : " + dt);
                sResult = mapper.registSposBillPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원수정 >>> SP_NEOE_SPOS_BILL_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/
        }

        // [1246 광운대아이스링크] 환경설정값 조회
        String kwuEnvstVal = "0";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            kwuEnvstVal = CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1246"), "0");

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            kwuEnvstVal = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1246"), "0");
        }
        System.out.println("kwuEnvstVal : " + kwuEnvstVal);
        if(("1").equals(kwuEnvstVal)) {
            result = mapper.mergeMemberInfoAddKwu(registVO);
        }


        return result;
    }

    /**
     * 회원 삭제
     */
    @Override
    public int deleteMemberInfo(RegistVO[] registVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int registCnt = 0;

        for (RegistVO registVO : registVOs) {
            registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            registVO.setModId(sessionInfoVO.getUserId());
            registVO.setModDt(DateUtil.currentDateTimeString());

            int result = mapper.deleteMemberInfo(registVO);
            if (result <= 0) {
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                registCnt += result;
            }
        }

        return registCnt;
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

        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        postpaidStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            postpaidStoreVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        postpaidStoreVO.setDefaultStoreCd(defaultStoreCd);

        List<DefaultMap<String>> resultList = null;

        // 등록매장 조회
        if (postpaidStoreVO.getRegYn() == UseYn.Y) {
            resultList = mapper.getRegStoreList(postpaidStoreVO);
        }
        // 미등록매장 조회
        else {
            resultList = mapper.getNoRegStoreList(postpaidStoreVO);
        }

        return resultList;
    }

    /**
     * 후불회원 매장 등록
     */
    @Override
    public int registPostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for (PostpaidStoreVO postpaidStoreVO : postpaidStoreVOs) {

            postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            postpaidStoreVO.setUseYn(UseYn.Y);
            postpaidStoreVO.setRegDt(dt);
            postpaidStoreVO.setRegId(sessionInfoVO.getUserId());
            postpaidStoreVO.setModDt(dt);
            postpaidStoreVO.setModId(sessionInfoVO.getUserId());

            result += mapper.registPostpaidStore(postpaidStoreVO);
        }
        return result;
    }

    /**
     * 후불회원 매장 삭제
     */
    @Override
    public int deletePostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for (PostpaidStoreVO postpaidStoreVO : postpaidStoreVOs) {

            postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            postpaidStoreVO.setModDt(dt);
            postpaidStoreVO.setModId(sessionInfoVO.getUserId());

            result += mapper.deletePostpaidStore(postpaidStoreVO);
        }
        return result;
    }

    /**
     * 회원 거래처 매핑코드 (보나비 전용 >> 비티스 추가 2020.01.28)
     */
    @Override
    public List<DefaultMap<String>> getMappingCompany(MemberMappingVO memberMappingVO, SessionInfoVO sessionInfoVO) {

        // ERP 연동과 관련, 본사코드에 의해 조회하는 DB가 다름 (이다솜_2020.01.28)
        memberMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getMappingCompany(memberMappingVO);
    }

    /**
     * 회원 거래처 매핑 팝업 - 회원 거래처 매핑 조회
     */
    @Override
    public List<DefaultMap<String>> getMemberVendorMappingList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (!StringUtil.isEmpties(registVO.getRegStoreCd())) {
            registVO.setRegStoreCds(registVO.getRegStoreCd().split(","));
        }

        return mapper.getMemberVendorMappingList(registVO);
    }

    /**
     * 해당회원 카드정보
     */
    @Override
    public List<DefaultMap<String>> getCardList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return mapper.getCardList(registVO);
    }

    /**
     *  회원명 중복 체크
     */
    @Override
    public int getMemberNmCount(RegistVO registVO, SessionInfoVO sessionInfoVO){

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        int result = mapper.getMemberNmCount(registVO);
        return result;
    }

    /**
     *  전화번호 중복 체크
     */
    @Override
    public int getMemberTelNoCount(RegistVO registVO, SessionInfoVO sessionInfoVO){

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        int result = mapper.getMemberTelNoCount(registVO);
        return result;
    }

    /**
     *  카드 중복 체크
     */
    @Override
    public int getMemberCardInfoCount(RegistVO registVO, SessionInfoVO sessionInfoVO){

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        System.out.println("getMembrOrgnCd ::::::"+registVO.getMembrOrgnCd());

        int result = mapper.getMemberCardInfoCount(registVO);
        return result;
    }

    /** 카드 중복 체크( 카드번호 사용중인 회원번호 / X (해당 카드번호 미사용) ) */
    @Override
    public String getMemberCardInfoCountDetail(RegistVO registVO, SessionInfoVO sessionInfoVO){

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        String result = mapper.getMemberCardInfoCountDetail(registVO);

        return result;
    }

    /**
     * 카드정보등록
     */
    @Override
    public int registCardInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        //신규등록일 때 이전 카드번호 '0'으로 set -> ''으로 수정 2020.11.12 김설아(확인 김중선)
        registVO.setOldCstCardNo("");
        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원카드 등록
        int result = mapper.insertMembrCard(registVO);
        int membrResult = mapper.updateMembr(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        if (membrResult <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /**
     * 카드정보수정
     */
    @Override
    public int updateMembrCard(RegistVO registVO, SessionInfoVO si) {

        String dt = currentDateTimeString();

        registVO.setOrgnFg(si.getOrgnFg());
        registVO.setMembrOrgnCd(si.getOrgnGrpCd());

        registVO.setRegDt(dt);
        registVO.setRegId(si.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(si.getUserId());

        //기존 카드 사용여부 '중지(1)'로 변경
        registVO.setCstCardUseFg("1");

        // 회원카드 수정
        int result = mapper.insertMembrCard(registVO);
        int membrResult = mapper.updateMembr(registVO);
        registVO.setMembrCardNo(registVO.getOldCstCardNo());
        int cardResult = mapper.updateMembrCard(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        if (cardResult <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        if (membrResult <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 배달정보 리스트 조회 */
    @Override
    public DefaultMap<Object> getDlvrList(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        LOGGER.debug("regStoreCd: {}", registVO.getRegStoreCd());
        LOGGER.debug("sessionInfoStoreCd: {}", sessionInfoVO.getStoreCd());

        List dlvrList = mapper.getDlvrList(registVO);
        List lZoneList = mapper.getMembrLzoneList(registVO);
        result.put("dlvrList", dlvrList);
        result.put("lZoneList", lZoneList);
        return result;
    }

    /** 중분류 리스트 조회 */
    @Override
    public DefaultMap<Object> getDlvrMzoneList(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();
        List mZoneList = mapper.getDlvrMzoneList(registVO);
        result.put("mZoneList", mZoneList);
        return result;
    }

    /** 대분류 리스트 조회 */
    @Override
    public List getLzoneList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
            registVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
        } else {
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                registVO.setStoreCd(sessionInfoVO.getHqOfficeCd());
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                registVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
        }

        List<DefaultMap<String>> resultList = mapper.getDlvrList(registVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "없음");
            resultList.add(tmpList);
        }
        return resultList;
    }

    // 배달전화 리스트
    @Override
    public List<DefaultMap<String>> getDlvrTelList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return mapper.getDlvrTelList(registVO);
    }

    // 배달정보등록
    @Override
    public int registDlvrInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원배달 등록
        int result = mapper.insertMembrDlvr(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    // 배달 전화번호 정보
    @Override
    public int registDlvrTelInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원카드 등록
        int result = mapper.insertMembrDlvrTel(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    // 배달 전화번호 수정
    @Override
    public int updateDlvrTelInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        int result = mapper.updateMembrDlvrTel(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    @Override
    public int deleteDlvrTelInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        int result = mapper.deleteMembrDlvrTel(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 배달주소지 수정 */
    @Override
    public int updateDlvrAddrInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        int result = mapper.updateDlvrAddrInfo(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 배달주소지 삭제 */
    @Override
    public int deleteDlvrAddrInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        int result = mapper.deleteDlvrAddrInfo(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 회원정보 포인트 조회 */
    @Override
    public List<DefaultMap<String>> getMemberInfoPointList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        return mapper.getMemberInfoPointList(registVO);
    }

    /** 회원정보 구매내역 조회 */
    @Override
    public List<DefaultMap<String>> getMemberInfoBuyList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getMemberInfoBuyList(registVO);
    }

    /** 회원 포인트 조회 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchMemberPointList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        if (registVO.getTelNo() != null && !"".equals(registVO.getTelNo())) {
            registVO.setTelNo(registVO.getTelNo().replaceAll("-",""));
        }

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 보내는 회원
        if(registVO.getGubun().equals("send")) {
            result = mapper.getSearchMemberPointList(registVO);

        // 받는 회원
        } else if(registVO.getGubun().equals("receive")) {
            result = mapper.getSearchMemberPointReceiveList(registVO);
        }

        return result;
    }

    /** 회원 포인트 이관 팝업 - 저장 */
    @Override
    public int getMemberPointMoveSave(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        registVO.setRegDt(currentDt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(currentDt);
        registVO.setModId(sessionInfoVO.getUserId());

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        registVO.setChgDate(registVO.getRegDt().substring(0, registVO.getRegDt().length()-6));
        registVO.setRegStoreCd(null);
        registVO.setPointChgFg("2");
        registVO.setRemark("[" + registVO.getMemberNoSend() + "] " + registVO.getMemberNmSend() + " 에서 " + "[" + registVO.getMemberNoReceive() + "] " + registVO.getMemberNmReceive() + " 로 " + registVO.getPointReceive() + " 포인트 이관");

        // 보내는 회원
        registVO.setMembrNo(registVO.getMemberNoSend());
        registVO.setChgPoint(-registVO.getPointReceive());
        mapper.insertMembrPointHist(registVO);

        // 받는 회원
        registVO.setMembrNo(registVO.getMemberNoReceive());
        registVO.setChgPoint(registVO.getPointReceive());
        mapper.insertMembrPointHist(registVO);

        return procCnt;
    }

    /** 회원 등급 조회 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchMemberClassList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        if (registVO.getTelNo() != null && !"".equals(registVO.getTelNo())) {
            registVO.setTelNo(registVO.getTelNo().replaceAll("-",""));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getSearchMemberClassList(registVO);
    }

    /** 회원 포인트 조정 팝업 - 저장 */
    @Override
    public int getMemberPointAdjustSave(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        registVO.setRegDt(currentDt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(currentDt);
        registVO.setModId(sessionInfoVO.getUserId());

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        if(registVO.getGubun().equals("memberInfoPointDtl")) {
            // 비고 수정
            mapper.getMemberPointAdjustSaveUpdate(registVO);

        } else {
            registVO.setChgDate(registVO.getRegDt().substring(0, registVO.getRegDt().length()-6));
            registVO.setRegStoreCd(null);
            registVO.setPointChgFg("3");

            registVO.setChgPoint(Integer.parseInt(registVO.getAvablPoint()));
            if(registVO.getRemark() == null || "".equals(registVO.getRemark())){
              registVO.setRemark("포인트조정[" + sessionInfoVO.getOrgnCd() + "-" + sessionInfoVO.getUserId() + "]");
            }

            mapper.insertMembrPointHist(registVO);
        }

        return procCnt;
    }

    /** 회원 포인트 조정 팝업 - 조회 */
    @Override
    public DefaultMap<String> getMemberPointAdjustList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        resultMap = mapper.getMemberPointAdjustList(registVO);

        return resultMap;
    }

    /** 회원 삭제 팝업 - 강제삭제 체크용 비밀번호 조회 */
    @Override
    public String getForcedDeleteChkPwd() {
        return mapper.getForcedDeleteChkPwd();
    }

    /** 선택회원삭제 */
    @Override
    public int selectMemberDelete(RegistVO[] registVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();
        String delMemberNo = ""; // 삭제할 회원번호

        for (RegistVO registVO : registVOs) {

            // 프랜차이즈 매장권한인 경우, 등록매장이 본인매장인 경우민 식제가능
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                if (!"00000".equals(sessionInfoVO.getHqOfficeCd())){
                    if (!sessionInfoVO.getStoreCd().equals(registVO.getRegStoreCd())) {
                        continue;
                    }
                }
            }

            registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
            registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                registVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            registVO.setModDt(currentDt);
            registVO.setModId(sessionInfoVO.getUserId());

            int delYn = 0; // 삭제가능여부 파악

            // 강제삭제 체크 시
            if("Y".equals(registVO.getForcedDeleteYn())) {
                // 삭제가능여부 무조건 가능
                delYn = 1;
            }else{
                // 삭제가능여부 파악
                delYn = mapper.getMemberDeleteYnChk(registVO);
            }

            if(delYn > 0){
                // 영구삭제가능
                // 한번에 삭제하기 위해 arr에 담을준비
                delMemberNo += (delMemberNo.equals("") ? "" : ",") + registVO.getMembrNo();

            }else{
                // 영구삭제불가('미사용' 처리)
                result += mapper.deleteMemberInfo(registVO);
            }
        }

        // 선택회원 영구삭제
        if(!StringUtil.getOrBlank(delMemberNo).equals("")) {

            RegistVO registVO2 = new RegistVO();

            registVO2.setOrgnFg(sessionInfoVO.getOrgnFg());
            registVO2.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                registVO2.setStoreCd(sessionInfoVO.getStoreCd());
            }
            registVO2.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            registVO2.setArrMembrNo(delMemberNo.split(","));

            // 회원 추가정보 (광운대 아이스링크) 영구삭제
            mapper.deleteMemberKwu(registVO2);
            // 회원 카드정보 영구삭제
            mapper.deleteMemberCard(registVO2);
            // 회원 포인트 변경내역 영구삭제
            mapper.deleteMemberPointHist(registVO2);
            // 회원 포인트 정보 영구삭제
            mapper.deleteMemberPoint(registVO2);
            // 회원 포인트 정보 영구삭제
            mapper.deleteMemberPointStore(registVO2);
            // 회원 후불원장 영구삭제
            mapper.deleteMemberPostpaid(registVO2);
            // 후불회원 등록매장 영구삭제
            mapper.deleteMemberPostpaidStore(registVO2);
            // 회원 선불원장 영구삭제
            mapper.deleteMemberPrepaid(registVO2);
            // 선불회원 등록매장 영구삭제
            mapper.deleteMemberPrepaidStore(registVO2);
            // 선/후불 잔액 영구삭제
            mapper.deleteMemberPaidBalance(registVO2);
            // 회원 정보 영구 삭제
            mapper.deleteMember(registVO2);

            result += registVO2.getArrMembrNo().length;
        }

        return result;
    }

    /** 전체회원삭제 */
    @Override
    public int allMemberDelete(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        registVO.setModDt(currentDt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 강제삭제가 아닐때
        if("N".equals(registVO.getForcedDeleteYn())) {

            // 전체회원 중 삭제불가회원 '미사용'으로 수정
            mapper.updateAllMemberUseYn(registVO);
        }

        // 삭제불가회원을 제외한 전체회원 영구삭제
        // 강제삭제시 전체회원 영구삭제

        // 전체회원 추가정보 (광운대 아이스링크) 영구삭제
        mapper.deleteAllMemberKwu(registVO);
        // 전체회원 회원 카드정보 영구삭제
        mapper.deleteAllMemberCard(registVO);
        // 전체회원 회원 포인트 변경내역 영구삭제
        mapper.deleteAllMemberPointHist(registVO);
        // 전체회원 회원 포인트 정보 영구삭제
        mapper.deleteAllMemberPoint(registVO);
        // 전체회원 회원 포인트 정보 영구삭제
        mapper.deleteAllMemberPointStore(registVO);
        // 전체회원 회원 후불원장 영구삭제
        mapper.deleteAllMemberPostpaid(registVO);
        // 전체회원 후불회원 등록매장 영구삭제
        mapper.deleteAllMemberPostpaidStore(registVO);
        // 전체회원 회원 선불원장 영구삭제
        mapper.deleteAllMemberPrepaid(registVO);
        // 전체회원 선불회원 등록매장 영구삭제
        mapper.deleteAllMemberPrepaidStore(registVO);
        // 전체회원 선/후불 잔액 영구삭제
        mapper.deleteAllMemberPaidBalance(registVO);
        // 전체회원 회원 정보 영구 삭제
        mapper.deleteAllMember(registVO);

        return result;
    }

    /** 코드별 공통코드 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getNmcodeComboList(SessionInfoVO sessionInfoVO, String nmcodeGrpCd) {

        RegistVO registVO = new RegistVO();
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        registVO.setNmcodeGrpCd(nmcodeGrpCd);

        return mapper.getNmcodeComboList(registVO);
    }

    /** 회원정보 조회 (광운대아이스링크 추가정보) */
    @Override
    public DefaultMap<String> getMemberInfoAddKwu(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // ERP 연동과 관련, 본사코드에 의해 조회하는 DB가 다름 (이다솜_2020.01.28)
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getMemberInfoAddKwu(registVO);
    }

}