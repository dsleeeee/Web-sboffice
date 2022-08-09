/****************************************************************
 *
 * 파일명 : virtualLoginAgency.js
 * 설  명 : 총판/대리점 가상로그인 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2010.03.31     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 업체구분
var vAgencyCd = [
    {"name":"총판","value":"P"},
    {"name":"대리점","value":"C"}
];

/**
 *  총판/대리점 가상로그인 그리드 생성
 */
app.controller('virtualLoginAgencyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('virtualLoginAgencyCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.agencyFgDataMap = new wijmo.grid.DataMap(vAgencyCd, 'value', 'name');

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("virtualLoginAgencyCtrl");

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 사용자아이디
                if (col.binding === "userId") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                //  사용자아이디 클릭시 상세정보 조회
                if ( col.binding === "userId") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    $scope.vLoginProcess(selectedRow.userId);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("virtualLoginAgencyCtrl", function(event, data) {
        $scope.searchVirtualLoginAgency();
        event.preventDefault();
    });

    $scope.searchVirtualLoginAgency = function(){
        var params = {};
        params.listScale = $scope.listScale;

        $scope._inquiryMain("/store/manage/virtualLoginAgency/virtualLoginAgency/getVirtualLoginAgencyrList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 가상로그인 수행
    // 최초 가상로그인으로 로그인시에는 vLoginId 가 아닌 vUserId 파라미터로 로그인 후 vLoginId로 사용한다.
    $scope.vLoginProcess = function(value) {

        if (isEmpty(value)) {
            $scope.$apply(function() {
                $scope._popMsg(messages["virtualLogin.vLogin.fail"]);
            });
            return false;
        } else {

            /* post */
            $scope.popupCnt = $scope.popupCnt + 1;

            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
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
                    if ( popup.document.getElementsByName("sessionId") ) {
                        params.sid = popup.document.getElementsByName("sessionId")[0].value;
                    }

                    $http({
                        method: 'POST',
                        url: "/store/manage/virtualLogin/virtualLogin/vLogout.sb",
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