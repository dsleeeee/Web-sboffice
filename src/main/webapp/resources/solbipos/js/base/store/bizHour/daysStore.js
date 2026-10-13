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

// 요일코드(1~7) → 요일명
function getDayNm(fg) {
  for (var k = 0; k < dayFgData.length; k++) {
    if (dayFgData[k].value === fg) return dayFgData[k].name;
  }
  return fg;
}

/** 요일별 그리드 controller (매장 전용 : 요일별 한 줄씩, 일~토 기본 표시) */
app.controller('daysCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('daysCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 진입 시 자동조회 1회 플래그
  var autoSearched = false;

  // grid 초기화
  $scope.initGrid = function (s, e) {
    // 조회 전 저장 시 collectionView null 방지
    $scope._gridDataInit();
    $scope._makePickColumns("daysCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.dayFgMap     = new wijmo.grid.DataMap(dayFgData, 'value', 'name');
    $scope.timeHourMap  = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');

    // 셀 수정 시 해당 행 자동 체크(저장 대상으로 표시)
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "startHour" || col.binding === "startMs" ||
            col.binding === "endHour" || col.binding === "endMs") {
          $scope.checked(s.rows[e.row].dataItem);
        }
      }
    });

    // 진입 시 자동조회 (최초 1회)
    if (!autoSearched) {
      autoSearched = true;
      $scope.searchDaysList();
    }
  };

  // 헤더머지/로우넘/readOnly 표시 + gChk 전체선택
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

  // 다른 컨트롤러의 broadcast 받기(신규등록 팝업 저장 후 재조회)
  $scope.$on("daysCtrl", function (event, data) {
    $scope.searchDaysList();
    event.preventDefault();
  });

  // 요일별 조회 (매장 : 자기 매장, 요일별 한 줄씩)
  $scope.searchDaysList = function () {
    var params = {};
    params.storeCd = orgnCd;
    $scope._inquiryMain(baseUrl + "days/storeList.sb", params);
  };

  // 요일별 신규등록 (팝업)
  $scope.newDays = function () {
    $scope._broadcast('daysRegistCtrl');
  };

  // 요일별 저장 (매장 : 행 단위 — 시간O+seqO=수정 / 시간O+seqX=등록 / 시간 비움+seqO=삭제)
  $scope.saveDays = function () {
    var cv = $scope.flex.collectionView;
    if (isEmpty(cv)) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }
    var allRows = cv.sourceCollection;

    // 체크 여부(셀 수정 시 자동 체크됨)
    var hasChk = false;
    for (var e = 0; e < allRows.length; e++) {
      if (allRows[e].gChk === true) {
        hasChk = true;
        break;
      }
    }
    if (!hasChk) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }

    // 체크된 행 수집 + 유효성 (행 단위)
    var params = [];
    for (var i = 0; i < allRows.length; i++) {
      var row = allRows[i];
      if (row.gChk !== true) continue;
      var rowNo = i + 1;
      var dayNm = getDayNm(row.dayFg);
      var sh = nvl(row.startHour, ''), sm = nvl(row.startMs, '');
      var eh = nvl(row.endHour, ''),   em = nvl(row.endMs, '');
      var startFilled = (sh !== '' && sm !== '');
      var endFilled   = (eh !== '' && em !== '');
      var rowFilled   = (sh !== '' || sm !== '' || eh !== '' || em !== '');
      if (!rowFilled) {
        // 시간 비움 : 기존(seq) 있으면 삭제 대상, 없으면 무시
        if (!isEmpty(row.seq)) {
          params.push({storeCd: orgnCd, dayFg: row.dayFg, seq: row.seq, startTime: '', endTime: ''});
        }
        continue;
      }
      if (!startFilled || !endFilled) {          // 한쪽만 입력 = 오류
        $scope._popMsg("(" + dayNm + "요일) " + messages["bizHour.invalidTime"]);
        return false;
      }
      var st = sh + sm, et = eh + em;
      if (st >= et) {                            // 시작 >= 종료 = 오류
        $scope._popMsg("(" + dayNm + "요일) " + messages["bizHour.invalidTime"]);
        return false;
      }
      var p = {storeCd: orgnCd, dayFg: row.dayFg, startTime: st, endTime: et};
      if (!isEmpty(row.seq)) p.seq = row.seq;    // 기존=수정, 없으면=등록
      params.push(p);
    }

    // 같은 요일 시간 겹침 검증 (전체 행의 최종값 기준, 몇번째 행인지 안내. 비운 행은 완전구간 아니라 자동 제외)
    var dayMap = {};   // 요일 -> [{st,et,rowNo,dayNm}]
    for (var j = 0; j < allRows.length; j++) {
      var r2 = allRows[j];
      var jsh = nvl(r2.startHour, ''), jsm = nvl(r2.startMs, '');
      var jeh = nvl(r2.endHour, ''),   jem = nvl(r2.endMs, '');
      if (jsh === '' || jsm === '' || jeh === '' || jem === '') continue;   // 완전한 구간만
      var jst = jsh + jsm, jet = jeh + jem;
      if (jst >= jet) continue;
      (dayMap[r2.dayFg] = dayMap[r2.dayFg] || []).push({st: jst, et: jet, rowNo: j + 1, dayNm: getDayNm(r2.dayFg)});
    }
    for (var fg in dayMap) {
      if (!dayMap.hasOwnProperty(fg)) continue;
      var arr = dayMap[fg];
      for (var a = 0; a < arr.length; a++) {
        for (var b = a + 1; b < arr.length; b++) {
          if (arr[a].st < arr[b].et && arr[a].et > arr[b].st) {
            $scope._popMsg("(" + arr[b].dayNm + "요일) 시간이 중복됩니다.");
            return false;
          }
        }
      }
    }

    if (params.length === 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    $scope._popConfirm(messages["cmm.choo.save"], function () {
      $.postJSONArray(baseUrl + "days/storeSave.sb", params,
        function (result) {
          s_alert.pop(messages["cmm.saveSucc"]);
          $scope.searchDaysList();
        },
        function (result) {
          s_alert.pop(result.message);
        });
    });
  };

  // 요일별 삭제 (체크된 행 중 저장된 데이터(seq)가 있는 것만 삭제, 빈 행은 건너뜀)
  $scope.deleteDays = function () {
    var cv = $scope.flex.collectionView;
    if (isEmpty(cv)) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }
    var params = [];
    var allRows = cv.sourceCollection;
    for (var i = 0; i < allRows.length; i++) {
      var item = allRows[i];
      if (item.gChk !== true) continue;
      // 체크됐지만 저장된 데이터(seq)가 없는 행은 그냥 건너뜀 (있는 것만 삭제)
      if (isEmpty(item.seq)) continue;
      params.push({storeCd: orgnCd, dayFg: item.dayFg, seq: item.seq});
    }
    if (params.length === 0) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }
    s_alert.popConf(messages["cmm.choo.delete"], function () {
      $.postJSONArray(baseUrl + "days/delete.sb", params,
        function (result) {
          s_alert.pop(messages["cmm.delSucc"]);
          $scope.searchDaysList();
        },
        function (result) {
          s_alert.pop(result.message);
        });
    });
  };

}]);
