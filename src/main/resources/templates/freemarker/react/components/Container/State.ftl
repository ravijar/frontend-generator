<#switch component.role>
    <#case "root">
    <#case "child">
        <#assign state = "${component.id}FetchResponse">
        <#include useState>
        <#assign state = "${component.id}Fetched">
        <#include useState>
        <#include resultState>
        <#break>
</#switch>
