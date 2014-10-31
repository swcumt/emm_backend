<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="appstoreEditionList.title"/></title>
    <meta name="menu" content="AppstoreEditionMenu"/>
</head>

<c:if test="{'$'}{not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="{'$'}{searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2><fmt:message key="appstoreEditionList.heading"/></h2>

    <form method="get" action="${ctx}/appstoreEditions" id="searchForm" class="form-inline">
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

    <fmt:message key="appstoreEditionList.message"/>

    <div id="actions" class="btn-group">
        <a href='<c:url value="/appstoreEditionform"/>' class="btn btn-primary">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
        <a href='<c:url value="/home"/>' class="btn btn-default"><i class="icon-ok"></i> <fmt:message key="button.done"/></a>
    </div>

<display:table name="appstoreEditionList" class="table table-condensed table-striped table-hover" requestURI="" id="appstoreEditionList" export="true" pagesize="25">
    <display:column property="id" sortable="true" href="appstoreEditionform" media="html"
        paramId="id" paramProperty="id" titleKey="appstoreEdition.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="appstoreEdition.id"/>
    <display:column property="appSize" sortable="true" titleKey="appstoreEdition.appSize"/>
    <display:column property="appStoreId" sortable="true" titleKey="appstoreEdition.appStoreId"/>
    <display:column property="createTime" sortable="true" titleKey="appstoreEdition.createTime"/>
    <display:column property="description" sortable="true" titleKey="appstoreEdition.description"/>
    <display:column property="fullTrialId" sortable="true" titleKey="appstoreEdition.fullTrialId"/>
    <display:column property="ipaUrl" sortable="true" titleKey="appstoreEdition.ipaUrl"/>
    <display:column property="plistUrl" sortable="true" titleKey="appstoreEdition.plistUrl"/>
    <display:column property="versions" sortable="true" titleKey="appstoreEdition.versions"/>

    <display:setProperty name="paging.banner.item_name"><fmt:message key="appstoreEditionList.appstoreEdition"/></display:setProperty>
    <display:setProperty name="paging.banner.items_name"><fmt:message key="appstoreEditionList.appstoreEditions"/></display:setProperty>

    <display:setProperty name="export.excel.filename"><fmt:message key="appstoreEditionList.title"/>.xls</display:setProperty>
    <display:setProperty name="export.csv.filename"><fmt:message key="appstoreEditionList.title"/>.csv</display:setProperty>
    <display:setProperty name="export.pdf.filename"><fmt:message key="appstoreEditionList.title"/>.pdf</display:setProperty>
</display:table>
