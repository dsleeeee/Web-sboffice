package kr.co.solbipos.application.persistance.sample;

import java.util.HashMap;
import java.util.List;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.domain.sample.CcdCodemT;
import kr.co.solbipos.application.domain.sample.ScdShopmT;
import kr.co.solbipos.application.domain.sample.SslTrdtlT;
import kr.co.solbipos.application.domain.sample.SslTrhdrT;
import kr.co.solbipos.application.domain.sample.TbMsStore;
import kr.co.solbipos.application.domain.sample.TestTable;
import kr.co.solbipos.application.domain.sample.TmpBoardT;
import kr.co.solbipos.application.domain.sample.TmpDragtT;

/**
 * 샘플
 * 
 * @author 정용길
 *
 */
public interface SampleMapper {

    <E> List<E> selectSample(String param);

    <E> List<E> selectDdSum();

    <E> List<E> selectDdlTrdtlT(Integer rnum);

    <E> List<E> selectCommonCodeList(String comCdFg);

    <E> List<E> selectDdlTrdtlTest(SslTrdtlT sslTrdtlT);

    <E> List<E> selectColumns(String table);

    <E> List<E> selectTestTable(TestTable testTable);

    <E> List<E> selectCode(CcdCodemT param);
    
    <E> List<E> selectTreeMenu();

    <E> List<E> getgroupGridSample();

    <E> List<E> getDragNDropSample(TmpDragtT tmpDragt);
    
    void insertUpdateDragSample(TmpDragtT tmpDragt);
    
    void insertBoardSample(TmpBoardT tmpBoardT);
    
    void updateBoardSample(TmpBoardT tmpBoardT);
    
    TmpBoardT getRecentBoardData();
    
    <E> List<E> selectStore(TbMsStore tbMsStore);
    
    List<ResrceInfo> selectMenu1();
    List<ResrceInfo> selectMenu2();
    List<ResrceInfo> selectMenu3();
    
    List<ResrceInfo> selectAuthMainMenu(HashMap<String, String> param);
    
    <E> List<E> selectDdlTrhdrTest(SslTrhdrT sslTrhdrT);
    <E> List<E> selectDdlTrdtl2Test(SslTrhdrT sslTrhdrT);
}
