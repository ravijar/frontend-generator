<#switch component.action>
    <#case "save">
        <#include saveLocalStorage>
        <#break>
    <#case "remove">
        <#include removeLocalStorage>
        <#break>
</#switch>
