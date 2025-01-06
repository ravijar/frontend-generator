<#assign heroSectionTemplatePath = "/components/call/HeroSection.ftl">
<#assign searchBarTemplatePath = "/components/call/SearchBar.ftl">
<#assign buttonTemplatePath = "/components/call/Button.ftl">
<#assign formTemplatePath = "/components/call/Form.ftl">
<#assign cardSectionTemplatePath = "/components/call/CardSection.ftl">

<#assign fetchTemplatePath = "/logic/Fetch.ftl">
<#assign navigateTemplatePath = "/logic/Navigate.ftl">
<#assign handleChangeTemplatePath = "/logic/HandleChange.ftl">
<#assign handleSubmitTemplatePath = "/logic/HandleSubmit.ftl">

<#assign useStateTemplatePath = "/hooks/UseState.ftl">
<#assign useEffectTemplatePath = "/hooks/UseEffect.ftl">

<#assign responsesTemplatePath = "/constants/Responses.ftl">
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createConfiguration, DefaultApi } from "../client_api";
import HeroSection from "../components/HeroSection";
import SearchBar from "../components/SearchBar";
import Button from "../components/Button";
import CardSection from "../components/CardSection";
<#list data.components as component>
    <#switch component.body.type>
        <#case "Form">
import ${component.id?cap_first} from "../components/${component.id?cap_first}";
        <#break>
    </#switch>
</#list>
import "./Page.css";

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
                <#assign state = "${component.id}${resource.urlParameters[0]?cap_first}">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}${resource.urlParameters[0]?cap_first}Error">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}FetchResponseSchema">
                <#assign indent = 1>
                <#include useStateTemplatePath>
            <#break>
            <#case "Form">
                <#list resource.urlParameters as parameter>
                    <#assign state = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                    <#assign state = "${component.id}${parameter?cap_first}Error">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#list>
                <#list resource.requestParameters as parameter>
                    <#assign state = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                    <#assign state = "${component.id}${parameter?cap_first}Error">
                    <#assign indent = 1>
                    <#include useStateTemplatePath>
                </#list>
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}FetchResponseSchema">
                <#assign indent = 1>
                <#include useStateTemplatePath>
            <#break>
            <#case "Container">
                <#assign state = "${component.id}FetchResponse">
                <#assign indent = 1>
                <#include useStateTemplatePath>
                <#assign state = "${component.id}FetchResponseSchema">
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
                <#assign value = "${component.id}${resource.urlParameters[0]?cap_first}">
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
                    <#assign value = "${component.id}${parameter?cap_first}">
                    <#assign indent = 1>
                    <#include handleChangeTemplatePath>
                </#list>
                <#list resource.requestParameters as parameter>
                    <#assign value = "${component.id}${parameter?cap_first}">
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