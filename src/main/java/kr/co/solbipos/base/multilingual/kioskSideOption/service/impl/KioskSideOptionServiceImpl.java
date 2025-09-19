package kr.co.solbipos.base.multilingual.kioskSideOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionService;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.impl.KioskKeyMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskSideOptionServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.20  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("KioskSideOptionService")
public class KioskSideOptionServiceImpl  implements KioskSideOptionService {

    private final KioskSideOptionMapper kioskSideOptionMapper;
    private final MessageService messageService;
    private final KioskKeyMapMapper kioskKeyMapMapper;

    @Autowired
    public KioskSideOptionServiceImpl(KioskSideOptionMapper kioskSideOptionMapper, MessageService messageService, KioskKeyMapMapper kioskKeyMapMapper) {

        this.kioskSideOptionMapper = kioskSideOptionMapper;
        this.messageService = messageService;
        this.kioskKeyMapMapper = kioskKeyMapMapper;
    }

    /** 키오스크(카테고리명) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getKioskCategoryList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getKioskCategoryList(kioskSideOptionVO);
    }

    /** 키오스크(카테고리명) 영문, 중문, 일문 저장 */
    @Override
    public int saveKioskCategory(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveKioskCategory(kioskSideOptionVO);
        }

        // 키오스크 카테고리 TX 데이터 변경처리 PKG 호출(맘스터치)
        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        kioskKeyMapVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        kioskKeyMapVO.setStoreCd("");
        kioskKeyMapVO.setTuClsType("");
        kioskKeyMapVO.setRegId(sessionInfoVO.getUserId());
        kioskKeyMapMapper.updateKioskClsMomsLsm(kioskKeyMapVO);

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 키오스크중분류(카테고리명) 키맵그룹 콤보박스 조회(중분류 사용 키맵그룹만 조회) */
    @Override
    public List<DefaultMap<String>> getKioskTuClsTypeComboList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getKioskTuClsTypeComboList(kioskSideOptionVO);
    }

    /** 키오스크중분류(카테고리명) 카테고리(대분류) 콤보박스 조회 */
    @Override
    public List<DefaultMap<String>> getKioskCategoryComboList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getKioskCategoryComboList(kioskSideOptionVO);
    }

    /** 키오스크중분류(카테고리명) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getKioskMClsList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getKioskMClsList(kioskSideOptionVO);
    }

    /** 키오스크중분류(카테고리명) 영문, 중문, 일문 저장 */
    @Override
    public int saveKioskMCls(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveKioskMCls(kioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 사이드(선택그룹명) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSideSdselGrpList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getSideSdselGrpList(kioskSideOptionVO);
    }

    /** 사이드(선택그룹명) 영문, 중문, 일문 저장 */
    @Override
    public int saveSideSdselGrp(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveSideSdselGrp(kioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 사이드(선택분류명) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSideSdselClassList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getSideSdselClassList(kioskSideOptionVO);
    }

    /** 사이드(선택분류명) 영문, 중문, 일문 저장 */
    @Override
    public int saveSideSdselClass(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveSideSdselClass(kioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 옵션(그룹명) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOptionGrpList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getOptionGrpList(kioskSideOptionVO);
    }

    /** 옵션(그룹명) 영문, 중문, 일문 저장 */
    @Override
    public int saveOptionGrp(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveOptionGrp(kioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

    /** 옵션(옵션명) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOptionValList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getOptionValList(kioskSideOptionVO);
    }

    /** 옵션(옵션명) 영문, 중문, 일문 저장 */
    @Override
    public int saveOptionVal(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveOptionVal(kioskSideOptionVO);
        }

        /*if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }*/

        return result;
    }

}
