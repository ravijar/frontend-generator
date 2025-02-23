<#assign indent = ""?left_pad(indentValue * 4)>
<#if component.resultComponent??>
    <#if component.resultComponent.subComponents??>
        <#list component.resultComponent.subComponents as subComponent>
            <#if subComponent.action == "resource">
${indent}${subComponent.id}FetchResponse,
            </#if>
        </#list>
    </#if>
</#if>