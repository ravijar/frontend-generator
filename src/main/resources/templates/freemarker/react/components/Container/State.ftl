<#switch component.role>
    <#case "root">
    <#case "child">
    <#switch component.action>
        <#case "resource">
            <#assign state = "${component.id}FetchResponse">
            <#include useState>
            <#assign state = "${component.id}Fetched">
            <#include useState>
            <#break>
        <#case "load">
            <#assign state = "${component.id}LoadResponse">
            <#include useState>
            <#break>
        <#break>
    </#switch>
    <#include resultState>
</#switch>
