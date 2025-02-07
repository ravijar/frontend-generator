<#switch component.resultComponent.type>
    <#case "CardSection">
        <#include cardSectionStyle>
        <#break>
    <#case "Alert">
        <#include alertStyle>
        <#break>
    <#case "Card">
        <#include cardStyle>
        <#break>
    <#case "Table">
        <#include tableStyle>
        <#break>
</#switch>
