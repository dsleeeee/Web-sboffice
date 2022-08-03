package kr.co.solbipos.base.prod.foodAllergy.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.foodAllergy.service.FoodAllergyService;
import kr.co.solbipos.base.prod.foodAllergy.service.FoodAllergyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : FoodAllergyServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 식품 알레르기 정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("foodAllergyService")
@Transactional
public class FoodAllergyServiceImpl implements FoodAllergyService {
    private final FoodAllergyMapper foodAllergyMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public FoodAllergyServiceImpl(FoodAllergyMapper foodAllergyMapper) {
        this.foodAllergyMapper = foodAllergyMapper;
    }

    /** 식품 알레르기 정보관리 조회 */
    @Override
    public List<DefaultMap<Object>> getFoodAllergyList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO) {

        foodAllergyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        foodAllergyVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            foodAllergyVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return foodAllergyMapper.getFoodAllergyList(foodAllergyVO);
    }

    /** 브랜드 콤보박스 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getBrandComboList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO) {
        return foodAllergyMapper.getBrandComboList(foodAllergyVO);
    }

    /** 식품 알레르기 정보관리 저장 */
    @Override
    public int getFoodAllergySave(FoodAllergyVO[] foodAllergyVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(FoodAllergyVO foodAllergyVO : foodAllergyVOs) {

            foodAllergyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            foodAllergyVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                foodAllergyVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            foodAllergyVO.setModDt(currentDt);
            foodAllergyVO.setModId(sessionInfoVO.getUserId());

            if (foodAllergyVO.getStatus() == GridDataFg.INSERT) {
                foodAllergyVO.setRegDt(currentDt);
                foodAllergyVO.setRegId(sessionInfoVO.getUserId());

                // 재료코드 (자동 채번)
                String RecipesCd = foodAllergyMapper.getFoodAllergyRecipesCd(foodAllergyVO);
                foodAllergyVO.setRecipesCd(RecipesCd);

                procCnt = foodAllergyMapper.getFoodAllergySaveInsert(foodAllergyVO);

            } else if(foodAllergyVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = foodAllergyMapper.getFoodAllergySaveUpdate(foodAllergyVO);

            } else if (foodAllergyVO.getStatus() == GridDataFg.DELETE) {
                procCnt = foodAllergyMapper.getFoodAllergySaveDelete(foodAllergyVO);

                // 식품 알레르기 정보관리 저장 delete 시, 알레르기-상품 저장 delete
                procCnt = foodAllergyMapper.getFoodAllergySaveDeleteAll(foodAllergyVO);
            }
        }

        return procCnt;
    }

    /** 알레르기-상품 조회 */
    @Override
    public List<DefaultMap<Object>> getFoodAllergyDetailList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO) {

        foodAllergyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        foodAllergyVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            foodAllergyVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        foodAllergyVO.setUserId(sessionInfoVO.getUserId());

        return foodAllergyMapper.getFoodAllergyDetailList(foodAllergyVO);
    }

    /** 알레르기-상품 등록 팝업 - 상품조회 */
    @Override
    public List<DefaultMap<Object>> getFoodAllergyProdList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO) {

        foodAllergyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        foodAllergyVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            foodAllergyVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        foodAllergyVO.setUserId(sessionInfoVO.getUserId());

        return foodAllergyMapper.getFoodAllergyProdList(foodAllergyVO);
    }

    /** 알레르기-상품 저장 */
    @Override
    public int getFoodAllergyDetailSave(FoodAllergyVO[] foodAllergyVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(FoodAllergyVO foodAllergyVO : foodAllergyVOs) {
            foodAllergyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            foodAllergyVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                foodAllergyVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            foodAllergyVO.setModDt(currentDt);
            foodAllergyVO.setModId(sessionInfoVO.getUserId());

            if (foodAllergyVO.getStatus() == GridDataFg.INSERT) {
                foodAllergyVO.setRegDt(currentDt);
                foodAllergyVO.setRegId(sessionInfoVO.getUserId());

                procCnt = foodAllergyMapper.getFoodAllergyProdSaveInsert(foodAllergyVO);

            } else if (foodAllergyVO.getStatus() == GridDataFg.DELETE) {
                procCnt = foodAllergyMapper.getFoodAllergyProdSaveDelete(foodAllergyVO);
            }
        }

        return procCnt;
    }
}