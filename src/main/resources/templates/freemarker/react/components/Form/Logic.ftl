<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "root">
    <#case "child">
        <#list component.resource.urlParameters as parameter>
            <#assign value = "${component.id}${parameter.name?cap_first}">
            <#include handleChange>
        </#list>
        <#list component.resource.requestProperties as property>
            <#assign value = "${component.id}${property.name?cap_first}">
            <#include handleChange>
        </#list>
        <#include handleSubmit>
        <#if component.fetchResource??>
${indent}const initializeStates = async () => {
${indent}    setLoading(true);
${indent}    try {
${indent}        const existingData = await clientApi.${component.fetchResource.apiFunctionName}(${page.urlParameter});
    <#list component.resource.requestProperties as property>
${indent}        set${component.id?cap_first}${property.name?cap_first}(existingData.${property.name} || "");
    </#list>
${indent}    } catch (error) {
${indent}        console.error("Error fetching pet details:", error.message);
${indent}    } finally {
${indent}        setLoading(false);
${indent}    }
${indent}};
        </#if>

        <#include resultLogic>
        <#break>
</#switch>
