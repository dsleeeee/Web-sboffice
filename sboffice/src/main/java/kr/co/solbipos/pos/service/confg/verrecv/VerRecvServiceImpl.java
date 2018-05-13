package kr.co.solbipos.pos.service.confg.verrecv;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.pos.domain.confg.verrecv.VerRecv;
import kr.co.solbipos.pos.persistence.confg.verrecv.VerRecvMapper;
import kr.co.common.data.structure.DefaultMap;

@Service
public class VerRecvServiceImpl implements VerRecvService{

    @Autowired
    VerRecvMapper mapper;

    @Override
    public List<DefaultMap<String>> selectVerList(VerRecv verRecv) {
        return mapper.selectVerList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreList(VerRecv verRecv) {
        return mapper.selectStoreList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreRecvList(VerRecv verRecv) {
        return mapper.selectStoreRecvList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreDtl(VerRecv verRecv) {
        return mapper.selectStoreDtl(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectVerStoreList(VerRecv verRecv) {
        return mapper.selectVerStoreList(verRecv);
    }
    
    @Override
    public List<DefaultMap<String>> selectVerStoreDtlList(VerRecv verRecv) {
        return mapper.selectVerStoreDtlList(verRecv);
    }
}
