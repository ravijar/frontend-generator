<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${component.id} : {
<#if component.resultComponent.type == "CardSection">
    <#assign indentValue = indentValue + 1>
    <#assign component = component.resultComponent>
    <#include cardSectionStyle>
    <#assign indentValue = indentValue - 1>
    <#assign indent = ""?left_pad(indentValue * 4)>
</#if>
${indent}},
<#include nestStyle>
