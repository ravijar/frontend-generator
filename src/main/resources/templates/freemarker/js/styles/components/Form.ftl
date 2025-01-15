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
<#if component.resultComponent.type == "Alert">
    <#assign indentValue = indentValue + 1>
    <#include alertStyle>
    <#assign indentValue = indentValue - 1>
    <#assign indent = ""?left_pad(indentValue * 4)>
</#if>
${indent}},
<#include nestStyle>
