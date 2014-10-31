<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="appStoreList.title"/></title>
    <meta name="menu" content="AppStoreMenu"/>
</head>

<c:if test="{'$'}{not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="{'$'}{searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="appStoreList.heading"/></h2>

    <form method="get" action="${ctx}/appStores" id="searchForm" class="form-inline">
    <div id="search" class="text-right">
        <span class="col-sm-9">
            <input type="text" size="20" name="q" id="query" value="${param.q}"
                   placeholder="<fmt:message key="search.enterTerms"/>" class="form-control input-sm"/>
        </span>
        <button id="button.search" class="btn btn-default" type="submit">
            <i class="icon-search"></i> <fmt:message key="button.search"/>
        </button>
    </div>
    </form>

    <fmt:message key="appStoreList.message"/>

    <div id="actions" class="btn-group">
        <a href='<c:url value="/appStoreform"/>' class="btn btn-primary">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
        <a href='<c:url value="/home"/>' class="btn btn-default"><i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

<display:table name="appStoreList" class="table table-condensed table-striped table-hover" requestURI="" id="appStoreList" export="true" pagesize="25">
    <display:column property="id" sortable="true" href="appStoreform" media="html"
        paramId="id" paramProperty="id" titleKey="appStore.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="appStore.id"/>
    <display:column property="appsource" sortable="true" titleKey="appStore.appsource"/>
    <display:column property="appsourceid" sortable="true" titleKey="appStore.appsourceid"/>
    <display:column property="company" sortable="true" titleKey="appStore.company"/>
    <display:column property="description" sortable="true" titleKey="appStore.description"/>
    <display:column property="icon" sortable="true" titleKey="appStore.icon"/>
    <display:column property="modelType" sortable="true" titleKey="appStore.modelType"/>
    <display:column property="name" sortable="true" titleKey="appStore.name"/>
    <display:column property="pkgname" sortable="true" titleKey="appStore.pkgname"/>
    <display:column property="schemesUrl" sortable="true" titleKey="appStore.schemesUrl"/>

    <display:setProperty name="paging.banner.item_name"><fmt:message key="appStoreList.appStore"/></display:setProperty>
    <display:setProperty name="paging.banner.items_name"><fmt:message key="appStoreList.appStores"/></display:setProperty>

    <display:setProperty name="export.excel.filename"><fmt:message key="appStoreList.title"/>.xls</display:setProperty>
    <display:setProperty name="export.csv.filename"><fmt:message key="appStoreList.title"/>.csv</display:setProperty>
    <display:setProperty name="export.pdf.filename"><fmt:message key="appStoreList.title"/>.pdf</display:setProperty>
</display:table>
