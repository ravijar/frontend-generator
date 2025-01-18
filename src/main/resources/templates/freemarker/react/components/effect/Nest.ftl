<#switch component.role>
    <#case "parent">
    <#case "result">
        <#if component.subComponents??>
            <#list component.subComponents as subComponent>
                <#assign component = subComponent>
                <#switch subComponent.type>
                    <#case "Container">
                        <#include containerEffect>
                        <#break>
                </#switch>
            </#list>
        </#if>
        <#break>
</#switch>
