<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "root">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}   <Button
${indent}       text="${component.text}"
        <#switch component.action>
            <#case "navigate">
${indent}       onClick={(<#if component.urlParameter??>${component.urlParameter}</#if>) => navigate(`<#if component.urlParameter??>${component.templateLiteralRoute}<#else>${component.route}</#if>`)}
                <#break>
            <#case "save">
${indent}       onClick={() => saveTo${component.localStorageKey?cap_first}(${resultComponent.id}Filter())}
                <#break>
            <#case "remove">
${indent}       onClick={(id) => removeFrom${component.localStorageKey?cap_first}(id)}
                <#break>
            <#case "resource">
${indent}       onClick={(id) => ${component.id}Fetch(id)}
                <#break>
        </#switch>
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>
<#include resultCall>
        <#break>
</#switch>
