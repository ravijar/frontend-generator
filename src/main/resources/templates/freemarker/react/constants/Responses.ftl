<#assign indent = ""?left_pad(indent * 4)>
<#if resource?? && resource.responses??>
${indent}const ${component.id}Responses = {
        <#list resource.responses as response>
${indent}    "${response.code}": {
${indent}        responseSchema: {
${indent}            name: <#if response.schemaName??>"${response.schemaName}"<#else>null</#if>,
${indent}            type: <#if response.type??>"${response.type}"<#else>null</#if>,
${indent}        },
${indent}        description: <#if response.description??>"${response.description}"<#else>null</#if>,
${indent}        displayNames: {
                 <#list response.schemaProperties as property>
${indent}            ${property.name}: "${property.displayName}",
                 </#list>
${indent}        },
${indent}    }<#if response_has_next>,</#if>
        </#list>
${indent}};
</#if>
