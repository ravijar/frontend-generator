<#switch component.role>
    <#case "parent">
        <#assign state = "${component.id}${component.resource.urlParameters[0].name?cap_first}">
        <#include useState>
        <#assign state = "${component.id}${component.resource.urlParameters[0].name?cap_first}Error">
        <#include useState>
        <#assign state = "${component.id}FetchResponse">
        <#include useState>
        <#assign state = "${component.id}Fetched">
        <#include useState>
        <#include resultState>
        <#break>
</#switch>
