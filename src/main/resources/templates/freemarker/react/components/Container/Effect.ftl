<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.fetch>
            <#case "resource">
                <#include fetchOnInitEffect>
                <#break>
        </#switch>
        <#break>
</#switch>
