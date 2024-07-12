import { useState } from "react";
import axios from "axios";
import InputField from "../components/InputField";

export default function ${pageName?cap_first}() {
<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

<#list fields as field>
    const handle${field.name?cap_first}Change = (value) => {
        set${field.name?cap_first}(value);
        if (value.trim() === '') {
            set${field.name?cap_first}Error('This field is required');
        } else {
            set${field.name?cap_first}Error('');
        }
    };
</#list>

    const handleSubmit = async (event) => {
        event.preventDefault();
        const url = `${endpointUrl}<#list fields as field>${"${" + field.name + "}"}<#if field_has_next>/</#if></#list>`;

        try {
            const response = await axios.get(url);
            console.log('Response:', response.data);
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
        <#list fields as field>
            <InputField
                label={"${field.name?cap_first}"}
                value={${field.name}}
                onChange={handle${field.name?cap_first}Change}
                placeholder={"Enter ${field.name?cap_first}"}
                required={true}
                error={${field.name}Error}
            />
        </#list>
            <button type="submit">Submit</button>
        </form>
    );
}
