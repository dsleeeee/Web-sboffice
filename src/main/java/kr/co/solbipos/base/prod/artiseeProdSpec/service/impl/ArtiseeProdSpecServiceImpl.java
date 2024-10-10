package kr.co.solbipos.base.prod.artiseeProdSpec.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.artiseeProdSpec.service.ArtiseeProdSpecService;
import kr.co.solbipos.base.prod.artiseeProdSpec.service.ArtiseeProdSpecVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ArtiseeProdSpecServiceImpl.java
 * @Description : 보나비 > 상품관리 > 아티제-상품특성관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("artiseeProdSpecService")
@Transactional
public class ArtiseeProdSpecServiceImpl implements ArtiseeProdSpecService {
    private final ArtiseeProdSpecMapper artiseeProdSpecMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ArtiseeProdSpecServiceImpl(ArtiseeProdSpecMapper artiseeProdSpecMapper, MessageService messageService) {
        this.artiseeProdSpecMapper = artiseeProdSpecMapper;
        this.messageService = messageService;
    }

    /** 아티제-상품특성관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getArtiseeProdSpecList(ArtiseeProdSpecVO artiseeProdSpecVO, SessionInfoVO sessionInfoVO) {

        artiseeProdSpecVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseeProdSpecMapper.getArtiseeProdSpecList(artiseeProdSpecVO);
    }

    /** 아티제-상품특성관리 - 적용 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getArtiseeProdSpecProdList(ArtiseeProdSpecVO artiseeProdSpecVO, SessionInfoVO sessionInfoVO) {

        artiseeProdSpecVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseeProdSpecMapper.getArtiseeProdSpecProdList(artiseeProdSpecVO);
    }

    /** 아티제-상품특성관리 - 미적용 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getArtiseeProdSpecNoProdList(ArtiseeProdSpecVO artiseeProdSpecVO, SessionInfoVO sessionInfoVO) {

        artiseeProdSpecVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseeProdSpecMapper.getArtiseeProdSpecNoProdList(artiseeProdSpecVO);
    }

    /** 아티제-상품특성관리 - 상품 저장 */
    @Override
    public int getArtiseeProdSpecProdSaveInsert(ArtiseeProdSpecVO[] artiseeProdSpecVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(ArtiseeProdSpecVO artiseeProdSpecVO : artiseeProdSpecVOs) {

            artiseeProdSpecVO.setRegDt(currentDt);
            artiseeProdSpecVO.setRegId(sessionInfoVO.getUserId());
            artiseeProdSpecVO.setModDt(currentDt);
            artiseeProdSpecVO.setModId(sessionInfoVO.getUserId());

            artiseeProdSpecVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = artiseeProdSpecMapper.getArtiseeProdSpecProdSaveInsert(artiseeProdSpecVO);
        }

        return procCnt;
    }

    /** 아티제-상품특성관리 - 상품 삭제 */
    @Override
    public int getArtiseeProdSpecProdSaveDelete(ArtiseeProdSpecVO[] artiseeProdSpecVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(ArtiseeProdSpecVO artiseeProdSpecVO : artiseeProdSpecVOs) {

            artiseeProdSpecVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt = artiseeProdSpecMapper.getArtiseeProdSpecProdSaveDelete(artiseeProdSpecVO);
        }

        return procCnt;
    }
}