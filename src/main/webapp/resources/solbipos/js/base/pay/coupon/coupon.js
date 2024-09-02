/****************************************************************
 *
 * 파일명 : coupon.js
 * 설  명 : 쿠폰등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
var selectedCouponClass;
var selectedCoupon;

// 쿠폰대상구분 코드
var coupnTargetFg = [
  {"name":"전체","value":"A"},
  {"name":"제외","value":"E"},
  {"name":"포함","value":"I"}
];

// 쿠폰적용품목구분 코드
var coupnProdFg = [
  {"name":"전체상품적용","value":"0"},
  {"name":"단일상품적용","value":"1"}
];

/**
 *  쿠폰분류등록 그리드 생성
 */
app.controller('couponClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('couponClassCtrl', $scope, $http, true));

  if(coupnEnvstVal == '1') { // 매장통제 본사에서는 버튼X
    $scope.userUseYn = true;
  } else { // 본사통제는 버튼O
    if(gvStoreCd === '' ) $scope.userUseYn = true;
  }

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.coupnDcFgDataMap = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
    $scope.coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');
    $scope.coupnTargetFgDataMap = new wijmo.grid.DataMap(coupnTargetFg, 'value', 'name');
    $scope.coupnProdFgDataMap = new wijmo.grid.DataMap(coupnProdFg, 'value', 'name');
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
        selectedCouponClass = selectedRow;

        if ( col.binding === "payClassCd" && selectedRow.status !== "I") {
          var params = {};
          params.payClassNm   = $scope.payClassNm;
          params.coupnNm      = $scope.coupnNm;
          params.storeCd      = $scope.storeCd;
          params.storeNm      = $scope.storeNm;
          params.prodCd       = $scope.prodCd;
          params.prodNm       = $scope.prodNm;
          if(selectedRow.status === "I") {
            e.cancel = false;
          } else if(selectedRow.useYn === "N") {
            $("#couponSubTitle").html(" [" + selectedRow.payClassCd+ "]" + selectedRow.payClassNm + "<label style='color: red'>(미사용)</label>");
            // $scope._pageView('couponCtrl', 1);
            $scope._broadcast('couponCtrl', params);
            return false;
          } else {
            $("#couponSubTitle").text(" [" + selectedRow.payClassCd+ "]" + selectedRow.payClassNm );
            // $scope._pageView('couponCtrl', 1);
            $scope._broadcast('couponCtrl', params);
          }
        }
      }
    });

    // 쿠폰 분류 그리드 분류코드 reod only 풀리는 오류 수정
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "payClassCd" ) {
        e.cancel = true;
      }
    });

    // 쿠폰 분류 그리드 조회 후에 dropdown readonly 되는 것 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "serNoYn" || col.binding === "useYn" || col.binding === "mappingCode" ) {
          e.cancel = false;
      }
    });

    // 조회 이벤트 발생
    // setTimeout(function() {
    //   $scope._broadcast('couponClassCtrl', true);
    // }, 100)
  };

  $scope.$on("couponClassCtrl", function(event, data) {
    $scope.searchCouponClass();
    event.preventDefault();
  });

  // 쿠폰 분류 그리드 조회
  $scope.searchCouponClass = function(){
    // 파라미터
    var params = {};
    params.coupnEnvstVal  = coupnEnvstVal;
    params.payClassNm     = $scope.payClassNm;
    params.coupnNm        = $scope.coupnNm;
    params.storeCd        = $scope.storeCd;
    params.storeNm        = $scope.storeNm;
    params.prodCd         = $scope.prodCd;
    params.prodNm         = $scope.prodNm;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getCouponClassList.sb", params, function() {

      var grid = wijmo.Control.getControl("#wjGridCouponClass");
      var rows = grid.rows;

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];

        if(orgnFg === "STORE" && item.payClassCd < '800'){
          item.gChk = false;
          rows[i].isReadOnly = true;
        }
      }

      $("#couponSubTitle").text(" []");
      selectedCouponClass = null;
      selectedCoupon = null;
      var couponScope = agrid.getScope('couponCtrl');
      couponScope.$apply(function(){
        couponScope._gridDataInit();
      });

      $('#btnCouponSeqChgSave').hide();
      $('#btnCouponSeqChgStoreApply').hide();
    }, false);
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

  // 쿠폰 분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.payClassCd="자동채번";
    params.serNoYn = "Y";
    params.useYn = "Y";
    params.mappingCode = "0601";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 쿠폰분류 그리드 행 삭제
  $scope.del = function(){

    var params = new Array();

    $scope._popConfirm(messages["coupon.exists.coupon"], function (){
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          $scope.flex.collectionView.items[i].status = "D";
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save(baseUrl + "class/saveCouponClassList.sb", params, function(){ $scope.allSearch() });
    });
  };

  // 쿠폰분류 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    var orgChk = 0;
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].payClassNm == undefined || $scope.flex.collectionView.itemsEdited[i].payClassNm.length == 0){
        $scope._popMsg(messages["coupon.require.payClassNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsEdited[i].payClassNm)){
        if ((orgnFg != null && orgnFg == "HQ") || ((orgnFg != null && orgnFg == "STORE") && $scope.flex.collectionView.itemsEdited[i].payClassCd > 799)) { //본사이거나 매장일때는 권종구분코드가 799이상인거만 수정가능
          $scope.flex.collectionView.itemsEdited[i].status = "U";
          $scope.flex.collectionView.itemsEdited[i].coupnEnvstVal = coupnEnvstVal;
          params.push($scope.flex.collectionView.itemsEdited[i]);
        } else if ((orgnFg != null && orgnFg == "STORE") && $scope.flex.collectionView.itemsEdited[i].payClassCd <= 799 && orgChk == 0){
          orgChk = 1;
        }
      } else {
        $scope._popMsg(messages["coupon.payClassNm"] + " " + messages["coupon.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].payClassNm == undefined || $scope.flex.collectionView.itemsAdded[i].payClassNm.length == 0){
        $scope._popMsg(messages["coupon.require.payClassNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].payClassNm)) {
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        $scope.flex.collectionView.itemsAdded[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.itemsAdded[i]);
      } else {
        $scope._popMsg(messages["coupon.payClassNm"] + " " + messages["coupon.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      $scope.flex.collectionView.itemsRemoved[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }
    if(orgChk==1){
      s_alert.pop(messages["coupon.payClassCd.edited"]);
      if (params.length == 0){
        return false;
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveCouponClassList.sb", params, function(){ $scope.allSearch() });
  };

  // 분류 매장 적용
  $scope.applyStore = function(){
    var params = new Array();

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.items[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/applyCouponClassList.sb", params, function(){ $scope.allSearch() })
  };

  // 쿠폰 삭제 완료 후처리 (쿠폰분류 재조회)
  $scope.allSearch = function () {
    $scope.searchCouponClass();
  };

}]);


/**
 * 쿠폰 그리드 생성
 */
app.controller('couponCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('couponCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다

  // 쿠폰 팝업 사용시 현재 페이지를 임시 갖고있다가 리프레쉬할때 해당 페이지로 보여줌
  $scope.couponGridCurr = 1;
  $scope.setCouponGridCurr = function(index){
    $scope.couponGridCurr = index;
  };
  $scope.getCouponGridCurr = function(){
    return $scope.couponGridCurr;
  };

  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.coupnDcFgDataMap = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
    $scope.coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');
    $scope.coupnTargetFgDataMap = new wijmo.grid.DataMap(coupnTargetFg, 'value', 'name');
    $scope.coupnProdFgDataMap = new wijmo.grid.DataMap(coupnProdFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "coupnCd") {
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        else if (col.binding === "prodCnt" || col.binding === "storeCnt" || col.binding === "prodClsCnt" || col.binding === "exceptProdCnt") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    // 쿠폰 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      var dataItem = s.rows[e.row].dataItem;

      // 쿠폰 코드는 입력 불가능
      if (col.binding === "coupnCd") {
        e.cancel = false;
      }
      // 데이터 조회 후 dropdown 이 readonly 되는 문제
      if (col.binding === "coupnDcFg" || col.binding === "coupnApplyFg" || col.binding === "coupnApplyFg") {
        e.cancel = false;
      }
      // 상품수량과 적용매장수는 수정할 수 없음.
      if (col.binding === "prodCnt" || col.binding === "storeCnt" ) {
        e.cancel = true;
      }
      // 할인구분 => 금액 관련이면, 할인율은 입력못함
      if(e.col === 6) {
        //if(dataItem.coupnDcFg === "3" || dataItem.coupnDcFg === "4" || dataItem.coupnDcFg === "6") {
        if(dataItem.coupnDcFg === "1" || dataItem.coupnDcFg === "3") {
          e.cancel = true;
        } else {
          e.cancel = false;
        }
      }
      // 할인구분 => % 관련이면, 할인금액 입력못함
      if(e.col === 7) {
        if(dataItem.coupnDcFg === "1" || dataItem.coupnDcFg === "2") {
          e.cancel = true;
        } else {
          e.cancel = false;
        }
      }
    });

    // 쿠폰 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        selectedCoupon = selectedRow;
        var params = {};
        params.coupnNm = $scope.coupnCoupnNm;
        params.storeCd = $scope.coupnStoreCd;
        params.storeNm = $scope.coupnStoreNm;
        params.prodCd  = $scope.coupnProdCd;
        params.prodNm  = $scope.coupnProdNm;

        if (col.binding === "prodCnt" && selectedRow.status !== "I") {

          $scope.setCouponGridCurr($scope._getPagingInfo('curr'));

          // 상품 등록 팝업
          $scope.couponProdLayer.show(true, function (s) {

            var regProdGrid = agrid.getScope('regProdCtrl');
            regProdGrid.$apply(function(){
              regProdGrid._gridDataInit();
              $("#srchApplyProdCd").val('');
              $("#srchApplyProdNm").val('');
            });
            var noRegProdGrid = agrid.getScope('noRegProdCtrl');
            noRegProdGrid.$apply(function(){
              noRegProdGrid._gridDataInit();
            });
            $scope._broadcast('couponCtrl', params);
          });
        }
        else if ( col.binding === "storeCnt" && selectedRow.status !== "I") {

          $scope.setCouponGridCurr($scope._getPagingInfo('curr'));

          // 매장 등록 팝업
          $scope.couponStoreLayer.show(true, function (s) {
            var regStoreGrid = agrid.getScope('regStoreCtrl');
            regStoreGrid.$apply(function(){
              regStoreGrid._gridDataInit();
              $("#srchCoupnStoreCd").val('');
              $("#srchCoupnStoreNm").val('');
            });
            var noRegStoreGrid = agrid.getScope('noRegStoreCtrl');
            noRegStoreGrid.$apply(function(){
              noRegStoreGrid._gridDataInit();
            });

            $scope._broadcast('couponCtrl', params);
          });

        }
        else if ( col.binding === "prodClsCnt" && selectedRow.status !== "I") {

          $scope.setCouponGridCurr($scope._getPagingInfo('curr'));

          // 소분류 등록 팝업
          $scope.couponProdClsLayer.show(true, function (s) {
            var regStoreClsGrid = agrid.getScope('regProdClsCtrl');
            regStoreClsGrid.$apply(function(){
              regStoreClsGrid._gridDataInit();
              $("#srchProdClsCd").val('');
              $("#srchProdClsNm").val('');
            });
            var noRegStoreClsGrid = agrid.getScope('noRegProdClsCtrl');
            noRegStoreClsGrid.$apply(function(){
              noRegStoreClsGrid._gridDataInit();
            });

            $scope._broadcast('couponCtrl', params);
          });

        }
        else if ( col.binding === "exceptProdCnt" && selectedRow.status !== "I") {

          $scope.setCouponGridCurr($scope._getPagingInfo('curr'));

          // 제외상품 등록 팝업
          $scope.couponExceptProdLayer.show(true, function (s) {
            var regExceptProdCtrl = agrid.getScope('regExceptProdCtrl');
            regExceptProdCtrl.$apply(function(){
              regExceptProdCtrl._gridDataInit();
              $("#srchExceptProdCd").val('');
              $("#srchExceptProdNm").val('');
            });
            var noRegExceptProdCtrl = agrid.getScope('noRegExceptProdCtrl');
            noRegExceptProdCtrl.$apply(function(){
              noRegExceptProdCtrl._gridDataInit();
            });

            $scope._broadcast('couponCtrl', params);
          });

        }

        if((col.binding === "prodCnt" || col.binding === "storeCnt" || col.binding === "prodClsCnt" || col.binding === "exceptProdCnt")
        && selectedRow.status !== "I")
        {
            setTimeout(function() {
                //alert("..[ht.row]:"+ht.row);
                //s.rows[ht.row].isSelected = true;
                s.select(ht.row, 0);
            }, 100)
            setTimeout(function() {
                //alert("..[ht.row]:"+ht.row);
                //s.rows[ht.row].isSelected = true;
                s.select(ht.row, 0);
            }, 500)
        }

      }
    });
  };

  $scope.$on("couponCtrl", function(event, data) {
    // if( !isEmptyObject(data) )  selectedCouponClass = data;
    if(data !== null && data !== "" && data !== undefined) {
      $scope.coupnCoupnNm = data.coupnNm;
      $scope.coupnStoreCd = data.storeCd;
      $scope.coupnStoreNm = data.storeNm;
      $scope.coupnProdCd = data.prodCd;
      $scope.coupnProdNm = data.prodNm;
    }
    $scope.searchCoupon(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 쿠폰 그리드 조회
  $scope.searchCoupon = function(data){
    var params = {};
    params.coupnEnvstVal = coupnEnvstVal;
    params.payClassCd = selectedCouponClass.payClassCd;
    if(data !== null && data !== "" && data !== undefined) {
      params.coupnNm      = data.coupnNm;
      params.storeCd      = data.storeCd;
      params.storeNm      = data.storeNm;
      params.prodCd       = data.prodCd;
      params.prodNm       = data.prodNm;
    }

    if((orgnFg != null && orgnFg == "STORE") && params.payClassCd <= 799) { // 본사에서 등록한 권종분류코드
      $('#btnCouponAdd').hide();
      $('#btnCouponDel').hide();
      $('#btnCouponSave').hide();
    } else {
      $('#btnCouponAdd').show();
      $('#btnCouponDel').show();
      $('#btnCouponSave').show();
    }

    // 프랜매장 && 본사쿠폰분류 && couponSeqChgVal=1
    if(hqOfficeCd !== "00000" && (orgnFg != null && orgnFg == "STORE") && params.payClassCd <= 799 && couponSeqChgVal === "1") {
      $('#btnCouponSeqChgSave').show();
    } else {
      $('#btnCouponSeqChgSave').hide();
    }

    // 본사 && couponSeqChgVal=1
    if((orgnFg != null && orgnFg == "HQ") && couponSeqChgVal === "1") {
      $('#btnCouponSeqChgStoreApply').show();
    } else {
      $('#btnCouponSeqChgStoreApply').hide();
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getCouponList.sb", params, function(){
      selectedCoupon = null;
    }, false);
  };

  // 쿠폰 그리드 행 추가
  $scope.addRow = function() {
    if ($("#couponSubTitle").text().length <= 3) {
      $scope._popMsg(messages["coupon.null.payClassCd"]);
      return false;
    }

    var gridRepresent = agrid.getScope("couponClassCtrl");
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;

    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.coupnCd="자동채번";
    params.coupnDcFg = "1";
    params.coupnApplyFg = "1";
    params.coupnTargetFg = "A";
    params.coupnProdFg = "0";
    params.useYn = "Y";
    params.prodCnt = "0";
    params.storeCnt = "0";
    params.payClassCd = selectedRow.payClassCd;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 쿠폰 그리드 행 삭제
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
    $scope._save(baseUrl + "class/saveCouponList.sb", params, function(){ $scope.searchCoupon(); });
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


  // 쿠폰 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();

    // dispSeq 재설정
    for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
      if ($scope.flex.collectionView.items[s].dispSeq !== num) {
          $scope.flex.collectionView.items[s].dispSeq = num;
          $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
          $scope.flex.collectionView.items[s].status = "U";
          $scope.flex.collectionView.commitEdit();
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      if($scope.flex.collectionView.itemsEdited[i].coupnNm == undefined || $scope.flex.collectionView.itemsEdited[i].coupnNm.length == 0){
        $scope._popMsg(messages["coupon.require.coupnNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsEdited[i].coupnNm)) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        $scope.flex.collectionView.itemsEdited[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.itemsEdited[i]);
      } else {
        $scope._popMsg(messages["coupon.coupnNm"] + " " + messages["coupon.maxChk"]);
        return false;
      }
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      if($scope.flex.collectionView.itemsAdded[i].coupnNm == undefined || $scope.flex.collectionView.itemsAdded[i].coupnNm.length == 0){
        $scope._popMsg(messages["coupon.require.coupnNm"]);
        return false;
      }
      if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].coupnNm)) {
        $scope.flex.collectionView.itemsAdded[i].status = "I";
        $scope.flex.collectionView.itemsAdded[i].coupnEnvstVal = coupnEnvstVal;
        params.push($scope.flex.collectionView.itemsAdded[i]);
      } else {
        $scope._popMsg(messages["coupon.coupnNm"] + " " + messages["coupon.maxChk"]);
        return false;
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      $scope.flex.collectionView.itemsRemoved[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveCouponList.sb", params, function(){ $scope.searchCoupon(); });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 적용 상품 팝업 핸들러 추가
    $scope.couponProdLayer.shown.addHandler(function (s) {
      $("#couponProdTitle").text('[' + selectedCouponClass.payClassCd + '] '
        + selectedCouponClass.payClassNm + ' > [' + selectedCoupon.coupnCd + '] ' + selectedCoupon.coupnNm);
    });

    // 적용 매장 팝업 핸들러 추가
    $scope.couponStoreLayer.shown.addHandler(function (s) {
      $("#couponStoreTitle").text('[' + selectedCouponClass.payClassCd + '] '
        + selectedCouponClass.payClassNm + ' > [' + selectedCoupon.coupnCd + '] ' + selectedCoupon.coupnNm);
    });

    // 적용대상소분류 팝업 핸들러 추가
    $scope.couponProdClsLayer.shown.addHandler(function (s) {
      $("#couponProdClsTitle").text('[' + selectedCouponClass.payClassCd + '] '
          + selectedCouponClass.payClassNm + ' > [' + selectedCoupon.coupnCd + '] ' + selectedCoupon.coupnNm);
    });

    // 제외 상품 팝업 핸들러 추가
    $scope.couponExceptProdLayer.shown.addHandler(function (s) {
      $("#couponExceptProdTitle").text('[' + selectedCouponClass.payClassCd + '] '
          + selectedCouponClass.payClassNm + ' > [' + selectedCoupon.coupnCd + '] ' + selectedCoupon.coupnNm);
    });

    // 쿠폰순서 매장적용 팝업 핸들러 추가
    $scope.wjCouponSeqChgRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
          $scope._broadcast('couponSeqChgStoreRegistCtrl', selectedCouponClass.payClassCd);
      }, 50)
    });
  });

  $scope.coupnDcFgEdit = function (s, e) {
    if(s.columns[e.col].binding === "coupnDcFg") {
      // 할인구분 => 금액 관련이면, 할인율은 입력못함
      if(s.rows[e.row].dataItem.coupnDcFg == "3") {
        s.rows[e.row].dataItem.coupnDcRate = "0";
      }
      // 할인구분 => % 관련이면, 할인금액 입력못함
      else if(s.rows[e.row].dataItem.coupnDcFg == "2") {
        s.rows[e.row].dataItem.coupnDcAmt = "0";
      } else if(s.rows[e.row].dataItem.coupnDcFg == "1") {
        s.rows[e.row].dataItem.coupnDcRate = "0";
        s.rows[e.row].dataItem.coupnDcAmt = "0";
      }
      s.refresh();
    }
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

  // 순서저장
  $scope.couponSeqChgSave = function() {
    // 파라미터 설정
    var params = new Array();

    // dispSeq 재설정
    for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
      if ($scope.flex.collectionView.items[s].dispSeq !== num) {
        $scope.flex.collectionView.items[s].dispSeq = num;
        $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
        $scope.flex.collectionView.items[s].status = "U";
        $scope.flex.collectionView.commitEdit();
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/getCouponSeqChgSave.sb", params, function(){ $scope.searchCoupon(); });
  };

  // 쿠폰순서 매장적용
  $scope.couponSeqChgStoreApply = function() {
    $scope.wjCouponSeqChgRegistLayer.show(true);
    event.preventDefault();
  };

}]);


// 탭 클릭
$("#couponStoreTab").click(function(){
  location.href = "/base/pay/coupon/store/couponStoreView.sb";
});
