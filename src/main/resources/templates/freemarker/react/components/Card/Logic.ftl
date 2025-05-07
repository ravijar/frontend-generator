<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
        <#switch component.parent.action>
            <#case "resource">
                <#assign currentComponent = component>
                <#assign component = currentComponent.parent>
                <#include fetchUrlIdParam>
                <#assign component = currentComponent>

${indent}const ${component.id}Filter = () => {
${indent}   let item = { data:[] };
${indent}   const responseData = ${component.parent.id}FetchResponse?.data;
${indent}   if (!responseData) return item;

${indent}   const { ${component.cardKey}, ${component.cardTitle}, ${component.cardDescription}, ${component.cardImage}, ${component.cardHighlight}, ...rest } = responseData;

${indent}   item.key = ${component.cardKey};
${indent}   item.title = ${component.cardTitle};
${indent}   item.description = ${component.cardDescription};
${indent}   item.image = ${component.cardImage};
${indent}   item.highlight = ${component.cardHighlight};
${indent}   item.data = rest;

${indent}   return item;
${indent}};

                    <#include nestLogic>
                <#break>
        </#switch>
        <#break>
</#switch>
