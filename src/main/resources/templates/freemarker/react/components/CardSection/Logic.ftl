<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
${indent}const ${component.resultComponent.id}Filter = () => {
${indent}   let items = [];
${indent}   const responseSchema = ${component.id}Responses[${component.id}FetchResponse?.httpStatusCode]?.responseSchema.type;
${indent}   const responseData = ${component.id}FetchResponse?.data;
${indent}   if (!responseData) return items;

${indent}   switch (responseSchema) {
${indent}       case "null":
${indent}           items.push({
${indent}               key: responseData.${component.resultComponent.cardKey},
${indent}               title: responseData.${component.resultComponent.cardTitle},
${indent}               description: responseData.${component.resultComponent.cardDescription},
${indent}               image: responseData.${component.resultComponent.cardImage},
${indent}           });
${indent}           break;
${indent}       case "array":
${indent}           items = responseData.map((item) => ({
${indent}               key: item.${component.resultComponent.cardKey},
${indent}               title: item.${component.resultComponent.cardTitle},
${indent}               description: item.${component.resultComponent.cardDescription},
${indent}               image: item.${component.resultComponent.cardImage}
${indent}           }));
${indent}           break;
${indent}   }
${indent}   return items;
${indent}};
        <#break>
</#switch>
