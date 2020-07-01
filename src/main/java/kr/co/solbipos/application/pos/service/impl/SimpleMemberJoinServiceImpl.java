package kr.co.solbipos.application.pos.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.pos.service.MemberVO;
import kr.co.solbipos.application.pos.service.SimpleMemberJoinService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


/**
 * @Class Name : SimpleMemberJoinServiceImpl.java
 * @Description : POS 화면에서 간편 회원가입
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("SimpleMemberJoinService")
public class SimpleMemberJoinServiceImpl implements SimpleMemberJoinService{

    @Autowired
    SimpleMemberJoinMapper mapper;

    @Autowired
    MessageService messageService;

    /** 환경변수 값 체크 */
    @Override
    public String getEnvstVal(StoreEnvVO storeEnvVO) {
        return mapper.getEnvstVal(storeEnvVO);
    }

    /** 회원 저장 */
    @Override
    public int save(MemberVO memberVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

//        memberVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());  //회원소속코드
        // 단독매장일때 MEMBR_ORGN_CD에 매장코드가 들어가 생기는 문제수정
        memberVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());  //회원소속코드

        // 회원정보 중복체크
        List<DefaultMap<String>> memberInfo = mapper.chkDuplicateMember(memberVO);

        if(memberInfo.size() > 0){
            for(int i=0; i<memberInfo.size(); i++){
                if("N".equals(memberInfo.get(i).getStr("useYn"))){
                    throw new JsonException(Status.FAIL, messageService.get("applicatoan.pos.simpleMemberJoin.duplidateAndUseYn"));
                }
            }
            throw new JsonException(Status.FAIL, messageService.get("applicatoan.pos.simpleMemberJoin.duplidate"));
        }

        // 신규회원번호 조회
        String membrNo = mapper.getNewMembrNo(sessionInfoVO);

        memberVO.setMembrNo(membrNo);                           //회원번호
        //TODO 2018.08.14 안동관. 현재는 회원분류코드를 하드코딩으로 넣고 있으나 나중에는 화면에서 받아서 처리해야함.
        memberVO.setMembrClassCd("000");                        //회원분류코드
        memberVO.setMembrCardNo(memberVO.getTelNo());           //회원카드번호
        memberVO.setRegStoreCd(sessionInfoVO.getOrgnCd());      //등록매장코드
        memberVO.setLunarYn("Y");                               //음력여부 Y:음력 N:양력
        memberVO.setShortNo(memberVO.getTelNo().substring(memberVO.getTelNo().length()-4, memberVO.getTelNo().length()));
        memberVO.setWeddingYn("N");                             //결혼여부 기혼:Y 미혼:N
        memberVO.setEmailRecvYn("Y");                           //이메일수신여부 Y:수신 N:거부
        memberVO.setSmsRecvYn("Y");                             //SMS수신여부 Y:수신 N:거부
        memberVO.setUseYn("Y");                                 //사용여부 Y:사용 N:사용안함
        memberVO.setRegDt(dt);                                  //등록일시
        memberVO.setRegId(sessionInfoVO.getUserId());           //등록아이디
        memberVO.setModDt(dt);                                  //수정일시
        memberVO.setModId(sessionInfoVO.getUserId());           //수정아이디

        // 회원등록
        result = mapper.insertMember(memberVO);

        if(memberVO.getMembrFg().equals("prepaid") || memberVO.getMembrFg().equals("prepostpaid")){

            // 선불회원 등록 (자점회원)
            result = mapper.registMemberPrepaid(memberVO);

        }

        if(memberVO.getMembrFg().equals("postpaid")|| memberVO.getMembrFg().equals("prepostpaid")){

            // 후불회원 등록
            result = mapper.registMemberPostpaid(memberVO);
        }

        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

}
