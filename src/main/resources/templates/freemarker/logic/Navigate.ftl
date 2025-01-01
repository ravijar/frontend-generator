<#assign indent = ""?left_pad(indent * 4)>
${indent}const ${component.id}Navigate = () => {
${indent}    navigate("${body.route.url}");
${indent}};
