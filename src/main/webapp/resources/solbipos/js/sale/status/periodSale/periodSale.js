/**
 * get application
 */
var app = agrid.getApp();

/** 영수증별매출상세현황 controller */
app.controller('periodSaleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('periodSaleCtrl', $scope, $http, true));

  $scope.srchPeriodSaleStartDate = wcombo.genDateVal("#srchPeriodSaleStartDate", gvStartDate);
  $scope.srchPeriodSaleEndDate = wcombo.genDateVal("#srchPeriodSaleEndDate", gvEndDate);

  // 그리드 매출구분
  $scope.saleYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["todayBillSaleDtl.saleY"]},
    {id: "N", name: messages["todayBillSaleDtl.saleN"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.getStorePosList();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("periodSaleCtrl");

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
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("periodSaleCtrl", function (event, data) {
    $scope.searchTodayBillSaleList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchTodayBillSaleList = function () {
    if ($("#periodSaleSelectStoreCd").val() === '') {
      $scope._popMsg(messages["todayBillSaleDtl.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }

    var startDt = new Date(wijmo.Globalize.format($scope.srchPeriodSaleStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchPeriodSaleEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (1000 * 60 * 60 * 24); // 시 * 분 * 초 * 밀리세컨

    // 조회일자 최대 한달(31일) 제한
    if (diffDay >= 31) {
      $scope._popMsg(messages['weight.date.error']);
      return false;
    }


    // 파라미터
    var params       = {};
    params.storeCd   = $("#periodSaleSelectStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchPeriodSaleStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchPeriodSaleEndDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/periodSale/list.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.periodSaleSelectStoreShow = function () {
    $scope._broadcast('periodSaleSelectStoreCtrl');
  };


  // 매장의 POS 리스트 조회
  $scope.getStorePosList = function () {
    var url             = '/sale/today/todayDtl/todayDtl/storePosList.sb';
    var comboParams     = {};
    if(orgnFg === "STORE"){
      comboParams.storeCd = storeCd;
    } else {
      comboParams.storeCd = $("#periodSaleSelectStoreCd").val();
    }
    console.log(comboParams.storeCd);
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
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayBillSaleDtl.billSale"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };
}]);
