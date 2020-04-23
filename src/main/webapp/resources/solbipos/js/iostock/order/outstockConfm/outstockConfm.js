/**
 * get application
 */
var app = agrid.getApp();

/** 출고확정 그리드 controller */
app.controller('outstockConfmCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('outstockConfmCtrl', $scope, $http, true));
  $scope.slipFg = 1;

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  var outDate       = wcombo.genDate("#outDate");

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $("#reqNoConfirmCnt").html("0");
    $("#outstockNoCreateCnt").html("0");

    $scope.setCombo();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("outstockConfmCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
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
        if (col.binding === "slipNo") { // 전표번호 클릭
          var params    = {};
          params.slipNo = selectedRow.slipNo;
          params.slipFg = $scope.slipFg;

          $scope._broadcast('outstockConfmDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // combo 세팅
  $scope.setCombo = function () {
    // 진행구분
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "086";
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo,map", "srchProcFg", "procFgMap", null, comboParams, "A", function () {
      $scope.procFg = "10"; // 진행구분 기본값 세팅
    }); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 전표종류
    comboParams             = {}; // 여러번 조회시 초기화를 해줘야함...
    comboParams.nmcodeGrpCd = "087";
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo,map", "srchSlipKind", "slipKindMap", null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 배송기사
    comboParams = {}; // 여러번 조회시 초기화를 해줘야함...
    var url     = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchDlvrCd", null, url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("outstockConfmCtrl", function (event, data) {
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
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
    
    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/iostock/order/outstockConfm/outstockConfm/reqNoConfirmCnt.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          $("#reqNoConfirmCnt").html(response.data.data.reqNoConfirmCnt); //매장요청 미확정건
          $("#outstockNoCreateCnt").html(response.data.data.outstockNoCreateCnt); //출고자료 미생성건
          $scope.searchOutstockConfmList();
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

  // 출고확정 리스트 조회
  $scope.searchOutstockConfmList = function () {
    // 파라미터
    var params       = {};
    params.slipFg    = $scope.slipFg;
    // params.procFg    = "10";
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.vendrCd   = $("#outstockConfmSelectVendrCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/outstockConfm/outstockConfm/list.sb", params);
  };

  $scope.saveOutstockConfirm = function () {
    var params = [];

    if ($scope.flex.collectionView.itemsEdited.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.gChk === true) {
        item.status    = "U";
        item.outDate   = wijmo.Globalize.format(outDate.value, 'yyyyMMdd');
        item.empNo     = "0000";
        item.storageCd = "999";	//전체재고용 창고코드 ('001' -> '000' -> '999')
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        params.push(item);
      }
    }

    $scope._save("/iostock/order/outstockConfm/outstockConfm/saveOutstockConfirm.sb", params, function () {
    
      $scope.getReqNoConfirmCnt()
    });
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


  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.outstockConfmSelectVendrShow = function () {
    $scope._broadcast('outstockConfmSelectVendrCtrl');
  };

}]);
