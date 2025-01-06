<#assign buttonTemplate = "/js/styles/components/Button.ftl">
<#assign cardSectionTemplate = "/js/styles/components/CardSection.ftl">
<#assign formTemplate = "/js/styles/components/Form.ftl">
<#assign heroSectionTemplate = "/js/styles/components/HeroSection.ftl">
<#assign searchBarTemplate = "/js/styles/components/SearchBar.ftl">
const styles = {
<#list data.components as component>
    <#switch component>
        <#case "Button">
            <#assign indentValue = 1>
            <#include buttonTemplate>
        <#break>
        <#case "CardSection">
            <#assign indentValue = 1>
            <#include cardSectionTemplate>
        <#break>
        <#case "Form">
            <#assign indentValue = 1>
            <#include formTemplate>
        <#break>
        <#case "HeroSection">
            <#assign indentValue = 1>
            <#include heroSectionTemplate>
        <#break>
        <#case "SearchBar">
            <#assign indentValue = 1>
            <#include searchBarTemplate>
        <#break>
    </#switch>
</#list>
}