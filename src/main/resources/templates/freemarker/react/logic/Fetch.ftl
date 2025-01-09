<#assign indent = ""?left_pad(indent * 4)>
${indent}const ${component.id}Fetch = async () => {
${indent}    try {
            <#if resource.httpMethod == "POST" || resource.httpMethod == "PUT">
${indent}        const body = {
                <#list resource.requestParameters as parameter>
${indent}            ${parameter} : ${component.id}${parameter?cap_first},
                </#list>
${indent}        };
            </#if>
            <#if resource.httpMethod == "GET">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(<#list resource.urlParameters as parameter>${component.id}${parameter?cap_first}, </#list>);
            <#elseif resource.httpMethod == "POST">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(body);
            <#elseif resource.httpMethod == "DELETE">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${component.id}${resource.urlParameters[0]?cap_first});
            <#elseif resource.httpMethod == "PUT" || resource.httpMethod == "PATCH">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${component.id}${resource.urlParameters[0]?cap_first}, body);
            </#if>
${indent}        console.log(response);
${indent}        set${component.id?cap_first}FetchResponse(response.data);
${indent}        set${component.id?cap_first}FetchResponseSchema(${component.id}Responses[response.httpStatusCode]?.responseSchema);
            <#if body.result.component.type == "Alert">
${indent}        set${component.id?cap_first}AlertData({
                    code : response.httpStatusCode,
                    message : ${component.id}Responses[response.httpStatusCode]?.description
                 });
                 set${component.id?cap_first}ShowAlert(true);
            </#if>
${indent}    } catch (error) {
${indent}        console.log(error.message);
${indent}        set${component.id?cap_first}FetchResponse({});
${indent}        set${component.id?cap_first}FetchResponseSchema(${component.id}Responses[error.code]?.responseSchema);
            <#if body.result.component.type == "Alert">
                 set${component.id?cap_first}AlertData({
                    code : error.code,
                    message : ${component.id}Responses[error.code]?.description
                 });
                 set${component.id?cap_first}ShowAlert(true);
            </#if>
${indent}    }
${indent}};

