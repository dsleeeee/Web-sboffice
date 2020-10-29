package kr.co.solbipos.membr.info.grade.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;

import java.util.ArrayList;
import java.util.List;

    public interface MemberClassService {

        DefaultMap<Object> getMember(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO);

        String getMemberClassList(SessionInfoVO sessionInfoVO);

        int classInfoChk(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO);

        int deleteClassInfo(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO);

        DefaultMap<Object> deleteClassInfoChk(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO);

        int saveClassPointList(MembrClassPointVO[] membrClassPointVOs, SessionInfoVO sessionInfoVO);

        List<DefaultMap<String>> getMemberClassGridList(SessionInfoVO sessionInfoVO);

        int getMemberClassPointDel(MembrClassPointVO[] membrClassPointVOs, SessionInfoVO sessionInfoVO);

//        List<DefaultMap<String>> getMemberClassPoint(SessionInfoVO sessionInfoVO);
}
