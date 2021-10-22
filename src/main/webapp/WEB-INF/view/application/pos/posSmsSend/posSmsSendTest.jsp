<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<f:form modelAttribute="sessionInfo" method="post" action="/application/pos/posLogin.sb">
    매장코드 : <input type="text" id="storeCd" name="storeCd" value="DS00001"/>
    <%--매장코드 : <input type="text" id="storeCd" name="storeCd" value="A000005"/>--%>
    하드웨어인증키 : <input type="text" id="hwAuthKey" name="hwAuthKey" value="1234"/>
    요청url : <input type="text" id="url" name="url" value="posSmsSend/posSmsSend"/>
    <button type="submit" class="btn_skyblue">gogogogo</button>
</f:form>