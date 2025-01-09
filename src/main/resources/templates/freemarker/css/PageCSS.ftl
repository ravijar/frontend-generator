.page-container {

}

<#list data.components as component>
.${component.styleId}-container {
    <#switch component.body.type>
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
<#switch component.body.type>
    <#case "SearchBar">
.${component.styleId}-result-container {

}
    <#break>
    <#case "Container">
.${component.styleId}-result-container {

}
    <#break>
    <#case "Form">
        .${component.styleId}-result-container {

        }
    <#break>
</#switch>
</#list>

