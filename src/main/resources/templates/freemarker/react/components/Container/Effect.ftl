<#switch component.role>
    <#case "parent">
    <#case "child">
        <#include fetchOnInitEffect>
        <#break>
</#switch>
