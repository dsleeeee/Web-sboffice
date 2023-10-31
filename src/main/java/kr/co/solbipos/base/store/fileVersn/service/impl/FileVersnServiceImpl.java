package kr.co.solbipos.base.store.fileVersn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.fileVersn.service.FileVersnService;
import kr.co.solbipos.base.store.fileVersn.service.FileVersnVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : FileVersnServiceImpl.java
 * @Description : 맘스터치 > 기타관리 > 점포별 파일 버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.17  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("fileVersnService")
@Transactional
public class FileVersnServiceImpl implements FileVersnService {
    private final FileVersnMapper fileVersnMapper;
    private final PopupMapper popupMapper;

    public FileVersnServiceImpl(FileVersnMapper fileVersnMapper, PopupMapper popupMapper) {
        this.fileVersnMapper = fileVersnMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getFileVersnList(FileVersnVO fileVersnVO, SessionInfoVO sessionInfoVO) {

        fileVersnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            fileVersnVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(fileVersnVO.getStoreCds()).equals("")) {
       		StoreVO storeVO = new StoreVO();
       		storeVO.setArrSplitStoreCd(CmmUtil.splitText(fileVersnVO.getStoreCds(), 3900));
            fileVersnVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
       	}

        return fileVersnMapper.getFileVersnList(fileVersnVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getFileVersnExcelList(FileVersnVO fileVersnVO, SessionInfoVO sessionInfoVO) {

        fileVersnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            fileVersnVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(fileVersnVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(fileVersnVO.getStoreCds(), 3900));
            fileVersnVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return fileVersnMapper.getFileVersnExcelList(fileVersnVO);
    }

}