/****************************************************************
 *
 * 파일명 : storeAdd.js
 * 설  명 : 미디어관리 > 매장추가 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09    권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 탭 변경
function changeTab(){
  var scope = agrid.getScope("addStoreCtrl");
  scope.changeTab();
}

// 조회
function search(){
  var scope = agrid.getScope("addStoreCtrl");
  scope._pageView('addStoreCtrl', 1);
}

/**********************************************************************
 *  적용매장 그리드
 **********************************************************************/
app.controller('addStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('addStoreCtrl', $scope, $http, false));

  $scope.hqOfficeCd = gvHqOfficeCd;
  // 조회조건
  $scope._setComboData("hqOffice", hqList);
  if(orgnFg === "HQ"){
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
  }

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("addStoreCtrl", function(event, data) {

    if($("#srchHqOffice").val() != '' || $("#srchHqOffice").val() != null || $("#srchHqOffice").val() != undefined){
      $scope.selectedHqOffice = $("#srchHqOffice").val();
    }
    $scope.addStoreSearch();
    event.preventDefault();
  });

  // 선택본사
  $scope.selectedHqOffice;
  $scope.setSelectedHqOffice = function(s) {
    $scope.selectedHqOffice = s.selectedValue;
  };
  $scope.getSelectedHqOffice = function(){
    return $scope.selectedHqOffice;
  };

  // 선택본사
  $scope.selectedSysStatFg;
  $scope.setSelectedSysStatFg = function(s) {
    $scope.selectedSysStatFg = s.selectedValue;
  };
  $scope.getSelectedSysStatFg = function(){
    return $scope.selectedSysStatFg;
  };

  // 적용매장 목록 조회
  $scope.addStoreSearch = function(){

    var params = {};
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    if( !isEmptyObject($scope.store)){
      params = $scope.store;
    }

    params.verSerNo    = ver;
    params.searchSatus = 'Y';
    params.hqOfficeCd  = $scope.hqOfficeCd;
    params.sysStatFg = $scope.sysStatFg;
    params.storeCd = $("#addStoreChoiceCd").val();

    if(orgnFg === "HQ"){

        // 선택한 매장브랜드가 있을 때
        params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

        // 선택한 매장브랜드가 없을 때('전체' 일때)
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var userHqBrandCd = "";
            for(var i=0; i < userHqBrandCdComboList.length; i++){
                if(userHqBrandCdComboList[i].value !== null) {
                    userHqBrandCd += userHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        }
    }

    $scope._inquiryMain("/base/store/media/applcStore/srchStoreList.sb", params, function() {
      // 적용매장 조회 후, 미적용 매장 조회
      var allStoreScope = agrid.getScope("allStoreCtrl");
      allStoreScope._broadcast('allStoreCtrl', orgnFg === "HQ" ? $scope.srchStoreHqBrandCdCombo.selectedValue : ""); // 미적용매장 조회시, 본사권한은 검색조건 매장브랜드 값 넘기기
      //allStoreScope._pageView('allStoreCtrl', 1);

    }, false);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.addStoreChoiceShow = function () {
    $scope._broadcast('addStoreChoiceCtrl');
  };


  // 삭제
  $scope.delete = function(){

    var params = new Array();
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/store/media/applcStore/removeStore.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };

  //탭변경
  $scope.changeTab = function(){
    $scope.storeAddLayer.hide();
    $scope.versionInfoDetailLayer.show();
  };

}]);


/**********************************************************************
 *  미적용매장 그리드
 **********************************************************************/
app.controller('allStoreCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('allStoreCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("sysStatFg", sysStatFgTotal);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name');
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  };

  // 조회 버튼 클릭
  $scope.$on("allStoreCtrl", function(event, data) {
    $scope.allStoreSearch(data);
    event.preventDefault();
  });

  // 미적용매장 목록 조회
  $scope.allStoreSearch = function(vStoreHqBrandCd){

    var params = {};
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    var addStoreScope = agrid.getScope('addStoreCtrl');

    params.verSerNo    = ver;
    params.searchSatus = 'N';
    params.hqOfficeCd  = addStoreScope.hqOfficeCd;
    params.sysStatFg = addStoreScope.sysStatFg;
    params.storeCd = $("#addStoreChoiceCd").val();

    // 복수검색 기능 사용여부
    if ($("#chkMulti").prop("checked")) {
      params.chkMulti = "Y";
    }else{
      params.chkMulti = "N";
    }

    if(orgnFg === "HQ"){

        // 선택한 매장브랜드가 있을 때
        params.storeHqBrandCd = vStoreHqBrandCd;

        // 선택한 매장브랜드가 없을 때('전체' 일때)
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var userHqBrandCd = "";
            for(var i=0; i < userHqBrandCdComboList.length; i++){
                if(userHqBrandCdComboList[i].value !== null) {
                    userHqBrandCd += userHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        }
    }

    $scope._inquiryMain("/base/store/media/applcStore/srchStoreList.sb", params, function() {
    }, false);
  };


  // 저장
  $scope.save = function(){

    var params = new Array();
    var scope  = agrid.getScope('mediaCtrl');
    var ver    = scope.getSelectVersion().verSerNo;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].verSerNo = ver;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    if(params.length == 0){
      $scope._popMsg("선택된 매장이 없습니다.");
      return false;
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/store/media/applcStore/regist.sb", params, function(){
      // 적용매장 조회 후, 미적용 매장 조회
      var addStoreScope = agrid.getScope("addStoreCtrl");
      addStoreScope._broadcast('addStoreCtrl');
    });
  };
}]);
