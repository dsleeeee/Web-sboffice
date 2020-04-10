/**
 * get application
 */
var app = agrid.getApp();

/** 현재고현황 그리드 controller */
app.controller('hqCurrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqCurrCtrl', $scope, $http, true));

  $scope._setComboData("srchUnitFg", [
    {"name": messages["hqCurr.unitStockFg"], "value": "stock"},
    {"name": messages["hqCurr.unitOrderFg"], "value": "order"}
  ]);

  $scope._setComboData("srchWeightFg", [
    {"name": messages["hqCurr.weightFg0"], "value": "0"},
    {"name": messages["hqCurr.weightFg1"], "value": "1"}
  ]);

  $scope._setComboData("srchSafeStockFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["hqCurr.safeStockFg0"], "value": "0"}
  ]);

  //조회조건 콤보박스 listScale 세팅
  $scope._setComboData("hqCurrListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("hqCurrCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "currQty" && s.cells.getCellData(e.row,12,false) != null ) { // 현재고
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
		if (col.binding === "currQty" && selectedRow.currQty != null) { // 현재고 클릭
			var params    = {};
			params.prodCd = selectedRow.prodCd;
			params.prodNm = selectedRow.prodNm;
			$scope._broadcast('cmmStockStatusCtrl', params);
		}
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("hqCurrCtrl", function (event, data) {
    $scope.searchHqCurrList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  

  // 현재고현황 리스트 조회
  $scope.searchHqCurrList = function (isPageChk) {
    // 파라미터
    var params     = {};
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.barcdCd = $scope.barcdCd;
    params.unitFg = $scope.unitFg;
    params.prodClassCd = $scope.prodClassCd;
    params.vendrCd = $("#hqCurrSelectVendrCd").val();
    params.isPageChk = isPageChk;
    params.listScale = $scope.listScale;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/curr/hqCurr/hqCurr/list.sb", params);
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
            $scope.prodClassCdNm = response.data.data;
          }
        );
      }
    });
  };

  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.hqCurrSelectVendrShow = function () {
    $scope._broadcast('hqCurrSelectVendrCtrl');
  };
  
  //상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  };

  //엑셀 다운로드
  $scope.excelDownload = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '재고현황_일자별수불현황_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
