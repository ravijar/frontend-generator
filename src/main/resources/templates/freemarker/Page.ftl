import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import InputField from "../components/InputField";
import RecursiveKeyValuePair from "../components/RecursiveKeyValuePair";
import ${responseSchema} from "../models/${responseSchema}";
import "./Page.css";

export default function ${pageName?cap_first}() {
    const navigate = useNavigate();

<#list fields as field>
    const [${field.name}, set${field.name?cap_first}] = useState("");
    const [${field.name}Error, set${field.name?cap_first}Error] = useState("");
</#list>

    const [responseData, setResponseData] = useState(new ${responseSchema}());

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
<#list nextPages as nextPage>
    const onClick${nextPage.name} = () => {
        navigate("/${nextPage.name?uncap_first}");
    }

</#list>
    const handleSubmit = async (event) => {
        event.preventDefault();
        const url = `${endpointUrl}<#list fields as field>${"${" + field.name + "}"}<#if field_has_next>/</#if></#list>`;

        try {
            const response = await axios.get(url);
            console.log('Response:', response.data);
            setResponseData(response.data);
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
                <RecursiveKeyValuePair data={responseData} />
            </div>

            <div className="navigation-buttons-container">
            <#list nextPages as nextPage>
                <button className="navigation-button" onClick={onClick${nextPage.name}}>${nextPage.name}</button>
            </#list>
            </div>
        </div>
    );
}
