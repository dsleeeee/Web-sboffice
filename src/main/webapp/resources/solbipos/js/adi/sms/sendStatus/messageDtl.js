/****************************************************************
 *
 * 파일명 : messageDtl.js
 * 설  명 : 메세지 팝업JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.18     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  메세지 팝업 조회 그리드 생성
 */
app.controller('messageDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('messageDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("messageDtlCtrl", function(event, data) {
        var messageContentDtl = document.getElementById("messageContentDtl");
        if(data != undefined) {
            messageContentDtl.innerHTML = data.msgContent;
            $("#srchMessageDtlSubject").val(data.subject);
        } else {
            messageContentDtl.innerHTML = "";
            $("#srchMessageDtlSubject").val("");
        }

        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);