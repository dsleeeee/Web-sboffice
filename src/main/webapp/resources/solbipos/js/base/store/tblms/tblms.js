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
app.controller('tblmsListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tblmsListCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);

  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("tblmsListCtrl");

    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYnAllComboData, 'value', 'name'); // 사용여부

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "tblmsCd") { // 창고코드
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
        if (col.binding === "tblmsCd") { // 창고코드
          var params       = {};
          params.tblmsCd = selectedRow.tblmsCd;
          params.tblmsNm = selectedRow.tblmsNm;
          params.useYn     = selectedRow.useYn;

          $scope._broadcast('tblmsModLayerCtrl', params);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("tblmsListCtrl", function (event, data) {
    $scope.searchTblmsList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

//다른 컨트롤러의 broadcast 받기
  $scope.$on("tblmsListCtrlSrch", function (event, data) {
    $scope.searchTblmsList(false);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //창고 정보 수정 후 리프레시

  // 창고 리스트 조회
  $scope.searchTblmsList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.useYn     = $scope.useYn;
    params.tblmsCd = $scope.tblmsCd;
    params.tblmsNm = $scope.tblmsNm;
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/store/tblms/tblms/list.sb", params);

  };

  // 신규창고등록 팝업
  $scope.regTblmsPopup = function () {
	  $scope._broadcast('tblmsRegLayerCtrl');
  };


  // 엑셀 다운로드
  $scope.excelDownloadTblms = function () {
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
   * 테이블관리 등록
   * *******************************************************/
  $scope.fnTblms = function(tblFgOp){
    var params          = {};
    var hrefChk         = window.location.href;
    var open_url        = 'http://192.168.0.72:2020/login/login_check_tbl.jsp?';
    var userStoreCd     = s_storeCd;
    var userIdChk       = s_userId;
    var vUserIdChk      = s_vUserIdChk;
    var userpwChk       = 'DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321';  //DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321
    var tblFg           = 'tblms';  //DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321
    var tblFgOption     = tblFgOp;
    var tblmsTempPw     = '';
    var tblmsTempPwDate = getCurDateTime();

    if(hrefChk.indexOf("neo.solbipos.com") != -1)   open_url = 'http://mob.solbipos.com/login/login_check_tbl.jsp?';
    else if(hrefChk.indexOf("192.168.0.85") != -1)  open_url = 'http://192.168.0.85:22001/login/login_check_tbl.jsp?';
    else if(hrefChk.indexOf("192.168.0.72") != -1)  open_url = 'http://192.168.0.72:2020/login/login_check_tbl.jsp?';
    else if(hrefChk.indexOf("localhost") != -1)     open_url = 'http://localhost:2020/login/login_check_tbl.jsp?';
    else
    {
        $scope._popMsg(messages["tblms.connectFail"]);
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
        $scope._postJSONSave.withPopUp("/base/store/tblms/tblms/tblmsOpn.sb", params, function (response) {
            var result = response.data.data;

            if(result === ""){
                $scope._popMsg(messages["tblms.connectFail"]);
                return false;
            }else{
                tblmsTempPw = result;
                var tblms_url   = open_url
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
                                + '&nx_tmp_pw='+ tblmsTempPw
                                + '&nx_tmp_pw_date=' + tblmsTempPwDate
                                ;

                //window.open( tblms_url, 'popup', '');
                var win = window.open(tblms_url, tblmsTempPwDate, '');
                //window.open( tblms_url);

                $scope._popMsg(messages["tblms.connectSuccess"]);
            }
        });
    }
  };


}]);
