/**
 * get application
 */
var app = agrid.getApp();

/** 분배마감 그리드 controller */
app.controller('dstbCloseStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstbCloseStoreCtrl', $scope, $http, true));

  $scope.slipFg     = 1;
  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  var reqDate       = wcombo.genDate("#reqDate");

  $scope._setComboData("srchDateFg", [
    {"name": messages["dstbCloseStore.reqDate"], "value": "req"},
    {"name": messages["dstbCloseStore.regDate"], "value": "reg"},
    {"name": messages["dstbCloseStore.modDate"], "value": "mod"}
  ]);

  // 본사 거래처 콤보박스
  $scope._setComboData('vendrCd', vendrList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "084";
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo,map", "srchProcFg", "procFgMap", null, comboParams, "A", function () {
      $scope.procFg = "10"; // 진행구분 기본값 세팅
    }); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dstbCloseStoreCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd") { // 매장코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        } else if (col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
          var item = s.rows[e.row].dataItem;
          if (item.procFg !== "10") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            s.rows[e.row].isReadOnly = true;
          }
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
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
          var params     = {};
          params.reqDate = selectedRow.reqDate;
          params.storeCd = selectedRow.storeCd;
          params.storeNm = selectedRow.storeNm;
          params.slipFg  = selectedRow.slipFg;
          params.procFg  = selectedRow.procFg;
          params.vendrCd = $scope.vendrCdCombo.selectedValue;
          $scope._broadcast('dstbCloseStoreDtlCtrl', params);
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
    s.columnHeaders.rows[0].dataItem = {
      gChk       : messages["cmm.chk"],
      reqDate    : messages["dstbCloseStore.reqDate"],
      storeCd    : messages["dstbCloseStore.storeCd"],
      storeNm    : messages["dstbCloseStore.storeNm"],
      procFg     : messages["dstbCloseStore.procFg"],
      orderAmt   : messages["dstbCloseStore.order"],
      orderVat   : messages["dstbCloseStore.order"],
      orderTot   : messages["dstbCloseStore.order"],
      mgrAmt     : messages["dstbCloseStore.mgr"],
      mgrVat     : messages["dstbCloseStore.mgr"],
      mgrTot     : messages["dstbCloseStore.mgr"],
      dtlCntOrder: messages["dstbCloseStore.dtlCnt"],
      dtlCntAdd  : messages["dstbCloseStore.dtlCnt"],
      dtlCntTot  : messages["dstbCloseStore.dtlCnt"],
      modDt      : messages["dstbCloseStore.modDt"],
      slipFg     : messages["dstbCloseStore.slipFg"],
    };

    // 현재 로그인 사원에 맵핑된 거래처코드로 셋팅(없으면 '본사'로 셋팅됨.)
    $scope.vendrCdCombo.selectedValue = empVendrCd;
    // 거래처는 수정 못하게 처리
    $("#vendrCd").attr("disabled", true);
    $("#vendrCd").css('background-color', '#F0F0F0');

  };


  //체크박스가 있는 헤더머지 때문에 현재 페이지에 재정의 한 itemFormatter 를 사용
  $scope.itemFormatter = function (panel, r, c, cell) {
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

      if ((panel.grid.columnHeaders.rows.length - 1) === r) {
        // 헤더의 전체선택 클릭 로직
        var flex   = panel.grid;
        var column = flex.columns[c];
        // check that this is a boolean column
        if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
          // prevent sorting on click
          column.allowSorting = false;
          // count true values to initialize checkbox
          var cnt             = 0;
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
          var cb           = cell.firstChild;
          cb.checked       = cnt > 0;
          cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
          // apply checkbox value to cells
          cb.addEventListener('click', function (e) {
            flex.beginUpdate();
            for (var i = 0; i < flex.rows.length; i++) {
              var cell = flex.cells.getCellElement(i, c);
              // 활성화 및 readOnly 아닌 경우에만 체크되도록
              if (flex.getCellData(i, 4) === '10') {
                  flex.setCellData(i, c, cb.checked);
              }
            }
            flex.endUpdate();
          });
        }
      }
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
  };




  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstbCloseStoreCtrl", function (event, data) {
    $scope.searchDstbCloseStoreList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 주문 리스트 조회
  $scope.searchDstbCloseStoreList = function () {
    // 파라미터
    var params       = {};
    params.dateFg    = $scope.dateFg;
    params.slipFg    = $scope.slipFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.vendrCd   = $scope.vendrCdCombo.selectedValue;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/dstbCloseStore/dstbCloseStore/list.sb", params);
  };

  $scope.saveConfirm = function () {
    // 선택하신 자료를 분배마감으로 확정합니다. 확정하시겠습니까?
    var msg = messages["dstbCloseStore.confirmText"];
    s_alert.popConf(msg, function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.gChk === true) {
          item.status    = "U";
          item.empNo     = "0000";
          item.storageCd = "999";	//전체재고용 창고코드 ('001' -> '000' -> '999')
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
          params.push(item);
        }
      }
      $scope._save("/iostock/order/dstbCloseStore/dstbCloseStore/saveConfirm.sb", params, function () {
        $scope.searchDstbCloseStoreList()
      });
    });
  };

  $scope.add = function () {
    var params     = {};
    params.reqDate = wijmo.Globalize.format(reqDate.value, 'yyyyMMdd');
    params.slipFg  = $scope.slipFg;
    params.vendrCd = $scope.vendrCdCombo.selectedValue;
    $scope._broadcast('dstbCloseStoreAddCtrl', params);
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
                                                //return column.visible;
                                                return column.binding != 'gChk'; //선택
                                            }
                },
              //'분배마감(매장별)_' + getToday() + '.xlsx',
                '분배마감(매장별)_' + getCurDate('-') + '.xlsx',
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
