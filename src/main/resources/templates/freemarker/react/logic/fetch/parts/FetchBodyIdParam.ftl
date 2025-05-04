<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.resource.httpMethod>
    <#case "GET">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(id);
        <#break>
    <#case "POST">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(body);
        <#break>
    <#case "DELETE">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(id);
        <#break>
    <#case "PUT">
    <#case "PATCH">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(id, body);
        <#break>
</#switch>
