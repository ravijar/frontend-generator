<#switch component.resultComponent.type>
    <#case "Alert">
        <#assign state = "${component.id}ShowAlert">
        <#include useState>
        <#break>
</#switch>