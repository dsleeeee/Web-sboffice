<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>




<div id="hqDtlDim" class="fullDimmed" style="display:none;"></div>
<div id="hqDtlLayer" class="layer" style="display:none;" ng-controller="speDateRegistCtrl">
    <div class="layer_inner">
        <div class="title w870">
            <p id="popTitle" class="tit"></p>
            <a href="javascript:;" class="btn_close"></a>
            <div class="con">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="outstockReqDate.specificDate" /><em class="imp">*</em></th>
                        <td>
                            <div class="sb-select">
                                <span class="txtIn"><input id="specificDate" class="w200" ></span>
                            </div>
                        </td>
                    </tr>
            </div>
        </div>
    </div>
</div>

<%--<wj-popup id="wjSpeDateRegistLayer" control="wjSpeDateRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">--%>
    <%--<div id="speDateRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="speDateRegistCtrl">--%>
        <%--<div class="wj-dialog-header wj-dialog-header-font">--%>
            <%--<s:message code="outstockReqDate.specificDate" /> <s:message code="cmm.new.add" />--%>
            <%--<a href="javascript:;" class="wj-hide btn_close"></a>--%>
        <%--</div>--%>
        <%--<div class="wj-dialog-body">--%>
            <%--<form id="speDateForm" ng-submit="submitForm()">--%>
                <%--<table class="tblType01">--%>
                    <%--<colgroup>--%>
                        <%--<col class="w15" />--%>
                        <%--<col class="w35" />--%>
                        <%--<col class="w15" />--%>
                        <%--<col class="w35" />--%>
                    <%--</colgroup>--%>
                    <%--<tbody>--%>
                    <%--<tr>--%>
                        <%--<th><s:message code="outstockReqDate.specificDate" /><em class="imp">*</em></th>--%>
                        <%--<td>--%>
                            <%--<div class="sb-select">--%>
                                <%--<span class="txtIn"><input id="specificDate" class="w200" ></span>--%>
                            <%--</div>--%>
                        <%--</td>--%>
                        <%--<th><s:message code="outstockReqDate.store" /><em class="imp">*</em></th>--%>
                        <%--<td><input type="text" id="storeCd" class="sb-input w100" maxlength="14" ng-model="speDate.storeCd"/></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<th><s:message code="outstockReqDate.outstockReqYn"/></th>--%>
                        <%--<td>--%>
                            <%--<select id="outstockReqYn" ng-model="speDate.outstockReqYn">--%>
                                <%--<option value="N"><s:message code="outstockReqDate.outstockReqYnN"/></option>--%>
                                <%--<option value="Y"><s:message code="outstockReqDate.outstockReqYnY"/></option>--%>
                            <%--</select>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<th><s:message code="outstockReqDate.specificDateRemark"/></th>--%>
                        <%--<td colspan="3">--%>
                            <%--<div>--%>
                                <%--<textarea id="specificDateRemark" class="w100 tArea1" style="height:100px;" ng-model="speDate.remark"></textarea>--%>
                            <%--</div>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--</tbody>--%>
                <%--</table>--%>
                <%--<div class="mt10 pdb20 oh bb">--%>
                    <%--<button type="submit" id="btnSave" class="btn_blue fr">저장</button>--%>
                <%--</div>--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</wj-popup>--%>




<script type="text/javascript">
    var specificDate = wcombo.genDate("#specificDate");

    /** 특정일 신규등록 controller */
    app.controller('speDateRegistCtrl', ['$scope', '$http', function($scope, $http) {
        $scope.default = {
            specificDate:getCurDate('-'),
            outstockReqYn:"N",
            storeCd:"",
            remark:""
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on('speDateRegistCtrl', function(event, paramObj) {
            // 특정일 신규등록 팝업 오픈
            // $scope.wjSpeDateRegistLayer.show(true);
            // $scope.speDate = angular.copy($scope.default);

            $("#hqDtlLayer").show();
            $("#hqDtlDim").show();

            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 특정일 저장
        $scope.submitForm = function() {
            //값체크
            if (!valueCheck()) return;

            $http({
                method: 'POST', //방식
                url: "/iostock/order/outstockReqDate/specificDate/saveNew.sb", /* 통신할 URL */
                params: $scope.speDate, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available

                // 특정일 그리드 새로고침
                $scope._broadcast('specificCtrl');

                s_alert.pop(messages["cmm.saveSucc"]);

                // 팝업 닫기
                // $scope.wjSpeDateRegistLayer.hide();

            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                s_alert.pop(response.data.message);
                return;
            });
        };
    }]);

    // 값 체크
    function valueCheck() {
        <%-- 매장을 선택해주세요. --%>
        var msg = "<s:message code='outstockReqDate.selectStore'/>";
        if($("#storeCd").val() === "") {
            s_alert.popOk(msg, function(){});
            return false;
        }

        <%-- 설명의 길이가 너무 깁니다. --%>
        var msg = "<s:message code='outstockReqDate.specificDateRemark'/> <s:message code='outstockReqDate.textOver'/>";
        if($("#specificDateRemark").val().getKr3ByteLength() > 18) {
            s_alert.popOk(msg, function(){ $("#specificDateRemark").select(); });
            return false;
        }

        return true;
    }
</script>
