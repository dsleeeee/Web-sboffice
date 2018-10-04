<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 사업자번호 사용현황  레이어 팝업 --%>
<div id="BizInfoDim" class="fullDimmed" style="display:none;"></div>
<div id="BizInfoLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w56">
      <p id="bizInfoLayerTitle" class="tit"></p>
      <a href="#" class="btn_close"></a>
      <div class="con sc2">
        <%-- 위즈모 그리드  --%>
        <div>
          <div id="theGridBizInfo" class="mt10 mb20" style="height:100px;"></div>
        </div>
        <div class="wj-TblWrap mr10">
          <div class="oh mb10">
            <span class="fl bk lh20 s14">수신자선택</span>
          </div>
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w98px" />
              <col class="w15" />
              <col class="w98px" />
              <col class="w15" />
              <col class="w98px" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 코드 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizInfo.storeCd" /></div>
                </th>
                <td id="bStoreCd"></td>
                <%-- 명칭 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizInfo.storeNm" /></div>
                </th>
                <td id="bStoreNm"></td>
                <%-- 상태 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.sysStatFg" /></div>
                </th>
                <td id="bSysStatFg"></td>
              </tr>
              <tr>
                <%-- 대표자 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.ownerNm" /></div>
                </th>
                <td id="bOwnerNm"></td>
                <%-- 사업자번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizNo" /></div>
                </th>
                <td id="bBizNo"></td>
                <%-- 상호명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizStoreNm" /></div>
                </th>
                <td id="bBizStoreNm"></td>
              </tr>
              <tr>
                <%-- 지역 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.area" /></div>
                </th>
                <td id="bArea"></td>
                <%-- 휴대폰번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.telNo" /></div>
                </th>
                <td id="bTelNo"></td>
                <%-- 팩스번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.faxNo" /></div>
                </th>
                <td id="bFaxNo"></td>
              </tr>
              <tr>
                <%-- 관리업체 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.agency" /></div>
                </th>
                <td id="bAgency"></td>
                <%-- 용도구분 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.clsFg" /></div>
                </th>
                <td id="bClsFg"></td>
                <%-- 시스템 오픈일자 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.sysOpenDate" /></div>
                </th>
                <td id="bSysOpenDate"></td>
              </tr>
              <tr>
                <%-- 주소 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.addr" /></div>
                </th>
                <td colspan="5" id="bAddr"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  var bizParam;
  var storeFgData = new wijmo.grid.DataMap([{id:"H", name:"본사"},{id:"S", name:"매장"}], 'id', 'name');

  <%-- Header --%>
  var bizInfoGridData =
    [
      {binding:"storeFg", header:"<s:message code='hqManage.bizInfo.storeFg' />", dataMap:storeFgData},
      {binding:"storeCd", header:"<s:message code='hqManage.bizInfo.storeCd' />"},
      {binding:"storeNm", header:"<s:message code='hqManage.bizInfo.storeNm' />"},
      {binding:"ownerNm", header:"<s:message code='hqManage.ownerNm' />"},
      {binding:"clsFgNm",   header:"<s:message code='hqManage.clsFg' />"},
      {binding:"sysStatFgNm", header:"<s:message code='hqManage.sysStatFg' />"}
    ];

  var theGridBizInfo = wgrid.genGrid("#theGridBizInfo", bizInfoGridData, "${menuCd}", 4, ${clo.getColumnLayout(4)});

  <%-- 그리드 포맷 --%>
  theGridBizInfo.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "storeCd") {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  theGridBizInfo.addEventListener(theGridBizInfo.hostElement, 'mousedown', function(e) {
    var ht = theGridBizInfo.hitTest(e);
    console.log(ht);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "storeCd") {
        getBizInfoDtl(theGridBizInfo.rows[ht.row].dataItem);
      }
    }
  });

  <%-- 사업자번호 사용현황 팝업 오픈 --%>
  function openBizInfoLayer(param) {

    $("#BizInfoDim").show();
    $("#BizInfoLayer").show();

    bizParam = param;

    var title = "<s:message code='hqManage.bizInfo' />"
              + " (" + "<s:message code='hqManage.bizNo' />" + " : "
              + param.bizNo1 + "-" + param.bizNo2 + "-" + param.bizNo3 + ")";

    $("#bizInfoLayerTitle").text(title);
    getBizInfo(param);
  }

  <%-- 사업자번호 사용현황 목록 조회 --%>
  function getBizInfo(param) {
    $.postJSON("/store/hq/hqManage/master/bizUseList.sb", param, function(result) {
      var list = result.data.list;
      theGridBizInfo.itemsSource = list;
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  function getBizInfoDtl(param) {
    $.postJSON("/store/hq/hqManage/master/bizInfoDtl.sb", param, function(result) {
      var dtlData = result.data;

      $("#bStoreCd").text(dtlData.storeCd);
      $("#bStoreNm").text(dtlData.storeNm);
      $("#bStoreCd").text(dtlData.storeCd);
      $("#bSysStatFg").text(dtlData.sysStatFgNm);
      $("#bOwnerNm").text(dtlData.ownerNm);
      $("#bBizNo").text(bizParam.bizNo1 + "-" + bizParam.bizNo2 + "-" + bizParam.bizNo3);
      $("#bBizStoreNm").text(dtlData.bizStoreNm);
      $("#bArea").text(dtlData.areaNm);
      $("#bTelNo").text(dtlData.telNo);
      $("#bFaxNo").text(dtlData.faxNo);
      $("#bAgency").text(dtlData.agencyCd);
      $("#bClsFg").text(dtlData.clsFgNm);
      $("#bSysOpenDate").text(dtlData.sysOpenDate);
      $("#bAddr").text("(" + dtlData.postNo + ") " + dtlData.addr + " " + dtlData.addrDtl);
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 레이어 팝업 닫기 --%>
  $("#BizInfoLayer .btn_close").click(function(){
    $("#BizInfoDim").hide();
    $("#BizInfoLayer").hide();
  });

</script>
