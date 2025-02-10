<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const load${component.loadKey?cap_first} = () => {
${indent}   return JSON.parse(localStorage.getItem("${component.loadKey}")) || [];
${indent}};

