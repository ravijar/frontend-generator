<#assign heroSectionTemplatePath = "/react/components/call/HeroSection.ftl">
<#assign searchBarTemplatePath = "/react/components/call/SearchBar.ftl">
<#assign buttonTemplatePath = "/react/components/call/Button.ftl">
<#assign formTemplatePath = "/react/components/call/Form.ftl">
<#assign cardSectionTemplatePath = "/react/components/call/CardSection.ftl">
<#assign alertTemplatePath = "/react/components/call/Alert.ftl">

<#assign fetchTemplatePath = "/react/logic/Fetch.ftl">
<#assign navigateTemplatePath = "/react/logic/Navigate.ftl">
<#assign handleChangeTemplatePath = "/react/logic/HandleChange.ftl">
<#assign handleSubmitTemplatePath = "/react/logic/HandleSubmit.ftl">

<#assign useStateTemplatePath = "/react/hooks/UseState.ftl">
<#assign useEffectTemplatePath = "/react/hooks/UseEffect.ftl">

<#assign responsesTemplatePath = "/react/constants/Responses.ftl">
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import HeroSection from "../components/HeroSection";
import SearchBar from "../components/SearchBar";
import Button from "../components/Button";
import CardSection from "../components/CardSection";
import Alert from "../components/Alert";
<#list data.components as component>
    <#switch component.body.type>
        <#case "Form">
import ${component.id?cap_first} from "../components/${component.id?cap_first}";
        <#break>
    </#switch>
</#list>
import "./${data.name?cap_first}.css";
import styles from "../custom_styles/${data.name?cap_first}";

<#-- Creating Constants -->
<#list data.components as component>
    <#assign resource = (component.resource)!>
    <#assign indent = 0>
    <#include responsesTemplatePath>
</#list>

export default function ${data.name?cap_first}() {
    const navigate = useNavigate();

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    <#-- Creating Component UseStates -->
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#switch body.type>
            <#case "SearchBar">
                <#assign state = "${component.id}${resource.urlParameters[0].name?cap_first}">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}${resource.urlParameters[0].name?cap_first}Error">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}Fetched">
                <#assign indent = 1>
                <#include useStateTemplatePath>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign state = "${component.id}${parameter.name?cap_first}">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                    <#assign state = "${component.id}${parameter.name?cap_first}Error">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#list>
                <#list resource.requestProperties as property>
                    <#assign state = "${component.id}${property.name?cap_first}">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                    <#assign state = "${component.id}${property.name?cap_first}Error">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#list>
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}Fetched">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#if body.result.component.type == "Alert">
                    <#assign state = "${component.id}ShowAlert">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#if>
            <#break>
            <#case "Container">
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}Fetched">
                <#assign indent = 1>
                <#include useStateTemplatePath>
            <#break>
        </#switch>
    </#list>

    <#-- Creating Component UseEffects -->
    <#list data.components as component>
    <#assign body = component.body>
    <#switch body.type>
        <#case "Container">
            <#assign indent = 1>
            <#include useEffectTemplatePath>
        <#break>
    </#switch>
    </#list>

    <#-- Creating Component Logic -->
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#switch body.type>
            <#case "SearchBar">
                <#assign value = "${component.id}${resource.urlParameters[0].name?cap_first}">
                <#assign indent = 1>
                <#include handleChangeTemplatePath>
                <#assign indent = 1>
                <#include fetchTemplatePath>
                <#assign indent = 1>
                <#include handleSubmitTemplatePath>
            <#break>
            <#case "Button">
                <#assign indent = 1>
                <#include navigateTemplatePath>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign value = "${component.id}${parameter.name?cap_first}">
                    <#assign indent = 1>
                    <#include handleChangeTemplatePath>
                </#list>
                <#list resource.requestProperties as property>
                    <#assign value = "${component.id}${property.name?cap_first}">
                    <#assign indent = 1>
                    <#include handleChangeTemplatePath>
                </#list>
                <#assign indent = 1>
                <#include fetchTemplatePath>
                <#assign indent = 1>
                <#include handleSubmitTemplatePath>
            <#break>
            <#case "Container">
                <#assign indent = 1>
                <#include fetchTemplatePath>
            <#break>
        </#switch>
    </#list>

    return (
        <div className = "page-container">
            <#-- Calling Components -->
            <#list data.components as component>
                <#assign body = component.body>
                <#assign resource = (component.resource)!>
                <#switch body.type>
                    <#case "HeroSection">
                        <#assign indent = 3>
                        <#include heroSectionTemplatePath>
                    <#break>
                    <#case "SearchBar">
                        <#assign indent = 3>
                        <#include searchBarTemplatePath>
                        <#if body.result.component.type == "CardSection">
                            <#assign indent = 3>
                            <#include cardSectionTemplatePath>
                        </#if>
                    <#break>
                    <#case "Button">
                        <#assign indent = 3>
                        <#include buttonTemplatePath>
                     <#break>
                    <#case "Form">
                        <#assign indent = 3>
                        <#include formTemplatePath>
                        <#if body.result.component.type == "Alert">
                            <#assign indent = 3>
                            <#include alertTemplatePath>
                        </#if>
                    <#break>
                    <#case "Container">
                        <#if body.result.component.type == "CardSection">
                            <#assign indent = 3>
                            <#include cardSectionTemplatePath>
                        </#if>
                    <#break>
                </#switch>
            </#list>
        </div>
    )
};