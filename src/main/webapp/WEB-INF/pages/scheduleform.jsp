<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="scheduleDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='scheduleDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="scheduleList.schedule"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="scheduleDetail.heading"/></h2>
    <fmt:message key="scheduleDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="schedule" method="post" action="scheduleform" cssClass="well"
           id="scheduleForm" onsubmit="return validateSchedule(this)">
<form:hidden path="id"/>
    <spring:bind path="schedule.content">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="schedule.content" styleClass="control-label"/>
        <form:input cssClass="form-control" path="content" id="content"  maxlength="500"/>
        <form:errors path="content" cssClass="help-block"/>
    </div>
    <spring:bind path="schedule.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="schedule.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="19"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="schedule.icon">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="schedule.icon" styleClass="control-label"/>
        <form:input cssClass="form-control" path="icon" id="icon"  maxlength="200"/>
        <form:errors path="icon" cssClass="help-block"/>
    </div>
    <spring:bind path="schedule.icons">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="schedule.icons" styleClass="control-label"/>
        <form:input cssClass="form-control" path="icons" id="icons"  maxlength="200"/>
        <form:errors path="icons" cssClass="help-block"/>
    </div>
    <spring:bind path="schedule.title">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="schedule.title" styleClass="control-label"/>
        <form:input cssClass="form-control" path="title" id="title"  maxlength="255"/>
        <form:errors path="title" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty schedule.id}">
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

<v:javascript formName="schedule" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['scheduleForm']).focus();
    });
</script>
