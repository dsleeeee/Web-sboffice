/****************************************************************
 *
 * 파일명 : prod.js
 * 설  명 : 상품정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
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
var useYnComboData = [
  {"name": "사용", "value": "Y"},
  {"name": "미사용", "value": "N"}
];

// 상품등록주체 (본사/매장)
var regOrgnFgComboData = [
  {"name": "전체", "value": ""},
  {"name": "본사", "value": "H"},
  {"name": "매장", "value": "S"}
];


/**
 * 상품정보관리 그리드 생성
 */
app.controller('prodCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  $scope.prodEnvstVal = prodEnvstVal;
  $scope.userOrgnFg = gvOrgnFg;

  // 상품 상세 정보
  $scope.prodInfo = {};
  $scope.setProdInfo = function(data){
    $scope.prodInfo = data;
  };
  $scope.getProdInfo = function(){
    return $scope.prodInfo;
  };
  // 전체기간 체크박스
  $scope.isChecked = true;
  // 커스텀콤보 : 사이드메뉴-속성
  $scope._getComboDataQueryCustom('getSideMenuAttrClassCombo', 'sdattrClassCdComboData', 'S');
  // 커스텀콤보 : 사이드메뉴-선택메뉴
  $scope._getComboDataQueryCustom('getSideMenuSdselGrpCdCombo', 'sdselGrpCdComboData', 'S');

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);
  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);
  // 상품등록주체 (본사/매장구분)
  $scope._setComboData('regOrgnFgComboData', regOrgnFgComboData);
  // 사용여부를 쓰는 콤보박스의 데이터
  $scope._setComboData('useYnComboData', useYnComboData);
  // 상품유형 콤보박스
  $scope._getComboDataQuery('008', 'prodTypeFgComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('091', 'saleProdYnComboData');
  // 주문상품구분 콤보박스
  $scope._getComboDataQuery('092', 'poProdFgComboData');
  // 주문단위 콤보박스
  $scope._getComboDataQuery('093', 'poUnitFgComboData');
  // 과세여부 콤보박스
  $scope._getComboDataQuery('039', 'vatFgComboData');
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 세트상품구분 콤보박스
  $scope._getComboDataQuery('095', 'setProdFgComboData');
  // 봉사료포함여부 콤보박스
  $scope._getComboDataQuery('058', 'prodTipYnComboData');


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드에서 사용하는 dataMap 초기화
    $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name');

    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "prodCd" || col.binding === "storeCnt") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if( col.binding === "prodCd") {
          $scope.searchProdDetail(selectedRow.prodCd);
        } else if(col.binding === "storeCnt"){
          $scope.registProdStore(selectedRow);
        }
      }
    });
    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;

  };
  // 상품정보관리 그리드 조회
  $scope.$on("prodCtrl", function(event, data) {

    $scope.searchProdList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품 목록 조회
  $scope.searchProdList = function(){
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prod/prod/list.sb", params);
  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.startDateCombo.isReadOnly = $scope.isChecked;
    $scope.endDateCombo.isReadOnly = $scope.isChecked;
  };

  // 상세정보 팝업
  $scope.searchProdDetail = function(prodCd) {
    var detailPopUp = $scope.prodDetailLayer;
    setTimeout(function() {
      detailPopUp.show(true, function (s) {
        // 수정 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          // 상품정보 수정 팝업
          var modifyPopUp = $scope.prodModifyLayer;
          modifyPopUp.show(true, function (s) {
            // 상품정보 수정 팝업 - 저장
            if (s.dialogResult === "wj-hide-apply") {
              // 팝업 속성에서 상품정보 get
              var params = s.data;
              // 저장수행
              $scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function () {
                $scope._popMsg(messages["cmm.saveSucc"]);
              });
            }
          });
        }
      });
    }, 50);
  };

  // 상품적용 매장 등록 팝업
  $scope.registProdStore = function(prodInfo) {
    $scope.setProdInfo(prodInfo);
    $scope.prodStoreRegistLayer.show(true, function (s) {
      var regStoreGrid = agrid.getScope('regStoreCtrl');
      regStoreGrid.$apply(function(){
        regStoreGrid._gridDataInit();
      });
      var noRegStoreGrid = agrid.getScope('noRegStoreCtrl');
      noRegStoreGrid.$apply(function(){
        noRegStoreGrid._gridDataInit();
      });
      $scope._pageView('prodCtrl', 1);
    });
  };

  // 신규상품 등록
  $scope.addProd = function() {
    $scope.setProdInfo({});
    var modifyPopUp = $scope.prodModifyLayer;
    setTimeout(function() {
      modifyPopUp.show(true, function (s) {
        // 상품정보 등록 팝업 - 저장
        if (s.dialogResult === "wj-hide-apply") {
          // 팝업 속성에서 상품정보 get
          var params = s.data;
          // 저장수행
          $scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function () {
            $scope._popMsg(messages["cmm.saveSucc"]);
          });
        }
      });
    }, 50);
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

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 상품상세정보 팝업 핸들러 추가
    $scope.prodDetailLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = {};
        params.prodCd = selectedRow.prodCd;
        $scope._broadcast('prodDetailCtrl', params);
      }, 50);
    });
    // 상품상세정보 수정 팝업 핸들러 추가
    $scope.prodModifyLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope.$apply(function() {
          $scope._broadcast('prodModifyCtrl', $scope.getProdInfo());
          // 팝업에 속성 추가 : 상품정보
          s.data = $scope.getProdInfo();
        });
      }, 50);
    });
    // 상품적용매장 등록 팝업 핸들러 추가
    $scope.prodStoreRegistLayer.shown.addHandler(function (s) {
      // setTimeout(function() {
      //   $scope.$apply(function() {
      //     $scope._broadcast('regStoreCtrl');
      //   });
      // }, 50);
    });
  });

}]);
