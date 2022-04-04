/****************************************************************
 *
 * 파일명 : alimtalkMessageDtl.js
 * 설  명 : 알림톡 메세지 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  알림톡 메세지 팝업 조회 그리드 생성
 */
app.controller('alimtalkMessageDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkMessageDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkMessageDtlCtrl", function(event, data) {
        if(data != undefined) {
            $("#srchMessageDtlSubject").val(data.subject);

            var content = data.msgContent;
            if(data.pageGubun == "alimtalkSendStatus") {
                if(data.reserveYn == "1" && data.sendStatus != "3") {
                    var arrAlkMsgParams = data.alkMsgParams.split(",");
                    for(var i = 0; i < arrAlkMsgParams.length; i++) {
                        var num = "#{num" + [i+1] + "}";
                        content = content.replace(num, arrAlkMsgParams[i]);
                    }
                }
            } else {
                if(data.reserveYn == "1" && data.successQty != "1") {
                    var arrAlkMsgParams = data.alkMsgParams.split(",");
                    for(var i = 0; i < arrAlkMsgParams.length; i++) {
                        var num = "#{num" + [i+1] + "}";
                        content = content.replace(num, arrAlkMsgParams[i]);
                    }
                }
            }
            $("#messageContentDtl").val(content);

        } else {
            $("#srchMessageDtlSubject").val("");
            $("#messageContentDtl").val("");
        }

        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);