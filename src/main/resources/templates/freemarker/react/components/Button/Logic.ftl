<#switch component.action>
    <#case "save">
        <#include saveLocalStorage>
        <#break>
    <#case "remove">
        <#include removeLocalStorage>
        <#break>
    <#case "resource">
    <#if component.modelProperties?has_content>
        <#include populateModel>
        <#include fetchReqParamAuth>
    <#else>
        <#include fetchIdParam>
    </#if>
        <#break>
</#switch>
