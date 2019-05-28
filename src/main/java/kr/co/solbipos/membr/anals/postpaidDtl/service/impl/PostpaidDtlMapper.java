package kr.co.solbipos.membr.anals.postpaidDtl.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.postpaidDtl.service.PostpaidDtlVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PostpaidDtlMapper.java
 * @Description : 회원관리 > 회원분석 > 후불회원상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.05.21  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.05.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PostpaidDtlMapper {

    /** 후불 회원 외상, 입금 내역 상세*/
    List<DefaultMap<Object>> getPostpaidDtlMemberList(PostpaidDtlVO postpaidDtlVO);
}
