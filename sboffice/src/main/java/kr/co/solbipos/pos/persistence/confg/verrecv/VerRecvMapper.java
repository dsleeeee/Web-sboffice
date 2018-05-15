package kr.co.solbipos.pos.persistence.confg.verrecv;

import java.util.List;
import kr.co.solbipos.pos.domain.confg.verrecv.VerRecvVO;
import kr.co.common.data.structure.DefaultMap;

/**
 * 포스관리 > POS 설정관리 > POS 버전 수신현황
 * 
 * @author 김지은
 */
public interface VerRecvMapper {

    /**
     * 버전별수신현황 - 버전 목록 조회
     * 
     * @return
     */
    List<DefaultMap<String>> selectVerList(VerRecvVO verRecv);

    /**
     * 버전별수신현황 - 매장 목록 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectStoreList(VerRecvVO verRecv);

    /**
     * 매장별수신현황 - 수신매장 목록 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectStoreRecvList(VerRecvVO verRecv);

    /**
     * 매장별수신현황 - 매장 리스트 조회 - 매장상세
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectStoreDtl(VerRecvVO verRecv);

    /**
     * 버전별매장현황 - 버전 리스트 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectVerStoreList(VerRecvVO verRecv);

    /**
     * 버전별매장현황 - 매장 리스트 조회
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectVerStoreDtlList(VerRecvVO verRecv);
}
