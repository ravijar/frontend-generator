<#switch component.role>
    <#case "root">
    <#case "child">
        <#switch component.action>
            <#case "resource">
                <#include fetchOnInitEffect>
                <#break>
        </#switch>
        <#break>
</#switch>
