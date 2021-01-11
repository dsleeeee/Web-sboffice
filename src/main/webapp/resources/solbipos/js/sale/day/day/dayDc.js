/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분별 controller */
app.controller('dayDcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayDcCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchDcStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchDcEndDate", gvEndDate);
  $scope.orgnFg        = gvOrgnFg;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dayDcCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        // 할인
        for (var i = 0; i < dcColList.length; i++) {
          if (col.binding === ("dc" + dcColList[i].dcCd)) {
            var item = s.rows[e.row].dataItem;

            // 07:포장할인, 08:현장할인 이 아닌 경우 링크효과
            if (dcColList[i].dcCd !== '07' && dcColList[i].dcCd !== '08') {
              // 값이 있으면 링크 효과
              if (nvl(item[("dc" + dcColList[i].dcCd)], '') !== '') {
                wijmo.addClass(e.cell, 'wijLink');
                wijmo.addClass(e.cell, 'wj-custom-readonly');
              }
            }
          }
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        } else if (col.format === "time") {
          e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params      = {};
        params.storeCd  = $scope.searchedStoreCd;
        params.saleDate = selectedRow.saleDate;

        // 할인
        for (var i = 0; i < dcColList.length; i++) {
          if (col.binding === ("dc" + dcColList[i].dcCd)) {
            // var item = s.rows[e.row].dataItem;

            // 07:포장할인, 08:현장할인이 아닌 경우
            if (dcColList[i].dcCd !== '07' && dcColList[i].dcCd !== '08') {
              // 값이 있으면 링크
              if (nvl(selectedRow[("dc" + dcColList[i].dcCd)], '') !== '') {
                params.dcCd = dcColList[i].dcCd;
                $scope._broadcast(dcColList[i].dcMethod.toLowerCase() + 'DcCtrl', params);
              }
            }
          }
        }

      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.saleDate    = messages["day.dayDc.saleDate"];
    dataItem.yoil        = messages["day.dayDc.yoil"];
    dataItem.storeCnt    = messages["day.dayDc.storeCnt"];
    dataItem.storeCd     = messages["day.dayDc.storeCd"];
    dataItem.totDcAmt    = messages["day.dayDc.totDcAmt"];

    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["day.dayDc.dcInfo"];
    }
    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging    = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display    : 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display      : 'table-cell',
          verticalAlign: 'middle',
          textAlign    : 'center'
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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dayDcCtrl", function (event, data) {
    $scope.searchDayTotalList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 할인구분별 리스트 조회
  $scope.searchDayTotalList = function () {
    $scope.searchedStoreCd = $("#dayDcSelectStoreCd").val();
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.searchedStoreCd;
    params.dcCol     = dcCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/day/day/dayDc/list.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dayDcSelectStoreShow = function () {
    $scope._broadcast('dayDcSelectStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownloadInfo = function () {

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
      },
          messages["day.day"] + '(' + messages["day.dc"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };
}]);
