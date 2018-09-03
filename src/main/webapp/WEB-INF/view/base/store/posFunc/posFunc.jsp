<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/base/store/posfunc/"/>

<div class="subCon">
  <div class="searchBar">
    <a href="javscript:;" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    <tr>
      <%-- 본사코드 --%>
      <th><s:message code="posFunc.hqOfficeCd" /></th>
      <td><input type="text" id="srchHqOfficeCd" class="sb-input w100" maxlength="5" /></td>
      <%-- 본사명 --%>
      <th><s:message code="posFunc.hqOfficeNm" /></th>
      <td><input type="text" id="srchHqOfficeNm" class="sb-input w100" maxlength="15" /></td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="posFunc.storeCd" /></th>
      <td><input type="text" id="srchStoreCd" class="sb-input w100" maxlength="7"/></td>
      <%-- 매장명 --%>
      <th><s:message code="posFunc.storeNm" /></th>
      <td><input type="text" id="srchStoreNm" class="sb-input w100" maxlength="15"/></td>
    </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="wj-TblWrap mt20">
    <div class="w25 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:700px;">
        <div class="sb-select dkbr mb10 oh">
          <%-- 페이지스케일 --%>
          <div id="listScaleBox" class="w130 fl"></div>
          <div class="fr">
            <%-- 전체펼치기 --%>
            <%--<button class="btn_skyblue" id="btnExpand"><s:message code="cmm.all.expand" /></button>--%>
            <%-- 전체접기 --%>
            <%--<button class="btn_skyblue" id="btnFold"><s:message code="cmm.all.fold" /></button>--%>
          </div>
        </div>
        <div id="storeGrid" style="height:550px; width:280px;"></div>

        <%-- 페이지리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="page" data-size="10">
          </ul>
        </div>

      </div>
    </div>

    <%-- 포스기능 사용관리--%>
    <c:import url="/WEB-INF/view/base/store/posFunc/posFuncUseManage.jsp">
      <c:param name="menuCd" value="${menuCd}"/>
      <c:param name="menuNm" value="${menuNm}"/>
      <c:param name="baseUrl" value="${baseUrl}"/>
    </c:import>

    <%-- 포스기능 사용관리--%>
    <c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuth.jsp">
      <c:param name="menuCd" value="${menuCd}"/>
      <c:param name="menuNm" value="${menuNm}"/>
      <c:param name="baseUrl" value="${baseUrl}"/>
    </c:import>


  </div>
</div>

<script>
  var posList;
  var selectedStore;

  $(document).ready(function(){

    var clsFg             = ${ccu.getCommCodeSelect("001")};
    var sysStatFg         = ${ccu.getCommCodeSelect("005")};

    var clsFgDataMap      = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    var sysStatFgDataMap  = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    <%-- 매장목록 header --%>
    var storeData =
        [
          {binding:"hqOfficeCdNm", header:"<s:message code='posFunc.hqOffice' />", visible:false},
          {binding:"hqOfficeCd", header:"<s:message code='posFunc.hqOfficeCd' />", visible:false},
          {binding:"hqOfficeNm", header:"<s:message code='posFunc.hqOfficeNm' />", visible:false},
          {binding:"storeCd", header:"<s:message code='posFunc.storeCd' />", width:80},
          {binding:"storeNm", header:"<s:message code='posFunc.storeNm' />", width:80},
          {binding:"clsFg", header:"<s:message code='posFunc.clsFg' />", dataMap:clsFgDataMap, width:"*"},
          {binding:"sysStatFg", header:"<s:message code='posFunc.sysStatFg' />", dataMap:sysStatFgDataMap, width:"*"}
          //{binding:"sysOpenDate", header:"<s:message code='posFunc.sysOpenDate' />", width:"*"}
        ];

    var storeGrid = wgrid.genGrid("#storeGrid", storeData);

    storeGrid.allowMerging = "Cells";

    var ldata         = ${ccu.getListScale()};
    var listScaleBox  = wcombo.genCommonBox("#listScaleBox", ldata);

    <%-- 그리드 포맷 --%>
    storeGrid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( (col.binding == "storeCd" || col.binding == "storeNm" ) && item.storeCd != null) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    <%-- 조회 버튼 클릭 --%>
    $("#btnSearch").click(function(){
      search(1);
    });

    <%-- 페이징 --%>
    $(document).on("click", ".page", function() {
      search($(this).data("value"));
    });

    <%-- 매장 목록 조회 --%>
    function search(index) {

      if($("#srchHqOfficeCd").val().length > 5) {
        s_alert.pop("<s:message code='posFunc.hqOfficeCd'/><s:message code='cmm.regexp' arguments='5'/>");
        return;
      }
      if($("#srchHqOfficeNm").val().length > 15) {
        s_alert.pop("<s:message code='posFunc.hqOfficeNm'/><s:message code='cmm.regexp' arguments='15'/>");
        return;
      }

      if($("#srchStoreCd").val().length > 7) {
        s_alert.pop("<s:message code='posFunc.storeCd'/><s:message code='cmm.regexp' arguments='7'/>");
        return;
      }
      if($("#srchStoreNm").val().length > 15) {
        s_alert.pop("<s:message code='posFunc.storeNm'/><s:message code='cmm.regexp' arguments='15'/>");
        return;
      }

      var param = {};

      param.hqOfficeCd = $("#srchHqOfficeCd").val();
      param.hqOfficeNm = $("#srchHqOfficeNm").val();
      param.storeCd = $("#srchStoreCd").val();
      param.storeNm = $("#srchStoreNm").val();
      param.listScale   = listScaleBox.selectedValue;
      param.curr        = index;

      $.postJSON("/base/store/posfunc/use/getStoreList.sb", param,

          function(result) {

            var list = result.data.list;

            if(list.length === undefined || list.length == 0) {
              s_alert.pop(result.message);
              storeGrid.itemsSource = new wijmo.collections.CollectionView([]);
              return;
            }

            storeGrid.itemsSource = new wijmo.collections.CollectionView(list, {
              groupDescriptions : [ 'hqOfficeCdNm']
            });

            storeGrid.collapseGroupsToLevel(1);

            <%-- 매장 없는 본사 merge --%>
            for(var i=0; i<storeGrid.itemsSource.items.length; i++){
              if(storeGrid.itemsSource.items[i].storeNm == null) {
                storeGrid.itemsSource.items[i].storeNm = "<s:message code='posFunc.no.regist.store'/>";
                storeGrid.itemsSource.items[i].storeCd = "<s:message code='posFunc.no.regist.store'/>";
                storeGrid.itemsSource.items[i].clsFg = "<s:message code='posFunc.no.regist.store'/>";
                storeGrid.itemsSource.items[i].sysOpenDate = "";
              }
            }

            for(var i=0; i<storeGrid.rows.length; i++) {
              if(storeGrid.rows[i].dataItem.storeNm) {
                storeGrid.rows[i].allowMerging = true;
              }
            }

            <%-- 그리드 선택 이벤트 --%>
            storeGrid.addEventListener(storeGrid.hostElement, 'mousedown', function(e) {
              var ht = storeGrid.hitTest(e);
              if(ht.panel == storeGrid.cells) {
                if(!(storeGrid.rows[ht.row] instanceof wijmo.grid.GroupRow)) {
                  var col = ht.panel.columns[ht.col];
                  if( col.binding == "storeCd" || col.binding == "storeNm") {
                    if(storeGrid.rows[ht.row].dataItem.storeCd != "<s:message code='posFunc.no.regist.store'/>") {
                      selectedStore = storeGrid.rows[ht.row].dataItem;
                      showPosFuncList();
                    }
                  }
                }
              }
            });
            // page.make("#page", result.data.page.curr, result.data.page.totalPage);
          },
          function (result) {
            s_alert.pop(result.message);
            return;
          }
      );
    }
  });
</script>

<%-- 인증허용대상 설정 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuthSetting.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 기능복사 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncCopy.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>





