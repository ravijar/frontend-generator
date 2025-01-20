<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "CardSection">
                <#include cardSectionCall>
                <#break>
            <#case "Alert">
                <#include alertCall>
                <#break>
        </#switch>
        <#break>
</#switch>
