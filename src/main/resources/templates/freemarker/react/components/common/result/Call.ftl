<#switch component.role>
    <#case "parent">
    <#case "child">
        <#assign resultComponent = component.resultComponent>
        <#switch component.resultComponent.type>
            <#case "CardSection">
                <#include cardSectionCall>
                <#break>
            <#case "Alert">
                <#include alertCall>
                <#break>
            <#case "Card">
                <#include cardCall>
                <#break>
            <#case "Table">
                <#include tableCall>
                <#break>
        </#switch>
        <#break>
</#switch>
