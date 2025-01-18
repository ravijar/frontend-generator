<#if component.subComponents??>
    <#list component.subComponents as subComponent>
        <#assign component = subComponent>
        <#switch subComponent.type>
            <#case "Button">
                <#include buttonStyle>
                <#break>
            <#case "SearchBar">
                <#include searchBarStyle>
                <#break>
            <#case "Form">
                <#include formStyle>
                <#break>
            <#case "Container">
                <#include containerStyle>
                <#break>
        </#switch>
    </#list>
</#if>
