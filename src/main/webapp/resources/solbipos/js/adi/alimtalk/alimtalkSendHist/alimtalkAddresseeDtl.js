/****************************************************************
 *
 * 파일명 : alimtalkAddresseeDtl.js
 * 설  명 : 알림톡 수신자정보 팝업 JavaScript
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
 *  수신자정보 팝업 조회 그리드 생성
 */
app.controller('alimtalkAddresseeDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkAddresseeDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkAddresseeDtlCtrl", function(event, data) {
        $scope.searchAlimtalkAddresseeDtl(data);
        event.preventDefault();
    });

    $scope.searchAlimtalkAddresseeDtl = function(data){
        var params = {};
        params.alkSendSeq = data.alkSendSeq;

        $scope._inquiryMain("/adi/alimtalk/alimtalkSendHist/alimtalkAddresseeDtl/getAlimtalkAddresseeDtlList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->
}]);