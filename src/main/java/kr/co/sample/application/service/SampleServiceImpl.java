package kr.co.sample.application.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.sample.application.domain.CcdCodemTVO;
import kr.co.sample.application.domain.SslTrdtlTVO;
import kr.co.sample.application.domain.SslTrhdrTVO;
import kr.co.sample.application.domain.TbMsStoreVO;
import kr.co.sample.application.domain.TestTableVO;
import kr.co.sample.application.domain.TmpBoardTVO;
import kr.co.sample.application.domain.TmpDragtTVO;
import kr.co.sample.application.persistence.SampleMapper;
import kr.co.solbipos.application.common.service.ResrceInfoVO;

@Service
public class SampleServiceImpl implements SampleService {

    private final SampleMapper sampleMapper;

    /** Constructor Injection */
    @Autowired
    public SampleServiceImpl(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

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
    public <E> List<E> selectDdlTrdtlTest(SslTrdtlTVO sslTrdtlTVO) {
        return sampleMapper.selectDdlTrdtlTest(sslTrdtlTVO);
    }

    @Override
    public <E> List<E> selectColumns(String table) {
        return sampleMapper.selectColumns(table);
    }

    @Override
    public <E> List<E> selectTestTable(TestTableVO testTableVO) {
        return sampleMapper.selectTestTable(testTableVO);
    }

    @Override
    public <E> List<E> selectCode(CcdCodemTVO param) {
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
    public <E> List<E> getDragNDropSample(TmpDragtTVO tmpDragtVO) {
      return sampleMapper.getDragNDropSample(tmpDragtVO);
    }

    @Override
    public void insertUpdateDragSample(TmpDragtTVO tmpDragtVO) {
      sampleMapper.insertUpdateDragSample(tmpDragtVO);
    }

    @Override
    public void insertBoardSample(TmpBoardTVO tmpBoardTVO) {
      sampleMapper.insertBoardSample(tmpBoardTVO);
    }

    @Override
    public void updateBoardSample(TmpBoardTVO tmpBoardTVO) {
      sampleMapper.updateBoardSample(tmpBoardTVO);
    }

    @Override
    public TmpBoardTVO getRecentBoardData() {
      return sampleMapper.getRecentBoardData();
    }

    @Override
    public <E> List<E> selectStore(TbMsStoreVO tbMsStoreVO) {
      return sampleMapper.selectStore(tbMsStoreVO);
    }

    @Override
    public List<ResrceInfoVO> selectMenu1() {
        return sampleMapper.selectMenu1();
    }

    @Override
    public List<ResrceInfoVO> selectMenu2() {
        return sampleMapper.selectMenu2();
    }

    @Override
    public List<ResrceInfoVO> selectMenu3() {
        return sampleMapper.selectMenu3();
    }

    @Override
    public List<ResrceInfoVO> selectAuthMainMenu(HashMap<String, String> param) {
        return sampleMapper.selectAuthMainMenu(param);
    }

    @Override
    public <E> List<E> selectDdlTrhdrTest(SslTrhdrTVO sslTrhdrTVO) {
        return sampleMapper.selectDdlTrhdrTest(sslTrhdrTVO);
    }

    @Override
    public <E> List<E> selectDdlTrdtl2Test(SslTrhdrTVO sslTrhdrTVO) {
        return sampleMapper.selectDdlTrdtl2Test(sslTrhdrTVO);
    }
}
