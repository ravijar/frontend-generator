<#switch component.role>
    <#case "parent">
    <#case "child">
        <#include fetch>
        <#include resultLogic>
        <#break>
</#switch>
