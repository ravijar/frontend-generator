<#switch component.role>
    <#case "root">
    <#case "child">
        <#assign state = "${component.id}ShowAlert">
        <#include useState>
</#switch>