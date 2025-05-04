<#assign indent = ""?left_pad(indentValue * 4)>
<#if component.resource.securityRequirements?has_content>
    <#switch component.resource.httpMethod>
        <#case "GET">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(token);
            <#break>
        <#case "POST">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(<#if component.resource.urlParameters?has_content>token, </#if><#if component.resource.requestProperties?has_content>body</#if>);
            <#break>
        <#case "DELETE">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(${component.id}${component.resource.urlParameters[0].name?cap_first});
            <#break>
        <#case "PUT">
        <#case "PATCH">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(${component.id}${component.resource.urlParameters[0].name?cap_first}, body);
            <#break>
    </#switch>
<#else>
    <#switch component.resource.httpMethod>
        <#case "GET">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(<#list component.resource.urlParameters as parameter>${component.id}${parameter.name?cap_first}, </#list>);
            <#break>
        <#case "POST">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(body);
            <#break>
        <#case "DELETE">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(${component.id}${component.resource.urlParameters[0].name?cap_first});
            <#break>
        <#case "PUT">
        <#case "PATCH">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(${component.id}${component.resource.urlParameters[0].name?cap_first}, body);
            <#break>
    </#switch>
</#if>
