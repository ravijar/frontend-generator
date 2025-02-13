<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.parent.role>
    <#case "root">
    <#case "child">
${indent}<div className="${component.styleId}-container">
${indent}    {${component.parent.id}Fetched && (
${indent}       <CardSection
${indent}           styles={styles.${component.id}}
${indent}           items={${component.id}Filter()}
${indent}           onClick={(${component.urlParameter}) => navigate(`${component.templateLiteralRoute}`)}
${indent}       >
${indent}       </CardSection>
${indent}    )}
${indent}</div>
        <#break>
</#switch>
