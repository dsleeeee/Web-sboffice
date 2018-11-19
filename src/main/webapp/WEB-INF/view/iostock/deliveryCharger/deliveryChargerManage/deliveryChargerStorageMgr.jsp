<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<wj-popup id="wjDlvrStorageMgrLayer" control="wjDlvrStorageMgrLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:600px;">
  <div id="dlvrStorageMgrLayer" class="wj-dialog wj-dialog-columns" ng-controller="dlvrStorageMgrCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="deliveryCharger.storageMgrPopTitle"/>
      <span id="storageMgrTitleDlvrNm" class="ml5"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div id="grid" class="w100 pdl20 pdr20 pdb20">
      <div class="mt20 oh sb-select dkbr">
        <div class="tr">
          <%-- 창고추가 --%>
          <button type="button" class="btn_skyblue ml5" ng-click="saveAddStore()">
            <s:message code="deliveryCharger.addStorage"/></button>
        </div>
      </div>

      <!--위즈모 테이블-->
      <div class="wj-gridWrap mt10" style="height: 300px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="deliveryCharger.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryCharger.storageCd"/>" binding="storageCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryCharger.storageNm"/>" binding="storageNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

          <!-- enable column filtering-->
          <wj-flex-grid-filter></wj-flex-grid-filter>
        </wj-flex-grid>
      </div>
      <!--//위즈모 테이블-->
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /** 배송기사 관리 창고 추가 그리드 controller */
  app.controller('dlvrStorageMgrCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrStorageMgrCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dlvrStorageMgrCtrl", function (event, data) {
      $scope.dlvrCd = data.dlvrCd;
      $scope.dlvrNm = data.dlvrNm;

      // 배송기사 창고 추가 팝업 오픈
      $scope.wjDlvrStorageMgrLayer.show(true);
      // 타이틀의 배송기사 명칭 세팅
      $("#storageMgrTitleDlvrNm").html("[" + $scope.dlvrNm + "]");

      $scope.searchStorageMgrList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 배송기사 관리 창고 추가 그리드 조회
    $scope.searchStorageMgrList = function () {
      // 파라미터
      var params    = {};
      params.dlvrCd = $scope.dlvrCd;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/deliveryCharger/deliveryChargerManage/dlvrStorageMgr/list.sb", params, "", false);
    };


    // 담당창고 추가 저장
    $scope.saveAddStore = function () {
      // 파라미터 설정
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        item.status = "U";
        item.dlvrCd = $scope.dlvrCd;
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/iostock/deliveryCharger/deliveryChargerManage/dlvrStorageMgr/save.sb", params, function () {
        $scope.callbackSearch();
      });
    };


    // 저장 후 그리드 재조회
    $scope.callbackSearch = function () {
      $scope.searchStorageMgrList();

      // 배송기사 상세페이지 담당창고 그리드 조회
      var dlvrRegistScope = agrid.getScope('dlvrRegistCtrl');
      dlvrRegistScope.searchDlvrChgrStorageList();
    };

  }]);
</script>
