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
        </#switch>
        <#break>
</#switch>