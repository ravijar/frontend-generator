<#assign alertStyle = "/js/styles/components/Alert.ftl">
<#assign buttonStyle = "/js/styles/components/Button.ftl">
<#assign cardStyle = "/js/styles/components/Card.ftl">
<#assign cardSectionStyle = "/js/styles/components/CardSection.ftl">
<#assign containerStyle = "/js/styles/components/Container.ftl">
<#assign formStyle = "/js/styles/components/Form.ftl">
<#assign heroSectionStyle = "/js/styles/components/HeroSection.ftl">
<#assign searchBarStyle = "/js/styles/components/SearchBar.ftl">
<#assign tableStyle = "/js/styles/components/Table.ftl">
<#assign inputFieldStyle = "/js/styles/components/InputField.ftl">
<#assign keyValuePairStyle = "/js/styles/components/KeyValuePair.ftl">
<#assign nestStyle = "/js/styles/components/Nest.ftl">
<#assign resultStyle = "/js/styles/components/Result.ftl">
const styles = {
<#assign indentValue = 1>
<#list page.components as parentComponent>
    <#assign component = parentComponent>
    <#switch parentComponent.type>
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