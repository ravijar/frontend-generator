<#switch component.role>
    <#case "root">
    <#case "child">
        <#if component.resultComponent??>
            <#assign resultComponent = component.resultComponent>
            <#assign component = resultComponent>
            <#switch component.type>
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
            <#include nestCall>
        </#if>
        <#break>
</#switch>
