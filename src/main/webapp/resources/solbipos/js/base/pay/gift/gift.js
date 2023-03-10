/****************************************************************
 *
 * 파일명 : gift.js
 * 설  명 : 상품권등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.18     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품권분류등록 그리드 생성
 */
app.controller('giftClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('giftClassCtrl', $scope, $http, true));

  //상품권은 프랜차이즈의 경우 무조건 본사에서 등록, 매장은 조회만 가능
  //         단독매장의 경우 무조건 매장에서 등록, 조회 가능
  $scope.userUseYn = true;
 /*
  if(gvHqOfficeCd === '00000') { // 단독매장
    $scope.userUseYn = true;
  } else { // 프랜차이즈는 본사만 추가 가능
    if(gvStoreCd === '' ) $scope.userUseYn = true;
  }
*/
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.giftDcFgDataMap = new wijmo.grid.DataMap(giftDcFg, 'value', 'name');
    $scope.giftApplyFgDataMap = new wijmo.grid.DataMap(giftApplyFg, 'value', 'name');
    $scope.mappingCdDataMap = new wijmo.grid.DataMap(mappingCd, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "payClassCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 쿠폰분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "payClassCd" && selectedRow.status !== "I") {
          if(selectedRow.status === "I") {
            e.cancel = false;
          } else if(selectedRow.useYn === "N") {
            $("#giftSubTitle").html(" [" + selectedRow.payClassCd+ "]" + selectedRow.payClassNm + "<label style='color: red'>(미사용)</label>");
            $("#hdPayClassCd").val(selectedRow.payClassCd);
            $scope._pageView('giftCtrl', 1);
          } else {
            $("#giftSubTitle").text(" [" + selectedRow.payClassCd+ "]" + selectedRow.payClassNm );
            $("#hdPayClassCd").val(selectedRow.payClassCd);
            $scope._pageView('giftCtrl', 1);
          }
        }
      }
    });

    // 상품권 분류 그리드 조회 후에 dropdown readonly 되는 것 방지
    // s.beginningEdit.addHandler(function (s, e) {
    //   var col = s.columns[e.col];
    //   if (col.binding === "serNoYn" || col.binding === "useYn") {
    //       e.cancel = false;
    //   }
    // });

    // 조회 이벤트 발생
    // setTimeout(function() {
    //   $scope._broadcast('giftClassCtrl', true);
    // }, 100)
  };

  $scope.$on("giftClassCtrl", function(event, data) {
    $scope.searchGiftClass();
    event.preventDefault();
  });

  // 상품권 분류 그리드 조회
  $scope.searchGiftClass = function(){
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getGiftClassList.sb", params, function() {

      var grid = wijmo.Control.getControl("#wjGridGiftClass");
      var rows = grid.rows;

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];

        if(orgnFg === "STORE" && item.payClassCd < '800'){
          item.gChk = false;
          rows[i].isReadOnly = true;
        }
      }

      $("#giftSubTitle").text(" []");
      var giftScope = agrid.getScope('giftCtrl');
      giftScope.$apply(function(){
        giftScope._gridDataInit();
      });
    }, false);
  };

  // 상품권 분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.payClassCd="자동채번"
    params.serNoYn = "N";
    params.useYn = "Y";
    params.mappingCode = "0801";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 상품권 분류 그리드 행 삭제
  $scope.del = function(){

    var params = new Array();

    $scope._popConfirm(messages["gift.exists.gift"], function (){
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          $scope.flex.collectionView.items[i].status = "D";
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save(baseUrl + "class/saveGiftClassList.sb", params, function(){ $scope.allSearch() });

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

  // 상품권분류 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    var orgChk = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++){
      if($scope.flex.collectionView.itemsEdited[i].payClassNm == undefined || $scope.flex.collectionView.itemsEdited[i].payClassNm.length == 0) {
        $scope._popMsg(messages["gift.require.payClassNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsEdited[i].payClassNm)) {
        if ((orgnFg != null && orgnFg == "HQ") || ((orgnFg != null && orgnFg == "STORE") && $scope.flex.collectionView.itemsEdited[i].payClassCd > 799)) { //본사이거나 매장일때는 권종구분코드가 799이상인거만 수정가능
          $scope.flex.collectionView.itemsEdited[i].status = "U";
          params.push($scope.flex.collectionView.itemsEdited[i]);
        } else if ((orgnFg != null && orgnFg == "STORE") && $scope.flex.collectionView.itemsEdited[i].payClassCd <= 799 && orgChk == 0){
          orgChk = 1;
        }
      } else {
        $scope._popMsg(messages["gift.payClassNm"] + " " + messages["gift.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].payClassNm == undefined || $scope.flex.collectionView.itemsAdded[i].payClassNm.length == 0) {
        $scope._popMsg(messages["gift.require.payClassNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].payClassNm)) {
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        params.push($scope.flex.collectionView.itemsAdded[i]);
      } else {
        $scope._popMsg(messages["gift.payClassNm"] + " " + messages["gift.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    if(orgChk==1){
      s_alert.pop(messages["gift.payClassCd.edited"]);
      if (params.length == 0){
        return false;
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveGiftClassList.sb", params, function(){ $scope.allSearch() });
  };

  // 상품권 삭제 완료 후처리 (상품권분류 재조회)
  $scope.allSearch = function () {
    $scope.searchGiftClass();
  };
}]);


/**
 * 상품권 그리드 생성
 */
app.controller('giftCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('giftCtrl', $scope, $http, false));

  //상품권은 프랜차이즈의 경우 무조건 본사에서 등록, 매장은 조회만 가능
  //         단독매장의 경우 무조건 매장에서 등록, 조회 가능
  $scope.userUseYn = true;
  /*
  if(gvHqOfficeCd === '00000') { // 단독매장
    $scope.userUseYn = true;
  } else { // 프랜차이즈는 본사만 추가 가능
    if(gvStoreCd === '' ) $scope.userUseYn = true;
  }
*/
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.giftDcFgDataMap = new wijmo.grid.DataMap(giftDcFg, 'value', 'name');
    $scope.giftApplyFgDataMap = new wijmo.grid.DataMap(giftApplyFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "giftCd") {
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        // else if (col.binding === "prodCnt" || col.binding === "storeCnt") {
        //   wijmo.addClass(e.cell, 'wijLink');
        // }
      }
    });
    // 상품권 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      var dataItem = s.rows[e.row].dataItem;

      // 상품권 코드는 입력 불가능
      if (col.binding === "giftCd") {
        e.cancel = false;
      }
      // 데이터 조회 후 dropdown 이 readonly 되는 문제
      // if (col.binding === "giftDcFg" || col.binding === "giftApplyFg" || col.binding === "useYn") {
      //   e.cancel = false;
      // }
      // 상품수량과 적용매장수는 수정할 수 없음.
      // if (col.binding === "prodCnt" || col.binding === "storeCnt" ) {
      //   e.cancel = true;
      // }
      // 할인구분 => 금액 관련이면, 할인율은 입력못함
      // if(e.col == 6) {
      //   if(dataItem.giftDcFg == "3" || dataItem.giftDcFg == "4" || dataItem.giftDcFg == "6") {
      //     e.cancel = true;
      //   } else {
      //     e.cancel = false;
      //   }
      // }
      // 할인구분 => % 관련이면, 할인금액 입력못함
      // if(e.col == 7) {
      //   if(dataItem.giftDcFg == "1" || dataItem.giftDcFg == "2") {
      //     e.cancel = true;
      //   } else {
      //     e.cancel = false;
      //   }
      // }
    });

    // 할인구분에 따라 %와 금액 초기화
    // s.cellEditEnded.addHandler(function (s, e) {
    //   var col = s.columns[e.col];
    //   var dataItem = s.rows[e.row].dataItem;
    //
    //   if(col.binding == "giftDcFg") {
    //     // 할인구분 => 금액 관련이면, 할인율은 입력못함
    //     if(dataItem.giftDcFg == "3" || dataItem.giftDcFg == "4" || dataItem.giftDcFg == "6") {
    //       dataItem.giftDcRate = "";
    //     }
    //     // 할인구분 => % 관련이면, 할인금액 입력못함
    //     else if(dataItem.giftDcFg == "1" || dataItem.giftDcFg == "2") {
    //       dataItem.giftDcAmt = "";
    //     }
    //   }
    // });

    // 상품권 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;

        // if ( col.binding === "prodCnt" && selectedRow.status != "I") {
        //   // 상품 등록 팝업
        //   var popup = $scope.giftPordLayer;
        //   popup.show(true, function (s) {
        //     var regProdGrid = agrid.getScope('regProdCtrl');
        //     regProdGrid._gridDataInit();
        //     var noRegProdGrid = agrid.getScope('noRegProdCtrl');
        //     noRegProdGrid._gridDataInit();
        //   });
        // }
        // else if ( col.binding === "storeCnt" && selectedRow.status != "I") {
        //   // 매장 등록 팝업
        //   var popup = $scope.giftStoreLayer;
        //   popup.show(true, function (s) {
        //     var regStoreGrid = agrid.getScope('regStoreCtrl');
        //     regStoreGrid._gridDataInit();
        //     var noRegStoreGrid = agrid.getScope('noRegStoreCtrl');
        //     noRegStoreGrid._gridDataInit();
        //   });
        // }
      }
    });
  };
  // 그리드 초기화
  $scope._gridDataInit = function () {
    $scope.data = new wijmo.collections.CollectionView([]);
  };

  $scope.$on("giftCtrl", function(event, data) {

    $scope.searchGift();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품권 그리드 조회
  $scope.searchGift = function(){
    var params = {};
    params.payClassCd = $("#hdPayClassCd").val();
    if((orgnFg != null && orgnFg == "STORE") && params.payClassCd <= 799) { // 본사에서 등록한 권종분류코드
      $('#btnGiftAdd').hide();
      $('#btnGiftDel').hide();
      $('#btnGiftSave').hide();
    } else {
      $('#btnGiftAdd').show();
      $('#btnGiftDel').show();
      $('#btnGiftSave').show();
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getGiftList.sb", params, function(){}, false);
  };

  // 상품권 그리드 행 추가
  $scope.addRow = function() {
    if ($("#giftSubTitle").text().length <= 3) {
      $scope._popMsg(messages["gift.null.payClassCd"]);
      return false;
    }

    var gridRepresent = agrid.getScope("giftClassCtrl");
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;

    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.giftCd="자동채번"
    params.giftUprc = 0;
    params.useYn = "Y";
    params.payClassCd = selectedRow.payClassCd;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 상품권 그리드 행 삭제
  $scope.del = function(){

    // 파라미터 설정
    var params = new Array();

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].status = "D";
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveGiftList.sb", params, function(){
      $scope.searchGift();
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

  // 상품권 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].giftNm == undefined || $scope.flex.collectionView.itemsEdited[i].giftNm.length == 0) {
        $scope._popMsg(messages["gift.require.giftNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsEdited[i].giftNm)) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      } else {
        $scope._popMsg(messages["gift.giftNm"] + " " + messages["gift.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].giftNm == undefined || $scope.flex.collectionView.itemsAdded[i].giftNm.length == 0) {
        $scope._popMsg(messages["gift.require.giftNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].giftNm)) {
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        params.push($scope.flex.collectionView.itemsAdded[i]);
      } else {
        $scope._popMsg(messages["gift.giftNm"] + " " + messages["gift.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveGiftList.sb", params, function(){
      $scope.searchGift();
    });
  }

}]);


