<#list resource.urlParameters as parameter>
    <#assign state = "${component.id}${parameter.name?cap_first}">
    <#include useState>
    <#assign state = "${component.id}${parameter.name?cap_first}Error">
    <#include useState>
</#list>
<#list resource.requestProperties as property>
    <#assign state = "${component.id}${property.name?cap_first}">
    <#include useState>
    <#assign state = "${component.id}${property.name?cap_first}Error">
    <#include useState>
</#list>
<#assign state = "${component.id}FetchResponse">
<#include useState>
<#assign state = "${component.id}Fetched">
<#include useState>
<#if body.result.component.type == "Alert">
    <#assign state = "${component.id}ShowAlert">
    <#include useState>
</#if>