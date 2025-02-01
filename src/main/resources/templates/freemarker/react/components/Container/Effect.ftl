<#switch component.role>
    <#case "parent">
    <#case "child">
        <#include fetchOnInitEffect>
        <#include resultEffect>
        <#break>
</#switch>
