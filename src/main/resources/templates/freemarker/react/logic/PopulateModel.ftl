${indent}const ${component.id}PopulateModel = () => {
${indent}   let modelData = { };
${indent}   const responseData = ${rootComponent.id}FetchResponse?.data;
${indent}   if (!responseData) return modelData;

${indent}   const { <#list component.modelProperties as map><#list map?keys as key>${key}</#list>, </#list> ...rest } = responseData;

    <#list component.modelProperties as map>
        <#list map?keys as key>
${indent}   modelData.${key} = ${map[key]}
        </#list>
    </#list>

${indent}   return modelData;
${indent}};

