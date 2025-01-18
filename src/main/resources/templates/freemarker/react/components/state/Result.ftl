<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "Alert">
                <#assign state = "${component.id}ShowAlert">
                <#include useState>
                <#break>
        </#switch>
        <#break>
</#switch>
