<#assign currentComponent = component>
<#assign component = currentComponent.parent>
<#switch component.action>
    <#case "resource">
        <#include fetch>
        <#break>
    <#case "load">
        <#include loadLocalStorage>
        <#break>
</#switch>
<#assign component = currentComponent>

