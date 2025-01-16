<#assign nestStyle = "/css/Nest.ftl">
.page-container {

}

<#list page.components as component>
    <#include nestStyle>
</#list>