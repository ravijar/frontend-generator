<#assign indent = ""?left_pad(indent * 4)>
${indent}const ${component.id}Fetch = async () => {
${indent}    try {
            <#if resource.httpMethod == "POST" || resource.httpMethod == "PUT">
${indent}        const body = {
                <#list resource.requestParameters as parameter>
${indent}            ${parameter} : ${parameter},
                </#list>
${indent}        };

            </#if>
            <#if resource.httpMethod == "GET">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(<#list resource.urlParameters as parameter>${parameter}, </#list>);
            <#elseif resource.httpMethod == "POST">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(body);
            <#elseif resource.httpMethod == "DELETE">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${resource.urlParameters[0]});
            <#elseif resource.httpMethod == "PUT" || resource.httpMethod == "PATCH">
${indent}        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${resource.urlParameters[0]}, body);
            </#if>
${indent}        console.log(response);
${indent}        set${component.id?cap_first}FetchResponse(response.data);
${indent}    } catch (error) {
${indent}        console.log(error.message);
${indent}        set${component.id?cap_first}FetchResponse({});
${indent}    }
${indent}};