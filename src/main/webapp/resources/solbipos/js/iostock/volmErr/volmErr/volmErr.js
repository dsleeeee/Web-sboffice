/**
 * get application
 */
var app = agrid.getApp();

/** 물량오류관리 그리드 controller */
app.controller('volmErrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout){
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('volmErrCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  $scope.slipFgMap = new wijmo.grid.DataMap([
    {id: "1",   name: messages["volmErr.orderSlipFg"]},
    {id: "-1",  name: messages["volmErr.rtnSlipFg"]},
  ], 'id', 'name');

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["volmErr.reg"]},
    {id: "1", name: messages["volmErr.confirm"]},
  ], 'id', 'name');

  $scope._setComboData("srchSlipFg", [
    {"name": messages["cmm.all"],               "value":  ""},
    {"name": messages["volmErr.orderSlipFg"],   "value":  "1"},
    {"name": messages["volmErr.rtnSlipFg"],     "value": "-1"}
  ]);

  $scope._setComboData("srchDateFg", [
    {"name": messages["volmErr.outDate"],   "value": "out"},
    {"name": messages["volmErr.inDate"],    "value": "in"}
  ]);

  $scope._setComboData("srchProcFg", [
    {"name": messages["cmm.all"],           "value": ""},
    {"name": messages["volmErr.reg"],       "value": "0"},
    {"name": messages["volmErr.confirm"],   "value": "1"}
  ]);

  // 본사 거래처 콤보박스
  $scope._setComboData('vendrCd', vendrList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("volmErrCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
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
        if (col.binding === "slipNo") { // 전표번호 클릭
          var params    = {};
          params.slipNo     = selectedRow.slipNo;
          params.slipFg     = selectedRow.slipFg;
          params.procFg     = selectedRow.procFg;
          params.storeCd    = selectedRow.storeCd;
          params.storeNm    = selectedRow.storeNm;
          params.hdRemark   = selectedRow.remark;

            /*
            console.log('params:' + JSON.stringify(params));
            console.log('params.procFg === 0  : ' + params.procFg === "0"     );
            console.log('params.procFg ==  0  : ' + params.procFg ==  "0"     );
            if(params.procFg === "0")   console.log("===");
            if(params.procFg ==  "0")   console.log("==" );
            */

          $scope._broadcast('volmErrDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');


    //Header column merge (출고수량, 입고수량)
    s.allowMerging                          = 'ColumnHeaders';
    s.columnHeaders.rows[0].allowMerging    = true;

    // 현재 로그인 사원에 맵핑된 거래처코드로 셋팅(없으면 '본사'로 셋팅됨.)
    $scope.vendrCdCombo.selectedValue = empVendrCd;
    // 거래처는 수정 못하게 처리
    $("#vendrCd").attr("disabled", true);
    $("#vendrCd").css('background-color', '#F0F0F0');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("volmErrCtrl", function (event, data) {
    $scope.searchVolmErrList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 물량오류 리스트 조회
  $scope.searchVolmErrList = function () {
    // 파라미터
    var params       = {};
    params.dateFg    = $scope.dateFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#volmErrSelectStoreCd").val();
    params.slipFg    = $scope.slipFg;
    params.procFg    = $scope.procFg;

    if(orgnFg === "HQ") {
      params.vendrCd = $scope.vendrCd;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/volmErr/volmErr/volmErr/list.sb", params);
  };

	//[엑셀 다운로드] - START	------------------------------------------------------------------------------------------------------------------------------
	$scope.excelDownload = function(){
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
		$timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(
                $scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles   : 	true,
                    includeColumns      :   function (column) {
                                                return column.visible;
                                            }
                },
              //'물량오류관리_' + getToday() + '.xlsx',
                '물량오류관리_' + getCurDate('-') + '.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
	};
    //[엑셀 다운로드] - END	------------------------------------------------------------------------------------------------------------------------------


}]);
