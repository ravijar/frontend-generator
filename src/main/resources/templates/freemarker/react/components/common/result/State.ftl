<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "Alert">
                <#include alertState>
                <#break>
        </#switch>
        <#break>
</#switch>
