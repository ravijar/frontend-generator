<#switch component.role>
    <#case "root">
    <#case "result">
        <#if component.subComponents??>
            <#list component.subComponents as subComponent>
                <#assign component = subComponent>
                <#switch component.type>
                    <#case "Container">
                        <#include containerEffect>
                        <#break>
                </#switch>
            </#list>
        </#if>
        <#break>
</#switch>
