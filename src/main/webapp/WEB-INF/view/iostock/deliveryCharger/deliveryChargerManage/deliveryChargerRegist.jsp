<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/"/>

<wj-popup id="wjDlvrRegistLayer" control="wjDlvrRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
  <div id="dlvrRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="dlvrRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="deliveryCharger.registPopTitle"/>
      <span id="registTitleDlvrNm" class="ml5"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <form id="dlvrForm" ng-submit="submitForm()">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="deliveryCharger.dlvrCdNm"/><em class="imp">*</em></th>
            <td>
              <input type="text" id="dlvrCd" class="sb-input" style="width:49%" ng-model="dlvr.dlvrCd" readonly/>
              <input type="text" id="dlvrNm" class="sb-input" style="width:49%" ng-model="dlvr.dlvrNm" maxlength="18"/>
            </td>
            <th><s:message code="deliveryCharger.carNo"/><em class="imp">*</em></th>
            <td>
              <input type="text" id="carNo" class="sb-input w100" maxlength="14" ng-model="dlvr.carNo"/>
            </td>
          </tr>
          <tr>
            <th><s:message code="deliveryCharger.telNo"/></th>
            <td>
              <input type="text" id="telNo" class="sb-input w100" maxlength="15" ng-model="dlvr.telNo"/>
            </td>
            <th><s:message code="deliveryCharger.hpNo"/></th>
            <td>
              <input type="text" id="hpNo" class="sb-input w100" maxlength="15" ng-model="dlvr.hpNo"/>
            </td>
          </tr>
          <tr>
            <th><s:message code="deliveryCharger.useYn"/></th>
            <td>
              <select id="useYn" ng-model="dlvr.useYn">
                <option value="Y"><s:message code="deliveryCharger.useYnY"/></option>
                <option value="N"><s:message code="deliveryCharger.useYnN"/></option>
              </select>
            </td>
          </tr>
          <tr>
            <th><s:message code="deliveryCharger.remark"/></th>
            <td colspan="3">
              <div>
                <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="dlvr.remark"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>

        <div class="w100 mt10 pdb20 oh bb">
          <div class="fr">
            <%-- 저장 --%>
            <button type="submit" id="btnSave" class="btn_blue mr5">
              <s:message code="cmm.save"/></button>
            <%-- 삭제 --%>
            <button type="button" id="btnDel" class="btn_blue mr5" ng-click="fnDlvrDel()" ng-if="btnDel">
              <s:message code="cmm.delete"/></button>
          </div>
        </div>
        <div style="clear: both;"></div>

      </form>

      <div class="tr mt20 mr10">
        <div class="mt20 oh sb-select dkbr">
          <span class="fl bk lh30 ml10"><s:message code='deliveryCharger.chargeStorage'/></span>
          <div class="tr fr">
            <%-- 창고추가 --%>
            <button type="button" id="btnAddStorage" class="btn_skyblue ml5" ng-click="openPopAddStorage()" ng-if="btnAddStorage">
              <s:message code="deliveryCharger.addStorage"/></button>
            <%-- 창고삭제 --%>
            <button type="button" id="btnDelStorage" class="btn_skyblue ml5" ng-click="delStorage()" ng-if="btnDelStorage">
              <s:message code="deliveryCharger.delStorage"/></button>
          </div>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <!--위즈모 테이블-->
        <div class="wj-gridWrap" style="height: 200px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="deliveryCharger.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.storageCd"/>" binding="storageCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.storageNm"/>" binding="storageNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

            <!-- enable column filtering-->
            <wj-flex-grid-filter></wj-flex-grid-filter>
          </wj-flex-grid>
        </div>
        <!--//위즈모 테이블-->
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /** 배송기사 관리 상세 controller */
  app.controller('dlvrRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrRegistCtrl', $scope, $http, true));

    $scope.default = {useYn: "Y"};

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on('dlvrRegistCtrl', function (event, data) {
      $scope.dlvrCd = data.dlvrCd;
      $scope.dlvrNm = data.dlvrNm;

      // 배송기사 상세 팝업 오픈
      $scope.wjDlvrRegistLayer.show(true);

      // 배송기사코드가 있는 경우 배송기사 상세 조회
      if ($scope.dlvrCd !== '') {
        // 타이틀의 배송기사 명칭 세팅
        $("#registTitleDlvrNm").html("[" + $scope.dlvrNm + "]");
        // 배송기사 상세 조회
        $scope.searchDlvrInfo();

        // 그리드 조회
        $scope.searchDlvrChgrStorageList();

        // 버튼 show
        $scope.btnDel = true;
        $scope.btnAddStorage = true;
        $scope.btnDelStorage = true;
      }
      // 신규등록인 경우
      else {
        $("#registTitleDlvrNm").html("신규등록");
        $scope.dlvr = angular.copy($scope.default); // 기본값 세팅

        // 그리드 초기화
        var cv          = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data     = cv;

        // 버튼 hide
        $scope.btnDel = false;
        $scope.btnAddStorage = false;
        $scope.btnDelStorage = false;
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 배송기사 상세 조회
    $scope.searchDlvrInfo = function () {
      var param    = {};
      param.dlvrCd = $scope.dlvrCd;

      $http({
        method : 'POST', //방식
        url    : "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrInfo.sb", /* 통신할 URL */
        params : param, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        // this callback will be called asynchronously
        // when the response is available

        var data = response.data.data;
        if (data === undefined || data === null) {
          // 팝업 닫기 및 값 초기화
          $scope.popupClose();
          s_alert.pop(response.data.message);
          return false;
        }

        $scope.dlvr = data;
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        s_alert.pop(response.data.message);
        return false;
      }).then(function () {
        // "complete" code here
      });
    };


    // 배송기사 저장
    $scope.submitForm = function () {
      //값체크
      if (!valueCheck()) return false;

      $http({
        method : 'POST', //방식
        url    : "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrSave.sb", /* 통신할 URL */
        params : $scope.dlvr, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response)) {
          s_alert.pop(messages["cmm.saveSucc"]);

          // 신규등록인 경우
          if ($scope.dlvrCd === '') {
            // 팝업 닫기 및 값 초기화
            $scope.popupClose();
          }
          else {
            // 배송기사 리스트 그리드 조회
            var dlvrChgrScope = agrid.getScope('dlvrChgrListCtrl');
            dlvrChgrScope.searchDlvrChgrList();
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        s_alert.pop(response.data.message);
        return false;
      });
    };


    // 배송기사 삭제
    $scope.fnDlvrDel = function () {
      var msg = messages["deliveryCharger.delConfirmMsg"]; // 배송기사와 등록된 관리창고가 모두 삭제됩니다. 삭제하시겠습니까?
      s_alert.popConf(msg, function() {
        var params    = {};
        params.dlvrCd = $scope.dlvrCd;

        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages['cmm.saving']);

        // ajax 통신 설정
        $http({
          method : 'POST', //방식
          url    : '/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrDelete.sb', /* 통신할 URL */
          params : params, /* 파라메터로 보낼 데이터 */
          headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
          if ($scope._httpStatusCheck(response)) {
            s_alert.pop(messages["cmm.saveSucc"]);
            $scope.popupClose();
          }
        }, function errorCallback(response) {
          // called asynchronously if an error occurs
          // or server returns response with an error status.
          $scope._popMsg(messages["cmm.saveFail"]);
          return false;
        }).then(function () {
          // "complete" code here
          // 로딩바 hide
          $scope.$broadcast('loadingPopupInactive');
        });

        // var url       = "/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/dlvrDelete.sb";
        // var params    = {};
        // params.dlvrCd = $scope.dlvrCd;
        // $scope._postJSONSave.withPopUp(url, params, function (response) {
        //   $scope.popupClose();
        // });
      });
    };


    // 팝업 닫기, 값 초기화, 배송기사 리스트 그리드 조회
    $scope.popupClose = function () {
      // 초기화
      $scope.dlvr = angular.copy($scope.default);
      $scope.wjDlvrRegistLayer.hide();
      $("#registTitleDlvrNm").html("");

      // 배송기사 리스트 그리드 조회
      var dlvrChgrScope = agrid.getScope('dlvrChgrListCtrl');
      dlvrChgrScope.searchDlvrChgrList();
    };


    // 그리드 조회
    $scope.searchDlvrChgrStorageList = function () {
      // 파라미터
      var params    = {};
      params.dlvrCd = $scope.dlvrCd;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/storageList.sb", params, "", false);
    };


    // 창고 삭제
    $scope.delStorage = function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
      $scope._save("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/delStorage.sb", params, function () {
        $scope.searchDlvrChgrStorageList();
      });
    };


    // 창고 추가 팝업 오픈
    $scope.openPopAddStorage = function () {
      var params    = {};
      params.dlvrCd = $scope.dlvrCd;
      params.dlvrNm = $scope.dlvrNm;
      $scope._broadcast('dlvrStorageMgrCtrl', params);
    };

  }]);

  // 값 체크
  function valueCheck() {
    <%-- 기사명을 입력해주세요. --%>
    var msg = messages["deliveryCharger.dlvrNm"] + " " + messages["cmm.require.text"];
    if ($("#dlvrNm").val() === "") {
      s_alert.popOk(msg, function () {
        $("#dlvrNm").focus();
      });
      return false;
    }

    <%-- 기사명의 길이가 너무 깁니다. --%>
    var msg = messages["deliveryCharger.dlvrNm"] + " " + messages["deliveryCharger.textOver"];
    if ($("#dlvrNm").val().getByteLengthForOracle() > 18) {
      s_alert.popOk(msg, function () {
        $("#dlvrNm").select();
      });
      return false;
    }

    <%-- 차량번호를 입력해주세요. --%>
    var msg = messages["deliveryCharger.carNo"] + " " + messages["cmm.require.text"];
    if ($("#carNo").val() === "") {
      s_alert.popOk(msg, function () {
        $("#carNo").focus();
      });
      return false;
    }

    <%-- 차량번호의 길이가 너무 깁니다. --%>
    var msg = messages["deliveryCharger.carNo"] + " " + messages["deliveryCharger.textOver"];
    if ($("#carNo").val().getByteLengthForOracle() > 14) {
      s_alert.popOk(msg, function () {
        $("#carNo").select();
      });
      return false;
    }

    <%-- 전화번호는 숫자만 입력할 수 있습니다. --%>
    var msg          = messages["deliveryCharger.telNo"] + " " + messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if (numChkregexp.test($("#telNo").val())) {
      s_alert.popOk(msg, function () {
        $("#telNo").select();
      });
      return false;
    }

    <%-- 전화번호를 정확히 입력해주세요. --%>
    var msg = messages["deliveryCharger.telNo"] + " " + messages["deliveryCharger.validCheck"];
    if ($("#telNo").val() !== "" && $("#telNo").val().length < 10) {
      s_alert.popOk(msg, function () {
        $("#telNo").select();
      });
      return false;
    }

    <%-- 핸드폰번호는 숫자만 입력할 수 있습니다. --%>
    var msg = messages["deliveryCharger.hpNo"] + " " + messages["cmm.require.number"];
    if (numChkregexp.test($("#hpNo").val())) {
      s_alert.popOk(msg, function () {
        $("#hpNo").select();
      });
      return false;
    }

    <%-- 핸드폰번호를 정확히 입력해주세요. --%>
    var msg = messages["deliveryCharger.hpNo"] + " " + messages["deliveryCharger.validCheck"];
    if ($("#hpNo").val() !== "" && $("#hpNo").val().length < 10) {
      s_alert.popOk(msg, function () {
        $("#hpNo").select();
      });
      return false;
    }
    return true;
  }
</script>
