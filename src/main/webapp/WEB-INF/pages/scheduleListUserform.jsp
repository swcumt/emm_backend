<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="scheduleListUserDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='scheduleListUserDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="scheduleListUserList.scheduleListUser"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="scheduleListUserDetail.heading"/></h2>
    <fmt:message key="scheduleListUserDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="scheduleListUser" method="post" action="scheduleListUserform" cssClass="well"
           id="scheduleListUserForm" onsubmit="return validateScheduleListUser(this)">
<form:hidden path="id"/>
    <spring:bind path="scheduleListUser.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleListUser.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="19"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleListUser.scheduleId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleListUser.scheduleId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="scheduleId" id="scheduleId"  maxlength="255"/>
        <form:errors path="scheduleId" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleListUser.scheduleListId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleListUser.scheduleListId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="scheduleListId" id="scheduleListId"  maxlength="255"/>
        <form:errors path="scheduleListId" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleListUser.scheduleUserId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleListUser.scheduleUserId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="scheduleUserId" id="scheduleUserId"  maxlength="255"/>
        <form:errors path="scheduleUserId" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty scheduleListUser.id}">
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

<v:javascript formName="scheduleListUser" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['scheduleListUserForm']).focus();
    });
</script>
