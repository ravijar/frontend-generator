<#assign heroSectionTemplate = "/react/components/call/HeroSection.ftl">
<#assign searchBarTemplate = "/react/components/call/SearchBar.ftl">
<#assign buttonTemplate = "/react/components/call/Button.ftl">
<#assign formTemplate = "/react/components/call/Form.ftl">
<#assign cardSectionTemplate = "/react/components/call/CardSection.ftl">
<#assign alertTemplate = "/react/components/call/Alert.ftl">

<#assign fetchTemplate = "/react/logic/Fetch.ftl">
<#assign navigateTemplate = "/react/logic/Navigate.ftl">
<#assign handleChangeTemplate = "/react/logic/HandleChange.ftl">
<#assign handleSubmitTemplate = "/react/logic/HandleSubmit.ftl">

<#assign useStateTemplate = "/react/hooks/UseState.ftl">
<#assign useEffectTemplate = "/react/hooks/UseEffect.ftl">

<#assign responsesTemplate = "/react/constants/Responses.ftl">
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
<#assign indentValue = 0>
<#list data.components as component>
    <#assign resource = (component.resource)!>
    <#include responsesTemplate>
</#list>

export default function ${data.name?cap_first}() {
    const navigate = useNavigate();

    const configuration = createConfiguration();
    const clientApi = new DefaultApi(configuration);

    <#-- Creating Component UseStates -->
    <#assign indentValue = 1>
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#switch body.type>
            <#case "SearchBar">
                <#assign state = "${component.id}${resource.urlParameters[0].name?cap_first}">
                <#include useStateTemplate>
                <#assign state = "${component.id}${resource.urlParameters[0].name?cap_first}Error">
                <#include useStateTemplate>
                <#assign state = "${component.id}FetchResponse">
                <#include useStateTemplate>
                <#assign state = "${component.id}Fetched">
                <#include useStateTemplate>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign state = "${component.id}${parameter.name?cap_first}">
                    <#include useStateTemplate>
                    <#assign state = "${component.id}${parameter.name?cap_first}Error">
                    <#include useStateTemplate>
                </#list>
                <#list resource.requestProperties as property>
                    <#assign state = "${component.id}${property.name?cap_first}">
                    <#include useStateTemplate>
                    <#assign state = "${component.id}${property.name?cap_first}Error">
                    <#include useStateTemplate>
                </#list>
                <#assign state = "${component.id}FetchResponse">
                <#include useStateTemplate>
                <#assign state = "${component.id}Fetched">
                <#include useStateTemplate>
                <#if body.result.component.type == "Alert">
                    <#assign state = "${component.id}ShowAlert">
                    <#include useStateTemplate>
                </#if>
            <#break>
            <#case "Container">
                <#assign state = "${component.id}FetchResponse">
                <#include useStateTemplate>
                <#assign state = "${component.id}Fetched">
                <#include useStateTemplate>
            <#break>
        </#switch>
    </#list>

    <#-- Creating Component UseEffects -->
    <#assign indentValue = 1>
    <#list data.components as component>
    <#assign body = component.body>
    <#switch body.type>
        <#case "Container">
            <#include useEffectTemplate>
        <#break>
    </#switch>
    </#list>

    <#-- Creating Component Logic -->
    <#assign indentValue = 1>
    <#list data.components as component>
        <#assign body = component.body>
        <#assign resource = (component.resource)!>
        <#switch body.type>
            <#case "SearchBar">
                <#assign value = "${component.id}${resource.urlParameters[0].name?cap_first}">
                <#include handleChangeTemplate>
                <#include fetchTemplate>
                <#include handleSubmitTemplate>
            <#break>
            <#case "Button">
                <#include navigateTemplate>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign value = "${component.id}${parameter.name?cap_first}">
                    <#include handleChangeTemplate>
                </#list>
                <#list resource.requestProperties as property>
                    <#assign value = "${component.id}${property.name?cap_first}">
                    <#include handleChangeTemplate>
                </#list>
                <#include fetchTemplate>
                <#include handleSubmitTemplate>
            <#break>
            <#case "Container">
                <#assign indent = 1>
                <#include fetchTemplate>
            <#break>
        </#switch>
    </#list>

    return (
        <div className = "page-container">
            <#-- Calling Components -->
            <#assign indentValue = 3>
            <#list data.components as component>
                <#assign body = component.body>
                <#assign resource = (component.resource)!>
                <#switch body.type>
                    <#case "HeroSection">
                        <#include heroSectionTemplate>
                    <#break>
                    <#case "SearchBar">
                        <#include searchBarTemplate>
                        <#if body.result.component.type == "CardSection">
                            <#include cardSectionTemplate>
                        </#if>
                    <#break>
                    <#case "Button">
                        <#include buttonTemplate>
                     <#break>
                    <#case "Form">
                        <#include formTemplate>
                        <#if body.result.component.type == "Alert">
                            <#include alertTemplate>
                        </#if>
                    <#break>
                    <#case "Container">
                        <#if body.result.component.type == "CardSection">
                            <#include cardSectionTemplate>
                        </#if>
                    <#break>
                </#switch>
            </#list>
        </div>
    )
};