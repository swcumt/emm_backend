<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="appStoreDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='appStoreDetail.heading'/>"/>
</head>

<c:set var="delObject" scope="request"><fmt:message key="appStoreList.appStore"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>

<div class="col-sm-2">
    <h2><fmt:message key="appStoreDetail.heading"/></h2>
    <fmt:message key="appStoreDetail.message"/>
</div>

<div class="col-sm-7">
<form:errors path="*" cssClass="alert alert-danger alert-dismissable" element="div"/>
<form:form commandName="appStore" method="post" action="appStoreform" cssClass="well"
           id="appStoreForm" onsubmit="return validateAppStore(this)">
<form:hidden path="id"/>
    <spring:bind path="appStore.appsource">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.appsource" styleClass="control-label"/>
        <form:input cssClass="form-control" path="appsource" id="appsource"  maxlength="11"/>
        <form:errors path="appsource" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.appsourceid">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.appsourceid" styleClass="control-label"/>
        <form:input cssClass="form-control" path="appsourceid" id="appsourceid"  maxlength="100"/>
        <form:errors path="appsourceid" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.company">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.company" styleClass="control-label"/>
        <form:input cssClass="form-control" path="company" id="company"  maxlength="100"/>
        <form:errors path="company" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.description">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.description" styleClass="control-label"/>
        <form:input cssClass="form-control" path="description" id="description"  maxlength="500"/>
        <form:errors path="description" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.icon">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.icon" styleClass="control-label"/>
        <form:input cssClass="form-control" path="icon" id="icon"  maxlength="200"/>
        <form:errors path="icon" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.modelType">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.modelType" styleClass="control-label"/>
        <form:input cssClass="form-control" path="modelType" id="modelType"  maxlength="255"/>
        <form:errors path="modelType" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.name">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.name" styleClass="control-label"/>
        <form:input cssClass="form-control" path="name" id="name"  maxlength="200"/>
        <form:errors path="name" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.pkgname">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.pkgname" styleClass="control-label"/>
        <form:input cssClass="form-control" path="pkgname" id="pkgname"  maxlength="200"/>
        <form:errors path="pkgname" cssClass="help-block"/>
    </div>
    <spring:bind path="appStore.schemesUrl">
    <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
    </spring:bind>
        <appfuse:label key="appStore.schemesUrl" styleClass="control-label"/>
        <form:input cssClass="form-control" path="schemesUrl" id="schemesUrl"  maxlength="255"/>
        <form:errors path="schemesUrl" cssClass="help-block"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary" name="save" onclick="bCancel=false">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>
        <c:if test="${not empty appStore.id}">
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

<v:javascript formName="appStore" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value='/scripts/validator.jsp'/>"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['appStoreForm']).focus();
    });
</script>
