<#switch component.role>
    <#case "parent">
    <#case "child">
        <#assign value = "${component.id}${component.resource.urlParameters[0].name?cap_first}">
        <#include handleChange>
        <#include fetch>
        <#include handleSubmit>
        <#break>
</#switch>
