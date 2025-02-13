<#switch component.role>
    <#case "root">
    <#case "child">
        <#assign resultComponent = component.resultComponent>
        <#assign component = resultComponent>
        <#switch component.type>
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
