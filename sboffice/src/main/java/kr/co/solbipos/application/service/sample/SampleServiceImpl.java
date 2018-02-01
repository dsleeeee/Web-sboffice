package kr.co.solbipos.application.service.sample;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.sample.CcdCodemT;
import kr.co.solbipos.application.domain.sample.ScdShopmT;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.domain.sample.SslTrhdrT;
import kr.co.solbipos.application.domain.sample.TbMsStore;
import kr.co.solbipos.application.domain.sample.TestTable;
import kr.co.solbipos.application.domain.sample.TmpBoardT;
import kr.co.solbipos.application.domain.sample.TmpDragtT;
import kr.co.solbipos.application.persistence.sample.SampleMapper;

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

    @Override
    public <E> List<E> selectCode(CcdCodemT param) {
        return sampleMapper.selectCode(param);
    }

    @Override
    public <E> List<E> selectTreeMenu() {
      return sampleMapper.selectTreeMenu();
    }

    @Override
    public <E> List<E> getgroupGridSample() {
      return sampleMapper.getgroupGridSample();
    }

    @Override
    public <E> List<E> getDragNDropSample(TmpDragtT tmpDragt) {
      return sampleMapper.getDragNDropSample(tmpDragt);
    }

    @Override
    public void insertUpdateDragSample(TmpDragtT tmpDragt) {
      sampleMapper.insertUpdateDragSample(tmpDragt);
    }

    @Override
    public void insertBoardSample(TmpBoardT tmpBoardT) {
      sampleMapper.insertBoardSample(tmpBoardT);
    }

    @Override
    public void updateBoardSample(TmpBoardT tmpBoardT) {
      sampleMapper.updateBoardSample(tmpBoardT);
    }
    
    @Override
    public TmpBoardT getRecentBoardData() {
      return sampleMapper.getRecentBoardData();
    }

    @Override
    public <E> List<E> selectStore(TbMsStore tbMsStore) {
      return sampleMapper.selectStore(tbMsStore);
    }

    @Override
    public List<ResrceInfo> selectMenu1() {
        return sampleMapper.selectMenu1();
    }

    @Override
    public List<ResrceInfo> selectMenu2() {
        return sampleMapper.selectMenu2();
    }

    @Override
    public List<ResrceInfo> selectMenu3() {
        return sampleMapper.selectMenu3();
    }
    
    @Override
    public List<ResrceInfo> selectAuthMainMenu(HashMap<String, String> param) {
        return sampleMapper.selectAuthMainMenu(param);
    }

    @Override
    public <E> List<E> selectDdlTrhdrTest(SslTrhdrT sslTrhdrT) {
        return sampleMapper.selectDdlTrhdrTest(sslTrhdrT);
    }

    @Override
    public <E> List<E> selectDdlTrdtl2Test(SslTrhdrT sslTrhdrT) {
        return sampleMapper.selectDdlTrdtl2Test(sslTrhdrT);
    }
}
