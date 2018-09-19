package kr.co.solbipos.adi.mony.status.service.impl;

import kr.co.solbipos.adi.mony.status.service.StatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DrawHistMapper.java
 * @Description : 부가서비스 > 금전처리 > 금전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.09  김태수      최초생성
 *
 * @author NHN한국사이버결제 KCP 김태수
 * @since 2018.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StatusMapper {
	public <E> List<E> selectStatus(StatusVO statusVO);

	public <E> List<E> selectStoreStatus(StatusVO statusVO);

	public <E> List<E> selectAccntList(StatusVO statusVO);
}
