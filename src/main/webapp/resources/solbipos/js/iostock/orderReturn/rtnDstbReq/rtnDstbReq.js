/**
 * get application
 */
var app = agrid.getApp();

/** 반품등록(요청분) 그리드 controller */
app.controller('rtnDstbReqCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnDstbReqCtrl', $scope, $http, true));

  $scope.slipFg     = -1;
  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  $scope._setComboData("srchDateFg", [
    {"name": messages["rtnDstbReq.reqDate"], "value": "req"},
    {"name": messages["rtnDstbReq.regDate"], "value": "reg"},
    {"name": messages["rtnDstbReq.modDate"], "value": "mod"}
  ]);
/* 기존소스수정 20200305
  $scope._setComboData("srchProcFg", [
    {"name": messages["rtnDstbReq.procFgAll"], "value": ""},
    {"name": messages["rtnDstbReq.procFgRegDstb"], "value": "10,20"},
    {"name": messages["rtnDstbReq.procFgReg"], "value": "10"},
    {"name": messages["rtnDstbReq.procFgDstb"], "value": "20"},
    {"name": messages["rtnDstbReq.procFgDstbCompt"], "value": "30"}
  ]);
*/  
  $scope._setComboData("srchProcFg", [
	    {"name": messages["rtnDstbReq.procFgAll"], "value": ""},
	    {"name": messages["rtnDstbReq.procFgRegDstb"], "value": "00,10"},
	    {"name": messages["rtnDstbReq.procFgReg"], "value": "00"},
	    {"name": messages["rtnDstbReq.procFgDstb"], "value": "10"},
	    {"name": messages["rtnDstbReq.procFgDstbCompt"], "value": "20"}
	  ]);
  $scope.procFg = ""; // 진행구분 기본값 세팅
/* 기존소스수정 20200305
  $scope.procFgMap = new wijmo.grid.DataMap([
	    {id: "10", name: messages["rtnDstbReq.procFgReg"]},
	    {id: "20", name: messages["rtnDstbReq.procFgDstb"]},
	    {id: "30", name: messages["rtnDstbReq.procFgDstbCompt"]}
	  ], 'id', 'name');
*/  
  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "00", name: messages["rtnDstbReq.procFgReg"]},
    {id: "10", name: messages["rtnDstbReq.procFgDstb"]},
    {id: "20", name: messages["rtnDstbReq.procFgDstbCompt"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnDstbReqCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd") { // 매장코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        else if (col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
          var item = s.rows[e.row].dataItem;
          if (item.procFg === "20") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            s.rows[e.row].isReadOnly = true;
          }
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
        if (col.binding === "storeCd") { // 매장코드 클릭
          var params      = {};
          params.reqDate  = selectedRow.reqDate;
          params.storeCd  = selectedRow.storeCd;
          params.storeNm  = selectedRow.storeNm;
          params.slipFg   = selectedRow.slipFg;
          params.procFg   = selectedRow.procFg;
          params.hdRemark = selectedRow.remark;
          $scope._broadcast('rtnDstbReqDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };
  
  // itemFormatter 기본설정 : private
  $scope.itemFormatter = function (panel, r, c, cell) {
    // 컬럼헤더 merged 의 헤더타이틀 중앙(vertical) 정렬
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      var mRange = panel.grid.getMergedRange(panel, r, c);
      if (mRange) {
        cell.innerHTML = '<div class=\"wj-header merged-custom\">' + cell.innerHTML + '</div>';
      }
      // 헤더의 전체선택 클릭 로직
      var flex = panel.grid;
      var column = flex.columns[c];
      // check that this is a boolean column
      if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
        // prevent sorting on click
        column.allowSorting = false;
        // count true values to initialize checkbox
        var cnt = 0;
        for (var i = 0; i < flex.rows.length; i++) {
          if (flex.getCellData(i, c) === true) {
            cnt++;
          }
        }
        // create and initialize checkbox
        if (column.format === 'checkBoxText') {
          cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
            + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
        } else {
          cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
        }
        var cb = cell.firstChild;
        cb.checked = cnt > 0;
        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
        // apply checkbox value to cells
        cb.addEventListener('click', function (e) {
          flex.beginUpdate();
          for (var i = 0; i < flex.rows.length; i++) {
            var cell = flex.cells.getCellElement(i, c);
            // TODO : 활성화 및 readOnly 아닌 경우에만 체크되도록             
             if (flex.getCellData(i, 4) === '00') {
              flex.setCellData(i, c, cb.checked);
             }
          }
          flex.endUpdate();
        });
      }
    } else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
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
      // readOnly 배경색 표시
    } else if (panel.cellType === wijmo.grid.CellType.Cell) {
      var col = panel.columns[c];
      if (col.isReadOnly) {
        wijmo.addClass(cell, 'wj-custom-readonly');
      }
    }
  }
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnDstbReqCtrl", function (event, data) {
    $scope.searchRtnDstbReqList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 반품 리스트 조회
  $scope.searchRtnDstbReqList = function () {
    // 파라미터
    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.dateFg    = $scope.dateFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/orderReturn/rtnDstbReq/rtnDstbReq/list.sb", params);
  };

  $scope.saveRtnDstbConfirm = function () {
    // 진행구분이 등록인 자료는 매장에서 등록한 수량으로, 분배중인 자료는 기분배된 수량으로 처리됩니다. 공급가 및 분배수량을 확인하시기 바랍니다. 분배완료 하시겠습니까?
    var msg = messages["rtnDstbReq.confirmText"];
    $scope._popConfirm(msg, function () {
    // s_alert.popConf(msg, function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.gChk === true) {
          item.status    = "U";
          item.empNo     = "0000";
          item.storageCd = "999";
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
          params.push(item);
        }
      }
      $scope._save("/iostock/orderReturn/rtnDstbReq/rtnDstbReq/saveDstbConfirm.sb", params, function () {
        $scope.searchRtnDstbReqList();
      });
    });
  };

}]);
