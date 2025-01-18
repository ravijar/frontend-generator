<#assign indent = ""?left_pad(indentValue * 4)>
<#switch component.role>
    <#case "parent">
${indent}<div className="${component.styleId}-container">
${indent}   <HeroSection
${indent}       backgroundImage="${component.image}"
${indent}       textContent="${component.text}"
${indent}       styles={styles.${component.id}}
${indent}   >
            <#include nestCall>
${indent}   </HeroSection>
${indent}</div>
        <#break>
</#switch>
