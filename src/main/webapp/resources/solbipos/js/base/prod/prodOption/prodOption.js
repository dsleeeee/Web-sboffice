/****************************************************************
 *
 * 파일명 : prodOption.js
 * 설  명 : 옵션관라 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/

/**
 * 사이드메뉴 속성분류 그리드 생성
 */
app.controller('prodOptionCtrl', ['$scope', '$http', function ($scope, $http) {
  
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodOptionCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'optionGrpCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });
    
    // 속성분류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'optionGrpCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    
    // 속성분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'optionGrpCd' && selectedRow.status !== 'I') {
          $("#optionGrpTitle").html(" [" + selectedRow.optionGrpCd+ "]" + selectedRow.optionGrpNm );
          if (orgnFg == 'STORE' && selectedRow.optionGrpCd <= 7999) {
            $("#btnOptionValAdd").hide();
            $("#btnOptionValDel").hide();
            $("#btnOptionValSave").hide();
          } else {
            $("#btnOptionValAdd").show();
            $("#btnOptionValDel").show();
            $("#btnOptionValSave").show();
          }
          $scope._broadcast('prodOptionValCtrl', selectedRow.optionGrpCd);
        }
      }
    });

    $scope._broadcast('prodOptionCtrl');
  };
  
  // 속성분류 그리드 조회
  $scope.$on('prodOptionCtrl', function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/prodOption/prodOption/getProdOptionGroup.sb', params, function (){
      var grid = wijmo.Control.getControl("#wjGridGrp");
      var rows = grid.rows;

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];

        if(orgnFg === "STORE" && item.optionGrpCd < 8000){
          item.gChk = false;
          rows[i].isReadOnly = true;
        }
      }
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  // 속성분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = 'I';
    params.gChk = true;
    params.optionGrpCd = '자동채번';
    params.useYn = 'Y';
    params.cnt = '0';
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 속성분류 그리드 행 삭제
  $scope.del = function() {

    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i--){
        var item = $scope.flex.collectionView.items[i];
        if(item.gChk) {
          if ((orgnFg == "HQ") || (orgnFg == "STORE" && item.optionGrpCd > 7999)){
            if(item.cnt == 0){
              $scope.flex.collectionView.removeAt(i);
            } else {
              $scope._popMsg(messages["prodOption.grp.notNull"]);
              // 저장 후 재조회
              $scope._broadcast('prodOptionCtrl');
              $("#optionGrpTitle").html("");
              var attrScope = agrid.getScope('prodOptionValCtrl');
              attrScope._gridDataInit();   // 그리드 초기화
              return false;
            }
          }
        }
      }

      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }

      // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/prodOption/prodOption/saveProdOptionGroup.sb', params, function () {
        // 저장 후 재조회
        $scope._broadcast('prodOptionCtrl');
        $("#optionGrpTitle").html("");
        var attrScope = agrid.getScope('prodOptionValCtrl');
        attrScope._gridDataInit();   // 그리드 초기화
      });
    });
  };

  // 저장
  $scope.save = function() {
    $scope._popConfirm(messages["cmm.choo.save"], function() {
      $scope.flex.collectionView.commitEdit();

      // 파라미터 설정
      var params = [];

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if ((orgnFg == "HQ") || (orgnFg == "STORE" && $scope.flex.collectionView.itemsEdited[u].optionGrpCd > 7999)){
          if($scope.flex.collectionView.itemsEdited[u].optionGrpNm == ""){
            $scope._popMsg(messages["prodOption.optionGrpNm"] + messages["sideMenu.selectMenu.inputEnv"]);
            return false;
          }
          if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].optionGrpNm)){
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
          } else {
            $scope._popMsg(messages["cmm.max50Chk"]);
            return false;
          }
        }
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].optionGrpNm == ""){
          $scope._popMsg(messages["prodOption.optionGrpNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].optionGrpNm)){
          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/prodOption/prodOption/saveProdOptionGroup.sb', params, function () {
        // 저장 후 재조회
        $scope._broadcast('prodOptionCtrl');
        $("#optionGrpTitle").html("");
        var attrScope = agrid.getScope('prodOptionValCtrl');
        attrScope._gridDataInit();   // 그리드 초기화
      });
    });
  }

  // 길이체크
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
      if(strLength > 50){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };
}]);

/**
 * 옵션 속성 그리드 생성
 */
app.controller('prodOptionValCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodOptionValCtrl', $scope, $http, false));

  $scope.optionGrpCd;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 속성 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'optionValCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
  };

  // 속성 그리드 조회
  $scope.$on('prodOptionValCtrl', function(event, data) {
    // 파라미터
    var params = {};
    $scope.optionGrpCd = data;
    params.optionGrpCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/prodOption/prodOption/getProdOptionVal.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 속성 그리드 행 추가
  $scope.addRow = function() {
    if ($("#optionGrpTitle").html() == ""){
      $scope._popMsg(messages["prodOption.grp.null"]);
      return false;
    }
    console.log($scope.optionGrpCd);
    // 파라미터 설정
    var params = {};
    params.optionGrpCd = $scope.optionGrpCd;
    params.status = 'I';
    params.gChk = true;
    params.optionValCd = '자동채번';
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 속성 그리드 행 삭제
  $scope.del = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          $scope.flex.collectionView.removeAt(i);
        }
      }
      var params = [];

      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }

      // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/prodOption/prodOption/saveProdOptionVal.sb', params, function() {
        // 삭제 후 재조회
        $scope._broadcast('prodOptionValCtrl', $scope.optionGrpCd);
        $scope._broadcast('prodOptionCtrl');
      });
    });
  };

  // 저장
  $scope.save = function() {
    $scope._popConfirm(messages["cmm.choo.save"], function() {
      $scope.flex.collectionView.commitEdit();

      // 파라미터 설정
      var params = [];

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if($scope.flex.collectionView.itemsEdited[u].optionValNm == ""){
          $scope._popMsg(messages["prodOption.optionValNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].optionValNm)){
          $scope.flex.collectionView.itemsEdited[u].status = 'U';
          params.push($scope.flex.collectionView.itemsEdited[u]);
        } else {
          $scope._popMsg(messages["cmm.max100Chk"]);
          return false;
        }
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].optionValNm == ""){
          $scope._popMsg(messages["prodOption.optionValNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].optionValNm)){
          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max100Chk"]);
          return false;
        }

      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/prodOption/prodOption/saveProdOptionVal.sb', params, function() {
        // 저장 후 재조회
        $scope._broadcast('prodOptionValCtrl', $scope.optionGrpCd);
        $scope._broadcast('prodOptionCtrl');
      });
    });
  };

  // MAX값 따지는 함수 50byte
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
      if(strLength > 100){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };

}]);
