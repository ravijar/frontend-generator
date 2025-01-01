const ${component.id}Fetch = async () => {
    try {
    <#if resource.httpMethod == "POST" || resource.httpMethod == "PUT">
        const body = {
        <#list resource.requestParameters as parameter>
            ${parameter} : ${parameter},
        </#list>
        };
    
    </#if>
    <#if resource.httpMethod == "GET">
        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(<#list resource.urlParameters as parameter>${parameter}, </#list>);
    <#elseif resource.httpMethod == "POST">
        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(body);
    <#elseif resource.httpMethod == "DELETE">
        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${resource.urlParameters[0]});
    <#elseif resource.httpMethod == "PUT" || resource.httpMethod == "PATCH">
        const response = await clientApi.${resource.apiFunctionName}WithHttpInfo(${resource.urlParameters[0]}, body);
    </#if>
        console.log(response);
        set${component.id?cap_first}FetchResponse(response.data);
    } catch (error) {
        console.log(error.message);
        set${component.id?cap_first}FetchResponse({});
    }
};
