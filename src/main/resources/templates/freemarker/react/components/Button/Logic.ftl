<#switch component.action>
    <#case "save">
        <#switch component.saveType>
            <#case "localStorage">
                <#include saveLocalStorage>
                <#break>
        </#switch>
        <#break>
</#switch>
