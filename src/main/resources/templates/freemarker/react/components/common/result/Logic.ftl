<#switch component.role>
    <#case "parent">
    <#case "child">
        <#switch component.resultComponent.type>
            <#case "CardSection">
                <#include cardSectionLogic>
                <#break>
        </#switch>
        <#break>
</#switch>
