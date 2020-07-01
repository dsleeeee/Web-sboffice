package kr.co.solbipos.dlvr.anals.dlvrReceipt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DlvrReceiptMapper {
    List<DefaultMap<String>> getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO);

    List<DefaultMap<String>> getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO);
}
