<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${component.id} : {
${indent}    form : {

${indent}    },
<#assign indentValue = indentValue + 1>
<#include inputFieldStyle>
<#assign indentValue = indentValue - 1>
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}    formSubmit : {

${indent}    },
${indent}},
<#include resultStyle>

