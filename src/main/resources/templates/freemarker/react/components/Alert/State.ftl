<#switch component.parent.role>
    <#case "root">
    <#case "child">
        <#assign state = "${component.parent.id}ShowAlert">
        <#include useState>
</#switch>