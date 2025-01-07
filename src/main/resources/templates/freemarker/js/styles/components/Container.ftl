<#assign cardSectionTemplate = "/js/styles/components/CardSection.ftl">
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${body.id} : {
<#if body.result.component.type == "CardSection">
    <#assign indentValue = indentValue + 1>
    <#include cardSectionTemplate>
    <#assign indentValue = indentValue - 1>
    <#assign indent = ""?left_pad(indentValue * 4)>
</#if>
${indent}},
