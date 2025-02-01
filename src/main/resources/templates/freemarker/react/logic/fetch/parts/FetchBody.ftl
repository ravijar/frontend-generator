<#assign indent = ""?left_pad(indentValue * 4)>
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
