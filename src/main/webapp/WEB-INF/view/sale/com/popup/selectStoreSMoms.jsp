<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>" />
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl mr5"
       style="cursor:pointer; width:200px;"
        <c:if test="${empty param.modiFg}">
          ng-click="<c:out value="${param.targetId}"/>Show()"
        </c:if>
       readonly/>
<c:if test="${empty param.modiFg}">
  <button type="button" class="btn_skyblue fl mr5" id="<c:out value="${param.targetId}SelectCancelBtn"/>">
    <s:message code="cmm.selectCancel"/></button>
</c:if>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerS" control="wj<c:out value="${param.targetId}"/>LayerS" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.store.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <div class="w100">

        <%-- 조회조건 --%>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="outstockReqDate.storeCd" /></th>
            <td>
              <input type="text" id="srchStoreCd" ng-model="srchStoreCd"/>
            </td>
            <th><s:message code="outstockReqDate.storeNm" /></th>
            <td>
              <input type="text" id="srchStoreNm" ng-model="srchStoreNm"/>
            </td>
          </tr>
          <tr>
            <%-- 매장브랜드 --%>
            <th><s:message code="outstockReqDate.storeHqBrand" /></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchPopStoreHqBrandCdCombo"
                        ng-model="popStoreHqBrandCd"
                        items-source="_getComboData('popStoreHqBrandCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchPopStoreHqBrandCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <%-- 지사 --%>
            <th><s:message code="outstockReqDate.branchCd"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchPopBranchCdComboo"
                        ng-model="popBranchCd"
                        items-source="_getComboData('popBranchCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchPopBranchCdComboo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 팀별 --%>
              <th><s:message code="outstockReqDate.momsTeam"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopMomsTeamCombo"
                          ng-model="popMomsTeam"
                          items-source="_getComboData('popMomsTeamCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchPopMomsTeamCombo">
                  </wj-combo-box>
                </div>
              </td>
                <%-- AC점포별 --%>
              <th><s:message code="outstockReqDate.momsAcShop"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopMomsAcShopCombo"
                          ng-model="popMomsAcShop"
                          items-source="_getComboData('popMomsAcShopCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchPopMomsAcShopCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
                <%-- 지역구분 --%>
              <th><s:message code="outstockReqDate.momsAreaFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopMomsAreaFgCombo"
                          ng-model="popMomsAreaFg"
                          items-source="_getComboData('popMomsAreaFgCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchPopMomsAreaFgCombo">
                  </wj-combo-box>
                </div>
              </td>
                <%-- 상권 --%>
              <th><s:message code="outstockReqDate.momsCommercial"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopMomsCommercialCombo"
                          ng-model="popMomsCommercial"
                          items-source="_getComboData('popMomsCommercialCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchPopMomsCommercialCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
                <%-- 점포유형 --%>
              <th><s:message code="outstockReqDate.momsShopType"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopMomsShopTypeCombo"
                          ng-model="popMomsShopType"
                          items-source="_getComboData('popMomsShopTypeCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchPopMomsShopTypeCombo">
                  </wj-combo-box>
                </div>
              </td>
                <%-- 매장관리타입 --%>
              <th><s:message code="outstockReqDate.momsStoreManageType"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopMomsStoreManageTypeCombo"
                          ng-model="popMomsStoreManageType"
                          items-source="_getComboData('popMomsStoreManageTypeCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchPopMomsStoreManageTypeCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
          </c:if>
          </tbody>
        </table>

        <%-- 버튼영역 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" ng-click="searchStore()"><s:message code="cmm.search" /></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-gridWrap mt10" style="height: 400px; overflow-y: hidden;">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  items-source="data"
                  control="flex"
                  initialized="initGrid(s,e)"
                  is-read-only="true"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>

    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 매장선택 controller */
  app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

    $scope.targetId = "${param.targetId}";
    $scope.subTargetId = "${param.subTargetId}";
    $("#"+$scope.targetId+"Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController($scope.targetId + 'Ctrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel == s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "storeCd") {
            var item = s.rows[e.row].dataItem;
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
        }
      });

      // 그리드 클릭 이벤트
      s.addEventListener(s.hostElement, 'mousedown', function (e) {
        var ht = s.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
          var col         = ht.panel.columns[ht.col];
          var selectedRow = s.rows[ht.row].dataItem;
          if (col.binding === "storeCd") {
            $("#" + $scope.targetId + "Cd").val(selectedRow.storeCd);
            $("#" + $scope.targetId + "Nm").val("[" + selectedRow.storeCd + "] " + selectedRow.storeNm);

            $("#" + $scope.subTargetId + "Nm").val(messages["cmm.all"]);
            $("#" + $scope.subTargetId + "Cd").val("");
            eval('$scope.wj' + $scope.targetId + 'LayerS.hide(true)');
          }
        }
      });


      // 매장브랜드
      var params = {};
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBrandMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popStoreHqBrandCdCombo", list);
          // 매장브랜드 콤보박스 항목 저장시 쓰려고
          momsHqBrandCdComboList = list;
        }
      });

      // 팀별
      var params = {};
      params.nmcodeGrpCd = "151";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popMomsTeamCombo", list);
          // 팀별
          if(list.length <= 1) {
            $("#srchPopMomsTeamCombo").css('background-color', '#F0F0F0');
            $("#srchPopMomsTeamCombo").attr("disabled", true);
          } else {
            $("#srchPopMomsTeamCombo").css('background-color', '#FFFFFF');
            $("#srchPopMomsTeamCombo").attr("disabled", false);
          }
        }
      });

      // AC점포별
      var params = {};
      params.nmcodeGrpCd = "152";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popMomsAcShopCombo", list);
          // AC점포별
          if(list.length <= 1) {
            $("#srchPopMomsAcShopCombo").css('background-color', '#F0F0F0');
            $("#srchPopMomsAcShopCombo").attr("disabled", true);
          } else {
            $("#srchPopMomsAcShopCombo").css('background-color', '#FFFFFF');
            $("#srchPopMomsAcShopCombo").attr("disabled", false);
          }
        }
      });

      // 지역구분
      var params = {};
      params.nmcodeGrpCd = "153";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popMomsAreaFgCombo", list);
          // 지역구분
          if(list.length <= 1) {
            $("#srchPopMomsAreaFgCombo").css('background-color', '#F0F0F0');
            $("#srchPopMomsAreaFgCombo").attr("disabled", true);
          } else {
            $("#srchPopMomsAreaFgCombo").css('background-color', '#FFFFFF');
            $("#srchPopMomsAreaFgCombo").attr("disabled", false);
          }
        }
      });

      // 상권
      var params = {};
      params.nmcodeGrpCd = "154";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popMomsCommercialCombo", list);
          // 상권
          if(list.length <= 1) {
            $("#srchPopMomsCommercialCombo").css('background-color', '#F0F0F0');
            $("#srchPopMomsCommercialCombo").attr("disabled", true);
          } else {
            $("#srchPopMomsCommercialCombo").css('background-color', '#FFFFFF');
            $("#srchPopMomsCommercialCombo").attr("disabled", false);
          }
        }
      });

      // 점포유형
      var params = {};
      params.nmcodeGrpCd = "155";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popMomsShopTypeCombo", list);
          // 점포유형
          if(list.length <= 1) {
            $("#srchPopMomsShopTypeCombo").css('background-color', '#F0F0F0');
            $("#srchPopMomsShopTypeCombo").attr("disabled", true);
          } else {
            $("#srchPopMomsShopTypeCombo").css('background-color', '#FFFFFF');
            $("#srchPopMomsShopTypeCombo").attr("disabled", false);
          }
        }
      });

      // 매장관리타입
      var params = {};
      params.nmcodeGrpCd = "156";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popMomsStoreManageTypeCombo", list);
          // 매장관리타입
          if(list.length <= 1) {
            $("#srchPopMomsStoreManageTypeCombo").css('background-color', '#F0F0F0');
            $("#srchPopMomsStoreManageTypeCombo").attr("disabled", true);
          } else {
            $("#srchPopMomsStoreManageTypeCombo").css('background-color', '#FFFFFF');
            $("#srchPopMomsStoreManageTypeCombo").attr("disabled", false);
          }
        }
      });

      // 지사
      var params = {};
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBranchMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popBranchCdCombo", list);
          // 지사
          if(list.length <= 1) {
            $("#srchPopBranchCdComboo").css('background-color', '#F0F0F0');
            $("#srchPopBranchCdComboo").attr("disabled", true);
          } else {
            $("#srchPopBranchCdComboo").css('background-color', '#FFFFFF');
            $("#srchPopBranchCdComboo").attr("disabled", false);
          }
        }
      });
    };

    $scope.searchFg = "N"; // 조회 했는지 여부
    // 다른 컨트롤러의 broadcast 받기
    $scope.$on($scope.targetId + 'Ctrl', function (event, paramObj) {
      // 매장선택 팝업 오픈
      eval('$scope.wj' + $scope.targetId + 'LayerS.show(true)');
      // 팝업 닫힐시 이벤트
      eval('$scope.wj' + $scope.targetId + 'LayerS').hidden.addHandler(function () {
        if ('${param.closeFunc}' !== '') {
          eval('$scope.${param.closeFunc}()');
        }
        $scope.srchPopStoreHqBrandCdCombo.selectedIndex = 0;
        $scope.srchPopMomsTeamCombo.selectedIndex = 0;
        $scope.srchPopMomsAcShopCombo.selectedIndex = 0;
        $scope.srchPopMomsAreaFgCombo.selectedIndex = 0;
        $scope.srchPopMomsCommercialCombo.selectedIndex = 0;
        $scope.srchPopMomsShopTypeCombo.selectedIndex = 0;
        $scope.srchPopMomsStoreManageTypeCombo.selectedIndex = 0;
        $scope.srchPopBranchCdComboo.selectedIndex = 0;
      });

      if ($scope.searchFg == "N") {
        $scope.searchStore();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchStore = function () {
      // 파라미터
      var params = {};
      params.storeCd = $scope.srchStoreCd;
      params.storeNm = $scope.srchStoreNm;
      params.storeCd = $scope.srchStoreCd;
      params.storeNm = $scope.srchStoreNm;
      params.storeHqBrandCd = $scope.popStoreHqBrandCd;
      params.momsTeam = $scope.popMomsTeam;
      params.momsAcShop = $scope.popMomsAcShop;
      params.momsAreaFg = $scope.popMomsAreaFg;
      params.momsCommercial = $scope.popMomsCommercial;
      params.momsShopType = $scope.popMomsShopType;
      params.momsStoreManageType = $scope.popMomsStoreManageType;
      params.branchCd = $scope.popBranchCd;
      // '전체' 일때
      if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
        var momsHqBrandCd = "";
        for(var i=0; i < momsHqBrandCdComboList.length; i++){
          if(momsHqBrandCdComboList[i].value !== null) {
            momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }

      $scope._inquirySub("/iostock/cmm/iostockCmm/selectStoreMomsList.sb", params, function () {
        $scope.searchFg = "Y";
      });
    };
  }]);

  $(document).ready(function () {
    <%-- 선택취소 버튼 클릭 --%>
    $("#${param.targetId}SelectCancelBtn").click(function () {
      $("#${param.targetId}Cd").val("");
      $("#${param.targetId}Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));
    });
  });

</script>
