<#assign indent = ""?left_pad(indent * 4)>
${indent}const ${component.id}Fetch = async () => {
${indent}    try {
            <#if resource.httpMethod == "POST" || resource.httpMethod == "PUT">
${indent}        const body = {
                <#list resource.requestProperties as property>
${indent}            ${property.name} : ${component.id}${property.name?cap_first},
                </#list>
${indent}        };
            </#if>
            <#if resource.httpMethod == "GET">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(<#list resource.urlParameters as parameter>${component.id}${parameter.name?cap_first}, </#list>);
            <#elseif resource.httpMethod == "POST">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(body);
            <#elseif resource.httpMethod == "DELETE">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${component.id}${resource.urlParameters[0].name?cap_first});
            <#elseif resource.httpMethod == "PUT" || resource.httpMethod == "PATCH">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${component.id}${resource.urlParameters[0].name?cap_first}, body);
            </#if>
${indent}        console.log(response);
${indent}        set${component.id?cap_first}FetchResponse(response);
${indent}        set${component.id?cap_first}Fetched(true);
            <#if body.result.component.type == "Alert">
${indent}        set${component.id?cap_first}ShowAlert(true);
            </#if>
${indent}    } catch (error) {
${indent}        console.log(error.message);
${indent}        set${component.id?cap_first}FetchResponse({httpStatusCode: error.code});
${indent}        set${component.id?cap_first}Fetched(false);
            <#if body.result.component.type == "Alert">
${indent}        set${component.id?cap_first}ShowAlert(true);
            </#if>
${indent}    }
${indent}};

