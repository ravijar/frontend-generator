<#switch component.action>
    <#case "save">
        <#include saveLocalStorage>
        <#break>
    <#case "remove">
        <#include removeLocalStorage>
        <#break>
    <#case "resource">
        <#include fetchParam>
        <#break>
</#switch>
