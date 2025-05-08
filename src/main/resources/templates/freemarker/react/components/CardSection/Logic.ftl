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

${indent}const ${component.id}Filter = (data, schema) => {
${indent}   let items = [];

${indent}   if (!data) return items;

${indent}   switch (schema) {
${indent}       case "null":
${indent}           items.push({
${indent}               key: data.${component.cardKey},
${indent}               title: data.${component.cardTitle},
${indent}               description: data.${component.cardDescription},
${indent}               image: data.${component.cardImage},
${indent}               highlight: data.${component.cardHighlight}
${indent}           });
${indent}           break;
${indent}       case "array":
${indent}           items = data.map((item) => ({
${indent}               key: item.${component.cardKey},
${indent}               title: item.${component.cardTitle},
${indent}               description: item.${component.cardDescription},
${indent}               image: item.${component.cardImage},
${indent}               highlight: item.${component.cardHighlight}
${indent}           }));
${indent}           break;
${indent}   }
${indent}   return items;
${indent}};
                <#break>
        </#switch>
        <#break>
</#switch>
