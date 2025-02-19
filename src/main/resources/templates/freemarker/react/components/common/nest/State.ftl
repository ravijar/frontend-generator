<#switch component.role>
    <#case "parent">
    <#case "result">
        <#if component.subComponents??>
            <#list component.subComponents as subComponent>
                <#assign component = subComponent>
                <#switch subComponent.type>
                    <#case "SearchBar">
                        <#include searchBarState>
                        <#break>
                    <#case "Form">
                        <#include formState>
                        <#break>
                    <#case "Container">
                        <#include containerState>
                        <#break>
                </#switch>
            </#list>
        </#if>
        <#break>
</#switch>
