<#assign keyValuePairTemplate = "/js/styles/components/KeyValuePair.ftl">
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}cardSection : {
${indent}    cardArrayContainer : {

${indent}    },
${indent}    cardArrayItem : {

${indent}    },
${indent}    singleCardContainer : {

${indent}    },
<#assign indentValue = indentValue + 1>
<#include keyValuePairTemplate>
<#assign indentValue = indentValue - 1>
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}},
