/****************************************************************
 *
 * 파일명 : smsSend.js
 * 설  명 : 매출관리 > 매출현황2 > 사원카드매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.05     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 승인 controller */
app.controller('empCardCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('empCardCtrl', $scope, $http, true));

  $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate    = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // 그리드 매출구분
  $scope.saleYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["todayDtl.saleY"]},
    {id: "N", name: messages["todayDtl.saleN"]}
  ], 'id', 'name');


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.getStorePosList();

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "billNo") { // 영수증번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        // 결제수단
        for (var i = 0; i < payColList.length; i++) {
          if (col.binding === ("pay" + payColList[i].payCd)) {
            var item = s.rows[e.row].dataItem;

            // 값이 있으면 링크 효과
            if (nvl(item[("pay" + payColList[i].payCd)], '') !== '') {
              wijmo.addClass(e.cell, 'wijLink');
              wijmo.addClass(e.cell, 'wj-custom-readonly');
            }
          }
        }

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
        params.storeCd  = selectedRow.storeCd;
        params.saleDate = selectedRow.saleDate.replaceAll("-", "");
        params.posNo    = selectedRow.posNo;
        params.billNo   = selectedRow.billNo;
        params.saleYn   = selectedRow.saleYn;
        params.webReg   = selectedRow.webReg;

        if (col.binding === "billNo") { // 영수증번호 클릭
          console.log(params);
          $scope._broadcast('billInfoCtrl', params);
        }
        // 결제수단
        for (var i = 0; i < payColList.length; i++) {
          if (col.binding === ("pay" + payColList[i].payCd)) {
            // var item = s.rows[e.row].dataItem;

            // 021:현금영수증은 따로 처리
            if(payColList[i].payCd !== "021"){
              // 값이 있으면 링크
              if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '') {
                $scope._broadcast(payColList[i].payMethod.toLowerCase().replaceAll('_','') + 'Ctrl', params);
              }
            } else if (payColList[i].payCd === "021") {
              // 값이 있으면 링크(현금영수증은 현금 팝업 이용)
              if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '') {
                $scope._broadcast(payColList[i-1].payMethod.toLowerCase().replaceAll('_','') + 'Ctrl', params);
              }
            }
          }
        }

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

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("empCardCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.storeCd     = messages["empCard.storeCd"];
    dataItem.storeNm     = messages["empCard.storeNm"];
    dataItem.saleDate    = messages["empCard.saleDate"];
    dataItem.yoil        = messages["empCard.yoil"];

    dataItem.posNo       = messages["todayDtl.dtl.posNo"];
    dataItem.billNo      = messages["todayDtl.dtl.billNo"];
    dataItem.billDt      = messages["todayDtl.dtl.billDt"];
    dataItem.saleYn      = messages["todayDtl.dtl.saleYn"];
    dataItem.totSaleAmt  = messages["todayDtl.dtl.totSaleAmt"];
    dataItem.totDcAmt    = messages["todayDtl.dtl.totDcAmt"];
    dataItem.realSaleAmt = messages["todayDtl.dtl.realSaleAmt"];
    dataItem.gaAmt       = messages["todayDtl.dtl.gaAmt"];
    dataItem.vatAmt      = messages["todayDtl.dtl.vatAmt"];
    dataItem.totTipAmt   = messages["todayDtl.dtl.totTipAmt"];
    dataItem.totEtcAmt   = messages["todayDtl.dtl.totEtcAmt"];
    dataItem.cupAmt      = messages["todayDtl.dtl.cupAmt"];

    dataItem.employeeCardNo = messages["empCard.employeeCardNo"];
    dataItem.employeeNo     = messages["empCard.employeeNo"];
    dataItem.employeeNm     = messages["empCard.employeeNm"];
    dataItem.divNm          = messages["empCard.divNm"];
    dataItem.deptNm         = messages["empCard.deptNm"];
    dataItem.positionNm     = messages["empCard.positionNm"];

    dataItem.totPayAmt   = messages["todayDtl.dtl.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["todayDtl.dtl.payMethod"];
    }
    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dcInfo"];
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
  $scope.$on("empCardCtrl", function (event, data) {
    $scope.searchEmpCardList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchEmpCardList = function () {
    if ($("#empCardStoreCd").val() === '') {
      $scope._popMsg(messages["todayDtl.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }

    var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1달(31일) 제한
    if (diffDay >= 31) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#empCardStoreCd").val();
    params.posNo     = $scope.posNo;
    params.payCol    = payCol;
    params.dcCol     = dcCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/empCard/list.sb", params);
  };

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };

  // 매장의 POS 리스트 조회
  $scope.getStorePosList = function () {
    var url             = '/sale/today/todayDtl/todayDtl/storePosList.sb';
    var comboParams     = {};
    comboParams.storeCd = $("#empCardStoreCd").val();
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchPosNo", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
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
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '사원카드매출_' +getCurDateTime()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);