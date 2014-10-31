<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="scheduleUserDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='scheduleUserDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="scheduleUserList.scheduleUser"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="scheduleUserDetail.heading"/></h2>
    <fmt:message key="scheduleUserDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="scheduleUser" method="post" action="scheduleUserform" cssClass="well"
           id="scheduleUserForm" onsubmit="return validateScheduleUser(this)">
<form:hidden path="id"/>
    <spring:bind path="scheduleUser.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleUser.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="19"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleUser.mobilePhoneNumber">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleUser.mobilePhoneNumber" styleClass="control-label"/>
        <form:input cssClass="form-control" path="mobilePhoneNumber" id="mobilePhoneNumber"  maxlength="50"/>
        <form:errors path="mobilePhoneNumber" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleUser.name">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleUser.name" styleClass="control-label"/>
        <form:input cssClass="form-control" path="name" id="name"  maxlength="255"/>
        <form:errors path="name" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleUser.phoneNumber">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleUser.phoneNumber" styleClass="control-label"/>
        <form:input cssClass="form-control" path="phoneNumber" id="phoneNumber"  maxlength="50"/>
        <form:errors path="phoneNumber" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty scheduleUser.id}">
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

<v:javascript formName="scheduleUser" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['scheduleUserForm']).focus();
    });
</script>
