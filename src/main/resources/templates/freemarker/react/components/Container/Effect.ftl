<#switch component.role>
    <#case "root">
    <#case "child">
        <#switch component.action>
            <#case "resource">
                <#include fetchOnInitEffect>
                <#break>
            <#case "load">
                <#include loadOnInitEffect>
                <#break>
        </#switch>
        <#break>
</#switch>
