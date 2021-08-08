package kr.co.solbipos.membr.info.dlvr.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.dlvr.service.DlvrService;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("dlvrService")
@Transactional
public class DlvrServiceImpl implements DlvrService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final DlvrMapper dlvrMapper;
    private final RegistMapper rMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public DlvrServiceImpl(DlvrMapper dlvrMapper, RegistMapper rMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.dlvrMapper = dlvrMapper;
        this.rMapper = rMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 배달주소지 */
    @Override
    public List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
//        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
//            dlvrVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
//        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
//            dlvrVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
//        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            dlvrVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        }
        dlvrVO.setDlvrStoreCd(sessionInfoVO.getStoreCd());
        return dlvrMapper.getDlvrList(dlvrVO);
    }

    /** 배달 전호번호 */
    @Override
    public List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            dlvrVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        }
        dlvrVO.setDlvrStoreCd(sessionInfoVO.getStoreCd());
        return dlvrMapper.getDlvrTelList(dlvrVO);
    }

    /** 회원등급 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO) {
        MembrClassVO membrClassVO = new MembrClassVO();
        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        }
        List<DefaultMap<String>> resultList = dlvrMapper.getMemberClassList(membrClassVO);
        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "기본등급");
            resultList.add(tmpList);
        }
        return resultList;
    }

    /** 중분류구역 조회 */
    @Override
    public DefaultMap<Object> getDlvrMzoneList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            dlvrVO.setRegStoreCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrVO.setRegStoreCd(sessionInfoVO.getOrgnGrpCd());
        }
        List mZoneList = dlvrMapper.getDlvrMzoneList(dlvrVO);
        result.put("mZoneList", mZoneList);
        return result;
    }

    /** 배달지 저장 */
    @Override
    public int saveDlvr(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO) {
        int resultCnt = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for (DlvrVO dlvrVO : dlvrVOs) {

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            } else {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            }

            dlvrVO.setModDt(dt);
            dlvrVO.setModId(sessionInfoVO.getUserId());

            int result = dlvrMapper.updateDlvr(dlvrVO);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                resultCnt += result;
            }

        }
        return resultCnt;
    }

    /** 배달 전화번호 저장 */
    @Override
    public int saveDlvrTel(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO) {
        int resultCnt = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for (DlvrVO dlvrVO : dlvrVOs) {

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            } else {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            }

            dlvrVO.setRegDt(dt);
            dlvrVO.setRegId(sessionInfoVO.getUserId());
            dlvrVO.setModDt(dt);
            dlvrVO.setModId(sessionInfoVO.getUserId());

            int result = dlvrMapper.updateDlvrTel(dlvrVO);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                resultCnt += result;
            }

        }
        return resultCnt;
    }

    /** 배달지 삭제 */
    @Override
    public int deleteDlvr(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for (DlvrVO dlvrVO : dlvrVOs) {

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            } else {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            }

            dlvrVO.setRegDt(dt);
            dlvrVO.setRegId(sessionInfoVO.getUserId());
            dlvrVO.setModDt(dt);
            dlvrVO.setModId(sessionInfoVO.getUserId());

            result = dlvrMapper.deleteDlvr(dlvrVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 배달지 전화번호 삭제 */
    @Override
    public int deleteDlvrTel(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procResult = "";

        OrgnFg orgnFg = sessionInfoVO.getOrgnFg();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();
        String dt = currentDateTimeString();

        for (DlvrVO dlvrVO : dlvrVOs) {

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            } else {
                dlvrVO.setOrgnFg(sessionInfoVO.getOrgnFg());
                dlvrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            }

            dlvrVO.setRegDt(dt);
            dlvrVO.setRegId(sessionInfoVO.getUserId());
            dlvrVO.setModDt(dt);
            dlvrVO.setModId(sessionInfoVO.getUserId());

            result = dlvrMapper.deleteDlvrTel(dlvrVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }
        return result;
    }

    /** 배달대분류구역 조회  */
    @Override
    public List<DefaultMap<String>> getDlvrLzoneList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dlvrVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> resultList = dlvrMapper.getDlvrLzoneList(dlvrVO);
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "");
            tmpList.put("name", "");
            resultList.add(tmpList);
        }

        return resultList;
    }


}