<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="appstoreEditionDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='appstoreEditionDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="appstoreEditionList.appstoreEdition"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="appstoreEditionDetail.heading"/></h2>
    <fmt:message key="appstoreEditionDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="appstoreEdition" method="post" action="appstoreEditionform" cssClass="well"
           id="appstoreEditionForm" onsubmit="return validateAppstoreEdition(this)">
<form:hidden path="id"/>
    <spring:bind path="appstoreEdition.appSize">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.appSize" styleClass="control-label"/>
        <form:input cssClass="form-control" path="appSize" id="appSize"  maxlength="255"/>
        <form:errors path="appSize" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.appStoreId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.appStoreId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="appStoreId" id="appStoreId"  maxlength="255"/>
        <form:errors path="appStoreId" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.createTime">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.createTime" styleClass="control-label"/>
        <form:input cssClass="form-control" path="createTime" id="createTime"  maxlength="19"/>
        <form:errors path="createTime" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.description">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.description" styleClass="control-label"/>
        <form:input cssClass="form-control" path="description" id="description"  maxlength="500"/>
        <form:errors path="description" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.fullTrialId">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.fullTrialId" styleClass="control-label"/>
        <form:input cssClass="form-control" path="fullTrialId" id="fullTrialId"  maxlength="255"/>
        <form:errors path="fullTrialId" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.ipaUrl">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.ipaUrl" styleClass="control-label"/>
        <form:input cssClass="form-control" path="ipaUrl" id="ipaUrl"  maxlength="500"/>
        <form:errors path="ipaUrl" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.plistUrl">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.plistUrl" styleClass="control-label"/>
        <form:input cssClass="form-control" path="plistUrl" id="plistUrl"  maxlength="500"/>
        <form:errors path="plistUrl" cssClass="help-block"/>
    </div>
    <spring:bind path="appstoreEdition.versions">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appstoreEdition.versions" styleClass="control-label"/>
        <form:input cssClass="form-control" path="versions" id="versions"  maxlength="100"/>
        <form:errors path="versions" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty appstoreEdition.id}">
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

<v:javascript formName="appstoreEdition" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['appstoreEditionForm']).focus();
    });
</script>
