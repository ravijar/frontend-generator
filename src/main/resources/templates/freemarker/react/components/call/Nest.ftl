<#switch component.role>
    <#case "result">
        <#if component.subComponents??>
            <#assign indentValue = indentValue + 3>
            <#list component.subComponents as subComponent>
                <#assign component = subComponent>
                <#switch subComponent.type>
                    <#case "Button">
                        <#include buttonCall>
                        <#break>
                </#switch>
            </#list>
            <#assign indentValue = indentValue - 3>
            <#assign indent = ""?left_pad(indentValue * 4)>
        </#if>
        <#break>
</#switch>
