<#switch component.role>
    <#case "root">
    <#case "result">
        <#if component.subComponents??>
            <#list component.subComponents as subComponent>
                <#assign component = subComponent>
                <#switch component.type>
                    <#case "SearchBar">
                        <#include searchBarLogic>
                        <#break>
                    <#case "Form">
                        <#include formLogic>
                        <#break>
                    <#case "Container">
                        <#include containerLogic>
                        <#break>
                    <#case "Button">
                        <#include buttonLogic>
                        <#break>
                </#switch>
            </#list>
        </#if>
        <#break>
</#switch>
