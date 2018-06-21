package kr.co.solbipos.pos.confg.func.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.func.service.FuncVO;

/**
 * 포스관리 > POS 설정관리 > POS 기능정의
 *
 * @author 김지은
 */
@Mapper
public interface FuncMapper {
    /**
     * 기능구분 상세 조회
     *
     * @param type
     * @return
     */
    List<DefaultMap<String>> getFuncList(FuncVO funcVO);

    /**
     * 기능구분 상세 등록
     *
     * @param funcVO
     * @return
     */
    int insertFunc(FuncVO funcVO);

    /**
     * 기능구분 상세 수정
     *
     * @param funcVO
     * @return
     */
    int updateFunc(FuncVO funcVO);

    /**
     * 기능구분 상세 삭제
     * @param funcVO
     * @return
     */
    int deleteFunc(FuncVO funcVO);
}
