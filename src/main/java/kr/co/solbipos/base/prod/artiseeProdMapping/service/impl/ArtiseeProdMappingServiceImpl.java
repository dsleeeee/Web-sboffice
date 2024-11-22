package kr.co.solbipos.base.prod.artiseeProdMapping.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.ArtiseeProdMappingService;
import kr.co.solbipos.base.prod.artiseeProdMapping.service.ArtiseeProdMappingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ArtiseeProdMappingServiceImpl.java
 * @Description : 보나비 - 상품관리 - 아티제상품코드맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.27  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("ArtiseeProdMappingService")
public class ArtiseeProdMappingServiceImpl implements ArtiseeProdMappingService {

    private final ArtiseeProdMappingMapper artiseeProdMappingMapper;

    @Autowired
    public ArtiseeProdMappingServiceImpl(ArtiseeProdMappingMapper artiseeProdMappingMapper) {
        this.artiseeProdMappingMapper = artiseeProdMappingMapper;
    }

    /** 맵핑정보 - 조회 */
    @Override
    public List<DefaultMap<String>> getMapStrList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO) {

        artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        artiseeProdMappingVO.setSessionId(sessionInfoVO.getSessionId());
        artiseeProdMappingVO.setUserId(sessionInfoVO.getUserId());

        // TMP테이블 삭제
        artiseeProdMappingMapper.deleteMappingTmp01(artiseeProdMappingVO);
        artiseeProdMappingMapper.deleteMappingTmp02(artiseeProdMappingVO);

        // 맵핑스트링 저장
        artiseeProdMappingMapper.insertMappingString(artiseeProdMappingVO);

        return artiseeProdMappingMapper.getMapStrList(artiseeProdMappingVO);
    }

    /** 상품정보 - 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO) {

        artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        artiseeProdMappingVO.setSessionId(sessionInfoVO.getSessionId());

        return artiseeProdMappingMapper.getProdList(artiseeProdMappingVO);
    }

    /** 맵핑정보 - 삭제 */
    @Override
    public int getDeleteMappingProd(ArtiseeProdMappingVO[] artiseeProdMappingVOs, SessionInfoVO sessionInfoVO) {
        int prodCnt = 0;

        for(ArtiseeProdMappingVO artiseeProdMappingVO : artiseeProdMappingVOs) {

            artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            prodCnt = artiseeProdMappingMapper.getDeleteMappingProd(artiseeProdMappingVO);

        }

        return prodCnt;
    }

    /** 상품정보 - 등록 */
    @Override
    public int getRegProd(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        artiseeProdMappingVO.setModDt(currentDt);
        artiseeProdMappingVO.setModId(sessionInfoVO.getUserId());
        artiseeProdMappingVO.setRegDt(currentDt);
        artiseeProdMappingVO.setRegId(sessionInfoVO.getUserId());

        return artiseeProdMappingMapper.getSaveMappingProd(artiseeProdMappingVO);
    }


    /** 상품정보 - 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getProdExcelList(ArtiseeProdMappingVO artiseeProdMappingVO, SessionInfoVO sessionInfoVO) {
        artiseeProdMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return artiseeProdMappingMapper.getProdExcelList(artiseeProdMappingVO);
    }
}
