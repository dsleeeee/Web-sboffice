
/** 실사관리 등록 그리드 controller */
app.controller('acinsRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('acinsRegistCtrl', $scope, $http, true));

  $scope._setComboData("regListScaleBox", gvListScaleBoxData);
  $scope._setComboData("acinsRegReason", reasonData);

  $scope._setComboData("srchAcinsFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["acins.reg.acinsFgN"], "value": "N"},
    {"name": messages["acins.reg.acinsFgY"], "value": "Y"},
  ]);

  $scope._setComboData("addQtyFg", [
    {"name": messages["acins.reg.addQtyFgApply"], "value": "apply"},
    {"name": messages["acins.reg.addQtyFgAdd"], "value": "add"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams             = {};
    // 출고창고
    var url = '/stock/acins/acins/acins/getOutStorageCombo.sb';

    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "acinsRegAdjStorageCd", null, url, comboParams, null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 실사수량 수정시 금액,VAT,합계 계산하여 보여준다.
        if (col.binding === "acinsQty") {
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }

      s.collectionView.commitEdit();
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  //DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
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

  $scope.calcAmt = function (item) {
    var costUprc    = parseInt(item.costUprc);
    var acinsQty    = parseInt(nvl(item.acinsQty, 0));
    var cmptCurrQty = parseInt(nvl(item.cmptCurrQty, 0));
    var adjQty      = parseInt(acinsQty) - parseInt(cmptCurrQty);
    var acinsAmt    = parseInt(acinsQty) * parseInt(costUprc);
    var adjAmt      = parseInt(adjQty) * parseInt(costUprc);

    item.adjQty   = adjQty;   // 조정수량
    item.acinsAmt = acinsAmt; // 실사금액
    item.adjAmt   = adjAmt; // 조정금액
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("acinsRegistCtrl", function (event, data) {

    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    if (!$.isEmptyObject(data)) {
      $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

      $scope.acinsDate  = data.acinsDate;
      $scope.seqNo      = data.seqNo;
      $scope.callParent = data.callParent;

      // 상품분류 값 초기화
      $scope.prodClassCdNm = messages["cmm.all"];
      $scope.prodClassCd   = '';

      // 상품찾기 변수값들 초기화
      $scope.addQty      = '';
      $scope.prodBarcdCd = '';
      $scope.autoAddChk  = false;
      $scope.acinsTitle  = '';
      $scope.acinsReason  = '';


      // 신규등록이면 실사구분 disabled 시킨다.
      if ($scope.callParent === "acins") {
        $scope.readAcinsFg = true;
        // 신규등록인 경우 진행구분 체크 필요없음으로 바로 팝업을 show 한다.
        $scope.layerShow();
      } else {
        $scope.readAcinsFg = false;
        $scope.procFgCheck(); // 실사진행구분 체크
      }
    } else { // 페이징처리에서 broadcast 호출시
      $scope.searchAcinsRegistList();
    }

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 실사진행구분 체크 및 실사제목 조회
  $scope.procFgCheck = function () {
    var params       = {};
    params.acinsDate = $scope.acinsDate;
    params.seqNo     = $scope.seqNo;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/stock/acins/acins/acinsRegist/procFgCheck.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 진행구분이 실사등록이 아니면 상품추가/변경 불가
        if (!$.isEmptyObject(response.data.data)) {
          if (response.data.data.procFg != "" && response.data.data.procFg != "0") {
            $scope._popMsg(messages["acins.reg.not.procEnd"]);
            return false;
          }
          $scope.acinsTitle = response.data.data.acinsTitle;
          $scope.acinsReason = response.data.data.acinsReason;
          $scope.acins.reg.adjStorageCd = response.data.data.adjStorageCd;
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
      $scope.layerShow();
    });
  };


  $scope.layerShow = function () {
    $scope.wjAcinsRegistLayer.show(true);
    $("#registSubTitle").html(messages["acins.reg.acinsDate"] + ' : ' + getFormatDate($scope.acinsDate, '-'));
  };


  // 실사상품 리스트 조회
  $scope.searchAcinsRegistList = function () {
    // 파라미터
    var params         = {};
    params.acinsDate   = $scope.acinsDate;
    params.seqNo       = $scope.seqNo;
    params.prodCd      = $scope.prodCd;
    params.prodNm      = $scope.prodNm;
    params.barcdCd     = $scope.barcdCd;
    params.prodClassCd = $scope.prodClassCd;
    params.acinsFg     = $scope.acinsFg;
    params.vendrCd     = $("#acinsRegistSelectVendrCd").val();
    params.listScale   = $scope.listScale;
    params.storageCd    = $scope.acins.reg.adjStorageCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/stock/acins/acins/acinsRegist/list.sb", params);
  };

  // 상품찾기버튼
  $scope.prodFind = function () {
    if($("#prodFind").css("display") === "none"){
      $("#prodFind").css("display", "");
    } else {
      $("#prodFind").css("display", "none");
    }
  }

  // 조회버튼으로 조회시
  $scope.fnSearch = function () {

	// if($scope.acins.reg.adjStorageCd === ""){
	//   alert("창고를 선택하여 주십시요.");
	//   return false;
	// }
    if ($scope.flex.collectionView.itemsEdited.length > 0 || $scope.flex.collectionView.itemsAdded.length > 0) {
      var msg = messages["acins.reg.searchMsg"]; // 저장되지 않은 자료가 있습니다. 조회하시겠습니까?
      s_alert.popConf(msg, function () {
        $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
        $scope.searchAcinsRegistList();
      });
    } else {
      $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
      $scope.searchAcinsRegistList();
    }
  };

  $scope.selectedIndexChangedReg = function (){
    $scope.searchAcinsRegistList();
  }

  // 실사 상품 저장
  $scope.saveAcinsRegist = function () {

    if (nvl($scope.acinsTitle, '') === '') {
      var msg = messages["acins.reg.acinsTitle"] + messages["cmm.require.text"]; // 실사제목을 입력하세요.
      $scope._popMsg(msg);
      return false;
    }

    if($scope.flex.collectionView.items.length > 0) {

      var params = [];

      // 체크만 되어있는 경우 체크 풀기
      // 추가된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        var item = $scope.flex.collectionView.itemsAdded[i];
        if (item.acinsQty === null) {
            item.gChk = false;
        }
      }
      // 수정된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];
        if (item.acinsQty === null) {
            item.gChk = false;
        }
      }

      // 추가된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        var item = $scope.flex.collectionView.itemsAdded[i];

        // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
        if (item.acinsQty === null || item.acinsQty === '') {
          item.status = "R";
        } else if (item.gChk === true && item.acinsProdStatus === 'U') {
          item.status = "D";
        } else {
          item.status = "U";
        }
        item.acinsDate  = $scope.acinsDate;
        item.seqNo      = $scope.seqNo;
        item.acinsTitle = $scope.acinsTitle;
        item.acinsReason = $scope.acinsReason;
        item.storageCd  = "999";		//001	->	999
        item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.adjStorageCd = $scope.acins.reg.adjStorageCd;

        if(item.status !== "R") params.push(item);
      }

      // 수정된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
        if (item.acinsQty === null || item.acinsQty === '') {
          item.status = "R";
        } else if (item.gChk === true && item.acinsProdStatus === 'U') {
          item.status = "D";
        } else {
          item.status = "U";
        }
        item.acinsDate  = $scope.acinsDate;
        item.seqNo      = $scope.seqNo;
        item.acinsTitle = $scope.acinsTitle;
        item.acinsReason = $scope.acinsReason;
        item.storageCd  = "999";	//001	->	999
        item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.adjStorageCd = $scope.acins.reg.adjStorageCd;

        if(item.status !== "R") params.push(item);
      }

      // 그리드를 조회한 후 입력안하고 저장시
      if(params.length === 0) {
        var params = [];
        var item = {};
        item.acinsDate = $scope.acinsDate;
        item.seqNo = $scope.seqNo;
        item.acinsTitle = $scope.acinsTitle;
        item.acinsReason = $scope.acinsReason;
        item.storageCd = "999";	//001	->	999
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.adjStorageCd = $scope.acins.reg.adjStorageCd;

        params.push(item);
      }

    // 신규,수정시 조회한 상품이 없을때 제목 저장하려고
    } else {

      var params = [];
      var item = {};
      item.acinsDate = $scope.acinsDate;
      item.seqNo = $scope.seqNo;
      item.acinsTitle = $scope.acinsTitle;
      item.acinsReason = $scope.acinsReason;
      item.storageCd = "999";	//001	->	999
      item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
      item.adjStorageCd = $scope.acins.reg.adjStorageCd;

      params.push(item);
    }

    // console.log(params);
    // if(params.length > 0 || ($scope.seqNo  !== null && $scope.seqNo !== ''))
    // {
        $scope._save("/stock/acins/acins/acinsRegist/save.sb", params, function () {
          $scope.saveRegistCallback()
        });
    // }
    // else
    // {
    //   $scope._popMsg(messages["cmm.not.modify"]);
    //   return false;
    // }

  };


  // 저장 후 콜백 서치 함수
  $scope.saveRegistCallback = function () {
    // 신규등록인 경우
    if ($scope.callParent === "acins") {
      var acinsScope = agrid.getScope('acinsCtrl');
      acinsScope.searchAcinsList();

      var params       = {};
      params.startDate = $scope.acinsDate;
      params.endDate   = $scope.acinsDate;

      $.postJSON("/stock/acins/acins/acins/list.sb", params, function(result) {
        $scope.seqNo = result.data.list[0].seqNo;
        $scope.searchAcinsRegistList();
      });

    }
    // 실사상세내역 페이지에서 호출한 경우
    else if ($scope.callParent === "acinsDtl") {
      var acinsScope = agrid.getScope('acinsCtrl');
      acinsScope.searchAcinsList();

      var acinsDtlScope = agrid.getScope('acinsDtlCtrl');
      acinsDtlScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
      acinsDtlScope.searchAcinsDtlList();
      acinsDtlScope.procFgCheck(false);
      $scope.searchAcinsRegistList();
    }

//    $scope.wjAcinsRegistLayer.hide(true);
  };


  // 현재고 수량적용.
  $scope.setCurrToAcins = function () {
    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
    // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
    $timeout(function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.cmptCurrQty !== null) {
          $scope.flex.collectionView.editItem(item);

          if (nvl(item.cmptCurrQty, 0) > 0) {
            item.acinsQty = parseInt(item.cmptCurrQty);
          } else {
            item.acinsQty = 0;
          }
          $scope.calcAmt(item);
          $scope.flex.collectionView.commitEdit();
        }
      }
      $scope.$broadcast('loadingPopupInactive');
    }, 100);
  };


  // 상품코드/바코드 input 박스에서 keyDown시
  $scope.searchProdKeyEvt = function (event) {
    if (event.keyCode === 13) { // 이벤트가 enter 이면
      var searchFg = true;

      // 조회된 상품중에 해당 상품코드/바코드가 있는지 검색
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
          searchFg = false; // 그리드에 이미 해당 상품이 있는 경우 서버 조회 하지않도록 변수값 false 로 변경
        }
      }

      if (searchFg) {
        // 파라미터
        var params         = {};
        params.acinsDate   = $scope.acinsDate;
        params.seqNo       = $scope.seqNo;
        params.prodBarcdCd = $scope.prodBarcdCd;
        params.listScale   = 1; // 상품 하나만 조회해야 하므로 listScale 1로 줌.
        params.curr        = 1;

        var url = "/stock/acins/acins/acinsRegist/getProdInfo.sb";
        $scope._postJSONQuery.withOutPopUp(url, params, function (response) {
          if ($.isEmptyObject(response.data.data)) {
            $scope._popMsg(messages["cmm.empty.data"]);
          } else {
            $scope.addRow(response.data.data);
            if ($("#autoAddChk").prop("checked")) {
              $scope.modifyAcinsQty(1);
            } else {
              $scope.addQty = 1;
              $("#addQty").select();
            }
          }
        });
      } else {
        if ($("#autoAddChk").prop("checked")) {
          $scope.modifyAcinsQty(1);
        } else {
          $scope.addQty = 1;
          $("#addQty").select();
        }
      }
    }
  };


  // 그리드의 상품을 찾아서 실사수 수정
  $scope.modifyAcinsQty = function (addQty) {

	// 숫자가 아닌 값
	var numChkexp = /[^0-9]/g;
	if (numChkexp.test(nvl(addQty, 0))) {
		return false;
	}

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
        $scope.flex.collectionView.editItem(item);

        item.acinsQty = parseInt(nvl(item.acinsQty, 0)) + parseInt(nvl(addQty, 0));

        $scope.calcAmt(item);
        $scope.flex.collectionView.commitEdit();
      }
    }

    // 자동추가가 체크되어 있는 경우 focus 를 계속 상품코드/바코드 입력하는곳에 둔다.
    if ($("#autoAddChk").prop("checked")) {
      $scope.addQty = '';
      $("#prodBarcdCd").select();
    }
  };


  // 추가버튼 클릭시
  $scope.fnAddQty = function () {
    var qty = $scope.addQty;
    $scope.modifyAcinsQty(qty);
  };


  // 추가수량 input 박스에서 keyDown시
  $scope.addQtyKeyEvt = function (event) {
    if (event.keyCode === 13) {
      $scope.fnAddQty();
    }
  };


  // grid 의 row 추가
  $scope.addRow = function (params) {
    var flex = $scope.flex;
    if (!flex.collectionView) {
      flex.itemsSource = new wijmo.collections.CollectionView();
    }
    flex.collectionView.trackChanges = true;
    var newRow                       = flex.collectionView.addNew();
    newRow.status                    = 'U';
    for (var prop in params) {
      newRow[prop] = params[prop];
    }
    flex.collectionView.commitNew();
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


  // 엑셀 다운로드
  $scope.excelDownload = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '실사_' + getCurDateTime() +'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


  /** 엑셀업로드 관련 공통 함수 */
  $scope.excelTextUpload = function (prcsFg) {
    if (nvl($scope.acinsTitle,'') === '' && prcsFg !== 'excelFormDown') {
      var msg = messages["acins.reg.acinsTitle"] + messages["cmm.require.text"]; // 실사제목을 입력하세요.
      $scope._popMsg(msg);
      return false;
    }
    // else if (nvl($scope.acins.reg.adjStorageCd,'') === '' && prcsFg !== 'excelFormDown') {
    //     var msg = messages["hqMove.outStorage"] + messages["cmm.require.text"];
    //     $scope._popMsg(msg);
    //     return false;
    // }

    var excelUploadScope = agrid.getScope('excelUploadStoreCtrl');
    /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. */
    var uploadFg = 'acins';

    // 엑셀 양식다운로드
    if (prcsFg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    } else {
      var msg = messages["excelUploadMPS.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
      s_alert.popConf(msg, function () {
        excelUploadScope.uploadFg = uploadFg;
        /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
        excelUploadScope.parentCtrl = 'acinsRegistCtrl';
        // 엑셀 업로드
        if (prcsFg === 'excelUp') {
          $("#excelUpFile").val('');
          $("#excelUpFile").trigger('click');
        }
        // 텍스트 업로드
        else if (prcsFg === 'textUp') {
          $("#textUpFile").val('');
          $("#textUpFile").trigger('click');
        }
      });
    }
  };


  /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
  $scope.uploadCallBack = function () {
    var params      = {};
    params.date     = $scope.acinsDate;
    params.seqNo    = $scope.seqNo;
    params.title    = $scope.acinsTitle;
    params.reason    = $scope.acinsReason;
    params.addQtyFg = $scope.addQtyFg;
    params.adjStorageCd    = $scope.acins.reg.adjStorageCd;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
    }

    var excelUploadScope = agrid.getScope('excelUploadStoreCtrl');

    $http({
      method : 'POST', //방식
      url    : '/stock/acins/acins/acinsRegist/excelUpload.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        // 엑셀 에러내역 팝업 호출
        $scope.excelUploadErrInfo();

        // 등록 그리드, 부모 그리드 조회
        $scope.saveRegistCallback();
      }
    }, function errorCallback(response) {
      $scope._popMsg(response.data.message);
      return false;
    }).then(function () {
      excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
    });
  };


  // 에러내역 팝업 호출
  $scope.excelUploadErrInfo = function () {
    var params      = {};
    params.uploadFg = 'acins';
    $scope._broadcast('excelUploadStoreErrInfoCtrl', params);
  };


  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.acinsRegistSelectVendrShow = function () {
    $scope._broadcast('acinsRegistSelectVendrCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 팝업 핸들러 추가
    $scope.wjAcinsRegistLayer.shown.addHandler(function () {
      setTimeout(function() {
        var params = {};
        $scope._inquirySub("/iostock/cmmExcelUpload/excelUploadStore/excelUploadStore/delete.sb", params, function() {}, false);
      }, 50);
    });

  });
}]);
