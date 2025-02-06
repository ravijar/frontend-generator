<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "CardSection">
                <#include cardSectionLogic>
                <#break>
            <#case "Card">
                <#include cardLogic>
                <#break>
            <#case "Alert">
                <#include alertLogic>
                <#break>
        </#switch>
        <#break>
</#switch>
