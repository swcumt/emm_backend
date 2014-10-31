<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="scheduleListDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='scheduleListDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="scheduleListList.scheduleList"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="scheduleListDetail.heading"/></h2>
    <fmt:message key="scheduleListDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="scheduleList" method="post" action="scheduleListform" cssClass="well"
           id="scheduleListForm" onsubmit="return validateScheduleList(this)">
<form:hidden path="id"/>
    <spring:bind path="scheduleList.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleList.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="19"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleList.scheduleId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleList.scheduleId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="scheduleId" id="scheduleId"  maxlength="255"/>
        <form:errors path="scheduleId" cssClass="help-block"/>
    </div>
    <spring:bind path="scheduleList.title">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="scheduleList.title" styleClass="control-label"/>
        <form:input cssClass="form-control" path="title" id="title"  maxlength="255"/>
        <form:errors path="title" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty scheduleList.id}">
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

<v:javascript formName="scheduleList" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['scheduleListForm']).focus();
    });
</script>
