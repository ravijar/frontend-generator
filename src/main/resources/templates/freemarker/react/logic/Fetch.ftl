<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const ${component.id}Fetch = async () => {
${indent}    try {
            <#if component.resource.httpMethod == "POST" || component.resource.httpMethod == "PUT">
${indent}        const body = {
                <#list component.resource.requestProperties as property>
${indent}            ${property.name} : ${component.id}${property.name?cap_first},
                </#list>
${indent}        };
            </#if>
            <#if component.resource.httpMethod == "GET">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(<#list component.resource.urlParameters as parameter>${component.id}${parameter.name?cap_first}, </#list>);
            <#elseif component.resource.httpMethod == "POST">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(body);
            <#elseif component.resource.httpMethod == "DELETE">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(${component.id}${component.resource.urlParameters[0].name?cap_first});
            <#elseif component.resource.httpMethod == "PUT" || component.resource.httpMethod == "PATCH">
${indent}        const response = await clientApi.${component.resource.apiFunctionName}WithHttpInfo(${component.id}${component.resource.urlParameters[0].name?cap_first}, body);
            </#if>
${indent}        console.log(response);
${indent}        set${component.id?cap_first}FetchResponse(response);
${indent}        set${component.id?cap_first}Fetched(true);
            <#if component.resultComponent.type == "Alert">
${indent}        set${component.id?cap_first}ShowAlert(true);
            </#if>
${indent}    } catch (error) {
${indent}        console.log(error.message);
${indent}        set${component.id?cap_first}FetchResponse({httpStatusCode: error.code});
${indent}        set${component.id?cap_first}Fetched(false);
            <#if component.resultComponent.type == "Alert">
${indent}        set${component.id?cap_first}ShowAlert(true);
            </#if>
${indent}    }
${indent}};

