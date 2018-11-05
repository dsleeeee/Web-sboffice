package kr.co.solbipos.stock.curr.storeCurr.service;

import kr.co.common.data.structure.DefaultMap;

import java.util.List;

public interface StoreCurrService {
    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getStoreCurrList(StoreCurrVO storeCurrVO);

}
