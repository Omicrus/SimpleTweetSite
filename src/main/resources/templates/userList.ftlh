<#import "parts/common.ftlh" as c>

<@c.page>
    Users List
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
    </table>
    <tbody>
    <#list users as user>
        <div>
            <td>${user.username}</td>
            <td><#list user.roles as role >${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">edit</a></td>
        </div>
    </#list>
    </tbody>
</@c.page>