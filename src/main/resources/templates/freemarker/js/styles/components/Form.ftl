<#assign inputFieldTemplate = "/js/styles/components/InputField.ftl">
<#assign buttonTemplate = "/js/styles/components/Button.ftl">
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${body.id} : {
${indent}    formContainer : {

${indent}    },
${indent}    formInputs : {

${indent}    },
<#assign indentValue = indentValue + 1>
<#include inputFieldTemplate>
<#include buttonTemplate>
<#assign indentValue = indentValue - 1>
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}},
