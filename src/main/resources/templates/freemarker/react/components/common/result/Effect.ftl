<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "Card">
                <#include cardEffect>
                <#break>
        </#switch>
        <#break>
</#switch>