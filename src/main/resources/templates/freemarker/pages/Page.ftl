<#assign heroSectionTemplatePath = "/components/HeroSection.ftl">
<#assign searchBarTemplatePath = "/components/SearchBar.ftl">
<#assign buttonTemplatePath = "/components/Button.ftl">

<#assign fetchTemplatePath = "/logic/Fetch.ftl">
<#assign navigateTemplatePath = "/logic/Navigate.ftl">

<#-- Creating Component Logic -->
<#list data.components as component>
    <#assign body = component.body>
    <#assign resource = (component.resource)!>
    <#switch body.type>
        <#case "Searchbar">
            <#include fetchTemplatePath>
        <#break>
        <#case "Button">
            <#include navigateTemplatePath>
        <#break>
    </#switch>
</#list>

<#-- Calling Components -->
<#list data.components as component>
    <#assign body = component.body>
    <#switch body.type>
        <#case "Herosection">
            <#include heroSectionTemplatePath>
        <#break>
        <#case "Searchbar">
            <#include searchBarTemplatePath>
        <#break>
        <#case "Button">
            <#include buttonTemplatePath>
        <#break>
    </#switch>
</#list>