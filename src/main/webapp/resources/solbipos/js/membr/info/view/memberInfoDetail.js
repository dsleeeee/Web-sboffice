/****************************************************************
 *
 * 파일명 : memberRegist.js
 * 설  명 : 회원정보관리 > 회원정보등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('memberInfoDetailCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberInfoDetailCtrl', $scope, $http, false));

  // 기본 회원등급
  if(memberClassList.length == 0) {
    memberClassList = [{value: "", name: "선택"}, {value: "001", name: "기본등급"}];
  }

  // 조회조건 콤보박스 데이터
  $scope._setComboData("rEmailRecvYn", recvDataMapEx);
  $scope._setComboData("rSmsRecvYn", recvDataMapEx);
  $scope._setComboData("rGendrFg", genderDataMapEx);
  $scope._setComboData("rWeddingYn", weddingDataMap);
  $scope._setComboData("rRegStoreCd", regstrStoreList);
  $scope._setComboData("rUseYn", useDataMap);
  $scope._setComboData("rMemberClass", memberClassList);

  $scope.selectedMember;
  $scope.setSelectedMember = function(data) {
    $scope.selectedMember = data;
  };


  /*********************************************************
   * 팝업 오픈
   * *******************************************************/
  $scope.$on("memberInfoDetailCtrl", function(event, data) {
    $scope.setSelectedMember(data);
    $scope.getMemberInfo();
    event.preventDefault();
  });

  /*********************************************************
   * 회원정보 조회
   * *******************************************************/
  $scope.getMemberInfo = function(){

    var params = $scope.selectedMember;

    $scope._postJSONQuery.withOutPopUp( '/membr/info/view/base/getMemberInfo.sb', params, function(response){

      // console.log(response);

      if($.isEmptyObject(response.data) ) { ww;
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.memberRegistLayer.hide();
        return false;
      }
      var memberDetailInfo = response.data.data;

      $("#memberInfoDetailTitle").text("[" + memberDetailInfo.membrNo + "] " + memberDetailInfo.membrNm);

      memberDetailInfo.birthday               = stringToDate(memberDetailInfo.birthday);
      memberDetailInfo.weddingday             = stringToDate(memberDetailInfo.weddingday);

      $scope.member                           = memberDetailInfo;

      if(memberDetailInfo.weddingYn == "N") {
        $scope.member.weddingday              = new Date();
      }
    });
  };

  /*********************************************************
   * 수정버튼 클릭
   * *******************************************************/
  $scope.modify = function(){
    $scope.memberRegistLayer.show(true);
  };

  /*********************************************************
   * 닫기 버튼 클릭
   * *******************************************************/
  $scope.close = function(){
    $scope.memberInfoDetailLayer.hide();
  };

}]);
