<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}   <Button
${indent}       text="${component.text}"
${indent}       onClick={(<#if component.urlParameter??>${component.urlParameter}</#if>) => navigate(`<#if component.urlParameter??>${component.templateLiteralRoute}<#else>${component.route}</#if>`)}
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>
        <#break>
</#switch>
