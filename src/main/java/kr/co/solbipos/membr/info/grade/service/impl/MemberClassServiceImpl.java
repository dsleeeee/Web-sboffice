package kr.co.solbipos.membr.info.grade.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.grade.service.MemberClassService;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

import java.util.ArrayList;
import java.util.List;

@Service("classService")
@Transactional
public class MemberClassServiceImpl implements MemberClassService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberClassMapper mapper;
    private final RegistMapper rMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MemberClassServiceImpl(MemberClassMapper mapper, RegistMapper rMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.rMapper = rMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 회원등급 상세
     *
     * @return
     */
    @Override
    public DefaultMap<Object> getMember(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO) {

        DefaultMap<Object> result = new DefaultMap<>();
        DefaultMap<String> mcd = mapper.getMemberClassDetail(membrClassVO);
        List<DefaultMap<String>> mcp = mapper.getMemberClassPoint(membrClassVO);

        result.put("mcd", mcd);
        result.put("mcp", mcp);
        return result;
    }

    /**
     * 회원등급 리스트 조회
     *
     * @return
     */
    @Override
    public String getMemberClassList(SessionInfoVO sessionInfoVO) {
        MembrClassVO membrClassVO = new MembrClassVO();
        List<DefaultMap<String>> classList;
        if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            classList = mapper.getMemberClassList(membrClassVO);
        } else {
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                classList = mapper.getMemberClassList(membrClassVO);
            } else {
                membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
                classList = mapper.getMemberClassList(membrClassVO);
            }
        }

        return convertToJson(classList);
    }

    /** 회원등급설정 조회 */
    @Override
    public List<DefaultMap<String>> getMemberClassGridList(SessionInfoVO sessionInfoVO) {
        MembrClassVO membrClassVO = new MembrClassVO();
        List<DefaultMap<String>> classList;

        LOGGER.info("sessionInfoVO.getHqOfficeCd() ::::{}",sessionInfoVO.getHqOfficeCd());
        if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            classList = mapper.getMemberClassList(membrClassVO);
        } else {
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                classList = mapper.getMemberClassList(membrClassVO);
            } else {
                membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
                classList = mapper.getMemberClassList(membrClassVO);
            }
        }
        return classList;
    }


    /**
     * 회원 등급 체크 및 저장 프로세스
     * @param membrClassVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public int classInfoChk(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();
        String dt = currentDateTimeString();
        String defltYn = membrClassVO.getDefltYn();

        if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
            membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
        } else {
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            } else {
                membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            }
        }

        //membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        // 회원등급 중복여부
        int classChk = mapper.classInfoChk(membrClassVO);
        // 회원등급 기본여부
        String defltChk = mapper.classDefltChk(membrClassVO);
        // 회원등급 기본여부(기존에 저장된 기본이 있는지)
        int defltChkList = mapper.classDefltChkList(membrClassVO);
        int classResult;
        int classPayRateResult;

        membrClassVO.setRegDt(dt);
        membrClassVO.setRegId(sessionInfoVO.getUserId());
        membrClassVO.setModDt(dt);
        membrClassVO.setModId(sessionInfoVO.getUserId());
        membrClassVO.setMembrOrgnClassCd(membrClassVO.getMembrOrgnCd() + membrClassVO.getMembrClassCd());
        LOGGER.debug("defltChk: {}", defltChk);

        // 2020.11.11 김설아 주석작업
        //  DEFLT_YN에 Y는 무조건 한개
        // 회원등급 코드가 중복이 되면(업데이트)
        if (classChk > 0) {
            // 기존에 Y으로 저장되어있어 && N으로 저장할거야
            if ("Y".equals(defltChk) && "N".equals(defltYn)) {
                classResult = 0;
            // 기존에 N으로 저장되어있어 && N으로 저장할거야 // 기존에 Y으로 저장되어있어 && Y으로 저장할거야 // 기존에 N으로 저장되어있어 && Y으로 저장할거야
            } else {
                classResult = mapper.updateClassInfo(membrClassVO);
                // 포인트적립구분 변경시에만 0으로
                if(!membrClassVO.getPointSaveFg().equals(membrClassVO.getPrePointSaveFg())) {
                    // 적립금 0으로 update
                    classPayRateResult = mapper.updateClassPayRateInfo(membrClassVO);
                }
                if (classResult > 0 && "Y".equals(defltYn)) {
                    membrClassVO.setDefltYn("N");
                    classResult = mapper.defaultUpdateClassInfo(membrClassVO);
                }
            }
        // 회원등급 코드가 중복이 안되면(신규)
        } else {
            if ("N".equals(defltYn)) {
                // 저장된 Y가 없다
                if(defltChkList < 1) {
                    classResult = 0;
                } else {
                    classResult = mapper.insertClassInfo(membrClassVO);
                    classPayRateResult = mapper.updateClassPayRateInfo(membrClassVO);
                    if (classResult > 0 && "Y".equals(defltYn)) {
                        membrClassVO.setDefltYn("N");
                        classResult = mapper.defaultUpdateClassInfo(membrClassVO);
                    }
                }
            } else {
                classResult = mapper.insertClassInfo(membrClassVO);
                classPayRateResult = mapper.updateClassPayRateInfo(membrClassVO);
                if (classResult > 0 && "Y".equals(defltYn)) {
                    membrClassVO.setDefltYn("N");
                    classResult = mapper.defaultUpdateClassInfo(membrClassVO);
                }
            }
        }

        return classResult;
    }

    /**
     * 회원등급 삭제
     *
     * @return
     */
    @Override
    public int deleteClassInfo(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO) {
        int classCnt = 0;
        for (MembrClassVO membrClassVO : membrClassVOs) {
            membrClassVO.setModId(sessionInfoVO.getUserId());
            membrClassVO.setModDt(DateUtil.currentDateTimeString());

            int result = mapper.deleteClassInfo(membrClassVO);
            if (result <= 0) {
                throw new JsonException(Status.FAIL, messageService.get("cmm.dltFail"));
            } else {
                int deletePayRateChk = mapper.deleteClassPayRateInfo(membrClassVO);
                classCnt += result;
            }
        }
        return classCnt;
    }

    /** 회원정보 삭제 */
    @Override
    public DefaultMap<Object> deleteClassInfoChk(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO) {
        int deleteChk = 0;

        DefaultMap<Object> result = new DefaultMap<>();
        for (MembrClassVO membrClassVO : membrClassVOs) {
            deleteChk = mapper.deleteClassChk(membrClassVO);
            if(deleteChk > 0){
                result.put("membrClassNm",membrClassVO.getMembrClassNm());
                result.put("membrClassCd",membrClassVO.getMembrClassCd());
                result.put("deleteChk",deleteChk);
                break;
                //throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        return result;
    }

    /**
     * 회원등급Point 저장
     *
     * @return
     */
    @Override
    public int saveClassPointList(MembrClassPointVO[] membrClassPointVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for (MembrClassPointVO membrClassPointVO : membrClassPointVOs) {

            if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
                membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            } else {
                if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                    membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                } else {
                    membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                    membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                }
            }
            membrClassPointVO.setRegDt(dt);
            membrClassPointVO.setRegId(sessionInfoVO.getUserId());
            membrClassPointVO.setModDt(dt);
            membrClassPointVO.setModId(sessionInfoVO.getUserId());

            if (membrClassPointVO.getInitPayCd() != null) {
                membrClassPointVO.setStatus(GridDataFg.INSERT);
                result = mapper.deleteClassInitPointInfo(membrClassPointVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                int classPointChk = mapper.classPointInfoChk(membrClassPointVO);

                if (classPointChk > 0) {
                    membrClassPointVO.setStatus(GridDataFg.INSERT);
                    result = mapper.deleteClassPointInfo(membrClassPointVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    membrClassPointVO.setStatus(GridDataFg.INSERT);
                }
            }

            if (membrClassPointVO.getStatus() == GridDataFg.INSERT) {
                result = mapper.insertClassPointInfo(membrClassPointVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
//            else if (membrClassPointVO.getStatus() == GridDataFg.UPDATE) {
//                result = mapper.updateClassPointInfo(membrClassPointVO);
//                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            else {
//                result = mapper.deleteClassPointInfo(membrClassPointVO);
//                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
//            }
//            // 매장에서 접속시
//            else {
//                payMethodClassVO.setStoreCd(sessionInfoVO.getStoreCd());
//
//                if (payMethodClassVO.getStatus() == GridDataFg.INSERT) {
//                    payMethodClassVO.setPayClassCd(mapper.getPayMethodClassCd(payMethodClassVO));
//                    result = mapper.insertStoreGiftClass(payMethodClassVO);
//                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//
//                } else if (payMethodClassVO.getStatus() == GridDataFg.UPDATE) {
//                    result = mapper.updateStoreGiftClass(payMethodClassVO);
//                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//
//                } else if (payMethodClassVO.getStatus() == GridDataFg.DELETE) {
//                    result = mapper.deleteStoreGiftClass(payMethodClassVO);
//                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//
//                }
//            }
        }
        return result;
    }

    /** 등급포인트 적립 저장 */
    @Override
    public int getMemberClassPointDel(MembrClassPointVO[] membrClassPointVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for (MembrClassPointVO membrClassPointVO : membrClassPointVOs) {

            if ("00000".equals(sessionInfoVO.getHqOfficeCd())) { // 단독매장
                membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            } else {
                if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                    membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                } else {
                    membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
                    membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
                }
            }

            membrClassPointVO.setRegDt(dt);
            membrClassPointVO.setRegId(sessionInfoVO.getUserId());
            membrClassPointVO.setModDt(dt);
            membrClassPointVO.setModId(sessionInfoVO.getUserId());

            result = mapper.deleteClassPointInfo(membrClassPointVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }
}