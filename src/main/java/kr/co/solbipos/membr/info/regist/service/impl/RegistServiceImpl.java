package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.ObjectUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("registService")
@Transactional
public class RegistServiceImpl implements RegistService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RegistMapper mapper;


    /** 등록매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectRgstrStore(SessionInfoVO sessionInfoVO) {

        // 회원정보 등록시 등록매장의 콤보박스 내용 조회
        // 본사 : 해당 본사에 소속된 매장만 조회한다.
        // 매장 : 해당 매장만 표시
        HqManageVO hqVO = new HqManageVO();

        hqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.selectRgstrStore(hqVO);
    }

    /** 회원등급 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectMembrClassList(SessionInfoVO sessionInfoVO) {

        MembrClassVO membrClassVO = new MembrClassVO();

        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        return mapper.selectMemberClassList(membrClassVO);
    }

    @Override
    public int insertRegistMember(RegistVO registVO) {
        return mapper.insertRegistMember(registVO);
    }

    @Override
    public RegistVO selectMember(RegistVO registVO) {
        return mapper.selectMember(registVO);
    }

    @Override
    public <E> List<E> selectMembers(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.selectMembers(registVO);
    }

    @Override
    public int updateMember(RegistVO registVO) {
        return mapper.updateMember(registVO);
    }

    @Override
    public int deleteMember(RegistVO registVO) {
        return mapper.deleteMember(registVO);
    }

    @Override
    public int insertMembrCard(RegistVO registVO) {
        return mapper.insertMembrCard(registVO);
    }

    @Override
    public int updateMembrCard(RegistVO registVO) {
        return mapper.updateMembrCard(registVO);
    }

    @Override
    public int saveRegistMember(RegistVO registVO) {
        RegistVO chkR = selectMember(registVO);
        int result = 0;
        // 없는 회원이면 신규 저장
        if(ObjectUtil.isEmpty(chkR)) {
            result = insertRegistMember(registVO);
            if(result == 1 && (!StringUtil.isEmpties(registVO.getMembrCardNo()))) {
                // 회원카드 등록
                insertMembrCard(registVO);
            }
        }
        // 있는 회원이면 수정
        else {
            result = updateMember(registVO);
            if(result == 1) {
                // 회원카드 수정
                updateMembrCard(registVO);
            }
        }
        return result;
    }
}
