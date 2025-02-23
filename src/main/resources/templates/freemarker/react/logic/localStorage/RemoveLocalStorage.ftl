<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const removeFrom${component.localStorageKey?cap_first} = (itemKey) => {
${indent}   let ${component.localStorageKey} = JSON.parse(localStorage.getItem("${component.localStorageKey}")) || [];
${indent}   const itemIndex = ${component.localStorageKey}.findIndex(cartItem => cartItem.key === itemKey);

${indent}   if (itemIndex !== -1) {
${indent}       if (${component.localStorageKey}[itemIndex].count > 1) {
${indent}           ${component.localStorageKey}[itemIndex].count -= 1;
${indent}       } else {
${indent}           ${component.localStorageKey}.splice(itemIndex, 1);
${indent}       }
${indent}       try {
${indent}           localStorage.setItem("${component.localStorageKey}", JSON.stringify(${component.localStorageKey}));
${indent}           set${component.id?cap_first}RemoveResponse((prevResponse) => ({
${indent}               httpStatusCode: "200",
${indent}               description: "Removed from ${component.localStorageKey?cap_first}!",
${indent}               itemIndex: itemIndex,
${indent}               count: prevResponse?.count !== undefined ? prevResponse.count + 1 : 0
${indent}           }));
${indent}       } catch (error) {
${indent}           set${component.id?cap_first}RemoveResponse((prevResponse) => ({
${indent}               httpStatusCode: "500",
${indent}               description: "Failed to remove from ${component.localStorageKey?cap_first}!",
${indent}               itemIndex: itemIndex,
${indent}               count: prevResponse?.count !== undefined ? prevResponse.count + 1 : 0
${indent}           }));
${indent}       }
${indent}   }
${indent}};

