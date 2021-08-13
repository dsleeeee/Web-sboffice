/**
 * get application
 */
var app = agrid.getApp();

/** 창고관리 그리드 controller */
app.controller('tblmsListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tblmsListCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("tblmsListCtrl");
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
    var userpwChk       = 'DFSFLEJ234OJDOOA98D092OECUN092R01U73C02103U09C82B093102V321';
    var tblFg           = 'tblms';
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
