<#assign indent = ""?left_pad(indentValue * 4)>
<#if component.resource?? && component.resource.responses??>
${indent}const ${component.id}Responses = {
        <#list component.resource.responses as response>
${indent}    "${response.code}": {
${indent}        responseSchema: {
${indent}            name: <#if response.schemaName??>"${response.schemaName}"<#else>null</#if>,
${indent}            type: <#if response.type??>"${response.type}"<#else>null</#if>,
${indent}        },
${indent}        description: <#if response.description??>"${response.description}"<#else>null</#if>,
${indent}        displayNames: {
            <#if response.schemaProperties??>
                 <#list response.schemaProperties as property>
${indent}            ${property.name}: "${property.displayName}",
                 </#list>
            </#if>
${indent}        },
${indent}    }<#if response_has_next>,</#if>
        </#list>
${indent}};
</#if>
<#if component.subComponents??>
    <#list component.subComponents as component>
        <#include responses>
    </#list>
</#if>
<#if component.resultComponent??>
    <#if component.resultComponent.subComponents??>
        <#list component.resultComponent.subComponents as component>
            <#include responses>
        </#list>
    </#if>
</#if>

