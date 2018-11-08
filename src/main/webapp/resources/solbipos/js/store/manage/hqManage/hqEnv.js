/****************************************************************
 *
 * 파일명 : hqEnvCtrl.js
 * 설  명 : 본사정보관리 > 본사환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.07     김지은      1.0
 *
 * **************************************************************/

app.controller('hqEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqEnvCtrl', $scope, $http, false));

  // // 조회시 선택된 환경변수 그룹 (재조회를 위해 변수로 관리)
  // $scope.envGroupCd = "00";
  // $scope.setEnvGroupCd = function(envGroupCd){
  //   $scope.envGroupCd = envGroupCd;
  // };
  // $scope.getEnvGroupCd = function(){
  //   return $scope.envGroupCd;
  // };

  // 팝업 오픈시 매장정보 조회
  $scope.$on('hqEnvCtrl', function(event, data) {
    $scope.searchHqEnv();
    event.preventDefault();
  });


  /*********************************************************
   *  환경변수 조회
   * *******************************************************/
  $scope.searchHqEnv = function(){

    var params        = {};
    var hqScope       = agrid.getScope('hqManageCtrl');
    params.hqOfficeCd = hqScope.getSelectedHq().hqOfficeCd;

    $scope._postJSONQuery.withPopUp( '/store/hq/hqManage/config/getConfiglist.sb', params, function(response){

      if (!$.isEmptyObject(response.data)) {

        $("#hqEnvTitle").text("[" + hqScope.getSelectedHq().hqOfficeCd + "] " + hqScope.getSelectedHq().hqOfficeNm);

        var list = response.data.data.list;

        $scope.$broadcast('loadingPopupInactive');
        $scope.setEnvContents(list);
      }
    });
  };

  /*********************************************************
   *  환경변수 그리기
   * *******************************************************/
  $scope.setEnvContents = function(list){

    var innerHtml = '';
    var allCnt    = 0; // 전체 환경값 갯수
    var existCnt  = 0; // 현재 등록된 환경값 갯수

    for(var i=0; i<envstGrp.length; i++) { // 환경변수 테이블 그리기

      var hqEnvCnt   = 0;
      var hqEnvHtml  = '';
      var hqEnvstGrp = envstGrp[i];

      hqEnvHtml += '<h3 class=\'h3_tbl2 lh30\'>' + hqEnvstGrp.name + '<button class=\'open\'></button>';

      if (i === 0) { // 기본사항에 기본값으로 설정 버튼 추가
        hqEnvHtml += '<span class=\'fr\'><a href=\'javascript:;\' class=\'btn_grayS\' id=\'btnDefault\' onclick=\'setDefault()\'>';
        hqEnvHtml += messages["hqManage.setting.default.env"];
        hqEnvHtml += '</a></span>';
      }

      hqEnvHtml += '</h3>';
      hqEnvHtml += '<table class=\'searchTbl\'>';
      hqEnvHtml += '  <colgroup>';
      hqEnvHtml += '    <col class=\'w5\' />';
      hqEnvHtml += '    <col class=\'w25\' />';
      hqEnvHtml += '    <col class=\'w20\' />';
      hqEnvHtml += '    <col class=\'w5\' />';
      hqEnvHtml += '    <col class=\'w25\' />';
      hqEnvHtml += '    <col class=\'w20\' />';
      hqEnvHtml += '  </colgroup>';
      hqEnvHtml += '<tbody>';

      var b_env = ''; // 변경전의 환경변수

      for (var j = 0; j < list.length; j++) {

        if (hqEnvstGrp.value === list[j].envstGrpCd) {
          if (b_env === '' || b_env != list[j].envstCd) {
            if (hqEnvCnt === 0 || hqEnvCnt % 2 === 0) hqEnvHtml += '<tr>';

            if (list[j].existFg    === 'Y') {
              hqEnvHtml += '  <th class=\'tc\'>' + list[j].envstCd + '</th>';
            } else {
              hqEnvHtml += '  <th class=\'tc\'>' + list[j].envstCd + '<em class=\'imp\'>*</em></th>';
            }

            hqEnvHtml += '  <td>' + list[j].envstNm + '</td>';
            hqEnvHtml += '  <td>';

            if (list[j].dirctInYn === 'Y') { // 직접입력
              hqEnvHtml += '    <input type=\'text\' name=\'envstValCd\' id=\'env' + list[j].envstCd + '\' class=\'sb-input w100\'>';
            } else {  // 값 선택
              hqEnvHtml += '    <select name=\'envstValCd\' id=\'env' + list[j].envstCd + '\' class=\'sb-select w100\' />';
            }

            hqEnvHtml += '    <input type=\'hidden\' name=\'status\'    value=\'' + (list[j].existFg === 'N' ? 'I' : 'U') + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'envstCd\'   value=\'' + list[j].envstCd + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'envstNm\'   value=\'' + list[j].envstNm + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'envstGrpCd\'value=\'' + list[j].envstGrpCd + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'defltYn\'   value=\'' + list[j].defltYn + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'dirctInYn\' value=\'' + list[j].dirctInYn + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'targtFg\'   value=\'' + list[j].targtFg + '\'>';
            hqEnvHtml += '    <input type=\'hidden\' name=\'oldEnvstVal\'   value=\'' + list[j].selEnvstVal + '\'>';
            hqEnvHtml += '  </td>';

            b_env = list[j].envstCd;
            hqEnvCnt++;
            allCnt++;

            if (list[j].existFg    === 'Y') existCnt++;
            if (list[j].envstCdCnt === hqEnvCnt && (hqEnvCnt % 2 === 1)) {
              hqEnvHtml += '  <td class=\'tc\'></td>';
              hqEnvHtml += '  <td></td>';
              hqEnvHtml += '  <td></td>';
              hqEnvHtml += '</tr>';
            } else if (hqEnvCnt % 2 === 0) {
              hqEnvHtml += '</tr>';
            }
          }
        }
      }

      hqEnvHtml += '  </tbody>';
      hqEnvHtml += '</table>';
      hqEnvHtml += '</div>';
      hqEnvHtml += '<br>';

      if (hqEnvCnt > 0) innerHtml += hqEnvHtml;

    }

    $('#configContent').html(innerHtml);

    // 환경변수 select option 추가
    var envstCd = '';
    var sOption = false;

    for(var i=0; i<list.length; i++){
      if(list[i].dirctInYn === 'N') {
        $('#env'+list[i].envstCd).append('<option value=\''+ list[i].envstValCd +'\' >' + list[i].envstValNm +  '</option>');
        if(i === 0 || envstCd !== list[i].envstCd ) {
          envstCd = list[i].envstCd;
          if(list[i].selEnvstVal === list[i].envstValCd){
            sOption = true;
            $('#env'+list[i].envstCd).val(list[i].envstValCd).prop('selected', true);
          }
          else{
            sOption = false;
            $('#env'+list[i].envstCd).val(list[i].envstValCd).prop('selected', true);
          }
        }else if(list[i].selEnvstVal === list[i].envstValCd){
          sOption = true;
          $('#env'+list[i].envstCd).val(list[i].envstValCd).prop('selected', true);
        }else if(sOption === false && list[i].defltYn === 'Y') {
          $('#env'+list[i].envstCd).val(list[i].envstValCd).prop('selected', true);
        }

        if(list[i].defltYn === 'Y') {
          $('#env'+list[i].envstCd).attr('defaultVal', list[i].envstValCd);
        }

      } else {
        if(list[i].selEnvstVal === '' || list[i].selEnvstVal === null ) {
          $('#env'+list[i].envstCd).val('*');
        } else{
          $('#env'+list[i].envstCd).val(list[i].selEnvstVal);
        }
      }
    }

    // 등록되지 않은 환경값이 있을 경우
    if(allCnt > existCnt) {

      var msg = messages["hqManage.no.regist.env"]
          + messages["hqManage.require.regist.env"] +"\n"
          + allCnt + messages["hqManage.total.env.count"]
          + (allCnt - existCnt)+ messages["hqManage.no.regist.env.count"]
      ;

      $scope._popMsg(msg);
    }

    // 기본값 설정 클릭 이벤트 추가 todo
    $('#btnDefault').append(function(){
      return $scope.setDefault();
    });
  };


  /*********************************************************
   * 환경변수 저장
   * *******************************************************/
  $scope.save = function(){

    var objStatus       = document.getElementsByName("status");
    var objEnvstCd      = document.getElementsByName("envstCd");
    var objEnvstNm      = document.getElementsByName("envstNm");
    var objEnvstGrpCd   = document.getElementsByName("envstGrpCd");
    var objDefault      = document.getElementsByName("defltYn");
    var objEnvstValCd   = document.getElementsByName("envstValCd");
    var objDirctInYn    = document.getElementsByName("dirctInYn");
    var objOldEnvstVal  = document.getElementsByName("oldEnvstVal");

    var chngCnt  = 0; // 변경된 건수
    var params = new Array();

    for(var i=0; i<objEnvstCd.length; i++){

      if(objDirctInYn[i].value == "Y" && objEnvstValCd[i].value == ""){
        var msgStr = messages["hqManage.envSetting"] + " ["
            + objEnvstCd[i].value + "] "
            + objEnvstNm[i].value
            + messages["hqManage.require.regist.inputEnv"];

        $scope._popMsg(msgStr);
        return false;
      }

      if(objOldEnvstVal[i].value != $("#env"+objEnvstCd[i].value).val()) {
        chngCnt ++;
      }
    }

    if(chngCnt == 0 ){
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    $scope._popConfirm( messages["cmm.choo.save"], function(){

      var hqScope = agrid.getScope('hqManageCtrl');

      for(var i=0; i<objEnvstCd.length; i++){

        var obj = {};
        obj.hqOfficeCd  = hqScope.getSelectedHq().hqOfficeCd;
        obj.status      = objStatus[i].value;
        obj.envstCd     = objEnvstCd[i].value;
        obj.envstNm     = objEnvstNm[i].value;
        obj.envstGrpCd  = objEnvstGrpCd[i].value;
        obj.envstVal    = objEnvstValCd[i].value;
        obj.dirctInYn   = objDirctInYn[i].value;

        params.push(obj);
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

      $scope._postJSONSave.withOutPopUp( "/store/hq/hqManage/config/saveHqEnv.sb", params, function () {
        $scope.$broadcast('loadingPopupInactive');
        $scope._popMsg(messages["cmm.saveSucc"]);

        // 재조회
        var envScope = agrid.getScope('hqEnvCtrl');
        $scope.searchHqEnv();
      });
    });
  };


  /*********************************************************
   * 기본값으로 설정
   * *******************************************************/
  $scope.setDefault = function(){
    //todo
    // $scope._popMsg("준비중인 메뉴입니다.");
    // return false;
  };

  /*********************************************************
   * 매장정보 탭 클릭
   * *******************************************************/
  $scope.showHqInfo = function(){
    $scope.hqEnvLayer.hide();
    $scope.hqInfoLayer.show(true, function (s) {
    });
  };

  /*********************************************************
   * 메뉴권한 탭 클릭
   * *******************************************************/
  $scope.showMenuSetting = function(){
  };

}]);

/*********************************************************
 * 기본값 설정버튼 클릭
 * *******************************************************/
function setDefault(){
  // alert('setDefault');

  var objDefaultCd  = document.getElementsByName("defltYn");
  var objEnvstValCd = document.getElementsByName("envstValCd");

  var loop_cnt = objEnvstValCd.length;
  for(var i = 0; i < loop_cnt; i++)
  {
    console.log(objEnvstValCd[i])
    console.log(objDefaultCd[i])

    if(objEnvstValCd[i].value !== '')
    {
      //objEnvstValCd[i].value = objDefaultCd[i].value;
    }
  }
}
