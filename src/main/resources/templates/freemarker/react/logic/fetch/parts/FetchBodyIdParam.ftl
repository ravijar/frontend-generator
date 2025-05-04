<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.resource.httpMethod>
    <#case "GET">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(id);
        <#break>
    <#case "POST">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(<#if component.resource.urlParameters?has_content>token, </#if><#if component.resource.requestProperties?has_content>body</#if>);
        <#break>
    <#case "DELETE">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(<#if component.resource.securityRequirements?has_content && (component.resource.urlParameters?size gt 1)>token, </#if>id);
        <#break>
    <#case "PUT">
    <#case "PATCH">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(id, body);
        <#break>
</#switch>
