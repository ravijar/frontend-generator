<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
        <#switch component.parent.action>
            <#case "resource">
                <#assign currentComponent = component>
                <#assign component = currentComponent.parent>
                <#include fetch>
                <#assign component = currentComponent>

${indent}const ${component.id}Filter = () => {
${indent}   let items = [];
${indent}   const responseSchema = ${component.parent.id}Responses[${component.parent.id}FetchResponse?.httpStatusCode]?.responseSchema.type;
${indent}   const responseData = ${component.parent.id}FetchResponse?.data;
${indent}   if (!responseData) return items;

${indent}   switch (responseSchema) {
${indent}       case "null":
${indent}           items.push({
${indent}               key: responseData.${component.cardKey},
${indent}               title: responseData.${component.cardTitle},
${indent}               description: responseData.${component.cardDescription},
${indent}               image: responseData.${component.cardImage},
${indent}           });
${indent}           break;
${indent}       case "array":
${indent}           items = responseData.map((item) => ({
${indent}               key: item.${component.cardKey},
${indent}               title: item.${component.cardTitle},
${indent}               description: item.${component.cardDescription},
${indent}               image: item.${component.cardImage}
${indent}           }));
${indent}           break;
${indent}   }
${indent}   return items;
${indent}};
                <#break>
        </#switch>
        <#break>
</#switch>
