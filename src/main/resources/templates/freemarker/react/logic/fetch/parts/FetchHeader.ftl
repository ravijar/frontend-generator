<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const ${component.id}Fetch = async () => {
${indent}    setLoading(true);
${indent}    try {
<#if component.resource.httpMethod == "POST" || component.resource.httpMethod == "PUT">
${indent}        const body = {
    <#list component.resource.requestProperties as property>
${indent}            ${property.name} : ${component.id}${property.name?cap_first},
    </#list>
${indent}        };
</#if>
