/**
 * get application
 */
var app = agrid.getApp();

/** 매장이동관리 그리드 controller */
app.controller('hqStoreMoveCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqStoreMoveCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchHqStoreMoveStartDate", getToday());
  var srchEndDate   = wcombo.genDateVal("#srchHqStoreMoveEndDate", getToday());

  $scope.dlvrFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["hqStoreMove.dlvrFg0"]},
    {id: "1", name: messages["hqStoreMove.dlvrFg1"]},
    {id: "2", name: messages["hqStoreMove.dlvrFg2"]}
  ], 'id', 'name');

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["hqStoreMove.procFg0"]},
    {id: "1", name: messages["hqStoreMove.procFg1"]},
    {id: "2", name: messages["hqStoreMove.procFg2"]},
    {id: "3", name: messages["hqStoreMove.procFg3"]}
  ], 'id', 'name');

  $scope._setComboData("srchHqStoreMoveDlvrFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["hqStoreMove.dlvrFg0"], "value": "0"},
    {"name": messages["hqStoreMove.dlvrFg1"], "value": "1"},
    {"name": messages["hqStoreMove.dlvrFg2"], "value": "2"}
  ]);

  $scope._setComboData("srchHqStoreMoveProcFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["hqStoreMove.procFg0"], "value": "0"},
    {"name": messages["hqStoreMove.procFg1"], "value": "1"},
    {"name": messages["hqStoreMove.procFg2"], "value": "2"},
    {"name": messages["hqStoreMove.procFg3"], "value": "3"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("hqStoreMoveCtrl");

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
      
      if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
    		var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
    		if (rng && rng.columnSpan > 1) {
    			e.preventDefault();
    		}
  	  }
      
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "slipNo") { // 전표번호 클릭
          var params    = {};
          params.slipNo = selectedRow.slipNo;
          $scope._broadcast('hqStoreMoveDtlCtrl', params);
        }
      }
    }, true);

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      moveDate  : messages["hqStoreMove.moveDate"],
      slipNo    : messages["hqStoreMove.slipNo"],
      ioFg      : messages["hqStoreMove.ioFg"],
      dlvrFg    : messages["hqStoreMove.dlvrFg"],
      procFg    : messages["hqStoreMove.procFg"],
      dtlCnt    : messages["hqStoreMove.dtlCnt"],
      outStoreCd: messages["hqStoreMove.out"],
      outStoreNm: messages["hqStoreMove.out"],
      outTot    : messages["hqStoreMove.out"],
      inStoreCd : messages["hqStoreMove.in"],
      inStoreNm : messages["hqStoreMove.in"],
      inTot     : messages["hqStoreMove.in"],
    };

    s.itemFormatter = function(panel, r, c, cell) {
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
  $scope.$on("hqStoreMoveCtrl", function (event, data) {
    $scope.searchHqStoreMoveList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장이동관리 리스트 조회
  $scope.searchHqStoreMoveList = function () {
    // 파라미터
    var params        = {};
    params.startDate  = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate    = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.outStoreCd = $("#hqStoreMoveOutSelectStoreCd").val();
    params.inStoreCd  = $("#hqStoreMoveInSelectStoreCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/move/hqStoreMove/hqStoreMove/list.sb", params);
  };

  // 신규등록
  $scope.newHqStoreMoveRegist = function () {
    var params = {};
    $scope._broadcast("hqStoreMoveRegistCtrl", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.hqStoreMoveOutSelectStoreShow = function () {
    $scope._broadcast('hqStoreMoveOutSelectStoreCtrl');
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.hqStoreMoveInSelectStoreShow = function () {
    $scope._broadcast('hqStoreMoveInSelectStoreCtrl');
  };

}]);
