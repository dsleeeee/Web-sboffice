package kr.co.solbipos.base.prod.vendr.service.impl;

import kr.co.solbipos.base.prod.vendr.service.VendrVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class Name : TouchkeyMapper.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
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
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface VendrMapper {

    public <E> List<E> getHqVendrList(VendrVO vendrVO);

    public <E> List<E> getMsVendrList(VendrVO vendrVO);

}
