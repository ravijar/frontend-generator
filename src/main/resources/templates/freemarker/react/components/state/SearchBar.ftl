<#assign state = "${component.id}${resource.urlParameters[0].name?cap_first}">
<#include useState>
<#assign state = "${component.id}${resource.urlParameters[0].name?cap_first}Error">
<#include useState>
<#assign state = "${component.id}FetchResponse">
<#include useState>
<#assign state = "${component.id}Fetched">
<#include useState>