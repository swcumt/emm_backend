<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="versionDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='versionDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="versionList.version"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="versionDetail.heading"/></h2>
    <fmt:message key="versionDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="version" method="post" action="versionform" cssClass="well"
           id="versionForm" onsubmit="return validateVersion(this)">
<form:hidden path="id"/>
    <spring:bind path="version.content">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="version.content" styleClass="control-label"/>
        <form:input cssClass="form-control" path="content" id="content"  maxlength="65535"/>
        <form:errors path="content" cssClass="help-block"/>
    </div>
    <spring:bind path="version.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="version.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="19"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="version.url">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="version.url" styleClass="control-label"/>
        <form:input cssClass="form-control" path="url" id="url"  maxlength="500"/>
        <form:errors path="url" cssClass="help-block"/>
    </div>
    <spring:bind path="version.versionId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="version.versionId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="versionId" id="versionId"  maxlength="100"/>
        <form:errors path="versionId" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty version.id}">
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

<v:javascript formName="version" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['versionForm']).focus();
    });
</script>
