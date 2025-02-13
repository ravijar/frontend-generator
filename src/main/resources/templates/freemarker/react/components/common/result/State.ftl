<#switch component.role>
    <#case "root">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "Alert">
                <#include alertState>
                <#break>
        </#switch>
        <#break>
</#switch>
