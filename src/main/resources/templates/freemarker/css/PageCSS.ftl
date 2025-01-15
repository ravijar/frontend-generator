.page-container {

}

<#list page.components as component>
.${component.styleId}-container {
    <#switch component.type>
        <#case "SearchBar">
    z-index: 1;
    position: absolute;
    top: 60px;
    right: 30px;
        <#break>
        <#case "Button">
    z-index: 1;
    position: absolute;
    bottom: 50px;
    left: 45%;
        <#break>
        <#case "Form">
    z-index: 1;
    position: absolute;
    top: 60px;
    left:35%
        <#break>
    </#switch>
}
<#switch component.type>
    <#case "SearchBar">
.${component.resultComponent.styleId}-container {

}
    <#break>
    <#case "Container">
.${component.resultComponent.styleId}-container {

}
    <#break>
    <#case "Form">
.${component.resultComponent.styleId}-container {

}
    <#break>
</#switch>
</#list>

