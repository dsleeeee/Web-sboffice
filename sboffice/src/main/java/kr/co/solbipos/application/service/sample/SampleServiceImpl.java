package kr.co.solbipos.application.service.sample;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.domain.sample.TestTable;
import kr.co.solbipos.application.persistance.sample.SampleMapper;

@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    SampleMapper sampleMapper;

    @Override
    public <E> List<E> selectSample(String param) {
        return sampleMapper.selectSample(param);
    }

    @Override
    public <E> List<E> selectDdSum() {
        return sampleMapper.selectDdSum();
    }

    @Override
    public <E> List<E> selectDdlTrdtlT(Integer rnum) {
        return sampleMapper.selectDdlTrdtlT(rnum);
    }

    @Override
    public <E> List<E> selectCommonCodeList(String comCdFg) {
        return sampleMapper.selectCommonCodeList(comCdFg);
    }

    @Override
    public <E> List<E> selectDdlTrdtlTest(SslTrdtlT sslTrdtlT) {
        return sampleMapper.selectDdlTrdtlTest(sslTrdtlT);
    }

    @Override
    public <E> List<E> selectColumns(String table) {
        return sampleMapper.selectColumns(table);
    }

    @Override
    public <E> List<E> selectTestTable(TestTable testTable) {
        return sampleMapper.selectTestTable(testTable);
    }

}
