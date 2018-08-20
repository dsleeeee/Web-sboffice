package kr.co.solbipos.base.prod.vendr.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.base.prod.vendr.service.VendrService;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;

/**
 * @Class Name : TouchkeyServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 거래처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.08  노해민      최초생성
 *
 * @author NHN한국사이버결제 KCP 노해민
 * @since 2018. 08.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("vendrService")
public class VendrServiceImpl implements VendrService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    MessageService messageService;
    @Autowired
    private VendrMapper mapper;

    @Override
    public <E> List<E> getHqVendrList(VendrVO vendrVO) {
        return mapper.getHqVendrList(vendrVO);
    }

    @Override
    public <E> List<E> getMsVendrList(VendrVO vendrVO) {
        return mapper.getMsVendrList(vendrVO);
    }

}
