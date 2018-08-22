<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--<f:form modelAttribute="sessionInfo" method="post" action="/application/pos/posLogin.sb">--%>
    <%--매장코드 : <input type="text" id="storeCd" name="storeCd" value="D000001"/>--%>
    <%--&lt;%&ndash;<input type="text" id="userId" name="userId" value="111"/>&ndash;%&gt;--%>
    <%--하드웨어인증키 : <input type="text" id="hwAuthKey" name="hwAuthKey" value="1234"/>--%>
    <%--요청url : <input type="text" id="url" name="url" value="simpleMemberJoin"/>--%>

    <%--<button type="submit" class="btn_skyblue">gogogogo</button>--%>
<%--</f:form>--%>

<div ng-app="app" ng-controller="appCtrl">
    <%-- 조회 --%>
    <div class="mt10 pdb20 oh bb">
        <button class="btn_blue fr" id="btnSearch" ng-click="search()"><s:message code="cmm.search"/></button>
    </div>
<wj-flex-grid
        class="gridStyle"
        items-source="data" >

    <!-- define columns -->
    <wj-flex-grid-column header="ID" binding="id" width="50"></wj-flex-grid-column>
    <wj-flex-grid-column header="Date" binding="date" width="100"></wj-flex-grid-column>
    <wj-flex-grid-column header="Country" binding="country" width="100"></wj-flex-grid-column>
    <wj-flex-grid-column header="Product" binding="product" width="140"></wj-flex-grid-column>
    <wj-flex-grid-column header="Color" binding="color" width="140"></wj-flex-grid-column>
    <wj-flex-grid-column header="Amount" binding="amount" width="100"></wj-flex-grid-column>
    <wj-flex-grid-column header="Active" binding="active"></wj-flex-grid-column>
    <%--<wj-flex-grid-column header="STORECD" binding="storeCd" width="100"></wj-flex-grid-column>--%>
    <%--<wj-flex-grid-column header="STORENM" binding="storeNm" width="100"></wj-flex-grid-column>--%>

    <!-- enable column filtering-->
    <wj-flex-grid-filter></wj-flex-grid-filter>
</wj-flex-grid>
</div>

<script type="text/javascript">
    var test;

    // declare app module
    var app = angular.module('app', ['wj']);
    // app controller provides data
    app.controller('appCtrl', ['$scope', function($scope) {
        $scope.count = 0;
        $scope.search = function() {
            $scope.itemCount = 0;
            // var data = getList();
            // var data = getData(0);
            // $scope.data = new wijmo.collections.CollectionView(data);

            test =$scope;
            var param = {};
            param.listScale = 15;
            param.curr = 1;


            // $.postJSON("/iostock/loan/storeLoanManage/storeLoanManage/list.sb", param,
            //     function (result) {
            //         var list = result.data.list;
            //         if (list.length === undefined || list.length === 0) {
            //             s_alert.pop(result.message);
            //             return;
            //         }
            //
            //         // console.log("list =======================");
            //         // console.log(list);
            //
            //         // $scope.data = new wijmo.collections.CollectionView(getData(0));
            //
            //         var data2 = getData(0);
            //         $scope.data = new wijmo.collections.CollectionView(data2);
            //
            //     },
            //     function (result) {
            //         s_alert.pop(result.message);
            //         return;
            //     }
            // );
        };
    }]);

    function getList() {
        // validation 추가
        var param = {};
        param.listScale = 15;
        param.curr = 1;

        var list;
        $.postJSON("/iostock/loan/storeLoanManage/storeLoanManage/list.sb", param,
            function (result) {
                list = result.data.list;
                if (list.length === undefined || list.length === 0) {
                    s_alert.pop(result.message);
                    return;
                }

                list = getData(0);
                return list;
            },
            function (result) {
                s_alert.pop(result.message);
                return;
            }
        );

    }






    // create some random data
    function getData(count) {
        var data = [];
        var countries = ['US', 'Germany', 'UK', 'Japan', 'Italy'];
        var products = ['Widget', 'Gadget', 'Doohickey'];
        var colors = ['Black', 'White', 'Red', 'Green', 'Blue', 'Yellow', 'Orange', 'Brown'];
        var dt = new Date();
        for (var i = 0; i <= count; i++) {

            // create a new item
            var item = {
                id: i,
                date: new Date(dt.getFullYear(), i % 12, 1),
                country: countries[Math.floor(Math.random() * countries.length)],
                product: products[Math.floor(Math.random() * products.length)],
                color: colors[Math.floor(Math.random() * colors.length)],
                amount: 1000 + Math.random() * 10000,
                active: i % 6 != 0
            };

            // binding to null values
            if ((i + 1) % 10 == 0) {
                item.date = null;
                item.amount = null;
                item.active = null;
            }

            // add new item to list
            data.push(item);
        }

        // console.log(data);
        return data;
    }
</script>
