<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8" />
        <title><#if add>Create a Note<#else>Edit a Note</#if></title>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    </head>
    <body>
        <h1><#if add>Create a Note:<#else>Edit a Note:</#if></h1>
        <a href="/notes">Back to Note List</a>
        <br/><br/>
        <#if add>
            <#assign urlAction>/notes/add</#assign>
            <#assign submitTitle>Create</#assign>
        <#else>
            <#assign urlAction>${'/notes/' + note.id + '/edit'}</#assign>
            <#assign submitTitle>Update</#assign>
        </#if>
        <form action="${urlAction}" name="note" method="POST">
            <table border="0">
                <#if note.id??>
                <tr>
                    <td>ID</td>
                    <td>:</td>
                    <td>${note.id}</td>             
                </tr>
                </#if>
                <tr>
                    <td>Title</td>
                    <td>:</td>
                    <td><input type="text" name="title" value="${(note.title)!''}" /></td>              
                </tr>
                <tr>
                    <td>Active</td>
                    <td>:</td>
                    <td>
                    <#if (note.active)?string('true', 'false') == 'false'>
                    	<input type="checkbox" name="active"/>
                    <#else>
                    	<input type="checkbox" name="active" checked=""/>
                    </#if>
                    <input id='active' type='hidden' value='false' name='active'>
                    </td>              
                </tr>
                <tr>
                    <td>Content</td>
                    <td>:</td>
                    <td><textarea name="content" rows="4" cols="50">${(note.content)!""}</textarea></td>
                </tr>
                <tr>
                    <td>Created On</td>
                    <td>:</td>
                    <td>
                    	<input type="datetime-local"  name="createdOn" value="${(note.createdOn)!''}"/>
                    </td>         
                </tr>
                <tr>
                    <td>Updated On</td>
                    <td>:</td>
                    <td>
                    	<input type="datetime-local" name="updatedOn" value="${(note.updatedOn)!''}"/>
                    	
                    </td>          
                </tr>
                
            </table>
            <input type="submit" value="${submitTitle}" />
        </form>
 
        <br/>
        <!-- Check if errorMessage is not null and not empty -->       
        <#if errorMessage?has_content>
            <div class="error">${errorMessage}</div>
        </#if>       
    </body>
</html>