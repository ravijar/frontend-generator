<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}   <Button
${indent}       text="${component.text}"
${indent}       onClick={() => navigate("${component.route}")}
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>
        <#break>
</#switch>
