<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="aCommentDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='aCommentDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="aCommentList.aComment"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="aCommentDetail.heading"/></h2>
    <fmt:message key="aCommentDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="aComment" method="post" action="aCommentform" cssClass="well"
           id="aCommentForm" onsubmit="return validateAComment(this)">
<ul>
    <spring:bind path="aComment.id">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.id" styleClass="control-label"/>
        <form:input path="id" id="id"/>
        <form:errors path="id" cssClass="help-block"/>
    </div>
    <spring:bind path="aComment.appId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.appId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="appId" id="appId"  maxlength="32"/>
        <form:errors path="appId" cssClass="help-block"/>
    </div>
    <spring:bind path="aComment.conntext">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.conntext" styleClass="control-label"/>
        <form:input cssClass="form-control" path="conntext" id="conntext"  maxlength="5000"/>
        <form:errors path="conntext" cssClass="help-block"/>
    </div>
    <spring:bind path="aComment.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="50"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="aComment.flag">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.flag" styleClass="control-label"/>
        <form:input cssClass="form-control" path="flag" id="flag"  maxlength="255"/>
        <form:errors path="flag" cssClass="help-block"/>
    </div>
    <spring:bind path="aComment.score">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.score" styleClass="control-label"/>
        <form:input cssClass="form-control" path="score" id="score"  maxlength="255"/>
        <form:errors path="score" cssClass="help-block"/>
    </div>
    <spring:bind path="aComment.userId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="aComment.userId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="userId" id="userId"  maxlength="32"/>
        <form:errors path="userId" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty aComment.id}">
            <button type="submit" class="btn btn-warning" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
                <i class="icon-trash icon-white"></i> <fmt:message key="button.delete"/>
            </button>
        </c:if>

        <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
    </div>
</form:form>
</div>

<v:javascript formName="aComment" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['aCommentForm']).focus();
    });
</script>
