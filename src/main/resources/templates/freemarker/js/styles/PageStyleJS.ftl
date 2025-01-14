<#assign buttonTemplate = "/js/styles/components/Button.ftl">
<#assign cardSectionTemplate = "/js/styles/components/CardSection.ftl">
<#assign formTemplate = "/js/styles/components/Form.ftl">
<#assign heroSectionTemplate = "/js/styles/components/HeroSection.ftl">
<#assign searchBarTemplate = "/js/styles/components/SearchBar.ftl">
<#assign containerTemplate = "/js/styles/components/Container.ftl">
const styles = {
<#assign indentValue = 1>
<#list data.components as component>
    <#assign body = component.body>
    <#switch body.type>
        <#case "Button">
            <#include buttonTemplate>
        <#break>
        <#case "CardSection">
            <#include cardSectionTemplate>
        <#break>
        <#case "Form">
            <#include formTemplate>
        <#break>
        <#case "HeroSection">
            <#include heroSectionTemplate>
        <#break>
        <#case "SearchBar">
            <#include searchBarTemplate>
        <#break>
        <#case "Container">
            <#include containerTemplate>
        <#break>
    </#switch>
</#list>
};

export default styles;