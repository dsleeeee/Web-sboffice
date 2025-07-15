/****************************************************************
 *
 * 파일명 : mobileVirtualLoginById.js
 * 설  명 : 기초관리_모바일 > 가상로그인(아이디별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.10     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 가상로그인(아이디별) 그리드 생성
 */
app.controller('mobileVirtualLoginByIdCtrl',  ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileVirtualLoginByIdCtrl', $scope, $http, true));

    // 가상로그인 개수
    $scope.popupCnt = 0;

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("srchClsFg", clsFg);
    $scope._setComboData("srchStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("mobileVirtualLoginByIdCtrl");

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 사용자아이디
                if(col.binding === "userId"){
                    wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });
        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "userId") {
                    if(selectedRow.storeModYn === 'N'){
                        $scope._popMsg(messages["virtualLoginById.msg.storeModYn"]); // 변경제한매장 입니다.
                        return false;
                    }else {
                        $scope.vLoginProcess(selectedRow.userId);
                    }
                }
            }
        });
    };

    // 가상로그인 그리드 조회
    $scope.$on("mobileVirtualLoginByIdCtrl", function (event, data) {

        // 파라미터
        var params = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/mobile/base/virtualLoginById/mobileVirtualLoginById/list.sb", params, function () {

        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 가상로그인 수행
    // 최초 가상로그인으로 로그인시에는 vLoginId 가 아닌 vUserId 파라미터로 로그인 후 vLoginId로 사용한다.
    $scope.vLoginProcess = function (value) {

        if (isEmpty(value)) {
            $scope.$apply(function () {
                $scope._popMsg(messages["virtualLogin.vLogin.fail"]);
            });
            return false;
        } else {

            /* post */
            $scope.popupCnt = $scope.popupCnt + 1;

            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "/mobile/base/virtualLogin/mobileVirtualLogin/vLogin.sb");
            form.setAttribute("target", value);

            var formField = document.createElement("input");
            formField.setAttribute("type", "hidden");
            formField.setAttribute("name", "vUserId");
            formField.setAttribute("value", value);
            form.appendChild(formField);
            document.body.appendChild(form);

            var popup = window.open("", value, "width=1024,height=768,resizable=yes,scrollbars=yes");
            var crono = window.setInterval(function () {
                if (popup.closed !== false) { // !== opera compatibility reasons
                    window.clearInterval(crono);
                    var params = {};
                    params.vUserId = value;
                    if (popup.document.getElementsByName("sessionId")) {
                        params.sid = popup.document.getElementsByName("sessionId")[0].value;
                    }

                    $http({
                        method: 'POST',
                        url: "/mobile/base/virtualLogin/mobileVirtualLogin/vLogout.sb",
                        params: params,
                        headers: {'Content-Type': 'application/json; charset=utf-8'}
                    }).then(function successCallback(response) {

                    }, function errorCallback(response) {
                        $scope._popMsg(response.message);
                        return false;
                    });

                }
            }, 250);
            form.submit();
            document.body.removeChild(form);
        }
    };

}]);