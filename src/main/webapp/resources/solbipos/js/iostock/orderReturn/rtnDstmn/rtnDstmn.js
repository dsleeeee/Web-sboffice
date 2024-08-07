/**
 * get application
 */
var app = agrid.getApp();

/** 거래명세표 그리드 controller */
app.controller('rtnDstmnCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnDstmnCtrl', $scope, $http, true));
  $scope.slipFg = -1;

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  var writtenDate   = wcombo.genDate("#writtenDate");

  // 전표구분 grid data-map
  $scope.slipFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["rtnDstmn.orderSlipFg"]},
    {id: "-1", name: messages["rtnDstmn.rtnSlipFg"]},
  ], 'id', 'name');

  // 진행구분 grid data-map
  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "10", name: messages["rtnDstmn.procFg10"]},
    {id: "20", name: messages["rtnDstmn.procFg20"]},
    {id: "30", name: messages["rtnDstmn.procFg30"]},
  ], 'id', 'name');

  // 진행구분
  $scope._setComboData("srchProcFg", [
    {"name": messages["rtnDstmn.procFgAll"], "value": ""},
    {"name": messages["rtnDstmn.procFg10"], "value": "10"},
    {"name": messages["rtnDstmn.procFg20"], "value": "20"},
    {"name": messages["rtnDstmn.procFg30"], "value": "30"},
    {"name": messages["rtnDstmn.procFg2030"], "value": "20,30"}
  ]);

  // 거래명세표
  $scope._setComboData("stmtAcctFg", [
    {"name": messages["rtnDstmn.stmtAcctAll"], "value": ""},
    {"name": messages["rtnDstmn.stmtAcctSplr"], "value": "1"},
    {"name": messages["rtnDstmn.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  // 세금계산서 청구, 영수 구분
  $scope._setComboData("billFg", [
    {"name": messages["rtnDstmn.billFg0"], "value": "0"},
    {"name": messages["rtnDstmn.billFg1"], "value": "1"}
  ]);

  // 세금계산서 일반, 과세/면세
  $scope._setComboData("taxFg", [
    {"name": messages["rtnDstmn.taxFg0"], "value": "0"},
    {"name": messages["rtnDstmn.taxFg1"], "value": "1"}
  ]);

  // 본사 거래처 콤보박스
  $scope._setComboData('vendrCd', vendrList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $("#reqNoConfirmCnt").html("0");
    $("#outstockNoCreateCnt").html("0");

    // 전표종류
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "087";
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo,map", "srchSlipKind", "slipKindMap", null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 배송기사
    comboParams = {}; // 여러번 조회시 초기화를 해줘야함...
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	comboParams.sid = document.getElementsByName('sessionId')[0].value;
    }
    var url     = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo,map", "srchDlvrCd", "dlvrMap", url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnDstmnCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        // else if(col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
        //   var item = s.rows[e.row].dataItem;
        //   if(item.procFg !== "10") {
        //     wijmo.addClass(e.cell, 'wj-custom-readonly');
        //     s.rows[e.row].isReadOnly = true;
        //   }
        // }

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
        if (col.binding === "slipNo") { // 전표번호 클릭
          var params    = {};
          params.startDate  = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
          params.endDate    = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
          params.slipFg = $scope.slipFg;
          params.slipNo = selectedRow.slipNo;
          if(selectedRow.procFg < '20'){
        	  params.reqDate = selectedRow.reqDate;
          }else{
        	  params.reqDate = selectedRow.outDate;
          }
          params.vendrCd   = $scope.vendrCdCombo.selectedValue;
          console.log(params);
          $scope._broadcast('rtnDstmnDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 현재 로그인 사원에 맵핑된 거래처코드로 셋팅(없으면 '본사'로 셋팅됨.)
    $scope.vendrCdCombo.selectedValue = empVendrCd;
    // 거래처는 수정 못하게 처리
    $("#vendrCd").attr("disabled", true);
    $("#vendrCd").css('background-color', '#F0F0F0');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnDstmnCtrl", function (event, data) {
    $scope.getReqNoConfirmCnt();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 매장요청 미확정건, 출고자료 미생성건 조회
  $scope.getReqNoConfirmCnt = function () {
    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.vendrCd   = $scope.vendrCdCombo.selectedValue;
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }	
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/orderReturn/rtnDstmn/rtnDstmn/reqNoConfirmCnt.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          $("#reqNoConfirmCnt").html(response.data.data.reqNoConfirmCnt); //매장요청 미확정건
          $("#outstockNoCreateCnt").html(response.data.data.outstockNoCreateCnt); //출고자료 미생성건
          $scope.searchRtnDstmnList();
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope._popMsg(messages["cmm.saveFail"]);
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 거래명세표 리스트 조회
  $scope.searchRtnDstmnList = function () {
    // 파라미터
    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.vendrcd   = $scope.vendrCdCombo.selectedValue;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/orderReturn/rtnDstmn/rtnDstmn/list.sb", params);
  };


  // 리포트
  $scope.report = function (reportFg) {
    var strSlipNo = '';
    var strDlvrCd = '';
    if (!$scope.flex.collectionView) {
      $scope.flex.itemsSource = new wijmo.collections.CollectionView();
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];
      if (item.gChk === true) {
        strSlipNo += (strSlipNo === '' ? '' : ',') + item.slipNo;
        if (nvl(item.dlvrCd, '') !== '' && strDlvrCd.indexOf(item.dlvrCd) < 0) {
          strDlvrCd += (strDlvrCd === '' ? '' : ',') + item.dlvrCd;
        }
      }
    }

    if (strSlipNo === '') {
      $scope._popMsg(messages['dstmn.require.slipNo']);
      return false;
    }

    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.strSlipNo = strSlipNo;

    // 상품
    if (reportFg === 'prod') {
      $scope._broadcast('dstbProdReportCtrl', params);
    }
    // 상품-매장
    else if (reportFg === 'prodStore') {
      $scope._broadcast('dstbProdStoreReportCtrl', params);
    }
    // 매장-상품
    else if (reportFg === 'storeProd') {
      $scope._broadcast('dstbStoreProdReportCtrl', params);
    }
    // 기사별
    else if (reportFg === 'dlvr') {
      if (strDlvrCd === '') {
        $scope._popMsg(messages['dstmn.require.dlvr']);
        return false;
      }
      params.strDlvrCd = strDlvrCd;
      $scope._broadcast('dstbDlvrCtrl', params);
    }
    // 거래명세표
    else if (reportFg === 'trans') {
      params.stmtAcctFg = $scope.stmtAcctFg;
      params.startDate  = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate    = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
      $scope._broadcast('transReportCtrl', params);
    }
    // 세금계산서
    else if (reportFg === 'tax') {
      params.writtenDate = wijmo.Globalize.format(writtenDate.value, 'yyyyMMdd');
      params.billFg      = $scope.billFg;
      params.taxFg       = $scope.taxFg;
      $scope._broadcast('taxReportCtrl', params);
    }

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

}]);
