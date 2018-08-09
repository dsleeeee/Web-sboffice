package kr.co.solbipos.application.com.fixing.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.com.fixing.service.FixingService;
import kr.co.solbipos.application.com.fixing.service.FixingVO;

/**
* @Class Name : FixingServiceImpl.java
* @Description : 어플리케이션 > 공통 > 고정메뉴
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
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("fixingService")
public class FixingServiceImpl implements FixingService {

    @Autowired
    FixingMapper fixingMapper;

    @Override
    public int saveFixing(FixingVO fixingVO, String userId) {

        int result = 0;

        fixingVO.setUserId(userId);
        fixingVO.setRegDt(currentDateTimeString());
        fixingVO.setRegId(userId);
        // 삭제후 재등록
        if ( fixingMapper.deleteFixing(fixingVO) > -1 ) {
            if ( fixingVO.getResrceCds() != null && fixingVO.getResrceCds().length > 0 ) {
                result = fixingMapper.insertFixing(fixingVO);
            }
        }
        return result;
    }

}
