<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
        <#include fetchUrlParam>

${indent}const ${component.resultComponent.id}Filter = () => {
${indent}   let item = { data:[] };
${indent}   const responseData = ${component.id}FetchResponse?.data;
${indent}   if (!responseData) return item;

${indent}   const { ${component.resultComponent.cardKey}, ${component.resultComponent.cardTitle}, ${component.resultComponent.cardDescription}, ${component.resultComponent.cardImage}, ...rest } = responseData;

${indent}       item.key = ${component.resultComponent.cardKey};
${indent}       item.title = ${component.resultComponent.cardTitle};
${indent}       item.description = ${component.resultComponent.cardDescription};
${indent}       item.image = ${component.resultComponent.cardImage};
${indent}       item.data = rest;

${indent}   return item;
${indent}};

        <#assign component = component.resultComponent>
        <#include nestLogic>
        <#break>
</#switch>
