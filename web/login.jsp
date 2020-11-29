<h1 style="margin: 150px 0px 50px 0;">Login</h1>
<form method="post" action="main">
    <input type="hidden" name="action" value="authenticate"/>
    <fieldset>
        <legend>User Login</legend>
        <label for="id">ID:</label>
        <input type="text" name="id" id="id"/>
        <br />
        <br />
        <label for="pw">Password:</label>
        <input type="password" name="pw" id="pw"/>
        <br />
        <br />
        <input type="submit" value="Login"/>
    </fieldset>
</form>
