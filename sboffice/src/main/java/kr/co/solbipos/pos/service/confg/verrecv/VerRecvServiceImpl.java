package kr.co.solbipos.pos.service.confg.verrecv;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.pos.domain.confg.verrecv.VerRecvVO;
import kr.co.solbipos.pos.persistence.confg.verrecv.VerRecvMapper;
import kr.co.common.data.structure.DefaultMap;

@Service
public class VerRecvServiceImpl implements VerRecvService{

    @Autowired
    VerRecvMapper mapper;

    @Override
    public List<DefaultMap<String>> selectVerList(VerRecvVO verRecv) {
        return mapper.selectVerList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreList(VerRecvVO verRecv) {
        return mapper.selectStoreList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreRecvList(VerRecvVO verRecv) {
        return mapper.selectStoreRecvList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreDtl(VerRecvVO verRecv) {
        return mapper.selectStoreDtl(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectVerStoreList(VerRecvVO verRecv) {
        return mapper.selectVerStoreList(verRecv);
    }
    
    @Override
    public List<DefaultMap<String>> selectVerStoreDtlList(VerRecvVO verRecv) {
        return mapper.selectVerStoreDtlList(verRecv);
    }
}
