package kr.co.solbipos.application.service.sample;

import java.util.List;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.domain.sample.TestTable;

/**
 * 샘플
 * 
 * @author 정용길
 *
 */
public interface SampleService {

    <E> List<E> selectSample(String param);

    <E> List<E> selectDdSum();

    <E> List<E> selectDdlTrdtlT(Integer rnum);

    <E> List<E> selectCommonCodeList(String comCdFg);


    <E> List<E> selectDdlTrdtlTest(SslTrdtlT sslTrdtlT);

    <E> List<E> selectColumns(String table);

    <E> List<E> selectTestTable(TestTable testTable);
}
