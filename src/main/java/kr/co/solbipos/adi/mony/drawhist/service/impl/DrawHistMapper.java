package kr.co.solbipos.adi.mony.drawhist.service.impl;

import kr.co.solbipos.adi.mony.drawhist.service.DrawHistVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DrawHistMapper.java
 * @Description : 부가서비스 > 금전처리 > 돈통오픈기록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  김태수      최초생성
 *
 * @author NHN한국사이버결제 KCP 김태수
 * @since 2018.08.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DrawHistMapper {
    public <E> List<E> selectDrawHist(DrawHistVO DrawHistVO);
}
