<#switch component.role>
    <#case "root">
    <#case "child">
        <#assign value = "${component.id}${component.resource.urlParameters[0].name?cap_first}">
        <#include handleChange>
        <#include handleSubmit>
        <#include resultLogic>
        <#break>
</#switch>
