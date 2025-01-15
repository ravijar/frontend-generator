<#if component.resultComponent.subComponents??>
    <#assign indentValue = indentValue + 3>
    <#list component.resultComponent.subComponents as component>
        <#switch component.type>
            <#case "Button">
                <#include buttonCall>
                <#break>
        </#switch>
    </#list>
    <#assign indentValue = indentValue - 3>
    <#assign indent = ""?left_pad(indentValue * 4)>
</#if>