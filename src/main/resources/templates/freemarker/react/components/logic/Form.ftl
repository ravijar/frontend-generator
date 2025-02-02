<#switch component.role>
    <#case "parent">
    <#case "child">
        <#list component.resource.urlParameters as parameter>
            <#assign value = "${component.id}${parameter.name?cap_first}">
            <#include handleChange>
        </#list>
        <#list component.resource.requestProperties as property>
            <#assign value = "${component.id}${property.name?cap_first}">
            <#include handleChange>
        </#list>
        <#include fetch>
        <#include handleSubmit>
        <#break>
</#switch>
