<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
<div class="collapse navbar-collapse" id="navbar">
<ul class="nav navbar-nav">
    <c:if test="${empty pageContext.request.remoteUser}">
        <li class="active">
            <a href="<c:url value="/login"/>"><fmt:message key="login.title"/></a>
        </li>
    </c:if>
    <menu:displayMenu name="Home"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="Logout"/>
    <!--Users-START-->
    <menu:displayMenu name="UsersMenu"/>
    <!--Users-END-->
    <!--AppstoreVersions-START-->
    <menu:displayMenu name="AppstoreVersionsMenu"/>
    <!--AppstoreVersions-END-->
    <!--AppstoreEdition-START-->
    <menu:displayMenu name="AppstoreEditionMenu"/>
    <!--AppstoreEdition-END-->
    <!--AComment-START-->
    <menu:displayMenu name="ACommentMenu"/>
    <!--AComment-END-->
    <!--Schedule-START-->
    <menu:displayMenu name="ScheduleMenu"/>
    <!--Schedule-END-->
    <!--ScheduleList-START-->
    <menu:displayMenu name="ScheduleListMenu"/>
    <!--ScheduleList-END-->
    <!--ScheduleListUser-START-->
    <menu:displayMenu name="ScheduleListUserMenu"/>
    <!--ScheduleListUser-END-->
    <!--ScheduleUser-START-->
    <menu:displayMenu name="ScheduleUserMenu"/>
    <!--ScheduleUser-END-->
</ul>
</div>
</menu:useMenuDisplayer>
