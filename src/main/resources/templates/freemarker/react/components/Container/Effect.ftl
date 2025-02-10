<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.action>
            <#case "resource">
                <#include fetchOnInitEffect>
                <#break>
        </#switch>
        <#break>
</#switch>
