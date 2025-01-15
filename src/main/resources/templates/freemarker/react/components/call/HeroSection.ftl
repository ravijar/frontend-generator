<#assign indent = ""?left_pad(indentValue * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <HeroSection
${indent}       backgroundImage="${component.image}"
${indent}       textContent="${component.text}"
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>

