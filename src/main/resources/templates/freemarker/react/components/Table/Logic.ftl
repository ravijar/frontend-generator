<#switch component.fetch>
    <#case "resource">
        <#include fetch>
        <#break>
    <#case "load">
        <#switch component.loadType>
            <#case "localStorage">
                <#include loadLocalStorage>
                <#break>
        </#switch>
        <#break>
</#switch>

