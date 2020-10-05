/**
 * get application
 */
var app = agrid.getApp();

/** 매장여신관리 그리드 controller */
app.controller('storeLoanManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeLoanManageCtrl', $scope, $http, true));

  //페이지 스케일 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 그리드 DataMap 설정
  $scope.orderFg = new wijmo.grid.DataMap([
    {id: "1", name: messages["loan.orderFg1"]},
    {id: "2", name: messages["loan.orderFg2"]},
    {id: "3", name: messages["loan.orderFg3"]}
  ], 'id', 'name');

  $scope.noOutstockAmtFg = new wijmo.grid.DataMap([
    {id: "N", name: messages["loan.noOutstockAmtFgN"]},
    {id: "Y", name: messages["loan.noOutstockAmtFgY"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeLoanManageCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd") { // 매장코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });
    
    // keydown event listener
    s.addEventListener(s.hostElement, 'keydown', function(e) {
       if (e.keyCode == wijmo.Key.Delete && !e.target.getAttribute("aria-readonly")) {
          var binding = $scope.flex.getColumn(s.selection._col).binding;
          if (binding == 'remark'){
             s.selectedItems[0].remark = "";
          } else {
             $scope.flex.setCellData(s.selection._row, binding, '0');
          }
          e.preventDefault();
       }
    });
        
    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storeCd") { // 매장코드 클릭
          var params     = {};
          params.storeCd = selectedRow.storeCd;
          params.storeNm = selectedRow.storeNm;
          $scope._broadcast('storeLoanManageDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  	//다른 컨트롤러의 broadcast 받기
	$scope.$on("storeLoanManageCtrl", function (event, data) {
	
//		if( $("#posDaySelectStoreCd").val() === ''){
//		 	$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
//		 	return false;
//		}  
		
		$scope.searchStoreLoanManage(true);
		
//		var storeCd = $("#posDaySelectStoreCd").val();
//			$scope.getRePosNmList(storeCd, posCd);
	});
  
	//다른 컨트롤러의 broadcast 받기(페이징 초기화)
	$scope.$on("storeLoanManageCtrlSrch", function (event, data) {
	
//		if( $("#posDaySelectStoreCd").val() === ''){
//		 	$scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해 주세요.
//		 	return false;
//		}  
		
		$scope.searchStoreLoanManage(false);
		
//		var storeCd = $("#posDaySelectStoreCd").val();
//			$scope.getRePosNmList(storeCd, posCd);
	});
  
  // 리스트 조회
  $scope.searchStoreLoanManage = function (isPageChk) {
    // 파라미터
    var params = {};
    params.isPageChk = isPageChk;
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    // param.storeCd = $("#srchStoreCd").val();
    // param.storeNm = $("#srchStoreNm").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/loan/storeLoanManage/storeLoanManage/list.sb", params);
  };

  // 저장
  $scope.save = function () {
    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if(item.maxOrderAmtYn === true && item.limitLoanAmt === null){
        $scope._popMsg(messages["loan.limitLoanAmt"]+" "+messages["cmm.require.text"]); // 여신한도액을 입력해주세요.
        return false;
      }

      if (item.limitLoanAmt !== null && item.maxOrderAmt === null) {
        $scope._popMsg(messages["loan.maxOrderAmt"]+" "+messages["cmm.require.text"]); // 1회주문한도액을 입력해주세요.
        return false;
      }
      if (item.maxOrderAmt !== null && item.limitLoanAmt === null) {
        $scope._popMsg(messages["loan.limitLoanAmt"]+" "+messages["cmm.require.text"]); // 여신한도액을 입력해주세요.
        return false;
      }

      item.status = "U";
      params.push(item);
    }

    $scope._save("/iostock/loan/storeLoanManage/storeLoanManage/save.sb", params, function () {
      $scope.searchStoreLoanManage();
    });
  };
  
//삭제
  $scope.fnDel = function () {
    var msg = messages["loan.delLimitLoanAmtMsg"]; // 선택하신 자료를 삭제합니다. 삭제하시겠습니까?
    s_alert.popConf(msg, function() {
    	var params = [];
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
          var item = $scope.flex.collectionView.itemsEdited[i];
          
          if (item.gChk === true) {
        	  // if (item.limitLoanAmt !== null ) {
              //     $scope._popMsg(messages["loan.delLimitLoanAmt.txt"]); // 여신한도액이 있는 자료만 삭제할 수 있습니다.
              //     return false;
              // }
        	  item.status    = "U";
        	  item.empNo     = "0000";
        	  item.storageCd = "999";	//전체재고용 창고코드 ('001' -> '000' -> '999')
        	  item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        	  params.push(item);
          }
        }
        $scope._save("/iostock/loan/storeLoanManage/storeLoanManage/delLimitLoanAmt.sb", params, function () {
          $scope.searchStoreLoanManage()
        }); 
    });
  };

}]);
