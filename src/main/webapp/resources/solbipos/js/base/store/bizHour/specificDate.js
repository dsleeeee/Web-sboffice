// 시 VALUE
var Hh = [24];
for (i = 0; i < 24; i++) {
  var timeVal = i.toString();
  if (i >= 0 && i <= 9) {
    timeVal = "0" + timeVal;
  }
  Hh[i] = {"name": timeVal, "value": timeVal}
}

// 분 VALUE
var MmSs = [60];
for (i = 0; i < 60; i++) {
  var timeVal = i.toString();
  if (i >= 0 && i <= 9) {
    timeVal = "0" + timeVal;
  }
  MmSs[i] = {"name": timeVal, "value": timeVal}
}

/** 특정일 그리드 controller */
app.controller('specificCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('specificCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화
  $scope.initGrid = function (s, e) {
    // 조회 전 저장/삭제 시 collectionView null 방지
    $scope._gridDataInit();
    $scope._makePickColumns("specificCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.bizFgMap     = new wijmo.grid.DataMap(bizFgData, 'value', 'name');
    $scope.timeHourMap  = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');

    // 날짜 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });

    // 셀 수정 시 체크박스 자동 체크
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "bizFg" || col.binding === "startHour" || col.binding === "startMs" ||
            col.binding === "endHour" || col.binding === "endMs" || col.binding === "remark") {
          $scope.checked(item);
        }
      }
    });
  };

  // 헤더머지/로우넘/readOnly 표시
  $scope.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      panel.rows[r].allowMerging    = true;
      panel.columns[c].allowMerging = true;
      wijmo.setCss(cell, {display: 'table', tableLayout: 'fixed'});
      cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
      wijmo.setCss(cell.children[0], {display: 'table-cell', verticalAlign: 'middle', textAlign: 'center'});

      // gChk 컬럼 헤더 전체선택 체크박스
      if ((panel.grid.columnHeaders.rows.length - 1) === r) {
        var flex   = panel.grid;
        var column = flex.columns[c];
        if (column.binding === 'gChk') {
          column.allowSorting = false;
          var cnt = 0;
          for (var i = 0; i < flex.rows.length; i++) {
            if (flex.getCellData(i, c) === true) cnt++;
          }
          cell.innerHTML   = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
          var cb           = cell.firstChild;
          cb.checked       = cnt > 0 && cnt === flex.rows.length;
          cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
          cb.addEventListener('click', function (e) {
            flex.beginUpdate();
            for (var i = 0; i < flex.rows.length; i++) {
              flex.setCellData(i, c, cb.checked);
            }
            flex.endUpdate();
          });
        }
      }
    }
    else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
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
    else if (panel.cellType === wijmo.grid.CellType.Cell) {
      var col = panel.columns[c];
      if (col.isReadOnly || panel.grid.isReadOnly) {
        wijmo.addClass(cell, 'wj-custom-readonly');
      }
    }
  };

  // 셀 수정 시 체크박스 체크
  $scope.checked = function (item) {
    item.gChk = true;
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("specificCtrl", function (event, data) {
    $scope.searchSpecificList();
    event.preventDefault();
  });

  // 특정일 그리드 조회
  $scope.searchSpecificList = function () {
    var params = {};
    if (orgnFg === "STORE") {
      params.storeCd = orgnCd;
    } else {
      params.storeCd = $("#speSelectStoreCd").val();
    }
    params.listScale = $scope.conListScale.text;
    $scope._inquiryMain(baseUrl + "specificDate/list.sb", params);
  };

  // 특정일 신규등록
  $scope.newSpecific = function () {
    $scope._broadcast('speRegistCtrl');
  };

  // 특정일 저장(수정)
  $scope.saveSpecific = function () {
    $scope._popConfirm(messages["cmm.choo.save"], function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];
        item.status    = "U";
        item.startTime = item.startHour + item.startMs;
        item.endTime   = item.endHour + item.endMs;
        // 시작 < 종료 체크
        if (item.startTime >= item.endTime) {
          $scope._popMsg(messages["bizHour.invalidTime"]);
          return false;
        }
        params.push(item);
      }
      if (params.length === 0) {
        $scope._popMsg(messages["cmm.not.modify"]);
        return false;
      }
      $.postJSONArray(baseUrl + "specificDate/save.sb", params,
        function (result) {
          s_alert.pop(messages["cmm.saveSucc"]);
          $scope.searchSpecificList();
        },
        function (result) {
          s_alert.pop(result.message);
        });
    });
  };

  // 특정일 삭제
  $scope.deleteSpecific = function () {
    s_alert.popConf(messages["cmm.choo.delete"], function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.gChk) {
          params.push(item);
        }
      }
      if (params.length === 0) {
        s_alert.pop(messages["bizHour.noChkData"]);
        return false;
      }
      $.postJSONArray(baseUrl + "specificDate/delete.sb", params,
        function (result) {
          s_alert.pop(messages["cmm.delSucc"]);
          $scope.searchSpecificList();
        },
        function (result) {
          s_alert.pop(result.message);
        });
    });
  };

}]);
