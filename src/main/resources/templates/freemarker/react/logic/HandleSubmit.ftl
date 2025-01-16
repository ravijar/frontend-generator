<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const ${component.id}HandleSubmit = (event) => {
${indent}    event.preventDefault();
${indent}    ${component.id}Fetch();
${indent}}

