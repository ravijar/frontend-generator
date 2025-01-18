<#assign nestStyle = "/css/Nest.ftl">
.page-container {

}

<#list page.components as parentComponent>
    <#assign component = parentComponent>
    <#include nestStyle>
</#list>