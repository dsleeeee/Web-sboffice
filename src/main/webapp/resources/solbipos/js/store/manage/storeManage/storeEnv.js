/****************************************************************
 *
 * 파일명 : storeEnv.js
 * 설  명 : 매장정보관리 > 매장환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('storeEnvCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeEnvCtrl', $scope, $http, false));

  // 조회시 선택된 환경변수 그룹 (재조회를 위해 변수로 관리)
  $scope.envGroupCd = "00";
  $scope.setEnvGroupCd = function(envGroupCd){
    $scope.envGroupCd = envGroupCd;
  };
  $scope.getEnvGroupCd = function(){
    return $scope.envGroupCd;
  };

  // 팝업 오픈시 매장정보 조회
  $scope.$on("storeEnvCtrl", function(event, data) {

    $scope.setEnvGroupCd("00"); // 초기 환경변수 그룹코드는 매장환경[00]
    $scope.showStoreEnv();

    // [조회] 버튼을 이용한 환경설정 조회 확인 값 초기화
    $("#hdSrchYn").val("");

    event.preventDefault();
  });


  /*********************************************************
   * 매장환경 탭 클릭시 매장환경 레이어 먼저 보여줌
   * *******************************************************/
  $scope.showStoreEnv = function(){
    $("#cmmEnvArea").show();
    $("#posEnvArea").hide();

    var cmmEnvScope = agrid.getScope('cmmEnvCtrl');
    cmmEnvScope.changeEnvGroup($scope.getEnvGroupCd());
  };

  /*********************************************************
   * 매장정보 탭 클릭
   * *******************************************************/
  $scope.changeInfoTab = function(){
    $scope.storeEnvLayer.hide();

    var infoPopup = $scope.storeInfoLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    infoPopup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('storeInfoCtrl');
      }, 50)
    });

    // 팝업 닫을때
    infoPopup.show(true, function (s) {
    });
  };

  /*********************************************************
   * 메뉴권한 탭 클릭
   * *******************************************************/
  $scope.changeAuthTab = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    if($.isEmptyObject(storeScope.getSelectedStore()) ) {
      $scope._popMsg(messages["storeManage.require.regist.store1"]);
      return false;
    }

    $scope.storeEnvLayer.hide();

    var authPopup = $scope.storeAuthLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    authPopup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('storeAuthCtrl');
      }, 50)
    });

    // 팝업 닫을때
    authPopup.show(true, function (s) {
    });
  };

  // 조회버튼 클릭 시 검색어를 포함한 설정값 조회
  $scope.srchEnvst = function(){

    // "Y" 는 조회버튼을 클릭하여 환경설정값을 조회하였다는 의미
    // [포스환경] 탭 포스명칭이 바뀌는것을 방지하기 위해
    $("#hdSrchYn").val("Y");

    $("#envGroupTab li a").each(function(index, item){
      if($(item).attr("class") === "on") {
        var cmmEnvScope = agrid.getScope('cmmEnvCtrl');
        cmmEnvScope.changeEnvGroup($(item).attr("envstFg"));

        // 탭 class가 === "on" 인것만 실행되지 않고, 여러번?? 실행되어 한번만 실행되도록 하기위해 추가
        return false;
      }
    });
  }

  /*********************************************************
   *  환경변수 그리기 [환경변수 타입 (P:포스환경변수, S:그 외)]
   * *******************************************************/
  $scope.setEnvContents = function(envType, list){

    // console.log(list)
    console.log(envstGrp)


    var innerHtml = "";
    // var envCnt    = 0;
    var allCnt    = 0; // 전체 환경값 갯수
    var existCnt  = 0; // 현재 등록된 환경값 갯수

    // 환경변수 테이블 그리기
    for (var i = 0; i < envstGrp.length; i++) {

      var storeEnvCnt = 0;
      var storeEnvHtml = "";
      var storeEnvstGrp = envstGrp[i];

      storeEnvHtml += '<h3 class=\"h3_tbl2 lh30\">' + storeEnvstGrp.name + ' <button class=\"open\" id=\"fldGrpBtn'+ i +'\" onclick=\"javascript:fldGrp(\''+ i +'\');\"></button>';

      if (i === 0) { // 기본값으로 설정 버튼
        storeEnvHtml += '<span class=\"fr\"><a href=\"javascript:;\" class=\"btn_grayS\" id=\"btnDefault\" ng-click=\"setDefault()\">';
        storeEnvHtml += messages["storeManage.setting.default.env"];
        storeEnvHtml += '</a></span>';
      }

      storeEnvHtml += '</h3>';
      storeEnvHtml += '<table id=\"searchTbl' + i + '\" class=\"searchTbl\">';
      storeEnvHtml += '  <colgroup>';
      storeEnvHtml += '    <col class=\"w5\" />';
      storeEnvHtml += '    <col class=\"w25\" />';
      storeEnvHtml += '    <col class=\"w20\" />';
      storeEnvHtml += '    <col class=\"w5\" />';
      storeEnvHtml += '    <col class=\"w25\" />';
      storeEnvHtml += '    <col class=\"w20\" />';
      storeEnvHtml += '  </colgroup>';
      storeEnvHtml += '<tbody>';

      var b_env = ""; // 변경전의 환경변수

      for (var j = 0; j < list.length; j++) {

        if (storeEnvstGrp.value === list[j].envstGrpCd) {
          if (b_env === "" || b_env !== list[j].envstCd) {
            if (storeEnvCnt === 0 || storeEnvCnt % 2 === 0) storeEnvHtml += '<tr>';

            if(list[j].remark !== null && list[j].remark !== "" && list[j].remark !== undefined){
              storeEnvHtml += '  <th class=\"tc\" style=\"color:blue;\"><a href=\"#\" onclick=\"envRemarkPop(\'' + list[j].envstCd + '\',\'' + envType + '\')\">' + list[j].envstCd + (list[j].existFg === "N" ? " <em class=\"imp\">*</em> " : "") + '</a></th>';
              storeEnvHtml += '  <td style=\"color:blue;\"><a href=\"#\" onclick=\"envRemarkPop(\'' + list[j].envstCd + '\',\'' + envType + '\')\">' + list[j].envstNm + '</a></td>';
            }else{
              storeEnvHtml += '  <th class=\"tc\">' + list[j].envstCd + (list[j].existFg === "N" ? " <em class=\"imp\">*</em> " : "") + '</th>';
              storeEnvHtml += '  <td>' + list[j].envstNm + '</td>';
            }

            storeEnvHtml += '  <td>';

            // 매장환경, 외식환경, 유통환경 환경변수 그리기
            if(envType === "S") {
              if (list[j].dirctInYn === "Y") { // 직접입력
                storeEnvHtml += '    <input type=\"text\" name=\"envstValCd\" id=\"env' + list[j].envstCd + '\" class=\"sb-input w100\">';
              } else {  // 값 선택
                storeEnvHtml += '    <select name=\"envstValCd\" id=\"env' + list[j].envstCd + '\" class=\"sb-select w100\" />';
              }

              storeEnvHtml += '    <input type=\"hidden\" name=\"status\"        id=\"status' + list[j].envstCd + '\"        value=\"' + (list[j].existFg === "N" ? "I" : "U") + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"envstCd\"       id=\"envstCd' + list[j].envstCd + '\"       value=\"' + list[j].envstCd + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"envstNm\"       id=\"envstNm' + list[j].envstCd + '\"       value=\"' + list[j].envstNm + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"envstGrpCd\"    id=\"envstGrpCd' + list[j].envstCd + '\"   value=\"' + list[j].envstGrpCd + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"defltYn\"       id=\"defltYn' + list[j].envstCd + '\"       value=\"' + list[j].defltYn + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"dirctInYn\"     id=\"dirctInYn' + list[j].envstCd + '\"     value=\"' + list[j].dirctInYn + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"targtFg\"       id=\"targtFg' + list[j].envstCd + '\"        value=\"' + list[j].targtFg + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"oldEnvstVal\"   id=\"oldEnvstVal' + list[j].envstCd + '\"   value=\"' + list[j].selEnvstVal + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"hqEnvstValCd\"  id=\"hqEnvstValCd' + list[j].envstCd + '\"  value=\"' + list[j].hqEnvstValCd + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"hqEnvstValNm\"  id=\"hqEnvstValNm' + list[j].envstCd + '\"  value=\"' + list[j].hqEnvstValNm + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"remark\"         id=\"remark' + list[j].envstCd + '\"         value=\"' + list[j].remark + '\">';
              storeEnvHtml += '  </td>';
            } else if(envType === "P") { // 포스환경 환경변수 그리기
              if (list[j].dirctInYn === "Y") { // 직접입력
                storeEnvHtml += '    <input type=\"text\" name=\"pos_envstValCd\" id=\"env' + list[j].envstCd + '\" class=\"sb-input w100\">';
              } else {  // 값 선택
                storeEnvHtml += '    <select name=\"pos_envstValCd\" id=\"env' + list[j].envstCd + '\" class=\"sb-select w100\" />';
              }

              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_status\"        id=\"pos_status' + list[j].envstCd + '\"         value=\"' + (list[j].existFg === "N" ? "I" : "U") + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_envstCd\"       id=\"pos_envstCd' + list[j].envstCd + '\"        value=\"' + list[j].envstCd + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_envstNm\"       id=\"pos_envstNm' + list[j].envstCd + '\"        value=\"' + list[j].envstNm + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_envstGrpCd\"    id=\"pos_envstGrpCd' + list[j].envstCd + '\"    value=\"' + list[j].envstGrpCd + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_defltYn\"        id=\"pos_defltYn' + list[j].envstCd + '\"       value=\"' + list[j].defltYn + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_dirctInYn\"      id=\"pos_dirctInYn' + list[j].envstCd + '\"     value=\"' + list[j].dirctInYn + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_targtFg\"        id=\"pos_targtFg' + list[j].envstCd + '\"        value=\"' + list[j].targtFg + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_oldEnvstVal\"   id=\"pos_oldEnvstVal' + list[j].envstCd + '\"    value=\"' + list[j].selEnvstVal + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_hqEnvstValCd\"  id=\"pos_hqEnvstValCd' + list[j].envstCd + '\"   value=\"' + list[j].hqEnvstValCd + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_hqEnvstValNm\"  id=\"pos_hqEnvstValNm' + list[j].envstCd + '\"   value=\"' + list[j].hqEnvstValNm + '\">';
              storeEnvHtml += '    <input type=\"hidden\" name=\"pos_remark\"         id=\"pos_remark' + list[j].envstCd + '\"          value=\"' + list[j].remark + '\">';
              storeEnvHtml += '  </td>';
            }
            //--------------------------------------------------------

            b_env = list[j].envstCd;
            storeEnvCnt++;
            allCnt++;

            if (list[j].existFg === "Y") existCnt++;
            if (list[j].envstCdCnt === storeEnvCnt && (storeEnvCnt % 2 === 1)) {
              storeEnvHtml += '  <td class=\"tc\"></td>';
              storeEnvHtml += '  <td></td>';
              storeEnvHtml += '  <td></td>';
              storeEnvHtml += '</tr>';
            } else if (storeEnvCnt % 2 === 0) {
              storeEnvHtml += '</tr>';
            }
          }
        }
      }

      storeEnvHtml += '  </tbody>';
      storeEnvHtml += '</table>';
      storeEnvHtml += '</div>';
      storeEnvHtml += '<br>';

      if (storeEnvCnt > 0) innerHtml += storeEnvHtml;
    }

    if(envType === "S") {
      $("#storeConfigContent").html(innerHtml);
    } else if(envType === "P") {
      $("#posConfigContent").html(innerHtml);
    }

    // 환경변수 select option 추가
    var envstCd = "";
    var sOption = false;

    for (var s = 0; s < list.length; s++) {
      if(list[s].dirctInYn === "N") {
        $("#env"+list[s].envstCd).append('<option value=\"'+ list[s].envstValCd +'\" >' + list[s].envstValNm +  '</option>');
        if(i === 0 || envstCd !== list[s].envstCd ) {
          envstCd = list[s].envstCd;
          if(list[s].selEnvstVal === list[s].envstValCd){
            sOption = true;
            $("#env"+list[s].envstCd).val(list[s].envstValCd).prop("selected", true);
          }
          else{
            sOption = false;
            $("#env"+list[s].envstCd).val(list[s].envstValCd).prop("selected", true);
          }
        }else if(list[s].selEnvstVal === list[s].envstValCd){
          sOption = true;
          $("#env"+list[s].envstCd).val(list[s].envstValCd).prop("selected", true);
        }else if(sOption === false && list[s].defltYn === "Y") {
          $("#env"+list[s].envstCd).val(list[s].envstValCd).prop("selected", true);
        }

        if(list[s].defltYn === "Y") {
          $("#env"+list[s].envstCd).attr("defaultVal", list[s].envstValCd);
        }

      } else {
        if(list[s].selEnvstVal === "" || list[s].selEnvstVal == null ) {
          if(list[s].remark !== "" && list[s].remark != null){ // 직접입력값 중, 기본값(Remark)이 있으면 셋팅, 없으면 * 로 표시
            $("#env" + list[s].envstCd).val(list[s].remark);
          }else{
            $("#env" + list[s].envstCd).val("*");
          }
        }else{
          $("#env"+list[s].envstCd).val(list[s].selEnvstVal);
        }

        // '기본값으로 셋팅' 클릭시 자동으로 직접입력 기본값 셋팅
        if(list[s].remark !== "" && list[s].remark != null){
            $("#env"+list[s].envstCd).attr("defaultVal", list[s].remark);
        }else{
            $("#env"+list[s].envstCd).attr("defaultVal", "*");
        }
      }

      // 다중사업자관리 버튼 show/hide 처리
      if (list[s].envstCd === "1337") {
          console.log("1337: " + list[s].selEnvstVal);
           if (list[s].selEnvstVal === null || list[s].selEnvstVal === undefined || list[s].selEnvstVal === "") {
              $("#btnMultiBizManage").css("display", "none");
          } else {
              if (list[s].selEnvstVal === "1") {
                  $("#btnMultiBizManage").css("display", "");
              } else {
                  $("#btnMultiBizManage").css("display", "none");
              }
          }
      }

      // 매장환경 저장전 체크에 필요한 환경설정값 갖고있기
      if(list[s].envstCd  === "1102"){
          console.log("1102: " + list[s].selEnvstVal);
          orgEnv1102 = list[s].selEnvstVal;
      }
      if(list[s].envstCd  === "1221"){
          console.log("1221: " + list[s].selEnvstVal);
          orgEnv1221 = list[s].selEnvstVal;
      }
      if(list[s].envstCd  === "2001"){
          console.log("2001: " + list[s].selEnvstVal);
          orgEnv2001 = list[s].selEnvstVal;
      }
      if(list[s].envstCd  === "2002"){
          console.log("2002: " + list[s].selEnvstVal);
          orgEnv2002 = list[s].selEnvstVal;
      }

      // 포스환경 저장전 체크에 필요한 환경설정값 갖고있기
      if(list[s].envstCd  === "1015"){
          console.log("1015: " + list[s].selEnvstVal);
          orgEnv1015 = list[s].selEnvstVal;
      }
      if(list[s].envstCd  === "4020"){
          console.log("4020: " + list[s].selEnvstVal);
          orgEnv4020 = list[s].selEnvstVal;
      }
      if(list[s].envstCd  === "4021"){
          console.log("4021: " + list[s].selEnvstVal);
          orgEnv4021 = list[s].selEnvstVal;
      }
    }

    // 등록되지 않은 환경값이 있을 경우
    if(allCnt > existCnt) {

      var msg = messages["storeManage.no.regist.env"]
          + messages["storeManage.require.regist.env"] +"\n"
          + allCnt + messages["storeManage.total.env.count"]
          + (allCnt - existCnt)+ messages["storeManage.no.regist.env.count"]
      ;

      $scope._popMsg(msg);
    }

    // 기본값 설정 클릭 이벤트 추가 todo
    $("#btnDefault").append(function(){
      return $scope.setDefault();
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

  // $("#btnDefault").click(function(){
  //   alert("기기기ㅣ")
  //   $scope.setDefault();
  // });

  // 매장환경설정 비고설명 팝업
  $scope.envRemarkPop = function (envstCd, envType) {

    var params    = {};
    params.envstCd = $("#" + (envType === "P" ? "pos_" : "") + "envstCd" + envstCd).val();
    params.envstNm = $("#" + (envType === "P" ? "pos_" : "") + "envstNm" + envstCd).val();
    params.remark = $("#" + (envType === "P" ? "pos_" : "") + "remark" + envstCd).val();

    $scope.envRemarkPopLayer.show(true);
    $scope._broadcast('envRemarkPopCtrl', params);
    event.preventDefault();
  }
  
  // 다중사업자관리 팝업
  $scope.multiBizManage = function () {

      var params    = {};
      $scope.multiBizManageLayer.show(true);
      $scope._broadcast('multiBizManageCtrl', params);
      event.preventDefault();
  }

}]);

/*********************************************************
 * 그룹 접기
 * *******************************************************/
var fldGrp = function(idx) {
  if ($('#fldGrpBtn' + idx).attr('class') === 'open') {
    $('#searchTbl' + idx).hide();
    $('#fldGrpBtn' + idx).removeClass("open").addClass("close");
  } else {
    $('#searchTbl' + idx).show();
    $('#fldGrpBtn' + idx).removeClass("close").addClass("open");
  }
};

// 매장환경설정 비고설명 팝업
function envRemarkPop(envstCd, envType) {
  var scope = agrid.getScope('storeEnvCtrl');
  scope.envRemarkPop(envstCd, envType);
}
