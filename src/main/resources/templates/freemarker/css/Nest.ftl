.${component.styleId}-container {
<#switch component.type>
    <#case "SearchBar">
    z-index: 1;
    margin: 10px 10px 0 0;
    display: flex;
    justify-content: flex-end;
        <#break>
    <#case "Button">
        <#switch component.role>
            <#case "root">
    z-index: 1;
    position: absolute;
    bottom: 50px;
    left: 45%;
                <#break>
        </#switch>
        <#break>
    <#case "Form">
    z-index: 1;
    margin-top: 20px;
    display: flex;
    justify-content: center;
        <#break>
    <#default>

</#switch>
}

<#if component.resultComponent??>
.${component.resultComponent.styleId}-container {
    <#switch component.resultComponent.type>
        <#case "Card">
    z-index: 1;
    margin-top: 50px;
    display: flex;
    justify-content: center;
            <#break>
        <#case "Alert">
    z-index: 1;
    position: absolute;
    top: 75px;
    right: 5px;
            <#break>
        <#default>

    </#switch>
}

    <#if component.resultComponent.subComponents??>
        <#list component.resultComponent.subComponents as restultSubComponent>
            <#assign component = restultSubComponent>
            <#include nestStyle>
        </#list>
    </#if>
</#if>
<#if component.subComponents??>
    <#list component.subComponents as subComponent>
        <#assign component = subComponent>
        <#include nestStyle>
    </#list>
</#if>
