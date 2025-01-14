<#list resource.urlParameters as parameter>
    <#assign value = "${component.id}${parameter.name?cap_first}">
    <#include handleChange>
</#list>
<#list resource.requestProperties as property>
    <#assign value = "${component.id}${property.name?cap_first}">
    <#include handleChange>
</#list>
<#include fetch>
<#include handleSubmit>