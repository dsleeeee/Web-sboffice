/****************************************************************
 *
 * 파일명 : dayDlvr.js
 * 설  명 : 배달 내역JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.09     Joshua      1.0
 *
 * **************************************************************/
/**
 *  일자별회원 구매내역 그리드 생성
 */
var app = agrid.getApp();
app.controller('dayDlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayDlvrCtrl', $scope, $http, true));
  // 검색조건에 조회기간
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  $scope.selectedMember;
  $scope.setSelectedMember = function (data) {
    $scope.selectedMember = data;
  };
  $scope.getSelectedMember = function () {
    return $scope.selectedMember;
  };

  $scope.initGrid = function (s, e) {
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "saleDate") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText.substring(0, 8));
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        // 회원코드 클릭시 해당 기능 목록 조회
        if (col.binding === "saleDate") {
          var selectedData = s.rows[ht.row].dataItem
          selectedData.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
          selectedData.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
          $scope.setSelectedMember(selectedData);
          params = {}
          params.searchDate = $scope.getSelectedMember().nonDlvrSaleDate;
          params.hqOfficeCd = $scope.getSelectedMember().hqOfficeCd;
          params.hqBrandCd = $scope.getSelectedMember().hqBrandCd;
          params.posNo = $scope.getSelectedMember().posNo;
          params.billNo = $scope.getSelectedMember().billNo;
          var detailScope = agrid.getScope('dayDlvrDtlCtrl');
          detailScope._broadcast('dayDlvrDtlCtrl', $scope.getSelectedMember());
          event.preventDefault();
        }
      }
    });

    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};
    dataItem.saleDate = messages["dayDlvr.saleDate"];
    dataItem.dlvrBillCnt = messages["dayDlvr.dlvrSale"];
    dataItem.dlvrAmt = messages["dayDlvr.dlvrSale"];
    dataItem.nonDlvrBillCnt = messages["dayDlvr.nonDlvrSale"];
    dataItem.nonDlvrAmt = messages["dayDlvr.nonDlvrSale"];

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display: 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display: 'table-cell',
          verticalAlign: 'middle',
          textAlign: 'center'
        });
      }
      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
        // GroupRow 인 경우에는 표시하지 않는다.
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      // readOnly 배경색 표시
      else if (panel.cellType === wijmo.grid.CellType.Cell) {
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }
  }
  // <-- 검색 호출 -->
  $scope.$on("dayDlvrCtrl", function (event, data) {

    // 리스트 초기화
    var scope = agrid.getScope('dayDlvrDtlCtrl');
    scope._gridDataInit();
    event.preventDefault();

    $scope.searchDaySaleList();
    event.preventDefault();
  });

  $scope.searchDaySaleList = function () {
    var params = {};
    // params.listScale = $scope.listScale;
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
    $scope._inquiryMain("/dlvr/manage/anals/dayDlvr/getDayDlvrList.sb", params, function () {
    }, false);

    // <-- //그리드 헤더2줄 -->
  };

  // 엑셀 다운로드
  $scope.leftExcelDownload = function () {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
      dd= '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    }

    today = String(yyyy) + String(mm) + dd;

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
      }, '일자별_배달내역_' + today + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);


app.controller('dayDlvrDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  angular.extend(this, new RootController('dayDlvrDtlCtrl', $scope, $http, true));
  $scope._setComboData("listScaleDatail", gvListScaleBoxData);

  $scope.selectedMember;
  $scope.setSelectedMember = function (data) {
    $scope.selectedMember = data;
  };
  $scope.getSelectedMember = function () {
    return $scope.selectedMember;
  };
  $scope.initGrid = function (s, e) {
    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};

    // dataItem.prodClassNm = messages["dayDlvr.prodClassNm"];
    // dataItem.lv1Cd = messages["dayDlvr.prodClassNm"];
    dataItem.lv1Nm = messages["dayDlvr.prodClassNm"];
    // dataItem.lv2Cd = messages["dayDlvr.prodLV2"];
    dataItem.lv2Nm = messages["dayDlvr.prodLV2"];
    dataItem.prodCd = messages["dayDlvr.prodCd"];
    dataItem.prodNm = messages["dayDlvr.prodNm"];
    dataItem.prodClassNm = messages["dayDlvr.prodClassNm"];
    dataItem.dlvrSaleQty = messages["dayDlvr.dlvrSale"];
    dataItem.dlvrAmt = messages["dayDlvr.dlvrSale"];
    dataItem.nonDlvrSaleQty = messages["dayDlvr.nonDlvrSale"];
    dataItem.nonDlvrAmt = messages["dayDlvr.nonDlvrSale"];

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display: 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display: 'table-cell',
          verticalAlign: 'middle',
          textAlign: 'center'
        });
      }
      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
        // GroupRow 인 경우에는 표시하지 않는다.
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      // readOnly 배경색 표시
      else if (panel.cellType === wijmo.grid.CellType.Cell) {
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }
  }
  // <-- //그리드 헤더2줄 -->
  $scope.$on("dayDlvrDtlCtrl", function (event, data) {
    $scope.setSelectedMember(data);
    $scope.searchDlvrDtlInfo(data);
    event.preventDefault();
  });

  $scope.searchDlvrDtlInfo = function (data) {
    var params = {};
    // params.listScale = 10;
    params.searchDate = data.saleDate;
    params.hqOfficeCd = data.hqOfficeCd;
    params.hqBrandCd = data.hqBrandCd;
    params.posNo = data.posNo;
    params.billNo = data.billNo;
    params.orgnFg = $("#resurceFg").val();

    $scope._inquirySub("/dlvr/manage/anals/dayDlvr/getDaySaleDtlList.sb", params, function () {
    }, false);
  };

// 엑셀 다운로드
  $scope.rightExcelDownload = function () {

    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
      dd= '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    }

    today = String(yyyy) + String(mm) + dd;

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
      }, '일자별_배달내역_상세_' + today + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


}])
;