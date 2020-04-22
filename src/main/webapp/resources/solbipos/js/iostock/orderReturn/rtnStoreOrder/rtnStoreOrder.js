/**
 * get application
 */
var app = agrid.getApp();

/** 반품등록 그리드 controller */
app.controller('rtnStoreOrderCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStoreOrderCtrl', $scope, $http, true));

  $scope.slipFg        = -1;
  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  $scope.reqDate       = wcombo.genDate("#reqDate");

  $scope._setComboData("srchDateFg", [
    {"name": messages["rtnStoreOrder.reqDate"], "value": "req"},
    {"name": messages["rtnStoreOrder.regDate"], "value": "reg"},
    {"name": messages["rtnStoreOrder.modDate"], "value": "mod"}
  ]);

  // 출고가능일자 세팅
  $scope.reqDate.value = new Date(getFormatDate(gReqDate, "-"));
  // 출고요청일자 선택가능여부에 따라 출고요청일자 선택여부 처리
  if (gEnvst1044 === "N") {
    $scope.reqDate.isReadOnly = true;
  }

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "083";
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("combo,map", "srchProcFg", 'procFgMap', null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnStoreOrderCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "reqDate") { // 출고요청일자
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "reqDate") { // 출고요청일자 클릭
          var params      = {};
          params.reqDate  = selectedRow.reqDate;
          params.slipFg   = selectedRow.slipFg;
          params.procFg   = selectedRow.procFg;
          params.hdRemark = selectedRow.remark;
          params.storeCd  = $scope.searchedStoreCd;
          $scope._broadcast('rtnStoreOrderDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStoreOrderCtrl", function (event, data) {
    $scope.searchRtnStoreOrderList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 반품 리스트 조회
  $scope.searchRtnStoreOrderList = function () {
    if ($("#rtnStoreOrderSelectStoreCd").val() === "") {
      $scope._popMsg(messages["rtnStoreOrder.dtl.require.selectStore"]); // 매장을 선택해 주세요.
      return false;
    }
    $scope.searchedStoreCd = $("#rtnStoreOrderSelectStoreCd").val(); // 반품요청일자를 클릭하여 상세내역을 봐야하므로 조회할 당시 매장코드를 담아둔다.
    // 파라미터
    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.dateFg    = $scope.dateFg;
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.searchedStoreCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrder/list.sb", params);
  };

  // 신규 요청등록
  $scope.newReqOrder = function () {
    if ($("#rtnStoreOrderSelectStoreCd").val() === "") {
      $scope._popMsg(messages["rtnStoreOrder.dtl.require.selectStore"]); // 매장을 선택해 주세요.
      return false;
    }
    var params        = {};
    params.callParent = "rtnStoreOrder";
    params.reqDate    = wijmo.Globalize.format($scope.reqDate.value, 'yyyyMMdd');
    params.slipFg     = $scope.slipFg;
    params.hdRemark   = "";
    params.storeCd    = $("#rtnStoreOrderSelectStoreCd").val();

    $scope._broadcast("rtnStoreOrderRegistCtrl", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnStoreOrderSelectStoreShow = function () {
    $scope._broadcast('rtnStoreOrderSelectStoreCtrl');
  };


  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
  // comboId : combo 생성할 ID
  // gridMapId : grid 에서 사용할 Map ID
  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
  // params : 데이터 조회할 url에 보낼 파라미터
  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
  // callback : queryCombo 후 callback 할 함수
  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
    if (url) {
      comboUrl = url;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : comboUrl, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list       = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
            comboArray = [];
            if (option === "A") {
              comboData.name  = messages["cmm.all"];
              comboData.value = "";
              comboArray.push(comboData);
            } else if (option === "S") {
              comboData.name  = messages["cmm.select"];
              comboData.value = "";
              comboArray.push(comboData);
            }

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].nmcodeNm;
              comboData.value = list[i].nmcodeCd;
              comboArray.push(comboData);
            }
            $scope._setComboData(comboId, comboArray);
          }

          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
            comboArray = [];
            for (var i = 0; i < list.length; i++) {
              comboData      = {};
              comboData.id   = list[i].nmcodeCd;
              comboData.name = list[i].nmcodeNm;
              comboArray.push(comboData);
            }
            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        $timeout(function () {
          callback();
        }, 10);
      }
    });
  };

  //엑셀 다운로드
	$scope.excelDownloadDay = function () {

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
			},'수불관리_매장반품관리_매장반품등록_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};

}]);
