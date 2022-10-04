<!-- Code fragment to get session data-->

<!-- Boolean variable initialization to security context-->
<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>


<#if known>
    <!-- If session exist (Signed) - we get the authorized user  -->
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    currentUserId = user.getId()
    >

<#else>
    <!-- Session doesn't exist -->
    <#assign
    name = "unknown"
    isAdmin = false
    currentUserId = -1
    >
</#if>