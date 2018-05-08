package kr.co.sample.application.persistence;

import java.util.HashMap;
import java.util.List;
import kr.co.sample.application.domain.CcdCodemTVO;
import kr.co.sample.application.domain.SslTrdtlTVO;
import kr.co.sample.application.domain.SslTrhdrTVO;
import kr.co.sample.application.domain.TbMsStoreVO;
import kr.co.sample.application.domain.TestTableVO;
import kr.co.sample.application.domain.TmpBoardTVO;
import kr.co.sample.application.domain.TmpDragtTVO;
import kr.co.solbipos.application.domain.resource.ResrceInfoVO;

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

    <E> List<E> selectDdlTrdtlTest(SslTrdtlTVO sslTrdtlTVO);

    <E> List<E> selectColumns(String table);

    <E> List<E> selectTestTable(TestTableVO testTableVO);

    <E> List<E> selectCode(CcdCodemTVO param);

    <E> List<E> selectTreeMenu();

    <E> List<E> getgroupGridSample();

    <E> List<E> getDragNDropSample(TmpDragtTVO tmpDragtVO);

    void insertUpdateDragSample(TmpDragtTVO tmpDragtVO);

    void insertBoardSample(TmpBoardTVO tmpBoardTVO);

    void updateBoardSample(TmpBoardTVO tmpBoardTVO);

    TmpBoardTVO getRecentBoardData();

    <E> List<E> selectStore(TbMsStoreVO tbMsStoreVO);

    List<ResrceInfoVO> selectMenu1();
    List<ResrceInfoVO> selectMenu2();
    List<ResrceInfoVO> selectMenu3();

    List<ResrceInfoVO> selectAuthMainMenu(HashMap<String, String> param);

    <E> List<E> selectDdlTrhdrTest(SslTrhdrTVO sslTrhdrTVO);
    <E> List<E> selectDdlTrdtl2Test(SslTrhdrTVO sslTrhdrTVO);
}
