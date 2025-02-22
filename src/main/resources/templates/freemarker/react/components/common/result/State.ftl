<#switch component.role>
    <#case "root">
    <#case "child">
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
        <#break>
</#switch>
