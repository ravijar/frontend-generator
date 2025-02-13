<#switch component.role>
    <#case "root">
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
            <#case "Table">
                <#include tableLogic>
                <#break>
        </#switch>
        <#break>
</#switch>
