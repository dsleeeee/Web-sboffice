/****************************************************************
 *
 * 파일명 : installRequest.js
 * 설  명 : 설치요청(의뢰) 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.01.03     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  설치요청 목록 그리드
 **********************************************************************/
app.controller('installRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('installRegistCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchInstReqFg", [
      {"name":"전체","value":""},
      /*{"name":"설치의뢰","value":"0"},*/
      {"name":"신규설치","value":"1"},
      {"name":"재설치","value":"2"}
  ]);
  $scope._setComboData("reasonCombo", reasonData);

  $scope.request;

  // 총판계정으로 접속한 경우, 해당 총판의 데이터만 조회되도록 함.
  // if(orgnFg === "AGENCY" && pAgencyCd !== "00000"){
  if(orgnFg === "AGENCY"){
      $("#pSrchAgencyCd").val(orgnCd);
      $("#pSrchAgencyNm").val(orgnNm);
      $("#agencyCd").val(orgnCd);
      $("#agencyNm").val(orgnNm);
  }

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name');
    $scope.reasonDataMap = new wijmo.grid.DataMap(reasonData, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  $scope.$on("installRegistCtrl", function(event, data) {
    $scope.getPosList();
    event.preventDefault();
  });

  // 포스목록 조회
  $scope.getPosList = function(){
    var params = {};

    params.agencyNm = $("#pSrchAgencyNm").val();
    params.agencyCd = $("#pSrchAgencyCd").val();

    // console.log('$scope._currentPageIndex : '+ $scope._currentPageIndex);
    $scope._inquiryMain("/pos/install/installManage/installManage/getPosList.sb", params, function() {
      console.log('search complete');
    });
  };

  $scope.reasonReadOnly = true;
  $scope.setSelectedCombo = function(s){
    // console.log('s.selectedItem',s.selectedItem);
    if(s.selectedValue === '005') {
      $scope.reasonReadOnly = false;
    } else {
      $scope.reasonReadOnly = false;
      $scope.request.remark = '';
    }
  };

  // 설치 요청
  $scope.request = function(){

    if(  isEmpty($("#agencyCd").val()) ) {
      $scope._popMsg("대리점을 선택해주세요");
      return false;
    }

    if( $scope.request.instReason === '005') {
      $scope._popMsg("기타사유를 입력해주세요.");
      return false;
    }

    console.log("$scope.request.instReason : "+ $scope.request.instReason);
    console.log("$scope.request.remark : "+ $scope.request.remark);

    var agencyCd = $("#agencyCd").val();
    var instReason = $scope.request.instReason;
    var remark = $scope.request.remark;

    var params = new Array();

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].agencyCd = agencyCd;
        $scope.flex.collectionView.items[i].instReason = instReason;
        $scope.flex.collectionView.items[i].remark = remark;

        params.push($scope.flex.collectionView.items[i]);
      }
    }

    console.log('params', params);

    if(params.length == 0) {
      $scope._popMsg("설치요청할 POS를 선택해주세요.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/pos/install/installManage/installManage/saveInstallRequest.sb', params, function(){
      // $scope.searchSalePriceList();

      $scope.getPosList(); // 리스트 초기화

      $("#agencyNm").val("선택");
      $("#agencyCd").val("");
      $scope.request.instReason = "";
      $scope.request.remark = "";
    });
  };


  // 대리점 조회
  $scope.searchAgency = function(val){
      if(orgnFg === "MASTER" || pAgencyCd === "00000"){
          $scope.agencyLayer.show(true, function(s){
              var agencyScope = agrid.getScope('searchAgencyCtrl');
              console.log('agencyResult ', agencyScope.getAgency());

              $scope.$apply(function() {
                  if( !$.isEmptyObject(agencyScope.getAgency())  ){
                      if(val === '1') {
                          $("#pSrchAgencyCd").val(agencyScope.getAgency().agencyCd);
                          $("#pSrchAgencyNm").val(agencyScope.getAgency().agencyNm);
                      } else {
                          $("#agencyCd").val(agencyScope.getAgency().agencyCd);
                          $("#agencyNm").val(agencyScope.getAgency().agencyNm);
                      }

                      // $scope.request.agencyCd = agencyScope.getAgency().agencyCd;
                      // $scope.request.agencyNm = agencyScope.getAgency().agencyNm;
                      // $("#agencyCd").val(agencyScope.getAgency().agencyCd);
                      // $("#agencyNm").val(agencyScope.getAgency().agencyNm);
                  }
              });
          });
      }
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 대리점 팝업 핸들러 추가
    $scope.agencyLayer.shown.addHandler(function (s) {
    });
  });
}]);
