<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${component.resultComponent.id} : {
${indent}    cardArrayContainer : {

${indent}    },
${indent}    cardArrayItem : {

${indent}    },
${indent}    singleCardContainer : {

${indent}    },
<#assign indentValue = indentValue + 1>
<#include keyValuePairStyle>
<#assign indentValue = indentValue - 1>
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}},
