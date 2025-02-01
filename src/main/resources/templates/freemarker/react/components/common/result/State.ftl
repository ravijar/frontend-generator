<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "Alert">
                <#include alertState>
                <#break>
            <#case "Card">
                <#include cardState>
                <#break>
        </#switch>
        <#break>
</#switch>
