<#switch component.role>
    <#case "parent">
    <#case "result">
        <#if component.subComponents??>
            <#list component.subComponents as subComponent>
                <#assign component = subComponent>
                <#switch subComponent.type>
                    <#case "SearchBar">
                        <#include searchBarLogic>
                        <#break>
                    <#case "Form">
                        <#include formLogic>
                        <#break>
                    <#case "Container">
                        <#include containerLogic>
                        <#break>
                </#switch>
            </#list>
        </#if>
        <#break>
</#switch>
