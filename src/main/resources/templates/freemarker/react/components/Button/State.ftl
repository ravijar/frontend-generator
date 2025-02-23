<#switch component.role>
    <#case "child">
        <#switch component.action>
            <#case "resource">
                <#assign state = "${component.id}FetchResponse">
                <#include useState>
                <#assign state = "${component.id}Fetched">
                <#include useState>
                <#include resultState>
                <#break>
            <#case "save">
                <#assign state = "${component.id}SaveResponse">
                <#include useState>
                <#break>
            <#case "remove">
                <#assign state = "${component.id}RemoveResponse">
                <#include useState>
                <#break>
        </#switch>
        <#break>
</#switch>