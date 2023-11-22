<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}No"/>" />
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
      <s:message code="cmm.emp.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <div class="w100">

        <%-- 조회조건 --%>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w30" />
            <col class="w15" />
            <col class="w40" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="outstockReqDate.empNo" /></th>
            <td>
              <input type="text" id="srchEmpNo" ng-model="srchEmpNo"/>
            </td>
            <th><s:message code="outstockReqDate.empNm" /></th>
            <td>
              <input type="text" id="srchEmpNm" ng-model="srchEmpNm"/>
            </td>
          </tr>
          <tr>
            <th><s:message code="outstockReqDate.userId" /></th>
            <td>
              <input type="text" id="srchUserId" ng-model="srchUserId"/>
            </td>
            <th><s:message code="outstockReqDate.mpNo" /></th>
            <td>
              <input type="text" id="srchMpNo" ng-model="srchMpNo"/>
            </td>
          </tr>
          <tr>
            <%-- 관리브랜드 --%>
            <th><s:message code="outstockReqDate.userHqBrand" /></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchPopUserHqBrandCdCombo"
                        ng-model="popUserHqBrandCd"
                        items-source="_getComboData('popUserHqBrandCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchPopUserHqBrandCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <%-- 그룹 --%>
            <th><s:message code="outstockReqDate.branchCd"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchPopBranchCdCombo"
                        ng-model="popBranchCd"
                        items-source="_getComboData('popBranchCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        control="srchPopBranchCdCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <c:if test="${momsEnvstVal == '1'}">
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
          <button class="btn_skyblue" ng-click="searchProd()"><s:message code="cmm.search" /></button>
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
            <wj-flex-grid-column header="<s:message code="outstockReqDate.empNo"/>" binding="empNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.empNm"/>" binding="empNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.userId"/>" binding="userId" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.mpNo"/>" binding="mpNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.userHqBrand"/>" binding="userHqBrand" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.branchCd"/>" binding="branchCd" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <c:if test="${momsEnvstVal == '1'}">
              <wj-flex-grid-column header="<s:message code="outstockReqDate.momsTeam"/>" binding="momsTeam" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="outstockReqDate.momsAcShop"/>" binding="momsAcShop" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="outstockReqDate.momsAreaFg"/>" binding="momsAreaFg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="outstockReqDate.momsCommercial"/>" binding="momsCommercial" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="outstockReqDate.momsShopType"/>" binding="momsShopType" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="outstockReqDate.momsStoreManageType"/>" binding="momsStoreManageType" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            </c:if>
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

  // 상품브랜드 콤보박스 항목 저장시 쓰려고
  var momsHqBrandCdComboList;

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
          if (col.binding === "empNo") {
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
          if (col.binding === "empNo") {
            $("#" + $scope.targetId + "No").val(selectedRow.empNo);
            $("#" + $scope.targetId + "Nm").val("[" + selectedRow.empNo + "] " + selectedRow.empNm);

            $("#" + $scope.subTargetId + "Nm").val(messages["cmm.all"]);
            $("#" + $scope.subTargetId + "No").val("");
            eval('$scope.wj' + $scope.targetId + 'LayerS.hide(true)');
          }
        }
      });

      // 관리브랜드
      var params = {};
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBrandMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popUserHqBrandCdCombo", list);
          // 관리브랜드 콤보박스 항목 저장시 쓰려고
          momsHqBrandCdComboList = list;
        }
      });

      // 그룹
      var params = {};
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBranchMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popBranchCdCombo", list);
          // 그룹
          if(list.length <= 1) {
            $("#srchPopBranchCdCombo").css('background-color', '#F0F0F0');
            $("#srchPopBranchCdCombo").attr("disabled", true);
          } else {
            $("#srchPopBranchCdCombo").css('background-color', '#FFFFFF');
            $("#srchPopBranchCdCombo").attr("disabled", false);
          }
        }
      });

      if(momsEnvstVal === "1") {
        // 팀별
        var params = {};
        params.nmcodeGrpCd = "151";
        $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectHqNmcodeMomsList.sb', params, function (response) {
          if (response.data.data.list.length > 0) {
            var list = response.data.data.list;
            $scope._setComboData("popMomsTeamCombo", list);
            // 팀별
            if (list.length <= 1) {
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
            if (list.length <= 1) {
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
            if (list.length <= 1) {
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
            if (list.length <= 1) {
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
            if (list.length <= 1) {
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
            if (list.length <= 1) {
              $("#srchPopMomsStoreManageTypeCombo").css('background-color', '#F0F0F0');
              $("#srchPopMomsStoreManageTypeCombo").attr("disabled", true);
            } else {
              $("#srchPopMomsStoreManageTypeCombo").css('background-color', '#FFFFFF');
              $("#srchPopMomsStoreManageTypeCombo").attr("disabled", false);
            }
          }
        });
      }
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
        $scope.srchPopUserHqBrandCdCombo.selectedIndex = 0;
        $scope.srchPopBranchCdCombo.selectedIndex = 0;
        if(momsEnvstVal === "1"){
          $scope.srchPopMomsTeamCombo.selectedIndex = 0;
          $scope.srchPopMomsAcShopCombo.selectedIndex = 0;
          $scope.srchPopMomsAreaFgCombo.selectedIndex = 0;
          $scope.srchPopMomsCommercialCombo.selectedIndex = 0;
          $scope.srchPopMomsShopTypeCombo.selectedIndex = 0;
          $scope.srchPopMomsStoreManageTypeCombo.selectedIndex = 0;
        }
      });

      if ($scope.searchFg == "N") {
        $scope.searchProd();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchProd = function () {
      // 파라미터
      var params = {};
      params.empNo = $scope.srchEmpNo;
      params.empNm = $scope.srchEmpNm;
      params.userId = $scope.srchUserId;
      params.mpNo = $scope.srchMpNo;
      params.userHqBrand = $scope.popUserHqBrandCd;
      // '전체' 일때
      if(params.userHqBrand === "" || params.userHqBrand === null) {
        var momsHqBrandCd = "";
        for(var i=0; i < momsHqBrandCdComboList.length; i++){
          if(momsHqBrandCdComboList[i].value !== null) {
            momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }
      params.branchCd = $scope.popBranchCd;
      params.momsTeam = $scope.popMomsTeam;
      params.momsAcShop = $scope.popMomsAcShop;
      params.momsAreaFg = $scope.popMomsAreaFg;
      params.momsCommercial = $scope.popMomsCommercial;
      params.momsShopType = $scope.popMomsShopType;
      params.momsStoreManageType = $scope.popMomsStoreManageType;

      $scope._inquirySub("/iostock/cmm/iostockCmm/selectEmpList.sb", params, function () {
        $scope.searchFg = "Y";
      });
    };

  }]);

  $(document).ready(function () {
    <%-- 선택취소 버튼 클릭 --%>
    $("#${param.targetId}SelectCancelBtn").click(function () {
      $("#${param.targetId}No").val("");
      $("#${param.targetId}Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));
    });
  });


</script>
