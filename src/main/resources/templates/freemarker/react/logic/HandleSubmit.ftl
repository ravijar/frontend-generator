<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const ${component.id}HandleSubmit = (event) => {
${indent}    event.preventDefault();
${indent}    ${component.id}Fetch();
         <#list component.resource.urlParameters as parameter>
${indent}    set${component.id?cap_first}${parameter.name?cap_first}("");
         </#list>
         <#list component.resource.requestProperties as property>
${indent}    set${component.id?cap_first}${property.name?cap_first}("");
         </#list>
${indent}}

