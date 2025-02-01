<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
    <#case "child">
${indent}<div className="${component.resultComponent.styleId}-container">
${indent}    {${component.id}Fetched && (
${indent}       <CardSection
${indent}           styles={styles.${component.resultComponent.id}}
${indent}           items={${component.resultComponent.id}Filter()}
${indent}           onClick={(${component.resultComponent.urlParameter}) => navigate(`${component.resultComponent.templateLiteralRoute}`)}
${indent}       >
${indent}       </CardSection>
${indent}    )}
${indent}</div>
        <#break>
</#switch>
