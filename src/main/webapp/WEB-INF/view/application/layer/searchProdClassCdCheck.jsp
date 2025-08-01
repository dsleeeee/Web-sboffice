<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="prodClassCheckPopUpLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;" onload="window.self.focus();">
    <div class="wj-dialog wj-dialog-columns" ng-controller="prodClassCheckPopUpCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.layer.prodClass"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 300px;">
            <%-- 상품분류 트리 --%>
            <div class="theTreeAll_cls" id="treeProdClassCheck" style="height:auto;overflow: hidden; "></div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue" id="btnSelectCheck"><s:message code="cmm.chk"/></button>
            <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/application/layer/searchProdClassCdCheck.js?ver=20250703.01" charset="utf-8"></script>
<script>

    <%--
        분류모둘v2(체크박스 추가)
        화면에 중복 사용 시 searchProdClassCdCheck2 화면 사용 필요
        (화면 진입할 때 중복호출 되면서 stackoverflow에러 발생)
        searchProdClassCdCheck  - 화면 진입 시 트리 데이터 조회
        searchProdClassCdCheck2 - [분류조회] 클릭 시 트리 데이터 조회
        참조 화면 - prod.jsp, storeProdBatchRegist.jsp
    --%>
    $(document).ready(function () {

        <%-- 메뉴 트리 생성 --%>
        var tree = new wijmo.nav.TreeView('#treeProdClassCheck', {
            displayMemberPath: 'prodClassNm',
            childItemsPath: 'items',
            expandOnClick: true,
            isReadOnly: true,
            showCheckboxes: true
        });

        var view = new wijmo.collections.CollectionView();

        <%-- 트리 체크박스 초기화 --%>
        tree.loadedItems.addHandler(function (s, e) {
            s.collapseToLevel(0);
            //view = new wijmo.collections.CollectionView(tree.checkedItems);
        });

        <%-- 트리에 아이템 체크 상태가 바뀌었을 때 CollectionView에 반영 --%>
        /*tree.checkedItemsChanged.addHandler(function (s, e) {

            view.itemsAdded.clear();
            view.itemsRemoved.clear();

            for (var i = 0; i < tree.checkedItems.length; i++) {
                if (!view.contains(tree.checkedItems[i])) {
                    view.itemsAdded.push(tree.checkedItems[i]);
                }
            }

            var viewNew = new wijmo.collections.CollectionView(tree.checkedItems);

            for (var i = 0; i < view.items.length; i++) {
                if (!viewNew.contains(view.items[i])) {
                    view.itemsRemoved.push(view.items[i]);
                }
            }
        });*/

        // 초기화
        tree.itemsSource = [];
        tree.refresh();

        // 트리 데이터 조회
        var param = {};
        $.postJSON("/treePopup/getProdClassTreeCheck.sb", param, function (result) {
                tree.itemsSource = result.data.list;
            },
            function (result) {
                s_alert.pop(result.message);
            });

        // 선택 버튼 클릭
        $("#btnSelectCheck").click(function (e) {

            var arr = [];

            for (var i = 0; i < tree.checkedItems.length; i++) {
                arr.push(tree.checkedItems[i].prodClassCd);
            }

            // set data
            var vScope = agrid.getScope('prodClassCheckPopUpCtrl');
            vScope.setSelectedClass(arr);
        });
    });



</script>
