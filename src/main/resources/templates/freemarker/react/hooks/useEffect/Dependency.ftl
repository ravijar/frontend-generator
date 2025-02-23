<#assign indent = ""?left_pad(indentValue * 4)>
<#if component.resultComponent??>
    <#if component.resultComponent.subComponents??>
        <#switch component.action>
            <#case "resource">
                <#list component.resultComponent.subComponents as subComponent>
                    <#if subComponent.action == "resource">
${indent}${subComponent.id}FetchResponse,
                    </#if>
                </#list>
                <#break>
            <#case "load">
                <#list component.resultComponent.subComponents as subComponent>
                    <#if subComponent.action == "save">
${indent}${subComponent.id}SaveResponse,
                    </#if>
                    <#if subComponent.action == "remove">
${indent}${subComponent.id}RemoveResponse,
                    </#if>
                </#list>
                <#break>
        </#switch>
    </#if>
</#if>