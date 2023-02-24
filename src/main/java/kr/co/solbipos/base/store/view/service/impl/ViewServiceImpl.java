package kr.co.solbipos.base.store.view.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.common.enums.ConfgFg;
import kr.co.solbipos.base.store.view.service.CopyStoreEnvVO;
import kr.co.solbipos.base.store.view.service.VanConfigVO;
import kr.co.solbipos.base.store.view.service.ViewService;
import kr.co.solbipos.base.store.view.service.ViewVO;
import kr.co.solbipos.base.store.view.service.enums.StoreEnv;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;
import kr.co.solbipos.store.manage.storemanage.service.StorePosEnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
* @Class Name : ViewServiceImpl.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @ 2018.08.14  김영근      최초생성
 * @ 2018.11.20  김지은      angular 방식으로 변경
*
* @author nhn kcp 개발2팀 김영근
* @since 2018. 08.14
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("viewService")
public class ViewServiceImpl implements ViewService {

    private final ViewMapper viewMapper;
    private final MessageService messageService;
    private final String CORNER_USE_YN_ENVST_CD = "2028"; // 코너사용여부 환경변수


    /** Constructor Injection */
    @Autowired
    public ViewServiceImpl(ViewMapper viewMapper, MessageService messageService) {
        this.viewMapper = viewMapper;
        this.messageService = messageService;
    }

    /** 매장정보 리스트조회 */
    @Override
    public List<DefaultMap<String>> getViewList(ViewVO viewVO, SessionInfoVO sessionInfoVO){

        viewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (viewVO.getStoreHqBrandCd() == "" || viewVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (viewVO.getUserBrands() != null && !"".equals(viewVO.getUserBrands())) {
                    String[] userBrandList = viewVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        viewVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return viewMapper.getViewList(viewVO);
    }

    /** 매장정보 리스트 엑셀 조회 */
    @Override
    public List<DefaultMap<String>> getStoreListExcel(ViewVO viewVO, SessionInfoVO sessionInfoVO){

        viewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 매장브랜드가 없을 때 (매장브랜드가 '전체' 일때)
            if (viewVO.getStoreHqBrandCd() == "" || viewVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (viewVO.getUserBrands() != null && !"".equals(viewVO.getUserBrands())) {
                    String[] userBrandList = viewVO.getUserBrands().split(",");
                    if (userBrandList.length > 0) {
                        viewVO.setUserBrandList(userBrandList);
                    }
                }
            }
        }

        return viewMapper.getStoreListExcel(viewVO);
    }

    /** 매장정보 상세조회 */
    @Override
    public DefaultMap<String> getViewDetail(ViewVO viewVO)
    {
        return viewMapper.getViewDetail(viewVO);
    }

    /** 코너 사용여부 조회 */
    @Override
    public String getCornerUseYnVal(VanConfigVO vanConfgVO) {

        StoreEnvVO storeEnvVO = new StoreEnvVO();

        storeEnvVO.setStoreCd(vanConfgVO.getStoreCd());
        storeEnvVO.setEnvstCd(CORNER_USE_YN_ENVST_CD);

        return viewMapper.getCornerUseYnVal(storeEnvVO);
    }

    /**  포스별 승인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosTerminalList(VanConfigVO vanConfigVO) {
        return viewMapper.getPosTerminalList(vanConfigVO);
    }

    /**  코너별 승인 목록 조회 */
    @Override
    public List<DefaultMap<String>> getCornerTerminalList(VanConfigVO vanConfigVO) {
        return viewMapper.getCornerTerminalList(vanConfigVO);
    }

    /** 포스목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosList(StorePosEnvVO storePosEnvVO) {
        return viewMapper.getPosList(storePosEnvVO);
    }

    /** 매장환경복사 */
    @Override
    public int copyStoreEnv(CopyStoreEnvVO[] copyStoreEnvVOs, Map<String, Object> posParam,
        SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        if(copyStoreEnvVOs.length == 0) {
            throw new JsonException(Status.FAIL, messageService.get("storeView.no.copy.env"));
        }

        for(CopyStoreEnvVO copyStoreEnvVO : copyStoreEnvVOs) {

            copyStoreEnvVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            copyStoreEnvVO.setOriginalStoreCd(String.valueOf(posParam.get("originalStoreCd")));
            copyStoreEnvVO.setTargetStoreCd(String.valueOf(posParam.get("targetStoreCd")));
            copyStoreEnvVO.setRegDt(currentDt);
            copyStoreEnvVO.setRegId(sessionInfoVO.getUserId());
            copyStoreEnvVO.setModDt(currentDt);
            copyStoreEnvVO.setModId(sessionInfoVO.getUserId());

            String procResult = "";

            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.STORE_ENV) { // 매장환경 복사 (기본)
                // 매장 공통코드 복사
                procResult = viewMapper.copyStoreNmcode(copyStoreEnvVO);

                // 매장 기본환경 복사
                copyStoreEnvVO.setEnvstFg("00");
                procResult = viewMapper.copyStoreEnv(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.FOOD_ENV) { // 외식환경
                copyStoreEnvVO.setEnvstFg("01");
                procResult = viewMapper.copyStoreEnv(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.POS_ENV) { // 포스환경
                procResult = viewMapper.copyPosEnv(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.PROD) { // 상품(현재 사용안함)
                // 상품분류, 상품, 판매가격 함께 복사
                procResult = viewMapper.copyProduct(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.SALE_PRICE) { // 판매가격
                procResult = viewMapper.copySalePrice(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.POS_FNKEY) { // 포스기능키

                // 포스 기능키 삭제 후, 복사
                procResult = viewMapper.copyPosFnKey(copyStoreEnvVO);

                // 왼쪽 키 복사
                copyStoreEnvVO.setConfgFg(ConfgFg.FUNC_KEY_LEFT.getCode());
                procResult = viewMapper.copyPosFnKeyXML(copyStoreEnvVO);

                // 오른쪽 키 복사
                copyStoreEnvVO.setConfgFg(ConfgFg.FUNC_KEY_RIGHT.getCode());
                procResult = viewMapper.copyPosFnKeyXML(copyStoreEnvVO);

                // 배달메뉴 키 복사
                copyStoreEnvVO.setConfgFg(ConfgFg.FUNC_KEY_DELIVERY.getCode());
                procResult = viewMapper.copyPosFnKeyXML(copyStoreEnvVO);

                // 셀프키 복사
                copyStoreEnvVO.setConfgFg(ConfgFg.FUNC_KEY_SELF.getCode());
                procResult = viewMapper.copyPosFnKeyXML(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.POS_TOUCHKEY) { // 판매터치키

                // 터치키 클래스와 터치키 복사
                procResult = viewMapper.copyTouchKey(copyStoreEnvVO);

                // 터치키 XML 복사
                copyStoreEnvVO.setConfgFg(ConfgFg.TOUCH_KEY.getCode());
                procResult = viewMapper.copyTouchKeyXML(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.COUPON_CLASS) { // 쿠폰분류
                procResult = viewMapper.copyCouponClass(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.GIFT) { // 상품권
                procResult = viewMapper.copyGift(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.ACCOUNT) { // 입금/출금계정
                procResult = viewMapper.copyAccount(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.RECP_ORIGIN) { // 원산지
                procResult = viewMapper.copyRecpOrigin(copyStoreEnvVO);
            }
            if( copyStoreEnvVO.getNmcodeCd() == StoreEnv.FOOD_ALLERGY) { // 식품 알레르기
                procResult = viewMapper.copyFoodAllergy(copyStoreEnvVO);
            }
        }

        return result;
    }

    /** 매장환경 복사를 위한 정보 조회 */
    @Override
    public List<DefaultMap<String>> getStoreEnvInfoList(CopyStoreEnvVO copyStoreEnvVO, SessionInfoVO sessionInfoVO) {

        return viewMapper.getStoreEnvInfoList(copyStoreEnvVO);
    }

    /** 매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(ViewVO viewVO, SessionInfoVO sessionInfoVO){

        viewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(viewVO.getMomsEnvstVal().equals("1")) {
            // 매장브랜드 '전체' 일때
            if (viewVO.getStoreHqBrandCd() == "" || viewVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = viewVO.getUserBrands().split(",");
                viewVO.setUserBrandList(userBrandList);
            }
        }

        return viewMapper.getStoreList(viewVO);
    }

    /** 매장 판매터치키 콤보박스 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getStoreTouchKeyGrpCombo(ViewVO viewVO, SessionInfoVO sessionInfoVO){

        return viewMapper.getStoreTouchKeyGrpCombo(viewVO);
    }

    /** 매장 판매터치키 선택그룹 복사 */
    @Override
    public int copyStoreTouchKeyGrp(CopyStoreEnvVO[] copyStoreEnvVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();
        String procResult = "";

        for (CopyStoreEnvVO copyStoreEnvVO : copyStoreEnvVOs) {

            copyStoreEnvVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            copyStoreEnvVO.setRegDt(currentDt);
            copyStoreEnvVO.setRegId(sessionInfoVO.getUserId());
            copyStoreEnvVO.setModDt(currentDt);
            copyStoreEnvVO.setModId(sessionInfoVO.getUserId());

            // 전체복사
            if("".equals(copyStoreEnvVO.getTukeyGrpCd())){

                // 터치키 클래스와 터치키 전체복사
                procResult = viewMapper.copyTouchKey(copyStoreEnvVO);

                // 터치키 XML 전체복사
                copyStoreEnvVO.setConfgFg(ConfgFg.TOUCH_KEY.getCode());
                procResult = viewMapper.copyTouchKeyXML(copyStoreEnvVO);

                viewMapper.deleteTouchKeyGrpNm(copyStoreEnvVO);
                viewMapper.copyTouchKeyGrpNm(copyStoreEnvVO);

            }else{ // 선택그룹 복사

                // 터치키 클래스와 터치키 선택그룹 복사
                procResult = viewMapper.copyTouchKeyGrp(copyStoreEnvVO);

                // 터치키 선택그룹 XML 복사
                copyStoreEnvVO.setConfgFg(ConfgFg.TOUCH_KEY.getCode());
                procResult = viewMapper.copyTouchKeyGrpXML(copyStoreEnvVO);

                viewMapper.deleteTouchKeyGrpNm(copyStoreEnvVO);
                viewMapper.copyTouchKeyGrpNm(copyStoreEnvVO);
            }
        }

        return result;
    }

}
