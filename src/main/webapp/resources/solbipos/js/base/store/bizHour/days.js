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

/** 요일별 그리드 controller (매장×요일 매트릭스) */
app.controller('daysCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('daysCtrl', $scope, $http, true));

  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 진입 시 자동조회 1회 플래그
  var autoSearched = false;

  // grid 초기화
  $scope.initGrid = function (s, e) {
    // 조회 전 저장/삭제 시 collectionView null 방지
    $scope._gridDataInit();
    // picker 사용시 호출
    $scope._makePickColumns("daysCtrl");

    // 그리드 DataMap 설정
    $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    $scope.timeHourMap  = new wijmo.grid.DataMap(Hh, 'value', 'name');
    $scope.timeMsMap    = new wijmo.grid.DataMap(MmSs, 'value', 'name');

    // 헤더머지 : 상단(요일) + 하단(시작/종료) 2줄 헤더
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      gChk        : messages["cmm.chk"],
      storeCd     : messages["bizHour.storeCd"],
      storeNm     : messages["bizHour.storeNm"],
      ownerNm     : messages["cmm.owner.nm"],
      sysStatFg   : messages["bizHour.sysStatFg"],
      startHourSun: messages["bizHour.day1"], startMsSun: messages["bizHour.day1"], endHourSun: messages["bizHour.day1"], endMsSun: messages["bizHour.day1"],
      startHourMon: messages["bizHour.day2"], startMsMon: messages["bizHour.day2"], endHourMon: messages["bizHour.day2"], endMsMon: messages["bizHour.day2"],
      startHourTue: messages["bizHour.day3"], startMsTue: messages["bizHour.day3"], endHourTue: messages["bizHour.day3"], endMsTue: messages["bizHour.day3"],
      startHourWed: messages["bizHour.day4"], startMsWed: messages["bizHour.day4"], endHourWed: messages["bizHour.day4"], endMsWed: messages["bizHour.day4"],
      startHourThu: messages["bizHour.day5"], startMsThu: messages["bizHour.day5"], endHourThu: messages["bizHour.day5"], endMsThu: messages["bizHour.day5"],
      startHourFri: messages["bizHour.day6"], startMsFri: messages["bizHour.day6"], endHourFri: messages["bizHour.day6"], endMsFri: messages["bizHour.day6"],
      startHourSat: messages["bizHour.day7"], startMsSat: messages["bizHour.day7"], endHourSat: messages["bizHour.day7"], endMsSat: messages["bizHour.day7"]
    };

    // 셀 수정 시 해당 행 자동 체크(저장 대상으로 표시)
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        // 체크박스(gChk) 토글은 자동체크 대상에서 제외 (해제가 되돌려지는 문제 방지)
        if (s.columns[e.col].binding !== 'gChk') {
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

  // 헤더머지/로우넘/readOnly 표시
  $scope.itemFormatter = function (panel, r, c, cell) {
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      panel.rows[r].allowMerging    = true;
      panel.columns[c].allowMerging = true;
      wijmo.setCss(cell, {display: 'table', tableLayout: 'fixed'});
      cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
      wijmo.setCss(cell.children[0], {display: 'table-cell', verticalAlign: 'middle', textAlign: 'center'});

      // gChk 컬럼 헤더 전체선택 체크박스 (최하단 헤더행만)
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
  $scope.$on("daysCtrl", function (event, data) {
    $scope.searchDaysList();
    event.preventDefault();
  });

  // 요일별 그리드 조회 (피벗)
  $scope.searchDaysList = function () {
    var params = {};
    if (orgnFg === "STORE") {
      params.storeCd = orgnCd;
    } else {
      params.storeCd = $("#daysSelectStoreCd").val();
    }
    $scope._inquiryMain(baseUrl + "days/pivotList.sb", params);
  };

  // 요일별 신규등록 (팝업)
  $scope.newDays = function () {
    $scope._broadcast('daysRegistCtrl');
  };

  // 요일 컬럼 정의 (요일코드 1=일 ~ 7=토)
  var DAY_DEFS = [
    {fg: '1', sh: 'startHourSun', sm: 'startMsSun', eh: 'endHourSun', em: 'endMsSun'},
    {fg: '2', sh: 'startHourMon', sm: 'startMsMon', eh: 'endHourMon', em: 'endMsMon'},
    {fg: '3', sh: 'startHourTue', sm: 'startMsTue', eh: 'endHourTue', em: 'endMsTue'},
    {fg: '4', sh: 'startHourWed', sm: 'startMsWed', eh: 'endHourWed', em: 'endMsWed'},
    {fg: '5', sh: 'startHourThu', sm: 'startMsThu', eh: 'endHourThu', em: 'endMsThu'},
    {fg: '6', sh: 'startHourFri', sm: 'startMsFri', eh: 'endHourFri', em: 'endMsFri'},
    {fg: '7', sh: 'startHourSat', sm: 'startMsSat', eh: 'endHourSat', em: 'endMsSat'}
  ];

  // 요일별 저장 (매트릭스 : 수정된 매장의 채워진 셀만 저장, 빈칸=삭제)
  //  - 서버는 수정된 매장을 통째 삭제 후 재삽입하므로, 비운 매장도 마커 1건을 보내 삭제되게 한다.
  $scope.saveDays = function () {
    var cv = $scope.flex.collectionView;
    if (isEmpty(cv)) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }

    // 체크된 행의 매장 집합 (셀 수정 시 자동 체크됨) — 페이징 무관하게 전체(sourceCollection) 기준
    var allRows = cv.sourceCollection;
    var storeSet = {};
    var hasChk = false;
    for (var e = 0; e < allRows.length; e++) {
      if (allRows[e].gChk === true) {
        storeSet[allRows[e].storeCd] = true;
        hasChk = true;
      }
    }
    if (!hasChk) {
      $scope._popMsg(messages["bizHour.noChkData"]);
      return false;
    }

    var params = [];
    var markedStore = {};
    var dayMap = {};   // 매장|요일 -> [{st,et,storeNm,dayNm}] (겹침검증용)
    for (var i = 0; i < allRows.length; i++) {
      var row = allRows[i];
      if (!storeSet[row.storeCd]) continue;   // 수정 안 된 매장은 건드리지 않음
      for (var d = 0; d < DAY_DEFS.length; d++) {
        var def = DAY_DEFS[d];
        var sh = nvl(row[def.sh], ''), sm = nvl(row[def.sm], '');
        var eh = nvl(row[def.eh], ''), em = nvl(row[def.em], '');
        var startFilled = (sh !== '' && sm !== '');
        var endFilled   = (eh !== '' && em !== '');
        var anyFilled   = (sh !== '' || sm !== '' || eh !== '' || em !== '');
        if (!anyFilled) continue;                // 빈칸 = 삭제(재삽입 안 함)
        var dayNm = getDayNm(def.fg);
        if (!startFilled || !endFilled) {        // 한쪽만 입력 = 오류
          $scope._popMsg((i + 1) + "번째 행(" + dayNm + ") : " + messages["bizHour.invalidTime"]);
          return false;
        }
        var st = sh + sm, et = eh + em;
        if (st >= et) {                          // 시작 >= 종료 = 오류
          $scope._popMsg((i + 1) + "번째 행(" + dayNm + ") : " + messages["bizHour.invalidTime"]);
          return false;
        }
        var key = row.storeCd + '|' + def.fg;
        (dayMap[key] = dayMap[key] || []).push({st: st, et: et, rowNo: (i + 1), dayNm: dayNm});
        params.push({storeCd: row.storeCd, dayFg: def.fg, startTime: st, endTime: et});
        markedStore[row.storeCd] = true;
      }
    }

    // 같은 (매장+요일) 시간 겹침 검증 — 어느 매장/요일인지 안내
    for (var key in dayMap) {
      if (!dayMap.hasOwnProperty(key)) continue;
      var arr = dayMap[key];
      for (var a = 0; a < arr.length; a++) {
        for (var b = a + 1; b < arr.length; b++) {
          if (arr[a].st < arr[b].et && arr[a].et > arr[b].st) {
            $scope._popMsg(arr[b].rowNo + "번째 행(" + arr[b].dayNm + ") : " + messages["bizHour.overlapTime"]);
            return false;
          }
        }
      }
    }

    // 전부 비운(삭제) 매장도 서버가 delete-all 하도록 마커 1건 추가
    for (var sc in storeSet) {
      if (storeSet.hasOwnProperty(sc) && !markedStore[sc]) {
        params.push({storeCd: sc, dayFg: '', startTime: '', endTime: ''});
      }
    }

    $scope._popConfirm(messages["cmm.choo.save"], function () {
      $.postJSONArray(baseUrl + "days/save.sb", params,
        function (result) {
          s_alert.pop(messages["cmm.saveSucc"]);
          $scope.searchDaysList();
        },
        function (result) {
          s_alert.pop(result.message);
        });
    });
  };

}]);
