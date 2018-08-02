package kr.co.solbipos.application.com.bkmk.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.com.bkmk.service.BkmkService;
import kr.co.solbipos.application.com.bkmk.service.BkmkVO;

/**
* @Class Name : BkmkServiceImpl.java
* @Description : 어플리케이션 > 공통 > 즐겨찾기
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  노현수      최초생성
*
* @author 솔비포스 차세대개발실 노현수
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("bkmkService")
public class BkmkServiceImpl implements BkmkService {

    @Autowired
    BkmkMapper bkmkMapper;

    @Override
    public int saveBkmk( BkmkVO bkmkVO, String userId ) {

        int result = 0;

        bkmkVO.setUserId( userId );
        bkmkVO.setRegDt( currentDateTimeString() );
        bkmkVO.setRegId( userId );
        // 삭제후 재등록
        if ( bkmkMapper.deleteBkmk( bkmkVO ) > -1 ) {
            if ( bkmkVO.getResrceCds() != null && bkmkVO.getResrceCds().length > 0 ) {
                result = bkmkMapper.insertBkmk( bkmkVO );
            }
        }
        return result;
    }

}
