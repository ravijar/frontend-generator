<#switch component.role>
    <#case "root">
    <#case "child">
        <#if component.resultComponent??>
            <#assign resultComponent = component.resultComponent>
            <#assign component = resultComponent>
            <#switch component.type>
                <#case "Alert">
                    <#include alertState>
                    <#break>
                <#case "Table">
                    <#include tableState>
                    <#break>
            </#switch>
        </#if>
        <#break>
</#switch>
<#include nestState>
