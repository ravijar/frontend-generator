<#assign alertStyle = "/js/styles/components/Alert.ftl">
<#assign buttonStyle = "/js/styles/components/Button.ftl">
<#assign cardSectionStyle = "/js/styles/components/CardSection.ftl">
<#assign formStyle = "/js/styles/components/Form.ftl">
<#assign heroSectionStyle = "/js/styles/components/HeroSection.ftl">
<#assign searchBarStyle = "/js/styles/components/SearchBar.ftl">
<#assign containerStyle = "/js/styles/components/Container.ftl">
<#assign inputFieldStyle = "/js/styles/components/InputField.ftl">
<#assign keyValuePairStyle = "/js/styles/components/KeyValuePair.ftl">
const styles = {
<#assign indentValue = 1>
<#list page.components as component>
    <#switch component.type>
        <#case "Button">
            <#include buttonStyle>
        <#break>
        <#case "CardSection">
            <#include cardSectionStyle>
        <#break>
        <#case "Form">
            <#include formStyle>
        <#break>
        <#case "HeroSection">
            <#include heroSectionStyle>
        <#break>
        <#case "SearchBar">
            <#include searchBarStyle>
        <#break>
        <#case "Container">
            <#include containerStyle>
        <#break>
    </#switch>
</#list>
};

export default styles;