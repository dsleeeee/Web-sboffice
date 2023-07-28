package kr.co.solbipos.membr.info.bbqMemberExcelUpload.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.bbqMemberExcelUpload.service.BbqMemberExcelUploadService;
import kr.co.solbipos.membr.info.bbqMemberExcelUpload.service.BbqMemberExcelUploadVO;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.service.impl.RegistMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BbqMemberExcelUploadServiceImpl.java
 * @Description : 회원관리 > 회원정보 > 회원엑셀업로드(BBQ)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.26  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("bbqMemberExcelUploadService")
@Transactional
public class BbqMemberExcelUploadServiceImpl implements BbqMemberExcelUploadService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final BbqMemberExcelUploadMapper bbqMemberExcelUploadMapper;
    private final RegistMapper registMapper;
    private final MessageService messageService;

    @Autowired
    public BbqMemberExcelUploadServiceImpl(BbqMemberExcelUploadMapper bbqMemberExcelUploadMapper, RegistMapper registMapper, MessageService messageService) {
        this.bbqMemberExcelUploadMapper = bbqMemberExcelUploadMapper;
        this.registMapper = registMapper;
        this.messageService = messageService;
    }

    /** 회원엑셀업로드(BBQ) 저장 */
    @Override
    public int memberExcelSave(BbqMemberExcelUploadVO[] bbqMemberExcelUploadVOs, RegistVO registVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for (BbqMemberExcelUploadVO bbqMemberExcelUploadVO : bbqMemberExcelUploadVOs) {

            registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
            registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            bbqMemberExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
            bbqMemberExcelUploadVO.setMembrNo(registMapper.getNewMemberNo(registVO));

            // NOT NULL 컬럼
            bbqMemberExcelUploadVO.setLunarYn("N");

            // match invalid variable
            String strTelNo = (bbqMemberExcelUploadVO.getMemberTelNo() == null || "".equals(bbqMemberExcelUploadVO.getMemberTelNo())) ? "01000000000" : bbqMemberExcelUploadVO.getMemberTelNo();
            bbqMemberExcelUploadVO.setTelNo(strTelNo);

            String strShortNo = (bbqMemberExcelUploadVO.getMemberShortNo() == null || "".equals(bbqMemberExcelUploadVO.getMemberShortNo())) ? "0000" : bbqMemberExcelUploadVO.getMemberShortNo();
            if ("0000".equals(strShortNo)) { // 단축번호
                bbqMemberExcelUploadVO.setShortNo( (strTelNo.length() > 4) ? StringUtils.right(strTelNo, 4) : "0000");
            } else {
                bbqMemberExcelUploadVO.setShortNo(strShortNo);
            }
            bbqMemberExcelUploadVO.setEmailAddr(bbqMemberExcelUploadVO.getMemberEmail());
            bbqMemberExcelUploadVO.setPostNo(bbqMemberExcelUploadVO.getMemberPostNo());
            bbqMemberExcelUploadVO.setAddr(bbqMemberExcelUploadVO.getMemberAddr());
            bbqMemberExcelUploadVO.setAddrDtl(bbqMemberExcelUploadVO.getMemberAddrDtl());
            // 결혼여부 선택값이 미혼이면 결혼기념일 null
            if(bbqMemberExcelUploadVO.getWeddingYn() == WeddingYn.N) {
                bbqMemberExcelUploadVO.setWeddingday(null);
            } else {
                if(bbqMemberExcelUploadVO.getWeddingday() != null || "".equals(bbqMemberExcelUploadVO.getWeddingday()) ) {
                    bbqMemberExcelUploadVO.setWeddingday(bbqMemberExcelUploadVO.getWeddingday().replaceAll("-", ""));
                }
            }
            bbqMemberExcelUploadVO.setRegDt(dt);
            bbqMemberExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            bbqMemberExcelUploadVO.setModDt(dt);
            bbqMemberExcelUploadVO.setModId(sessionInfoVO.getUserId());

            if (bbqMemberExcelUploadVO.getStatus() == GridDataFg.INSERT) {

                if(bbqMemberExcelUploadVO.getMembrNm() == null || bbqMemberExcelUploadVO.getMembrNm() == ""){
                    bbqMemberExcelUploadVO.setMembrNm(bbqMemberExcelUploadVO.getMembrNo());
                }
                result = bbqMemberExcelUploadMapper.insertMember(bbqMemberExcelUploadVO);

                if(result == 1){
                    // 회원포인트 저장
                    result = bbqMemberExcelUploadMapper.insertMemberPoint(bbqMemberExcelUploadVO);
                }

                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return result;
    }
}
