package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegistMapper {

    /**
     * 등록 매장 리스트 조회
     *
     * @return
     */
    List<DefaultMap<String>> selectRgstrStore();

    /**
     * 회원정보 리스트 조회
     *
     * @param registVO
     * @param <E>
     * @return
     */
    <E> List<E> selectMembers(RegistVO registVO);

    /**
     * 회원정보 조회
     *
     * @param registVO
     * @return
     */
    RegistVO selectMember(RegistVO registVO);

    /**
     * 회원정보 저장
     *
     * @param registVO
     * @return
     */
    int insertRegistMember(RegistVO registVO);

    /**
     * 회원정보 수정
     *
     * @param registVO
     * @return
     */
    int updateMember(RegistVO registVO);

    /**
     * 회원정보 삭제
     *
     * @param registVO
     * @return
     */
    int deleteMember(RegistVO registVO);

    /**
     * 회원카드 등록
     *
     * @param registVO
     * @return
     */
    int insertMembrCard(RegistVO registVO);

    /**
     * 회원카드 수정
     *
     * @param registVO
     * @return
     */
    int updateMembrCard(RegistVO registVO);
}
