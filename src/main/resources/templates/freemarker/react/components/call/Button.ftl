<#assign indent = ""?left_pad(indentValue * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <Button
${indent}       text="${component.text}"
${indent}       onClick={() => navigate("${component.route}")}
<#switch component.role>
    <#case "parent">
${indent}       styles={styles.${component.id}}
        <#break>
    <#case "child">
${indent}       styles={styles.${parentComponent.id}.${component.id}}
        <#break>
</#switch>
${indent}   />
${indent}</div>

