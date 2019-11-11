package kr.co.solbipos.pos.license.instlManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstlManageMapper {
    /** 설치요청 목록 조회 */
    List<DefaultMap<String>> getInstlRequestList(InstlManageVO instlManageVO);

    /** 설치요청 목록 상세 */
    List<DefaultMap<String>> getInstlRequestDtl(InstlManageVO instlManageVO);
}
