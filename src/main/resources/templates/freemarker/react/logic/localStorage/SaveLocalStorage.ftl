<#assign indent = ""?left_pad(indentValue * 4)>
${indent}const saveTo${component.localStorageKey?cap_first} = (item) => {
${indent}   let ${component.localStorageKey} = JSON.parse(localStorage.getItem("${component.localStorageKey}")) || [];
${indent}   const existingItem = ${component.localStorageKey}.find(${component.localStorageKey}Item => ${component.localStorageKey}Item.key === item.key);

${indent}   if (existingItem) {
${indent}       existingItem.count += 1;
${indent}   } else {
${indent}       ${component.localStorageKey}.push({...item, count: 1});
${indent}   }

${indent}   localStorage.setItem("${component.localStorageKey}", JSON.stringify(${component.localStorageKey}));
${indent}};

