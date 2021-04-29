/****************************************************************
 *
 * 파일명 : hqBrandManage.js
 * 설  명 : 브랜드정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.27     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var useYnFg = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"사용안함","value":"N"}
];
// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([
  {id: "", name: "전체"},
  {id: "Y", name: "사용"},
  {id: "N", name: "사용안함"}
], 'id', 'name');

app.controller('hqBrandManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqBrandManageCtrl', $scope, $http, true));

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchUseYnFg", useYnFg);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      var row = ht.row;
      if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if( col.binding == "hqOfficeCd" || col.binding == "hqOfficeNm") {
          selectedBrand = grid.rows[ht.row].dataItem;
          if(selectedBrand.regId == undefined) {
            addHqOfficeLayer(row);
          }
        }
      }
    });
  };

  $scope.$on("hqBrandManageCtrl", function(event, data) {
    $scope.search();
    event.preventDefault();
  });

  // 그리드 조회
  $scope.search = function(){
    var param = {};
    param.hqOfficeCd  = hqOfficeCd;
    param.hqBrandCd   = $scope.hqBrandCd;
    param.hqBrandNm   = $scope.hqBrandNm;
    if(orgnFg == "STORE"){
      param.storeCd = storeCd;
    }
    param.useYn       = $scope.useYnFg;
    param.listScale   = 10;
    param.curr        = 1;
    $scope._inquiryMain("/store/hq/hqBrand/hqBrandManage/getBrandlist.sb", param, function(result){});
  };

  // 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd = storeCd;
    params.hqOfficeNm = "";
    params.hqBrandCd  = "자동채번";
    params.hqBrandNm  = "";
    params.msBrandCd  = "자동채번";
    params.msBrandNm  = "";
    params.useYn      = "Y";
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++){
      if(orgnFg == "HQ"){
        if($scope.flex.collectionView.itemsEdited[i].hqBrandNm == undefined || $scope.flex.collectionView.itemsEdited[i].hqBrandNm.length == 0) {

          $scope._popMsg(messages["hqBrand.require.hqBrandNm"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsEdited[i].hqBrandNm)) {
          $scope.flex.collectionView.itemsEdited[i].status = "U";
          params.push($scope.flex.collectionView.itemsEdited[i]);
        } else {
          $scope._popMsg(messages["cmm.max30Chk"]);
          return false;
        }

      } else if(orgnFg == "STORE"){
        if($scope.flex.collectionView.itemsEdited[i].msBrandNm == undefined || $scope.flex.collectionView.itemsEdited[i].msBrandNm.length == 0) {
          $scope._popMsg(messages["hqBrand.require.hqBrandNm"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsEdited[i].msBrandNm)) {
          if($scope.flex.collectionView.itemsEdited[i].msBrandCd > 7999999 || hqOfficeCd == "00000") {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
          } else {
            $scope._popMsg(messages["hqBrand.setting.store"]);
            return false;
          }
        } else {
          $scope._popMsg(messages["cmm.max30Chk"]);
          return false;
        }
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if(orgnFg == "HQ"){
        if($scope.flex.collectionView.itemsAdded[i].hqBrandNm == undefined || $scope.flex.collectionView.itemsAdded[i].hqBrandNm.length == 0) {
          $scope._popMsg(messages["hqBrand.require.hqBrandNm"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].hqBrandNm)) {
          $scope.flex.collectionView.itemsAdded[i].status = "I";
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max30Chk"]);
          return false;
        }
      } else if(orgnFg == "STORE"){
        if($scope.flex.collectionView.itemsAdded[i].msBrandNm == undefined || $scope.flex.collectionView.itemsAdded[i].msBrandNm.length == 0) {

          $scope._popMsg(messages["hqBrand.require.hqBrandNm"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].msBrandNm)) {
          $scope.flex.collectionView.itemsAdded[i].status = "I";
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max30Chk"]);
          return false;
        }
      }
    }

    $scope._save("/store/hq/hqBrand/hqBrandManage/save.sb", params, function(result) {
      $scope.search();
    });
  };

  $scope.maxChk = function (val){
    var str = val;
    var strLength = 0;
    var strTitle = "";
    var strPiece = "";
    for (i = 0; i < str.length; i++){
      var code = str.charCodeAt(i);
      var ch = str.substr(i,1).toUpperCase();
      //체크 하는 문자를 저장
      strPiece = str.substr(i,1)
      code = parseInt(code);
      if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
        strLength = strLength + 3; //UTF-8 3byte 로 계산
      }else{
        strLength = strLength + 1;
      }
      if(strLength > 30){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };

}]);
