/****************************************************************
 *
 * 파일명 : sideMenuAttr.js
 * 설  명 : 사이드메뉴>속성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/

/**
 * 사이드메뉴 속성분류 그리드 생성
 */
app.controller('sideMenuAttrClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuAttrClassCtrl', $scope, $http, false));

    // 상품 본사통제구분 (H : 본사, S: 매장)
    // $scope.prodEnvstVal = prodEnvstVal;

    // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
    // $scope.userStoreCd = gvStoreCd;
    // $scope.btnShowFg = false;

    // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
    //     || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    //     $scope.btnShowFg = true;
    // }

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdattrClassCd') {
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
      if (col.binding === 'sdattrClassCd') {
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
        if ( col.binding === 'sdattrClassCd' && selectedRow.status !== 'I') {
          $("#sideMenuAttrTitle").html(" [" + selectedRow.sdattrClassCd+ "]" + selectedRow.sdattrClassNm );
          if (hqOfficeCd != '00000' && orgnFg == 'STORE' && selectedRow.sdattrClassCd <= 79999) {
            $("#btnUpAttr").hide();
            $("#btnDownAttr").hide();
            $("#btnAddAttr").hide();
            $("#btnDelAttr").hide();
            $("#btnSaveAttr").hide();
          } else {
            $("#btnUpAttr").show();
            $("#btnDownAttr").show();
            $("#btnAddAttr").show();
            $("#btnDelAttr").show();
            $("#btnSaveAttr").show();
          }
          $scope._broadcast('sideMenuAttrAttrCtrl', selectedRow.sdattrClassCd);
        }
      }
    });
    $scope._broadcast('sideMenuAttrClassCtrl');
  };
  // 속성분류 그리드 조회
  $scope.$on('sideMenuAttrClassCtrl', function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/attrClass/list.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 속성분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = 'I';
    params.gChk = true;
    params.sdattrClassCd = '자동채번';
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 속성분류 그리드 행 삭제
  $scope.deleteRow = function() {

    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i--){
        var item = $scope.flex.collectionView.items[i];
        if(item.gChk) {
          if ((orgnFg == "HQ") || (orgnFg == "STORE" && hqOfficeCd == "00000") || (orgnFg == "STORE" && hqOfficeCd !="00000" && item.sdattrClassCd > 79999)){
            if(item.cnt == 0){
              $scope.flex.collectionView.removeAt(i);
            } else {
              $scope._popMsg(messages["sideMenu.attr.class.notNull"]);
              return false;
            }
          } else  {
            $scope._popMsg(messages["sideMenu.selectMenu.edited"]);
            $scope._broadcast('sideMenuAttrClassCtrl');
            return false;
          }
        }
      }

      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }

      // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/attrClass/save.sb', params, function () {
        // 저장 후 재조회
        $scope._broadcast('sideMenuAttrClassCtrl');
        $("#sideMenuAttrTitle").html("");
        var attrScope = agrid.getScope('sideMenuAttrAttrCtrl');
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
      var orgChk = 0;

      // dispSeq 재설정
      var editItems = [];
      for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
        if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
          editItems.push($scope.flex.collectionView.items[s]);
        }
      }

      for (var s = 0; s < editItems.length; s++) {
        editItems[s].dispSeq = (s + 1);
        console.log(editItems);
        $scope.flex.collectionView.editItem(editItems[s]);
        $scope.flex.collectionView.commitEdit();
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if ((orgnFg == "HQ") || (orgnFg == "STORE" && hqOfficeCd =="00000") || (orgnFg == "STORE" && hqOfficeCd !="00000" && $scope.flex.collectionView.itemsEdited[u].sdattrClassCd > 79999)){
          if($scope.flex.collectionView.itemsEdited[u].sdattrClassNm == ""){
            $scope._popMsg(messages["sideMenu.attr.sdattrClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
            return false;
          }
          if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdattrClassNm)){
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
          } else {
            $scope._popMsg(messages["cmm.max50Chk"]);
            return false;
          }
        } else if(orgChk == 0) {
          orgChk = 1;
        }
      }
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].sdattrClassNm == ""){
          $scope._popMsg(messages["sideMenu.attr.sdattrClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdattrClassNm)){
          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      if (orgChk) {
        $scope._popMsg(messages["sideMenu.selectMenu.edited"]);
        orgChk = 0;
        return false;
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/attrClass/save.sb', params, function () {
        // 저장 후 재조회
        $scope._broadcast('sideMenuAttrClassCtrl');
        $("#sideMenuAttrTitle").html("");
        var attrScope = agrid.getScope('sideMenuAttrAttrCtrl');
        attrScope._gridDataInit();   // 그리드 초기화
      });
    });
  }

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
 * 사이드메뉴 속성 그리드 생성
 */
app.controller('sideMenuAttrAttrCtrl', ['$scope', '$http', 'sdattrClassCd', function ($scope, $http, sdattrClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuAttrAttrCtrl', $scope, $http, false));

    // 상품 본사통제구분 (H : 본사, S: 매장)
    // $scope.prodEnvstVal = prodEnvstVal;

    // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
    // $scope.userStoreCd = gvStoreCd;
    // $scope.btnShowFg = false;

    // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
    //     || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    //     $scope.btnShowFg = true;
    // }

  // sdattrClassCd Data Setter
  $scope.setSdattrClassCd = function (value) {
    sdattrClassCd.set(value);
  };
  // sdattrClassCd Data Getter
  $scope.getSdattrClassCd = function () {
    return sdattrClassCd.get();
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 속성 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdattrCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
  };
  // 속성 그리드 조회
  $scope.$on('sideMenuAttrAttrCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdattrClassCd(data);
    // 파라미터
    var params = {};
    params.sdattrClassCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/attrCd/list.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 속성 그리드 행 추가
  $scope.addRow = function() {
    if ($("#sideMenuAttrTitle").html() == ""){
      $scope._popMsg(messages["sideMenu.attr.class.null"]);
      return false;
    }
    // 파라미터 설정
    var params = {};
    params.sdattrClassCd = $scope.getSdattrClassCd();
    params.status = 'I';
    params.gChk = true;
    params.sdattrCd = '자동채번';
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 속성 그리드 행 삭제
  $scope.deleteRow = function() {
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
      $scope._save('/base/prod/sideMenu/attrCd/save.sb', params, function() {
        // 삭제 후 재조회
        $scope._broadcast('sideMenuAttrAttrCtrl', $scope.getSdattrClassCd());
        $scope._broadcast('sideMenuAttrClassCtrl');
      });
    });
  };

  // 저장
  $scope.save = function() {
    $scope._popConfirm(messages["cmm.choo.save"], function() {
      $scope.flex.collectionView.commitEdit();

      // 파라미터 설정
      var params = [];

      // dispSeq 재설정
      var editItems = [];
      for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
        if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
          editItems.push($scope.flex.collectionView.items[s]);
        }
      }

      for (var s = 0; s < editItems.length; s++) {
        editItems[s].dispSeq = (s + 1);
        console.log(editItems);
        $scope.flex.collectionView.editItem(editItems[s]);
        editItems[s].status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if($scope.flex.collectionView.itemsEdited[u].sdattrNm == ""){
          $scope._popMsg(messages["sideMenu.attr.sdattrNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdattrNm)){
          $scope.flex.collectionView.itemsEdited[u].status = 'U';
          params.push($scope.flex.collectionView.itemsEdited[u]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].sdattrNm == ""){
          $scope._popMsg(messages["sideMenu.attr.sdattrNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdattrNm)){
          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }

      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/attrCd/save.sb', params, function() {
        // 저장 후 재조회
        $scope._broadcast('sideMenuAttrAttrCtrl', $scope.getSdattrClassCd());
        $scope._broadcast('sideMenuAttrClassCtrl');
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
      if(strLength > 50){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };

  // 위로 옮기기 버튼
  $scope.rowMoveUp = function() {
    var movedRows = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (i > 0 && item.gChk) {
        if (!$scope.flex.collectionView.items[i - 1].gChk) {
          movedRows = i - 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
  // 아래로 옮기기 버튼
  $scope.rowMoveDown = function() {
    var movedRows = 0;
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      var item = $scope.flex.collectionView.items[i];
      if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
        if (!$scope.flex.collectionView.items[i + 1].gChk) {
          movedRows = i + 1;
          var tmpItem = $scope.flex.collectionView.items[movedRows];
          $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
          $scope.flex.collectionView.items[i] = tmpItem;
          $scope.flex.collectionView.commitEdit();
          $scope.flex.collectionView.refresh();
        }
      }
    }
    $scope.flex.select(movedRows, 1);
  };
}]).factory('sdattrClassCd', function () {
  // 사이드메뉴 속성 그리드 의 변수 값 영역
  var sdattrClassCd = {};
  sdattrClassCd.set = function (value) {
    sdattrClassCd.value = value;
  };
  sdattrClassCd.get = function () {
    return sdattrClassCd.value;
  };
  return sdattrClassCd;
});
