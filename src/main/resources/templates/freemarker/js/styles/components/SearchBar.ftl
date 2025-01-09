<#assign cardSectionTemplate = "/js/styles/components/CardSection.ftl">
<#assign indent = ""?left_pad(indentValue * 4)>
${indent}${body.id} : {
${indent}    searchBar : {

${indent}    },
${indent}    searchInput : {

${indent}    },
${indent}    searchButton : {

${indent}    },
<#if body.result.component.type == "CardSection">
    <#assign indentValue = indentValue + 1>
    <#include cardSectionTemplate>
    <#assign indentValue = indentValue - 1>
    <#assign indent = ""?left_pad(indentValue * 4)>
</#if>
${indent}},
