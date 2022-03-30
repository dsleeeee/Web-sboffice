/**
 * get application
 */
var app = agrid.getApp();

/** 신용카드 상세 팝업 내역 controller */
app.controller('dayCardCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayCardCtrl', $scope, $http, true));
  var gubun;
  var startDate;
  var endDate;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        } else if (col.format === "time") {
          e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
        }

        if (col.binding === "cardNm") {
          var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        if(orgnFg === "STORE"){
          params.storeCd  = storeCd;
        }
        params.chkPop = 'cardApprPop';
        params.acquireNm = selectedRow.cardNm;
        params.startDate = startDate;
        params.endDate = endDate;

        if (col.binding === "cardNm") { // 영수증번호 클릭
          $scope._broadcast('saleApprCardCtrl', params);
        }

      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dayCardCtrl", function (event, data) {
    $scope.wjDayCardLayer.show(true);
    $scope.searchDayCardList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 신용카드 승인내역 리스트 조회
  $scope.searchDayCardList = function (data) {
    // 파라미터
    var params      = {};
    gubun = data.gubun;
    // 기간별매출 > 일자별 탭 > 일별종합 탭
    if(data.gubun == "day") {
      params.saleDate = data.saleDate;
      startDate = data.saleDate;
      endDate = data.saleDate;
    }
    // 기간별매출 > 월별 탭 > 월별종합 탭
    if(data.gubun == "month") {
      params.yearMonth = data.yearMonth;
      startDate = data.yearMonth + '01';
      endDate = data.yearMonth + '31';
    }
    params.storeCd  = data.storeCd;
    params.gubun  = data.gubun;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/cmmSalePopup/dayPayInfo/dayCard/list.sb", params);
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      }, '카드사별결제내역_' + getToday() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
