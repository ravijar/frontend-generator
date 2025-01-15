<#if component.resultComponent.subComponents??>
    <#assign indentValue = indentValue + 1>
    <#list component.resultComponent.subComponents as component>
        <#switch component.type>
            <#case "Button">
                <#include buttonStyle>
                <#break>
        </#switch>
    </#list>
    <#assign indentValue = indentValue - 1>
    <#assign indent = ""?left_pad(indentValue * 4)>
</#if>