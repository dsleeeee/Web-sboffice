/**
 * get application
 */
var app = agrid.getApp();

/** 창고관리 그리드 controller */
app.controller('tblptListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tblptListCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("tblptListCtrl");
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
    var userpwChk       = 'DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321';
    var tblFg           = 'tblpt';
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
