package kr.co.solbipos.sys.admin.funcKey.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.funcKey.service.FuncKeyService;
import kr.co.solbipos.sys.admin.funcKey.service.FuncKeyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : FuncKeyServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > 기능키적용버전등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.09  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.09
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("FuncKeyServiceImpl")
@Transactional
public class FuncKeyServiceImpl implements FuncKeyService {

    private final  FuncKeyMapper funcKeyMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    public FuncKeyServiceImpl(FuncKeyMapper funcKeyMapper, MessageService messageService) {
        this.funcKeyMapper = funcKeyMapper;
        this.messageService = messageService;
    }

    /** 기능구분 그룹코드 조회(콤보박스용) */
    @Override
    public List<DefaultMap<String>> getNmcodeGrpCdList(){

        return funcKeyMapper.getNmcodeGrpCdList();
    }

    /** 기능키적용버전 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFuncKeyList(FuncKeyVO funcKeyVO, SessionInfoVO sessionInfoVO) {
        return funcKeyMapper.getFuncKeyList(funcKeyVO);
    }

    /** 기능키적용버전 저장*/
    @Override
    public int saveFuncKey(FuncKeyVO[] funcKeyVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        for(FuncKeyVO funcKeyVO: funcKeyVOs){

            funcKeyVO.setRegDt(dt);
            funcKeyVO.setRegId(sessionInfoVO.getUserId());
            funcKeyVO.setModDt(dt);
            funcKeyVO.setModId(sessionInfoVO.getUserId());

            result += funcKeyMapper.saveFuncKey(funcKeyVO);
        }
        if(result == funcKeyVOs.length){
            return result;
        } else{
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /**기능키적용버전 삭제 */
    @Override
    public int deleteFuncKey(FuncKeyVO[] funcKeyVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for(FuncKeyVO funcKeyVO: funcKeyVOs){
            result += funcKeyMapper.deleteFuncKey(funcKeyVO);
        }
        return result;
    }

}
