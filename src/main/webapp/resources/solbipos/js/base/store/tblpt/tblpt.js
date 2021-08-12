/**
 * get application
 */
var app = agrid.getApp();

var useYnAllComboData = [
	  {"name": "전체", "value": ""},
	  {"name": "사용", "value": "Y"},
	  {"name": "미사용", "value": "N"}
	];

/** 창고관리 그리드 controller */
app.controller('tblptListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tblptListCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);

  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("tblptListCtrl");

    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnAllComboData, 'value', 'name'); // 사용여부

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "tblptCd") { // 창고코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "tblptCd") { // 창고코드
          var params       = {};
          params.tblptCd = selectedRow.tblptCd;
          params.tblptNm = selectedRow.tblptNm;
          params.useYn     = selectedRow.useYn;

          $scope._broadcast('tblptModLayerCtrl', params);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("tblptListCtrl", function (event, data) {
    $scope.searchTblptList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

//다른 컨트롤러의 broadcast 받기
  $scope.$on("tblptListCtrlSrch", function (event, data) {
    $scope.searchTblptList(false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //창고 정보 수정 후 리프레시

  // 창고 리스트 조회
  $scope.searchTblptList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.useYn     = $scope.useYn;
    params.tblptCd = $scope.tblptCd;
    params.tblptNm = $scope.tblptNm;
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/store/tblpt/tblpt/list.sb", params);

  };

  // 신규창고등록 팝업
  $scope.regTblptPopup = function () {
	  $scope._broadcast('tblptRegLayerCtrl');
  };


  // 엑셀 다운로드
  $scope.excelDownloadTblpt = function () {
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
      }, '창고관리_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };




  /*********************************************************
   * 테이블속성 등록
   * *******************************************************/
  $scope.fnTblpt = function(tblFgOp){
    var params          = {};
    var hrefChk         = window.location.href;
    var open_url        = 'http://192.168.0.72:2020/login/login_check_tbl.jsp?';
    var userStoreCd     = s_storeCd;
    var userIdChk       = s_userId;
    var vUserIdChk      = s_vUserIdChk;
    var userpwChk       = 'DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321';  //DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321
    var tblFg           = 'tblpt';  //DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321
    var tblFgOption     = tblFgOp;
    var tblptTempPw     = '';
    var tblptTempPwDate = getCurDateTime();

    if(hrefChk.indexOf("neo.solbipos.com") != -1)   open_url = 'http://mob.solbipos.com/login/login_check_tbl.jsp?';
    else if(hrefChk.indexOf("192.168.0.85") != -1)  open_url = 'http://192.168.0.85:22001/login/login_check_tbl.jsp?';
    else if(hrefChk.indexOf("192.168.0.72") != -1)  open_url = 'http://192.168.0.72:2020/login/login_check_tbl.jsp?';
    else if(hrefChk.indexOf("localhost") != -1)     open_url = 'http://localhost:2020/login/login_check_tbl.jsp?';
    else
    {
        $scope._popMsg(messages["tblpt.connectFail"]);
        return false;
    }

    if(tblFgOp == 'explainSetting')
    {
        window.open('http://mob.solbipos.com/etc/manual/explain_manual.jsp');
    }
    else if(tblFgOp == 'chromeSetup')
    {
        window.open('https://www.google.com/chrome/');
    }
    else
    {
        $scope._postJSONSave.withPopUp("/base/store/tblpt/tblpt/tblptOpn.sb", params, function (response) {
            var result = response.data.data;

            if(result === ""){
                $scope._popMsg(messages["tblpt.connectFail"]);
                return false;
            }else{
                tblptTempPw = result;
                var tblpt_url   = open_url
                                + 'AutoFg=M'
                                + '&nx_user_store_cd='+userStoreCd
                                + '&user_id='+userIdChk
                                + '&v_user_id='+vUserIdChk
                                + '&user_pwd='+userpwChk
                                + '&nx_user_id='+userIdChk
                                + '&nx_v_user_id='+vUserIdChk
                                + '&nx_user_pwd='+userpwChk
                                + '&login_auto_serial='
                                + '&appfg=web'
                                + '&tblfg='+tblFg
                                + '&tblFgOption='+tblFgOption
                                + '&nx_tmp_pw='+ tblptTempPw
                                + '&nx_tmp_pw_date=' + tblptTempPwDate
                                ;

                //window.open( tblpt_url, 'popup', '');
                var win = window.open(tblpt_url, tblptTempPwDate, '');
                //window.open( tblpt_url);

                $scope._popMsg(messages["tblpt.connectSuccess"]);
            }
        });
    }
  };


}]);
