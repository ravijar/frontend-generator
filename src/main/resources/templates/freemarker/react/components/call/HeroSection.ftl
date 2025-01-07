<#assign indent = ""?left_pad(indent * 4)>
${indent}<div className="${component.styleId}-container">
${indent}   <HeroSection
${indent}       backgroundImage="${body.image.url}"
${indent}       textContent="${body.text.body}"
${indent}       styles={styles.${component.id}}
${indent}   />
${indent}</div>

