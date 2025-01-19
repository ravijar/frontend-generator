<#switch component.role>
    <#case "parent">
    <#case "child">
        <#assign state = "${component.id}ShowAlert">
        <#include useState>
</#switch>