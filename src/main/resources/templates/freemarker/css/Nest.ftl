.${component.styleId}-container {
<#switch component.type>
    <#case "SearchBar">
    z-index: 1;
    position: absolute;
    top: 60px;
    right: 30px;
        <#break>
    <#case "Button">
    z-index: 1;
    position: absolute;
    bottom: 50px;
    left: 45%;
        <#break>
    <#case "Form">
    z-index: 1;
    position: absolute;
    top: 60px;
    left:35%
        <#break>
    <#default>

</#switch>
}

<#if component.resultComponent??>
.${component.resultComponent.styleId}-container {

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
