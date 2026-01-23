<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="orderkitCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">오더킷</a>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <button class="btn01 first01" id="btnOpenWeb" ng-click="orderkitGoto();">오더킷(Order kit) 관리자 웹 이동</button>
    </div>

    <div>
        <img src="/resource/solbipos/css/img/orderkit/banner_260123@2x.png" alt="" style="width:100%; height: 100%;"/>
    </div>

</div>

<script type="text/javascript">

    // 오더킷(Order kit) 관리자 웹 이동
    setTimeout(function() {
        $("#btnOpenWeb").trigger('click');
    }, 500);

</script>

<script type="text/javascript" src="/resource/solbipos/js/orderkit/orderkit/orderkit/orderkit.js?ver=20260122.01" charset="utf-8"></script>

<style>
    /* 단순버튼 01 */
    .btn01 {
        box-sizing: border-box;
        appearance: none;
        background-color: #2c3e50;
        border: 2px solid blue;
        border-radius: 0.6em;
        color: #fff;
        cursor: pointer;
        display: flex;
        align-self: center;
        font-size: 1rem;
        font-weight: 400;
        line-height: 1;
        margin: 20px 200px 20px 200px;
        padding: 1.2em 2.8em;
        text-decoration: none;
        text-align: center;
        text-transform: uppercase;
        font-family: 'Montserrat', sans-serif;
        justify-content: center;
        align-items: center;
    }

    .btn01:hover {
        color: #fff;
        outline: 0;
    }

    .btn01:focus {
        color: #fff;
        outline: 0;
    }

    .first01 {
        transition: box-shadow 300ms ease-in-out, color 300ms ease-in-out;
    }

    .first01:hover {
        box-shadow: 0 0 40px 40px blue inset;
    }
</style>