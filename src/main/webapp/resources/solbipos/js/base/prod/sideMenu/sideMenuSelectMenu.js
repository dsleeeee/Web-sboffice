/****************************************************************
 *
 * 파일명 : sideMenuSelectMenu.js
 * 설  명 : 사이드메뉴>선택메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/

/**
 * 사이드메뉴 선택그룹 그리드 생성
 */
app.controller('sideMenuSelectGroupCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectGroupCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  $scope.prodEnvstVal = prodEnvstVal;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  $scope.userStoreCd = gvStoreCd;
  $scope.btnShowFg = false;

  if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
      || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    $scope.btnShowFg = true;
  }


  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });

  $scope.selectedSdselGrpCd;
  $scope.setSelectedSdselGrpCd = function(sdselGrpCd) {
    $scope.selectedSdselGrpCd = sdselGrpCd;
  };
  $scope.getSelectedSdselGrpCd = function() {
    return $scope.selectedSdselGrpCd;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdselGrpCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });
    // 선택그룹 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdselGrpCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    // 선택그룹 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'sdselGrpCd' && selectedRow.status !== 'I') {
          $("#sideSelectGroupTitle").html(" [" + selectedRow.sdselGrpCd+ "]" + selectedRow.sdselGrpNm );
          $("#sideClassTitle").html("");
          if (hqOfficeCd != '00000' && orgnFg == 'STORE' && selectedRow.sdselGrpCd <= 799999) {
            $("#btnUpSelClass").hide();
            $("#btnDownSelClass").hide();
            $("#btnAddSelClass").hide();
            $("#btnDelSelClass").hide();
            $("#btnSaveSelClass").hide();

            $("#btnUpSelProd").hide();
            $("#btnDownSelProd").hide();
            $("#btnAddSelProd").hide();
            $("#btnDelSelProd").hide();
            $("#btnSaveSelProd").hide();
          } else {
            $("#btnUpSelClass").show();
            $("#btnDownSelClass").show();
            $("#btnAddSelClass").show();
            $("#btnDelSelClass").show();
            $("#btnSaveSelClass").show();

            $("#btnUpSelProd").show();
            $("#btnDownSelProd").show();
            $("#btnAddSelProd").show();
            $("#btnDelSelProd").show();
            $("#btnSaveSelProd").show();
          }
          $scope.setSelectedSdselGrpCd(selectedRow.sdselGrpCd);
          $scope._broadcast('sideMenuSelectClassCtrl', selectedRow.sdselGrpCd);
          var prodGrid = agrid.getScope('sideMenuSelectProdCtrl');
          prodGrid._gridDataInit();
        }
      }
    });
  };
  // 선택그룹 그리드 조회
  $scope.$on('sideMenuSelectGroupCtrl', function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuGrp/list.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택그룹 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = 'I';
    params.gChk = true;
    params.sdselGrpCd = '자동채번';
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 선택그룹 그리드 행 삭제
  $scope.deleteRow = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          if((orgnFg != null && orgnFg == "HQ") || hqOfficeCd == '00000' || ((orgnFg != null && orgnFg == "STORE") && item.sdselGrpCd > 799999)) {
            if (item.cnt == 0) {
              $scope.flex.collectionView.removeAt(i);
            } else {
              $scope._popMsg("선택분류가 등록된 선택그룹은 삭제할 수 없습니다. ");
              return false;
            }
          } else  {
            $scope._popMsg("본사에서 등록한 속성은 변경 할 수 없습니다");
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
      $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
        $("#sideSelectGroupTitle").html("");
        var attrScope = agrid.getScope('sideMenuSelectClassCtrl');
        attrScope._gridDataInit();   // 그리드 초기화

        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
        // 저장 후 그리드 재조회
        $scope._broadcast("sideMenuSelectGroupCtrl");
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
        if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
          editItems.push($scope.flex.collectionView.items[s]);
        }
      }

      for (var s = 0; s < editItems.length; s++) {
        editItems[s].dispSeq = (s + 1);
        $scope.flex.collectionView.editItem(editItems[s]);
        $scope.flex.collectionView.commitEdit();
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if ((orgnFg != null && orgnFg == "HQ") || ((orgnFg != null && orgnFg == "STORE") && $scope.flex.collectionView.itemsEdited[u].sdselGrpCd > 799999)) {
          if($scope.flex.collectionView.itemsEdited[u].sdselGrpNm == ""){
            $scope._popMsg(messages["sideMenu.selectMenu.sdselGrpNm"] + messages["sideMenu.selectMenu.inputEnv"]);
            return false;
          }
          if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdselGrpNm)) {
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
        if($scope.flex.collectionView.itemsAdded[i].sdselGrpNm == ""){
          $scope._popMsg(messages["sideMenu.selectMenu.sdselGrpCd"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdselGrpNm)) {
          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      if (orgChk) {
        $scope._popMsg("본사에서 등록한 속성은 변경 할 수 없습니다");
        orgChk = 0;
        return false;
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
        $("#sideSelectGroupTitle").html("");
        var attrScope = agrid.getScope('sideMenuSelectClassCtrl');
        attrScope._gridDataInit();   // 그리드 초기화

        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
        // 저장 후 그리드 재조회
        $scope._broadcast("sideMenuSelectGroupCtrl");
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
 * 사이드메뉴 선택분류 그리드 생성
 */
app.controller('sideMenuSelectClassCtrl', ['$scope', '$http', 'sdselGrpCd', function ($scope, $http, sdselGrpCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectClassCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  $scope.prodEnvstVal = prodEnvstVal;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  $scope.userStoreCd = gvStoreCd;
  $scope.btnShowFg = false;

  if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
      || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    $scope.btnShowFg = true;
  }

  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });
  // sdselGrpCd Data Setter
  $scope.setSdselGrpCd = function (value) {
    sdselGrpCd.set(value);
  };
  // sdselGrpCd Data Getter
  $scope.getSdselGrpCd = function () {
    return sdselGrpCd.get();
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === 'sdselClassCd') {
          var item = s.rows[e.row].dataItem;
          if (item.status !== 'I') {
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });
    // 선택분류 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'sdselClassCd') {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
          e.cancel = true;
        }
      }
    });
    // 선택분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === 'sdselClassCd' && selectedRow.status !== 'I') {
          $("#sideClassTitle").html(" [" + selectedRow.sdselClassCd+ "]" + selectedRow.sdselClassNm );
          $scope._broadcast('sideMenuSelectProdCtrl', selectedRow.sdselClassCd);
        }
      }
    });
  };
  // 선택분류 그리드 조회
  $scope.$on('sideMenuSelectClassCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselGrpCd(data);

    // 파라미터
    var params = {};
    params.sdselGrpCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuClass/list.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 선택분류 그리드 행 추가
  $scope.addRow = function() {
    if($("#sideSelectGroupTitle").html() == ""){
      $scope._popMsg("선택그룹을 선택해주세요.");
      return false;
    }
    // 파라미터 설정
    var params = {};
    params.sdselGrpCd = $scope.getSdselGrpCd();
    params.sdselQty = '0';
    params.status = 'I';
    params.gChk = true;
    params.sdselClassCd = '자동채번';
    // 추가기능 수행 : 파라미터
    $scope._addRow(params, 2);
  };

  // 선택분류 그리드 행 삭제
  $scope.deleteRow = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          if (item.cnt == 0) {
            $scope.flex.collectionView.removeAt(i);
          } else {
            $scope._popMsg("선택분류가 등록된 선택그룹은 삭제할 수 없습니다. ");
            return false;
          }
        }
      }
      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }
      $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {
        $scope._broadcast("sideMenuSelectGroupCtrl");
        $scope._broadcast('sideMenuSelectClassCtrl', $scope.getSdselGrpCd());
        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
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
        if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
          editItems.push($scope.flex.collectionView.items[s]);
        }
      }

      for (var s = 0; s < editItems.length; s++) {
        editItems[s].dispSeq = (s + 1);
        $scope.flex.collectionView.editItem(editItems[s]);
        editItems[s].status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        if($scope.flex.collectionView.itemsEdited[u].sdselClassNm == ""){
          $scope._popMsg(messages["sideMenu.selectMenu.sdselClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdselClassNm)){
          $scope.flex.collectionView.itemsEdited[u].status = 'U';
          params.push($scope.flex.collectionView.itemsEdited[u]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        if($scope.flex.collectionView.itemsAdded[i].sdselClassNm == ""){
          $scope._popMsg(messages["sideMenu.selectMenu.sdselClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
          return false;
        }
        if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdselClassNm)) {
          $scope.flex.collectionView.itemsAdded[i].status = 'I';
          params.push($scope.flex.collectionView.itemsAdded[i]);
        } else {
          $scope._popMsg(messages["cmm.max50Chk"]);
          return false;
        }
      }

      // console.log('2 params',params);

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {

        // // 선택분류가 없을 경우 선택그룹까지 재조회 해야한다.
        // if($scope.flex.collectionView.itemCount > 0){
        //   // 그리드 저장 후 재조회
        $scope._broadcast('sideMenuSelectClassCtrl', $scope.getSdselGrpCd());
        // $scope._broadcast("sideMenuSelectGroupCtrl");
        $("#sideClassTitle").html("");
        var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
          // $scope._broadcast("sideMenuSelectGroupCtrl");
        // } else {
        //   var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
        //   $scope._broadcast('sideMenuSelectGroupCtrl');
        //   $scope.$apply(function(){
        //     $scope._gridDataInit();
        //   });
        // }
      });
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
}]).factory('sdselGrpCd', function () {
  // 사이드메뉴 선택분류 그리드 의 변수 값 영역
  var sdselGrpCd = {};
  sdselGrpCd.set = function (value) {
    sdselGrpCd.value = value;
  };
  sdselGrpCd.get = function () {
    return sdselGrpCd.value;
  };
  return sdselGrpCd;
});

/**
 * 사이드메뉴 선택상품 그리드 생성
 */
app.controller('sideMenuSelectProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http, sdselClassCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectProdCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  $scope.prodEnvstVal = prodEnvstVal;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  $scope.userStoreCd = gvStoreCd;
  $scope.btnShowFg = false;

  if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
      || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    $scope.btnShowFg = true;
  }

  // 그리드 Refresh
  $scope.$on('selectMenuRefresh', function (event, data) {
    $scope.flex.refresh();
  });
  // sdselGrpCd Data Setter
  $scope.setSdselClassCd = function (value) {
    sdselClassCd.set(value);
  };
  // sdselGrpCd Data Getter
  $scope.getSdselClassCd = function () {
    return sdselClassCd.get();
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    // s.formatItem.addHandler(function (s, e) {
    //   if (e.panel === s.cells) {
    //     var col = s.columns[e.col];
    //     if (col.binding === 'prodCd') {
    //       var item = s.rows[e.row].dataItem;
    //       if (item.status !== 'I') {
    //         wijmo.addClass(e.cell, 'wijLink');
    //       }
    //     }
    //   }
    // });
    // 선택상품 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'prodCd' || col.binding === 'prodNm') {
        e.cancel = true;
      }
    });
    // 그리드 선택 이벤트
    // s.addEventListener(s.hostElement, 'mousedown', function(e) {
    //   var ht = s.hitTest(e);
    //   if (ht.cellType === wijmo.grid.CellType.Cell) {
    //     var col = ht.panel.columns[ht.col];
    //     var selectedRow = s.rows[ht.row].dataItem;
    //     // 상품코드/상품명 클릭시
    //     if (col.binding === 'prodCd') {
    //       $scope.selectProdView(false);
    //     }
    //   }
    // });
  };
  // 선택상품 그리드 조회
  $scope.$on('sideMenuSelectProdCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselClassCd(data);
    // 파라미터
    var params = {};
    params.sdselClassCd = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/sideMenu/menuProd/list.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 선택상품 그리드 행 추가
  $scope.addRow = function() {
    if($("#sideClassTitle").html() == ""){
      $scope._popMsg("선택분류를 선택해주세요.");
      return false;
    }
    $scope.selectProdView(true);
  };

  // 선택상품 그리드 행 삭제
  $scope.deleteRow = function() {
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = [];
      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          $scope.flex.collectionView.removeAt(i);
        }
      }
      for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
        $scope.flex.collectionView.itemsRemoved[d].status = 'D';
        params.push($scope.flex.collectionView.itemsRemoved[d]);
      }

      // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {
        $scope._broadcast('sideMenuSelectProdCtrl', $scope.getSdselClassCd());
        var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
        var sdselGrpCd = grpGrid.getSelectedSdselGrpCd();
        $scope._broadcast('sideMenuSelectClassCtrl', sdselGrpCd);
        // $scope._broadcast("sideMenuSelectGroupCtrl");
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
        if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
          editItems.push($scope.flex.collectionView.items[s]);
        }
      }

      for (var s = 0; s < editItems.length; s++) {
        editItems[s].dispSeq = (s + 1);
        $scope.flex.collectionView.editItem(editItems[s]);
        editItems[s].status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
        $scope.flex.collectionView.itemsEdited[u].status = 'U';
        params.push($scope.flex.collectionView.itemsEdited[u]);
      }
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        $scope.flex.collectionView.itemsAdded[i].status = 'I';
        params.push($scope.flex.collectionView.itemsAdded[i]);
      }

      for (var m = 0; m < params.length; m++) {
        if(params[m].status !== 'D') {
          //if(  params[m].addProdQty === null  || params[m].addProdQty === '' || params[m].addProdQty === 0 ) {
          if(  params[m].addProdQty === null  || params[m].addProdQty === '') {
            $scope._popMsg("상품 수량을 한 개 이상 입력해주세요.");
            return false;
          }
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {

        // // 선택상품이 없을 경우 선택분류까지 재조회 해야한다.
        // if($scope.flex.collectionView.itemCount > 0){
          // 그리드 저장 후 재조회
          $scope._broadcast('sideMenuSelectProdCtrl', $scope.getSdselClassCd());
          var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
          var sdselGrpCd = grpGrid.getSelectedSdselGrpCd();
          $scope._broadcast('sideMenuSelectClassCtrl', sdselGrpCd);
          // $scope._broadcast("sideMenuSelectGroupCtrl");
        // } else {
        //   var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
        //   var sdselGrpCd = grpGrid.getSelectedSdselGrpCd();
        //   $scope._broadcast('sideMenuSelectClassCtrl', sdselGrpCd);
        //   $scope.$apply(function(){
        //     $scope._gridDataInit();
        //
        //   });
        // }
      });
    });
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
  // 상품선택 팝업
  $scope.selectProdView = function(type) {
    var popUp = $scope.sideMenuProdLayer;
    setTimeout(function() {
      popUp.show(true, function (s) {
        // 수정 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          var scope = agrid.getScope('sideMenuProdCtrl', $scope.getSdselClassCd());
          for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            if (scope.flex.collectionView.items[i].gChk) {
              var prodCd = scope.flex.collectionView.items[i].prodCd;
              var prodNm = scope.flex.collectionView.items[i].prodNm;
              if ( type ) {
                // 행추가
                var params = {};
                params.sdselClassCd = $scope.getSdselClassCd();
                params.status = 'I';
                params.prodCd = prodCd;
                params.prodNm = prodNm;
                params.addProdUprc = 0;
                params.addProdQty = 1; // 기본으로 하나씩 들어가도록 // todo 추후 수정
                params.gChk = true;
                // 추가기능 수행 : 파라미터
                $scope._addRow(params);
              } else {
                var selectedRow = $scope.flex.selectedRows[0]._idx;
                $scope.flex.setCellData(selectedRow, 'prodCd', prodCd);
                $scope.flex.setCellData(selectedRow, 'prodNm', prodNm);
              }
            }
          }
        }
      });
    }, 50);
  };
  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 상품상세정보 팝업 핸들러 추가
    $scope.sideMenuProdLayer.shown.addHandler(function (s) {
      setTimeout(function () {
        $scope._broadcast('sideMenuProdCtrl', $scope.getSdselClassCd());
      }, 50);
    });
  });

}]).factory('sdselClassCd', function () {
  // 사이드메뉴 선택상품 그리드 의 변수 값 영역
  var sdselClassCd = {};
  sdselClassCd.set = function (value) {
    sdselClassCd.value = value;
  };
  sdselClassCd.get = function () {
    return sdselClassCd.value;
  };
  return sdselClassCd;
});
