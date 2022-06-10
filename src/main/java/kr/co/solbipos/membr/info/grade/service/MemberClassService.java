package kr.co.solbipos.membr.info.grade.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

    public interface MemberClassService {

        /** 회원등급 조회 */
        DefaultMap<Object> getMember(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO);

        /** 회원등급설정 조회 */
        String getMemberClassList(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO);

        /** 등급체크 */
        int classInfoChk(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO);

        /** 회원정보 삭제 체크 */
        int deleteClassInfo(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO);

        /** 회원정보 삭제 */
       DefaultMap<Object> deleteClassInfoChk(MembrClassVO[] membrClassVOs, SessionInfoVO sessionInfoVO);

       /** 등급포인트 적립 저장 */
        int saveClassPointList(MembrClassPointVO[] membrClassPointVOs, SessionInfoVO sessionInfoVO);

        /** 회원등급설정 조회 */
        List<DefaultMap<String>> getMemberClassGridList(MembrClassVO membrClassVO, SessionInfoVO sessionInfoVO);

        /** 등급포인트 적립 저장 */
        int getMemberClassPointDel(MembrClassPointVO[] membrClassPointVOs, SessionInfoVO sessionInfoVO);

//        List<DefaultMap<String>> getMemberClassPoint(SessionInfoVO sessionInfoVO);
}
