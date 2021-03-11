/****************************************************************
 *
 * 파일명 : prodKitchenprintLink.js
 * 설  명 : 상품-메장프린터연결 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.15     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnAllComboData = [
  {"name": "전체", "value": ""},
  {"name": "사용", "value": "Y"},
  {"name": "미사용", "value": "N"}
];

function prodNmChange(prodNm, prodCd){
  $("#prodNm").text(prodNm);
  $("#prodCd").text(prodCd);
}

/** 상품정보 그리드 생성 */
app.controller('prodKitchenprintLinkCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodKitchenprintLinkCtrl', $scope, $http, false));

  $scope.prodKitchenprintStoreShow = function () {
    $scope._broadcast('prodKitchenprintStoreCtrl');
  };

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  $scope.userStoreCd = gvStoreCd;
  $scope.btnShowFg = false;

  if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
      || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
    $scope.btnShowFg = true;
  }
  // 전체기간 체크박스
  $scope.isChecked = true;
  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);
  $scope._setComboData("srchSysStatFg", sysStatFg);

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

// grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.sysStatFgChange();

    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "prodCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        // 상품코드/상품명 클릭시
        if (col.binding === 'prodCd') {
          prodNmChange(selectedRow.prodNm, selectedRow.prodCd);
          // 연결된 프린터
          $scope._broadcast('prodKitchenprintLinkedCtrl', selectedRow.prodCd);
          // 안연결된 프린터
          $scope._broadcast('prodKitchenprintUnlinkCtrl', selectedRow.prodCd);
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodKitchenprintLinkCtrl", function(event, data) {
    var linked = agrid.getScope('prodKitchenprintLinkedCtrl');
    var nulink = agrid.getScope('prodKitchenprintUnlinkCtrl');
    linked._gridDataInit();   // 그리드 초기화
    nulink._gridDataInit();   // 그리드 초기화

    $scope.searchProdList();
    // 기능수행 종료 : 반드시 추가prodKitchenprintLinkCtrl
    event.preventDefault();
  });

  // 상품 목록 조회
  $scope.searchProdList = function(){
    prodNmChange('','');
    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prodKitchenprintLink/prodKitchenprintLink/list.sb", params, null, false);
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
        var params = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function(response){
              $scope.prodClassCd = prodClassCd;
              $scope.prodClassCdNm = response.data.data;
            }
        );
      }
    });
  };

  // 상품분류정보 선택취소
  $scope.delProdClass = function(){
    $scope.prodClassCd = "";
    $scope.prodClassCdNm = "";
  };

  //매장상태값 전달
  $scope.sysStatFgChange = function(){
  $("#srchSysStatFg2").text($scope.sysStatFg);
  };

}]);

/* 연결된 프린터 */
app.controller('prodKitchenprintLinkedCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodKitchenprintLinkedCtrl', $scope, $http, true));


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  $scope.$on("prodKitchenprintLinkedCtrl", function (event, data) {
    var params = {};
    params.prodCd = data;
    $scope.searchProdKitchenprintLinkedList(params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.searchProdKitchenprintLinkedList = function (params) {
    var param = {};
    param.prodCd = params.prodCd;
    param.sysStatFg = $("#srchSysStatFg2").text();
    param.storeCd   = $("#prodKitchenprintStoreCd").val();
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/base/prod/prodKitchenprintLink/prodKitchenprintLinked/list.sb", param, function () {
    }, false);

  }

  $scope.unlinkPrint = function(){
    var params = [];
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        var param = {};
        param.storeCd = $scope.flex.collectionView.items[i].storeCd;
        param.prterNo = $scope.flex.collectionView.items[i].prterNo;
        param.prodCd = $("#prodCd").text();
        params.push(param);
      }
    }
    if (params.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }
    $.postJSONArray("/base/prod/prodKitchenprintLink/prodKitchenprintLinked/unlink.sb", params, function(result) {
          $scope._popMsg(messages['cmm.saveSucc']);
          // 연결된 프린터
          $scope._broadcast('prodKitchenprintLinkedCtrl', $("#prodCd").text());
          // 안연결된 프린터
          $scope._broadcast('prodKitchenprintUnlinkCtrl', $("#prodCd").text());
      },
       function(result) {
        s_alert.pop(result.message);
      });
  };
}]);

/* 안연결된 프린터 */
app.controller('prodKitchenprintUnlinkCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodKitchenprintUnlinkCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  $scope.$on("prodKitchenprintUnlinkCtrl", function (event, data) {
    var params = {};
    params.prodCd = data;
    $scope.searchProdKitchenprintUnlinkList(params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.searchProdKitchenprintUnlinkList = function (params) {
    var param = {};
    param.prodCd = params.prodCd;
    param.sysStatFg = $("#srchSysStatFg2").text();
    param.storeCd   = $("#prodKitchenprintStoreCd").val();
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/base/prod/prodKitchenprintLink/prodKitchenprintUnlink/list.sb", param, null, false);
  };

  $scope.linkedPrint = function(){
    var params = [];
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        var param = {};
        param.storeCd = $scope.flex.collectionView.items[i].storeCd;
        param.prterNo = $scope.flex.collectionView.items[i].prterNo;
        param.prodCd = $("#prodCd").text();
        params.push(param);
      }
    }
    if (params.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }
    $.postJSONArray("/base/prod/prodKitchenprintLink/prodKitchenprintUnlink/linked.sb", params, function(result) {
          $scope._popMsg(messages['cmm.saveSucc']);

          // 연결된 프린터
          $scope._broadcast('prodKitchenprintLinkedCtrl', $("#prodCd").text());
          // 안연결된 프린터
          $scope._broadcast('prodKitchenprintUnlinkCtrl', $("#prodCd").text());
        },
        function(result) {
          s_alert.pop(result.message);
        });
  };
}]);
