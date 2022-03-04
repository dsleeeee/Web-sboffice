/****************************************************************
 *
 * 파일명 : sideMenuSoldOut.js
 * 설  명 : 사이드메뉴>선택메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.02.28     권지현      1.0
 *
 * **************************************************************/

// 고정상품구분
var fixProdFgDataMap = new wijmo.grid.DataMap([
  {id: "0", name: "선택"},
  {id: "1", name: "고정"}
], 'id', 'name');

// 품절여부
var soldOutYnData = [
  {"name": "품절", "value": "Y"},
  {"name": "정상", "value": "N"}
];

/**
 * 사이드메뉴 선택그룹 그리드 생성
 */
app.controller('sideMenuSoldOutCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSoldOutCtrl', $scope, $http, false));

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
        if ( col.binding === 'sdselGrpCd') {
          if (selectedRow.sdselGrpCd !== '' && selectedRow.sdselGrpCd !== undefined && selectedRow.sdselGrpCd !== '자동채번') {
            $("#sideSelectGroupTitle").html(" [" + selectedRow.sdselGrpCd + "]" + selectedRow.sdselGrpNm);
            $("#sideClassTitle").html("");

            $scope.setSelectedSdselGrpCd(selectedRow.sdselGrpCd);
            $scope._broadcast('sideMenuSelectClassCtrl', selectedRow.sdselGrpCd);
            var prodGrid = agrid.getScope('sideMenuSelectProdCtrl');
            prodGrid._gridDataInit();
          }
        }
      }
    });
  };

  // 선택그룹 그리드 조회
  $scope.$on('sideMenuSoldOutCtrl', function(event, data) {

    // 파라미터
    var params = {};

    if(orgnFg == "HQ"){
      if($("#sideMenuSoldOutStoreCd").val() == "" || $("#sideMenuSoldOutStoreCd").val() == undefined){
        $scope._popMsg(messages["cmm.require.selectStore"]);
        return false;
      } else {
        params.storeCd = $("#sideMenuSoldOutStoreCd").val();
      }
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/soldOut/soldOut/getMenuGrpList.sb', params);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);

/**
 * 사이드메뉴 선택분류 그리드 생성
 */
app.controller('sideMenuSelectClassCtrl', ['$scope', '$http', 'sdselGrpCd', function ($scope, $http, sdselGrpCd) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuSelectClassCtrl', $scope, $http, false));

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
        if(col.binding === 'sdselClassCd') {
          if(selectedRow.sdselClassCd !== '' && selectedRow.sdselClassCd !== undefined && selectedRow.sdselClassCd !== '자동채번') {
            $("#sideClassTitle").html(" [" + selectedRow.sdselClassCd + "]" + selectedRow.sdselClassNm);

            var params = {};
            params.sdselClassCd = selectedRow.sdselClassCd;
            params.sdselQty = selectedRow.sdselQty;
            $scope._broadcast('sideMenuSelectProdCtrl', params);
          }
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

    if(orgnFg == "HQ"){
      if($("#sideMenuSoldOutStoreCd").val() == "" || $("#sideMenuSoldOutStoreCd").val() == undefined){
        $scope._popMsg(messages["cmm.require.selectStore"]);
        return false;
      } else {
        params.storeCd = $("#sideMenuSoldOutStoreCd").val();
      }
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/soldOut/soldOut/getMenuClassList.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

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

  // 선택분류의 수량 set
  $scope.sdselQty = 0;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.soldOutYnDataMap = new wijmo.grid.DataMap(soldOutYnData, 'value', 'name'); // 품절여부
    // 그리드 내 콤보박스 설정
    $scope.fixProdFgDataMap = fixProdFgDataMap;

    // 품절여부 변경 시 체크박스 체크
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "soldOutYn") {
          item.gChk = true;
        }
      }
      s.collectionView.commitEdit();
    });

    // 선택상품 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === 'prodCd' || col.binding === 'prodNm') {
        e.cancel = true;
      }
    });
  };
  // 선택상품 그리드 조회
  $scope.$on('sideMenuSelectProdCtrl', function(event, data) {
    // scope 영역에 변수 Set
    $scope.setSdselClassCd(data.sdselClassCd); // 선택분류코드
    $scope.sdselQty = parseInt(data.sdselQty); // 선택분류의 수량

    // 파라미터
    var params = {};
    params.sdselClassCd = data.sdselClassCd;

    if(orgnFg == "HQ"){
      if($("#sideMenuSoldOutStoreCd").val() == "" || $("#sideMenuSoldOutStoreCd").val() == undefined){
        $scope._popMsg(messages["cmm.require.selectStore"]);
        return false;
      } else {
        params.storeCd = $("#sideMenuSoldOutStoreCd").val();
      }
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain('/base/prod/soldOut/soldOut/getMenuProdList.sb', params);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.saveProd = function() {
    if(orgnFg == "HQ"){
      if($("#prodSoldOutStoreCd").val() == "" || $("#prodSoldOutStoreCd").val() == undefined){
        $scope._popMsg(messages["cmm.require.selectStore"]);
        return false;
      }
    }

    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          $scope.flex.collectionView.items[i].status = "U";
          params.push($scope.flex.collectionView.items[i]);
        }
      }
      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/soldOut/soldOut/getSideMenuSoldOutSave.sb", params, function(){
        var param = {};
        param.sdselClassCd = $("#sideClassTitle").html().substr(2,6);
        param.sdselQty = 0;
        $scope._broadcast('sideMenuSelectProdCtrl', param);

      });
    });
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

  // 일괄적용
  $scope.batchChange = function(chgGubun) {
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    if(params.length <= 0) {
      s_alert.pop(messages["cmm.not.select"]);
      return;
    }

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].soldOutYn = $scope.soldOutYnChg;
      }
    }
    $scope.flex.refresh();
  };

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

app.controller('soldOutChgCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('soldOutChgCtrl', $scope, $http, false));

  // 일괄적용
  $scope.batchChange = function(chgGubun) {
    var scope = agrid.getScope("sideMenuSelectProdCtrl");
      if(scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    var params = new Array();
    for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
      if(scope.flex.collectionView.items[i].gChk) {
        params.push(scope.flex.collectionView.items[i]);
      }
    }
    if(params.length <= 0) {
      s_alert.pop(messages["cmm.not.select"]);
      return;
    }

    for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
      if(scope.flex.collectionView.items[i].gChk) {
        scope.flex.collectionView.items[i].soldOutYn = $scope.soldOutYnChg;
      }
    }
    scope.flex.refresh();
  };

  // <-- 그리드 저장 -->
  $scope.save = function() {
    var scope = agrid.getScope("sideMenuSelectProdCtrl");
    scope.saveProd();
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.sideMenuSoldOutStoreShow = function () {
    $scope._broadcast('sideMenuSoldOutStoreCtrl');
  };
}]);