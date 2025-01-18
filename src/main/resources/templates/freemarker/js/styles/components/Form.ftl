<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${component.id} : {
${indent}    formContainer : {

${indent}    },
${indent}    formInputs : {

${indent}    },
<#assign indentValue = indentValue + 1>
<#include inputFieldStyle>
<#include buttonStyle>
<#assign indentValue = indentValue - 1>
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}},
<#include resultStyle>

