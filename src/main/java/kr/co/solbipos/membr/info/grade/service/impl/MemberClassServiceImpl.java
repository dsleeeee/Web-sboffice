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
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;
import kr.co.solbipos.membr.info.grade.service.MemberClassService;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

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
        List<DefaultMap<String>> classList = mapper.getMemberClassList(membrClassVO);
        return convertToJson(classList);
    }

    /**
     * 회원등급 check
     *
     * @return
     */
    @Override
    public DefaultMap<Object> classInfoChk(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();
        String dt = currentDateTimeString();
        int classChk = mapper.classInfoChk(membrClassVO);
        int classResult;
        membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        membrClassVO.setRegDt(dt);
        membrClassVO.setRegId(sessionInfoVO.getUserId());
        membrClassVO.setModDt(dt);
        membrClassVO.setModId(sessionInfoVO.getUserId());
        if (classChk > 0) {
            classResult = mapper.updateClassInfo(membrClassVO);
        } else {
            classResult = mapper.insertClassInfo(membrClassVO);
        }
        result.put("classResult", classResult);
        return result;
    }

//    /**
//     * 회원등급 등록
//     *
//     * @return
//     */
//    @Override
//    public int insertClassInfo(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO) {
//        int classInsert = mapper.insertClassInfo(membrClassVO);
//        return classInsert;
//    }
//
//    /**
//     * 회원등급 수정
//     *
//     * @return
//     */
//    @Override
//    public int updateClassInfo(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO) {
//        int classUpdate = mapper.updateClassInfo(membrClassVO);
//        return classUpdate;
//    }

    /**
     * 회원등급 삭제
     *
     * @return
     */
    @Override
    public int deleteClassInfo(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO) {
        String currentDate = currentDateTimeString();
        int classCnt = 0;
        for (MembrClassVO membrClassVO : membrClassVOs) {
//            membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            membrClassVO.setModId(sessionInfoVO.getUserId());
            membrClassVO.setModDt(DateUtil.currentDateTimeString());
            System.out.println();

            int result = mapper.deleteClassInfo(membrClassVO);
            if (result <= 0) {
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                classCnt += result;
            }
        }
        return classCnt;
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


        /*
         * 상품권은 프랜차이즈의 경우 무조건 본사에서 등록
         *          단독매장의 경우 무조건 매장에서 등록
         */

        for (MembrClassPointVO membrClassPointVO : membrClassPointVOs) {

            membrClassPointVO.setRegDt(dt);
            membrClassPointVO.setRegId(sessionInfoVO.getUserId());
            membrClassPointVO.setModDt(dt);
            membrClassPointVO.setModId(sessionInfoVO.getUserId());
            membrClassPointVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
            membrClassPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());

            if (membrClassPointVO.getStatus() == GridDataFg.INSERT) {
                result = mapper.insertClassPointInfo(membrClassPointVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (membrClassPointVO.getStatus() == GridDataFg.UPDATE) {
                result = mapper.updateClassPointInfo(membrClassPointVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                result = mapper.deleteClassPointInfo(membrClassPointVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
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
}