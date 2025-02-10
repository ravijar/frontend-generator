<#switch component.action>
    <#case "resource">
        <#include fetch>
        <#break>
    <#case "load">
        <#include loadLocalStorage>
        <#break>
</#switch>

