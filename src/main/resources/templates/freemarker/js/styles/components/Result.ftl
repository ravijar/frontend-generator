<#switch component.resultComponent.type>
    <#case "CardSection">
        <#include cardSectionStyle>
        <#break>
    <#case "Alert">
        <#include alertStyle>
        <#break>
</#switch>
