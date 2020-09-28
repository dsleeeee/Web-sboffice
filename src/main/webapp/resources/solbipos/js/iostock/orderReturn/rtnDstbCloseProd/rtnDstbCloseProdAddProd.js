/** 분배마감 추가등록 상품 그리드 controller */
app.controller('rtnDstbCloseProdAddProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnDstbCloseProdAddProdCtrl', $scope, $http, true));

  //페이지 스케일 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "093";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prodCd") { // 상품코드
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
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "prodCd") { // 상품코드 클릭
          var params      = {};
          params.storeCd  = $("#rtnDstbCloseProdAddProdSelectStoreCd").val();
          params.reqDate  = $scope.reqDate;
          params.prodCd   = selectedRow.prodCd;
          params.prodNm   = selectedRow.prodNm;
          params.slipFg   = $scope.slipFg;
          params.storeCds = $("#rtnDstbCloseProdAddProdSelectStoreCd").val();
          $scope._broadcast('rtnDstbCloseProdAddRegistCtrl', params);
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnDstbCloseProdAddProdCtrl", function (event, data) {

    if (!$.isEmptyObject(data)) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.reqDate = data.reqDate;
      $scope.slipFg  = data.slipFg;

      // 값 초기화
      $scope.prodClassCdNm = messages["cmm.all"];
      $scope.prodClassCd   = '';

      $scope.wjRtnDstbCloseProdAddProdLayer.show(true);
      $("#addProdSubTitle").html(' ('+messages["rtnDstbCloseProd.add.reqDate"]+' : ' + getFormatDate($scope.reqDate, '-') + ')');
    }
    else { // 페이징처리에서 broadcast 호출시
      $scope.searchRtnDstbCloseProdAddProdList();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 분배가능상품 리스트 조회
  $scope.searchRtnDstbCloseProdAddProdList = function () {
	
//    if ($("#rtnDstbCloseProdAddProdSelectStoreCd").val() === "") {
//        $scope._popMsg(messages["rtnDstbCloseStore.add.require.selectStore"]); // 매장을 선택해 주세요.
//        return false;
//    }

    // 파라미터
    var params     = {};
    params.reqDate = $scope.reqDate;
    params.slipFg  = $scope.slipFg;
    params.storeCd = $("#rtnDstbCloseProdAddProdSelectStoreCd").val();
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddProd/list.sb", params);
    
//    // 조회 수행 : 조회URL, 파라미터, 콜백함수
//    $scope._postJSONQuery.withPopUp( "/iostock/order/dstbCloseProd/dstbCloseProdAddProd/dstbList.sb", params, function(response){
//	    var dstbFg = response.data.data;
//	    
//	    if(dstbFg < 1){ // 마감이 아닐때
//		    // 조회 수행 : 조회URL, 파라미터, 콜백함수
//		    $scope._inquiryMain("/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddProd/list.sb", params);
//	    }else{
//	    	$scope._popMsg(messages["rtnDstbCloseStore.add.txt2"]); // 이미 마감된 매장입니다.
//	    	// 그리드 초기화
//		    var rtnDstbCloseProdAddProdScope = agrid.getScope('rtnDstbCloseProdAddProdCtrl');
//		    rtnDstbCloseProdAddProdScope.dtlGridDefault();
//	    }
//    });
  };
  
  // 그리드 초기화
  $scope.dtlGridDefault = function () {
    $timeout(function () {
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;
      $scope.flex.refresh();
    }, 10);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnDstbCloseProdAddProdSelectStoreShow = function () {
    $scope._broadcast('rtnDstbCloseProdAddProdSelectStoreCtrl');
  };


  // 상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
            $scope.prodClassCdNm = response.data.data;
          }
        );
      }
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
    
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
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
