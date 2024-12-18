package kr.co.solbipos.sys.auth.authgroup.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import kr.co.solbipos.sys.auth.authgroup.service.AuthGroupService;
import kr.co.solbipos.sys.auth.authgroup.service.AuthGroupVO;
import kr.co.solbipos.sys.auth.authgroup.service.AuthorExceptVO;
import kr.co.solbipos.sys.auth.authgroup.service.AuthorGrpResrceVO;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 *
 */
@Service("authGroupService")
public class AuthGroupServiceImpl implements AuthGroupService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthGroupMapper mapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    MessageService messageService;

    /** 로그인 아이디 기준 선택 가능 그룹 조회 */
    @Override
    public List<DefaultMap<String>> listAvailGroup(SessionInfoVO sessionInfoVO) {
        return mapper.selectAvailGroup(sessionInfoVO.getOrgnCd());
    }

    /** 조회 */
    @Override
    public List<DefaultMap<String>> list(AuthGroupVO authGroupVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() != OrgnFg.MASTER) {
            authGroupVO.setTargetOrgn(sessionInfoVO.getOrgnCd());
        }
        return mapper.selectGroup(authGroupVO);
    }

    /** 저장 */
    @Override
    public int save(AuthGroupVO[] authGroupVOs, SessionInfoVO sessionInfo) {

        int procCnt = 0;
        String dt = currentDateTimeString();
        for(AuthGroupVO authGroupVO: authGroupVOs) {

            // 기본값 세팅
            authGroupVO.setRegDt(dt);
            authGroupVO.setModDt(dt);
            authGroupVO.setRegId(sessionInfo.getUserId());
            authGroupVO.setModId(sessionInfo.getUserId());

            if(authGroupVO.getStatus() == GridDataFg.INSERT) {
                authGroupVO.setUseYn(UseYn.Y);
                procCnt += mapper.insertGroup(authGroupVO);
            }
            else if(authGroupVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateGroup(authGroupVO);
            }
            else if(authGroupVO.getStatus() == GridDataFg.DELETE) {
                authGroupVO.setUseYn(UseYn.N);
                procCnt += mapper.deleteGroup(authGroupVO);
            }
        }

        if(procCnt == authGroupVOs.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 리소스 정보 조회 */
    @Override
    public List<AuthorGrpResrceVO> listResrce(AuthGroupVO authGroupVO, SessionInfoVO sessionInfoVO) {

        //Tree 생성을 위한 그룹 기준 권한이 있는 모든! 리소스(Menu 포함) 조회 - 기준
        authGroupVO.setAuthGrpCd(authGroupVO.getAuthGrpCd());
        List<DefaultMap<String>> list = mapper.selectResrceByGroup(authGroupVO);

        //로그인 아이디 기준 권한이 있는 기능의 탐색 경로 조회
        //Tree의 기준 데이터에서 권한이 없는 리소스를 빼기 위해 사용
        List<DefaultMap<String>> authedList = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() != OrgnFg.MASTER) {
            authGroupVO.setAuthGrpCd(sessionInfoVO.getAuthGrpCd());
            authGroupVO.setUserId(sessionInfoVO.getUserId());
            authedList = mapper.selectResrceById(authGroupVO);
        }
        return makeTreeData(list, authedList);
    }

    /** 리소스 정보 저장 */
    @Override
    public int saveResrce(AuthorGrpResrceVO[] authorGrpResrceVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String insertDt = currentDateTimeString();
        for(AuthorGrpResrceVO authorGrpResrceVO: authorGrpResrceVOs) {

            // 기본값 세팅
            authorGrpResrceVO.setRegDt(insertDt);
            authorGrpResrceVO.setModDt(insertDt);
            authorGrpResrceVO.setRegId(sessionInfoVO.getUserId());
            authorGrpResrceVO.setModId(sessionInfoVO.getUserId());

            if(authorGrpResrceVO.getStatus() == GridDataFg.INSERT) {
                authorGrpResrceVO.setUseYn(UseYn.Y);
                procCnt += mapper.mergeAuthorGrpResrce(authorGrpResrceVO);
            }
            else if(authorGrpResrceVO.getStatus() == GridDataFg.DELETE) {
                authorGrpResrceVO.setUseYn(UseYn.N);
                procCnt += mapper.mergeAuthorGrpResrce(authorGrpResrceVO);
            }
        }

        if(procCnt == authorGrpResrceVOs.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 트리 데이터 생성 */
    @Override
    public List<AuthorGrpResrceVO> makeTreeData(List<DefaultMap<String>> list,
            List<DefaultMap<String>> authedList) {

        List<AuthorGrpResrceVO> input = new ArrayList<AuthorGrpResrceVO>();
        AuthorGrpResrceVO authorGrpResrceVO = new AuthorGrpResrceVO();
        for(DefaultMap<String> rm : list) {
            authorGrpResrceVO = new AuthorGrpResrceVO();
            authorGrpResrceVO.setResrceCd(rm.getStr("resrceCd"));
            authorGrpResrceVO.setpResrce(rm.getStr("pResrce"));
            authorGrpResrceVO.setResrceNm(rm.getStr("resrceNm"));
            authorGrpResrceVO.setResrceDisp(rm.getStr("resrceDisp"));
            authorGrpResrceVO.setAuthFg(rm.getLong("authFg") == 1 ? true : false);
            authorGrpResrceVO.setItems(new ArrayList<AuthorGrpResrceVO>());
            input.add(authorGrpResrceVO);
        }
        //권한이 있는 기능에서 상위 리소스 정보 생성
        //상위 리소스 맵에 없는 리소스는 권한이 없는 것으로 판단.
        Map<String, String> authedResrce = new HashMap<String, String>();
        if(authedList != null && authedList.size() > 0) {
            String resrcePath = "";
            String[] arrayRes;
            for(DefaultMap<String> item : authedList) {
                resrcePath = item.getStr("resrcePath").substring(1);
                arrayRes = resrcePath.split("\\/");
                for(String res : arrayRes) {
                    authedResrce.put(res, "");
                }
            }
            LOGGER.debug(authedResrce.toString());
        }

        Map<String, AuthorGrpResrceVO> hm = new LinkedHashMap<String, AuthorGrpResrceVO>();
        AuthorGrpResrceVO child;
        AuthorGrpResrceVO mmdParent;

        for(AuthorGrpResrceVO item : input) {

            if(!hm.containsKey(item.getResrceCd())) {
                if(authedResrce.size() > 0) {
                    if(authedResrce.containsKey(item.getResrceCd())) {
                        hm.put(item.getResrceCd(), item);
                    }
                }
                else {
                    hm.put(item.getResrceCd(), item);
                }
            }
            child = hm.get(item.getResrceCd());

            if(child != null && !item.getpResrce().equals("") && !item.getpResrce().equals("000000")) {
                if(hm.containsKey(item.getpResrce())) {
                    mmdParent = hm.get(item.getpResrce());
                    mmdParent.getItems().add(child);
                }
            }

        }
        //log.debug( hm.toString() );

        List<AuthorGrpResrceVO> retrunData = new ArrayList<AuthorGrpResrceVO>();
        for (AuthorGrpResrceVO mmd : hm.values()) {
            if (mmd.getpResrce() == null || mmd.getpResrce().equals("")
                    || mmd.getpResrce().equals("000000")) {
                retrunData.add(mmd);
            }
        }
        LOGGER.debug( retrunData.toString() );
        return retrunData;
    }

    /** 아이디 기준 리소스 정보 조회 - 예외 관리 */
    @Override
    public List<AuthorGrpResrceVO> listResrceById(String userId, SessionInfoVO sessionInfoVO) {

        AuthGroupVO authGroupVO = new AuthGroupVO();

        //Tree 생성을 위한 모든 리소스(Menu 포함) 조회
        //로그인 아이디의 그룹 기준 권한이 있는 것 체크
        authGroupVO.setAuthGrpCd(mapper.selectAuthGrpCdById(userId));
        authGroupVO.setUserId(userId);
        List<DefaultMap<String>> list = mapper.selectResrceByGroupAndId(authGroupVO);

        //로그인 아이디 기준 권한이 있는 기능의 탐색 경로 조회
        //Tree의 기준 데이터에서 권한이 없는 리소스를 빼기 위해 사용
        List<DefaultMap<String>> authedList = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() != OrgnFg.MASTER) {
            authGroupVO.setAuthGrpCd(sessionInfoVO.getAuthGrpCd());
            authGroupVO.setUserId(sessionInfoVO.getUserId());
            authedList = mapper.selectResrceById(authGroupVO);
        }

        return makeTreeData(list, authedList);
    }

    /** 아이디 기준 리소스 정보 저장 - 예외관리 */
    @Override
    public int saveResrceById(AuthorExceptVO[] authorExceptVOs, SessionInfoVO sessionInfoVO) {

        //1.화면에서 체크한 경우
        //1.1 그룹에 있을 경우 : 제외 삭제
        //1.2 그룹에 없을 경우 : 포함으로 등록
        //2.화면에서 체크를 해제한 경우
        //2.1 그룹에 있을 경우 : 제외로 등록
        //2.2 그룹에 없을 경우 : 포함으로 삭제

        //로그인 아이디 그룹에 리소스를 모두 조회
        DefaultMap<String> resMap = new DefaultMap<String>();
        if(authorExceptVOs.length > 0 ) {
            List<DefaultMap<String>> list = mapper.selectResrceByGrp(authorExceptVOs[0].getUserId());
            for(DefaultMap<String> item : list) {
                resMap.put(item.getStr("resrceCd"), "");
            }
        }

        int procCnt = 0;
        String insertDt = currentDateTimeString();
        for(AuthorExceptVO authorExceptVO: authorExceptVOs) {

            // 기본값 세팅
            authorExceptVO.setRegDt(insertDt);
            authorExceptVO.setModDt(insertDt);
            authorExceptVO.setRegId(sessionInfoVO.getUserId());
            authorExceptVO.setModId(sessionInfoVO.getUserId());

            //1.화면에서 체크한 경우
            if(authorExceptVO.getStatus() == GridDataFg.INSERT) {
                //1.1 그룹에 있을 경우 : (기존)제외 삭제,
                if(resMap.containsKey(authorExceptVO.getResrceCd())) {
                    authorExceptVO.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                    authorExceptVO.setUseYn(UseYn.N);
                    procCnt += mapper.mergeAuthorExcept(authorExceptVO);
                }
                //1.2 그룹에 없을 경우 : 포함으로 등록
                else {
                    authorExceptVO.setIncldExcldFg(IncldExcldFg.INCLUDE);
                    authorExceptVO.setUseYn(UseYn.Y);
                    procCnt += mapper.mergeAuthorExcept(authorExceptVO);
                }
            }
            //2.화면에서 체크를 해제한 경우
            else if(authorExceptVO.getStatus() == GridDataFg.DELETE) {
                //2.1 그룹에 있을 경우 : 제외로 등록
                if(resMap.containsKey(authorExceptVO.getResrceCd())) {
                    authorExceptVO.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                    authorExceptVO.setUseYn(UseYn.Y);
                    procCnt += mapper.mergeAuthorExcept(authorExceptVO);
                }
                //2.2 그룹에 없을 경우 : 포함으로 삭제
                else {
                    authorExceptVO.setIncldExcldFg(IncldExcldFg.INCLUDE);
                    authorExceptVO.setUseYn(UseYn.N);
                    procCnt += mapper.mergeAuthorExcept(authorExceptVO);
                }
            }
        }

        if(procCnt == authorExceptVOs.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

}
