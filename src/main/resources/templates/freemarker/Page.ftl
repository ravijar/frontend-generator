import { useState } from "react";
import axios from "axios";
import InputField from "../components/InputField";
import KeyValuePair from "../components/KeyValuePair";
import "./Page.css";

export default function ${pageName?cap_first}() {
<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

<#list data as datum>
    const [${datum.property}Output, set${datum.property?cap_first}Output] = useState("");
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
        <#list data as datum>
            set${datum.property?cap_first}Output(response.data.${datum.property});
        </#list>
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div className="page-container">
            <form onSubmit={handleSubmit} className="form-container">
                <div className="form-inputs">
                <#list fields as field>
                    <InputField
                        label={"${field.name?cap_first}"}
                        value={${field.name}}
                        onChange={handle${field.name?cap_first}Change}
                        placeholder={"Enter ${field.name?cap_first}"}
                        error={${field.name}Error}
                    />
                </#list>
                </div>
                <button type="submit" className="form-submit">Submit</button>
            </form>

            <div className="key-value-pairs-container">
            <#list data as datum>
                <KeyValuePair
                    keyName="${datum.property?cap_first}"
                    value={${datum.property}Output}
                />
            </#list>
            </div>
        </div>
    );
}
