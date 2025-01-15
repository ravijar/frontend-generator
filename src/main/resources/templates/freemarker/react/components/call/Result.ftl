<#switch component.resultComponent.type>
    <#case "CardSection">
        <#include cardSectionCall>
        <#break>
    <#case "Alert">
        <#include alertCall>
        <#break>
</#switch>
