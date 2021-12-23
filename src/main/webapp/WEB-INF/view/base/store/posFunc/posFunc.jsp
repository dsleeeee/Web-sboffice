<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="baseUrl" value="/base/store/posfunc/"/>

<div class="subCon" ng-controller="posFuncCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" >
        <s:message code="cmm.search" />
      </button>
    </div>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
    <c:if test="${orgnFg != 'HQ'}">
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="posFunc.hqOfficeCd" /></th>
        <td><input type="text" id="srchHqOfficeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
        <%-- 본사명 --%>
        <th><s:message code="posFunc.hqOfficeNm" /></th>
        <td><input type="text" id="srchHqOfficeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
      </tr>
    </c:if>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="posFunc.storeCd" /></th>
      <td><input type="text" id="srchStoreCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
      <%-- 매장명 --%>
      <th><s:message code="posFunc.storeNm" /></th>
      <td><input type="text" id="srchStoreNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
    </tr>
    </tbody>
  </table>

  <div class="wj-TblWrap mt40">
    <div class="w40 fl" style="overflow-x: visible">
      <div class="wj-TblWrapBr mr10 pd20" style="height:700px;">
        <div class="sb-select dkbr mb10 oh">
          <%-- 페이지스케일 --%>
          <%--<div id="listScaleBox" class="w100px fl" style="display: none;"></div>--%>
          <div class="fr">
            <%-- 전체펼치기 --%>
            <%--<button class="btn_skyblue" id="btnExpand"><s:message code="cmm.all.expand" /></button>--%>
            <%-- 전체접기 --%>
            <%--<button class="btn_skyblue" id="btnFold"><s:message code="cmm.all.fold" /></button>--%>
           <button class="btn_skyblue" id="btnDefaultFunc" ng-click="defaultFunc()">
            <s:message code="func.defaultFunc" />
           </button>
           <button class="btn_skyblue" id="btnBatchStore" ng-click="batchStore()">
            <s:message code="func.batch" />
           </button>
          </div>
        </div>
        <div id="storeGrid" style="height: 550px; overflow-x: hidden;"></div>

        <%-- 페이지리스트 --%>
        <div class="pageNum2 mt20">
          <%-- id --%>
          <ul id="page" data-size="5">
          </ul>
        </div>

      </div>
    </div>

    <div class="w60 fr">
      <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
        <%-- 포스기능 인증관리--%>
        <c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuth.jsp">
          <c:param name="menuCd" value="${menuCd}"/>
          <c:param name="menuNm" value="${menuNm}"/>
          <c:param name="baseUrl" value="${baseUrl}"/>
        </c:import>

      <%-- 포스기능 사용관리--%>
      <c:import url="/WEB-INF/view/base/store/posFunc/posFuncUseManage.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
        <c:param name="baseUrl" value="${baseUrl}"/>
      </c:import>

      </div>
    </div>

  </div>
</div>

<script>

  // 본사코드 가져오기
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
  var hqList = null;
  if(orgnFg === "MASTER"){
    hqList = ${ccu.getHqOfficeList()};
  }else{
    hqList = ${ccu.getHqOfficeListChkAgency(orgnCd, "A")};
  }

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
          {binding:"storeNm", header:"<s:message code='posFunc.storeNm' />", width:180},
          {binding:"clsFg", header:"<s:message code='posFunc.clsFg' />", dataMap:clsFgDataMap, width:60, visible:false},
          {binding:"sysStatFg", header:"<s:message code='posFunc.sysStatFg' />", dataMap:sysStatFgDataMap, width:60}
          //{binding:"sysOpenDate", header:"<s:message code='posFunc.sysOpenDate' />", width:"*"}
        ];

    var storeGrid = wgrid.genGrid("#storeGrid", storeData);

    storeGrid.allowMerging = "Cells";

    var ldata         = ${ccu.getListScale()};
    // var listScaleBox  = wcombo.genCommonBox("#listScaleBox", ldata);

    <%-- 그리드 포맷 --%>
    storeGrid.formatItem.addHandler(function(s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( (col.binding === "storeCd" || col.binding === "storeNm" ) && item.storeCd != null) {
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

      // 오른쪽 화면 숨기기
      $("#posFuncManageArea").hide();
      $("#posFuncAuthArea").hide();
      if(orgnFg != 'HQ'){
        if($("#srchHqOfficeCd").val().length > 5) {
          s_alert.pop("<s:message code='posFunc.hqOfficeCd'/><s:message code='cmm.regexp' arguments='5'/>");
          return;
        }
        if($("#srchHqOfficeNm").val().length > 15) {
          s_alert.pop("<s:message code='posFunc.hqOfficeNm'/><s:message code='cmm.regexp' arguments='15'/>");
          return;
        }
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
      if(orgnFg != 'HQ') {
        param.hqOfficeCd = $("#srchHqOfficeCd").val();
      } else if(orgnFg == 'HQ'){
        param.hqOfficeCd = hqOfficeCd;
      }

      param.hqOfficeNm = $("#srchHqOfficeNm").val();
      param.storeCd = $("#srchStoreCd").val();
      param.storeNm = $("#srchStoreNm").val();
      param.listScale   = 50;
      // param.listScale   = listScaleBox.selectedValue;
      param.curr        = index;

      $.postJSON("/base/store/posfunc/use/getStoreList.sb", param,

          function(result) {

            var list = result.data.list;

            if(list.length === undefined || list.length === 0) {
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
              if(ht.panel === storeGrid.cells) {
                if(!(storeGrid.rows[ht.row] instanceof wijmo.grid.GroupRow)) {
                  var col = ht.panel.columns[ht.col];
                  if( col.binding === "storeCd" || col.binding === "storeNm") {
                    if(storeGrid.rows[ht.row].dataItem.storeCd !== "<s:message code='posFunc.no.regist.store'/>") {
                      selectedStore = storeGrid.rows[ht.row].dataItem;
                      showPosFuncList();
                    }
                  }
                }
              }
            });

            page.make("#page", result.data.page.curr, result.data.page.totalPage);
          },
          function (result) {
            s_alert.pop(result.message);
          }
      );
    }
  });

</script>

<script>
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 매장선택 controller */
  app.controller('posFuncCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('posFuncCtrl', $scope, $http, true));

    $scope.$on('posFuncCtrl', function (event, data) {

    });

    // 기본기능적용
    $scope.defaultFunc = function(){

      // 레이어팝업 오픈
      $scope.defaultFuncLayer.show(true, function(){

        var regScope  = agrid.getScope('defaultFuncCtrl');
        regScope._gridDataInit();
      });

      event.preventDefault();
    };

    // 일괄기능적용
    $scope.batchStore = function(){

      // 레이어팝업 오픈
      $scope.batchStoreLayer.show(true, function(){

        var regScope  = agrid.getScope('batchStoreCtrl');
        regScope._gridDataInit();
      });

      event.preventDefault();
    };

  }]);
</script>

<%-- 포스기능 인증관리 상세 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncAuthDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

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

<%-- 기본기능적용 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/func/defaultFunc.jsp">
</c:import>

<%-- 일괄기능적용 매장리스트 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/func/batchStore.jsp">
</c:import>

<%-- 일괄기능적용 팝업 --%>
<c:import url="/WEB-INF/view/pos/confg/func/batchFunc.jsp">
</c:import>





