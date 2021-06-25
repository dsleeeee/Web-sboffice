package kr.co.solbipos.base.prod.info.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.info.service.InfoService;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmm.service.impl.IostockCmmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : InfoServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 분류코드등록(상품기초정보등록)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.03
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("infoService")
public class InfoServiceImpl implements InfoService {


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final InfoMapper mapper;
    private final IostockCmmMapper iostockCmmMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public InfoServiceImpl(InfoMapper mapper, IostockCmmMapper iostockCmmMapper, MessageService messageService) {
        this.mapper = mapper;
        this.iostockCmmMapper = iostockCmmMapper;
        this.messageService = messageService;
    }

    /** 분류 조회 */
    @Override
    public List<ProductClassVO> getProdClsList(ProductClassVO prodClsVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> classList;

        prodClsVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            prodClsVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            classList = mapper.getHqProdClsList(prodClsVO);
            return makeTreeData(classList);
        }
        // 매장
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodClsVO.setStoreCd(sessionInfoVO.getOrgnCd());
            classList = mapper.getStoreProdClsList(prodClsVO);
            return makeTreeData(classList);
        }
        // 기타 시스템, 대리점 권한으로 접근시
        else {
            // 본사와 매장의 권한으로만 접근 가능한 메뉴임.
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }
    }

    /** 트리 데이터 생성 */
    public List<ProductClassVO> makeTreeData(List<DefaultMap<String>> lists) {
        List<ProductClassVO> prodClsVOs = new ArrayList<ProductClassVO>();

        for(DefaultMap<String> list : lists){
            ProductClassVO prodClsVO = new ProductClassVO();
            prodClsVO.setHqOfficeCd(list.getStr("hqOfficeCd"));
            prodClsVO.setStoreCd(list.getStr("storeCd"));
            prodClsVO.setProdClassCd(list.getStr("prodClassCd"));
            prodClsVO.setProdClassNm(list.getStr("prodClassNm"));
            prodClsVO.setpProdClassCd(list.getStr("pProdClassCd"));
            prodClsVO.setItems(new ArrayList<ProductClassVO>());
            prodClsVOs.add(prodClsVO);
        }

        Map<String, ProductClassVO> hm = new LinkedHashMap<String, ProductClassVO>();

        ProductClassVO child;
        ProductClassVO parent;

        for(ProductClassVO prodClsVO : prodClsVOs){
            if(!hm.containsKey(prodClsVO.getProdClassCd())) {
                hm.put(prodClsVO.getProdClassCd(), prodClsVO);
            }

            child = hm.get(prodClsVO.getProdClassCd());

            if(hm.containsKey(prodClsVO.getpProdClassCd())){
                parent = hm.get(prodClsVO.getpProdClassCd());
                parent.getItems().add(child);
            }
        }

        List<ProductClassVO> returnData = new ArrayList<ProductClassVO>();
        for(ProductClassVO prodClsVO : hm.values()) {


            if(prodClsVO.getpProdClassCd() == null || "".equals(prodClsVO.getpProdClassCd())) {
                returnData.add(prodClsVO);
            }
        }
        return returnData;
    }

    /** 분류 저장 */
    @Override
    public int productClassSave(ProductClassVO[] productClassVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String procResult;
        String dt = currentDateTimeString();

        for(ProductClassVO productClassVO : productClassVOs){

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {   // 본사
                productClassVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {    // 매장
                productClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }

            productClassVO.setRegDt(dt);
            productClassVO.setRegId(sessionInfoVO.getUserId());
            productClassVO.setModDt(dt);
            productClassVO.setModId(sessionInfoVO.getUserId());
            productClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            // 새 분류코드 생성시, 분류코드와 Level값 조회
            if("".equals(productClassVO.getProdClassCd()) || productClassVO.getProdClassCd() == null) {
                String prodClassCd = "";
                prodClassCd = mapper.getClsCd(productClassVO);
                productClassVO.setProdClassCd(prodClassCd);

                if("00000".equals(productClassVO.getpProdClassCd())){
                    productClassVO.setClsLevelCd("1");
                }else{
                    productClassVO.setClsLevelCd(mapper.getProdClsLevel(productClassVO));
                }
            }

            // 상위 분류까지 신규로 만들 경우, 상위 분류코드 조회 (0레벨 제외)
            if(("".equals(productClassVO.getpProdClassCd()) || productClassVO.getpProdClassCd() == null) && productClassVO.getLevel() != 0 ) {
                String pprodClassCd = "";
                pprodClassCd = mapper.getPProdClsCd(productClassVO);
                productClassVO.setpProdClassCd(pprodClassCd);
            }

            if(productClassVO.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertCls(productClassVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = mapper.insertClsToStore(productClassVO);
                }
            }
            else if(productClassVO.getStatus() == GridDataFg.UPDATE) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procCnt += mapper.updateHqCls(productClassVO);
                    procResult = mapper.updateClsToStore(productClassVO);

                } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    procCnt += mapper.updateStoreCls(productClassVO);
                }
            }
            else if(productClassVO.getStatus() == GridDataFg.DELETE) {
                // 해당 분류로 상품이 등록되어있으면 삭제 불가능
                int chkProdCnt = mapper.chkProdCnt(productClassVO);

                //본사권한인 경우, 매장에서도 해당 분류로 상품이 등록되어 있는지 확인 추가 : 2019.08.12_이다솜
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    chkProdCnt += mapper.chkProdCntinStore(productClassVO);
                }

                if(chkProdCnt > 0) {
                    throw new JsonException(Status.FAIL, messageService.get("info.delete.fail"));
                }
                else {
                    procCnt += mapper.deleteCls(productClassVO);

                    // 본사에서 접속시
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                        procResult = mapper.deleteClsToStore(productClassVO);
                    }
                }
            }
        }

        if(procCnt == productClassVOs.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 해당 분류로 등록된 상품 조회 */
    @Override
    public int chkProdCnt(ProductClassVO productClassVO, SessionInfoVO sessionInfoVO){

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {   // 본사
            productClassVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {    // 매장
            productClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
        }
        productClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 해당 분류로 상품이 등록되어있으면 삭제 불가능
        int chkProdCnt = mapper.chkProdCnt(productClassVO);

        //본사권한인 경우, 매장에서도 해당 분류로 상품이 등록되어 있는지 확인 추가 : 2019.08.12_이다솜
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            chkProdCnt += mapper.chkProdCntinStore(productClassVO);
        }

        if(chkProdCnt > 0) {
            throw new JsonException(Status.FAIL, messageService.get("info.delete.fail"));
        }else{
            return chkProdCnt;
        }
    }

    /** 상품분류정보관리(3단계) - 분류 조회 */
    @Override
    public  List<DefaultMap<String>> getProdClass(ProductClassVO productClassVO, SessionInfoVO sessionInfoVO){

        productClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        productClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            productClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getProdClass(productClassVO);
    }

}
