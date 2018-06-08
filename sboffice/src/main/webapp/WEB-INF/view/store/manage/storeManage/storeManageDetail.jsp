<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%--
* id 규칙
 1. v로 시작하는 id는 단순 조회용 데이터
 2. r로 시작하는 id는 수정, 등록용 데이터
--%>

<%-- 매장 선택 전 --%>
<div class="w50 fr" id="noDataArea">
  <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
    <p class="tc s18 mt200 bk"><s:message code="storeManage.require.select.store" /></p>
    <p class="tc s14 mt40 lh25"><s:message code="storeManage.select.hqOffice" /><br /><s:message code="storeManage.can.regist.store" /></p>
    <p class="tc s14 mt40 lh25"><s:message code="storeManage.select.store" /><br /><s:message code="storeManage.can.edit.store" /></p> 
  </div>
</div>

<%-- 매장 상세정보 --%>
<div class="w50 fr" id="storeInfoViewArea" style="display:none;">
  <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
    <h2 class="h2_tit" id="storeInfoTitle">C000001 청해동태탕</h2>

    <%-- 탭 --%>
    <ul class="subTab">
      <%-- 매장정보 --%>
      <li><a id="storeInfo" href="javascript:;" class="on"><s:message code="storeManage.storeInfo" /></a></li>
      <%-- 메뉴권한 --%>
      <li><a id="storeMenuAuth" href="javascript:;"><s:message code="storeManage.storeMenuAuth" /></a></li>
      <%-- 매장환경 --%>
      <li><a id="storeEnv"  href="javascript:;"><s:message code="storeManage.storeEnv" /></a></li>
    </ul>    

    <%-- 기본정보 --%>
    <h3 class="h3_tbl"><s:message code="storeManage.basicInfo" /></h3>
    <table class="searchTbl">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
        <tr>
          <%-- 매장코드 --%>
          <th><s:message code="storeManage.storeCd" /><em class="imp">*</em></th>
          <td id="rStoreCd">C000001</td>
          <%-- 매장명 --%>
          <th><s:message code="storeManage.storeCd" /><em class="imp">*</em></th>
          <td id="rStoreNm">청해동태탕</td>
        </tr>
        <tr>
          <%-- 포스개점일자 --%>
          <th><s:message code="storeManage.posOpenDate" /></th>
          <td id="rOpenPosDate">2017.11.12</td>
          <%-- 설치포스수 --%>
          <th><s:message code="storeManage.instrallPosCnt" /><em class="imp">*</em></th>
          <td id="rInstrallPosCnt">3</td>
        </tr>
        <tr>
          <%-- 대표자명 --%>
          <th><s:message code="storeManage.onwerNm" /><em class="imp">*</em></th>
          <td id="rOwnerNm">김솔비</td>
          <%-- 상호명 --%>
          <th><s:message code="storeManage.bizStoreNm" /><em class="imp">*</em></th>
          <td id="rBizStoreNm">청해동태탕</td>
        </tr>
        <tr>
          <%-- 용도 --%>
          <th><s:message code="storeManage.cls" /><em class="imp">*</em></th>
          <td id="rCls">KCP</td>
          <%-- 매장상태 --%>
          <th><s:message code="storeManage.sysStatFg" /><em class="imp">*</em></th>
          <td id="rSysStatFg">오픈</td>
        </tr>
        <tr>
          <%-- 사업자번호 --%>
          <th><s:message code="storeManage.bizNo" /></th>
          <td id="rBizNo" colspan="3">123-45-6789</td>
        </tr>
        <tr>
          <%-- 전화번호 --%>
          <th><s:message code="storeManage.telNo" /></th>
          <td id="rTelNo">010-6578-9995</td>
          <%-- 팩스번호 --%>
          <th><s:message code="storeManage.faxNo" /></th>
          <td id="rFaxNo">070-265-7895</td>
        </tr>
        <tr>
          <%-- 이메일 --%>
          <th><s:message code="storeManage.emailAddr" /></th>
          <td id="rEmailAddr" colspan="3">solbi@naver.com</td>
        </tr>
        <tr>
          <%-- 홈페이지 --%>
          <th><s:message code="storeManage.hmpgAddr" /></th>
          <td id="rHmpgAddr" colspan="3"><a href="#" class="link">http://www.solbipos.co.kr</a></td>
        </tr>
        <tr>
          <%-- 주소 --%>
          <th><s:message code="storeManage.addr" /><em class="imp">*</em></th>
          <td id="rAddr" colspan="3">06578) 서울 구로구 디지털로 31길 61(신세계아이앤씨 디지털센터) 801호</td>
        </tr>
        <tr>
          <%-- 지역 --%>
          <th><s:message code="storeManage.area" /><em class="imp">*</em></th>
          <td id="rArea">서울</td>
          <%-- 용도  ? --%>
          <th><s:message code="storeManage.cls" /><em class="imp">*</em></th>
          <td id="rCls">KCP</td>
        </tr>
        <tr>
          <%-- 관리밴사 --%>
          <th><s:message code="storeManage.van" /><em class="imp">*</em></th>
          <td id="rVan">케이씨피포스</td>
          <%-- 대리점 --%>
          <th><s:message code="storeManage.agency" /><em class="imp">*</em></th>
          <td id="rAgency">케이씨피포스</td>
        </tr>
      </tbody>
    </table>
    <%-- 비고 --%>
    <h3 class="h3_tbl"><s:message code="storeManage.remark" /></h3>
    <table class="searchTbl">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
        <tr>
          <%-- 시스템비고 --%>
          <th>시스템 비고</th>
          <td colspan="3">3개월동안 매출자료가 발생되지 않아 2017-09-01 자동으로 폐점처리</td>
        </tr>
        <tr>
          <%-- 본사비고 --%>
          <th>본사 비고</th>
          <td colspan="3">가맹점의 요청으로 다시 오픈 2017-09-02</td>
        </tr>
        <tr>
          <%-- 특이사항 --%>
          <th>특이사항</th>
          <td colspan="3">기존관리업체 솔비통신에서 케이씨피포스로 이전</td>
        </tr>
      </tbody>
    </table>
    <%-- VAN사 설정 --%>
    <h3 class="h3_tbl">VAN사설정</h3>
    <table class="searchTbl2">
      <colgroup>
        <col class="w15" />
        <col class="w40" />
        <col class="w45" />
      </colgroup> 
      <thead>
        <tr>
          <th>구분</th>
          <th>VAN사</th>
          <th>VAN사 계약번호</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th>기본</th>
          <td>KCP</td>
          <td>123456789</td>
        </tr>
        <tr>
          <th>OK캐쉬백</th>
          <td>직승인</td>
          <td>123123</td>
        </tr>
      </tbody>
    </table>
    <h3 class="h3_tbl pdt5 lh30">코너별 승인<span class="fr"><a href="#" class="btn_grayS mr5">추가</a><a href="#" class="btn_grayS">삭제</a></span></h3>
    <table class="searchTbl2">
      <colgroup>
        <col class="w4" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
        <col class="w8" />
      </colgroup>
      <thead>
        <tr>
          <th><span class="chk"><input type="checkbox" /></span></th>
          <th>코너명</th>
          <th>VAN사<br/>계약번호</th>
          <th>OK캐쉬백<br/>계약번호</th>
          <th>현금<br/>수수료율</th>
          <th>카드<br/>수수료율</th>
          <th>기타<br/>수수료율</th>
          <th>대표자</th>
          <th>사업자<br/>번호</th>
          <th>업태</th>
          <th>종목</th>
          <th>전화번호</th>
          <th>사용여부</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th class="tc"><span class="chk"><input type="checkbox" /></span></th>
          <td class="tc">기본코너</td>
          <td class="tc">123456</td>
          <td class="tc">123456</td>
          <td class="tc">1%</td>
          <td class="tc">2%</td>
          <td class="tc">3%</td>
          <td class="tc">김솔비</td>
          <td class="tc">12-123-456</td>
          <td class="tc">음식</td>
          <td class="tc">서양식음식점</td>
          <td class="tc">02-1234-5678</td>
          <td class="tc">사용</td>
        </tr>
      </tbody>
    </table>
    <h3 class="h3_tbl">포스별 승인</h3>
    <table class="searchTbl2">
      <colgroup>
        <col class="w20" />
        <col class="w20" />
        <col class="w20" />
        <col class="w20" />
        <col class="w20" />
      </colgroup> 
      <thead>
        <tr>
          <th>POS번호</th>
          <th>터미널번호</th>
          <th>인증여부</th>
          <th>최초인증인자</th>
          <th>최종인증일자</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th class="tc">01</th>
          <td class="tc">1003123456</td>
          <td class="tc">인증</td>
          <td class="tc">2017.11.11</td>
          <td class="tc">2017.11.11</td>
        </tr>
      </tbody>
    </table>
      <!--//searchTbl-->

    <div class="tc mt20">
      <button class="btn_blue">등록</button>
    </div> 
  </div>
</div>

<script>

<%-- 매장 상세정보 --%>
function showStoreDetail(data) {
  $("#noDataArea").hide();
  $("#storeInfoEditArea").show();
 
  $("#storeInfoTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);
  
  
  
}

</script>