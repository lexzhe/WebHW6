<#-- @ftlvariable name="" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <link rel="stylesheet" type="text/css" href="css/article.css">
    <form method="post" action="">
        <div class="middle">
            <main>
                <div class="article form-box">
                    <div class="header">Create Article</div>
                    <div class="body">
                        <input type="hidden" name="action" value="create"/>
                        <div class="field">
                            <div class="name">
                                <label for="title">Title</label>
                            </div>
                            <div class="value">
                                <input id="title" name="title" value="${title!}"/>
                            </div>
                        </div>
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text" value="${text!}"></textarea>
                            </div>
                        </div>
                        <#if error??>
                            <div class="error">
                                ${error!}
                            </div>
                        </#if>
                        <div class="button-field">
                            <input type="submit" value="Create">
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </form>
    <script>
        $(function () {
            $(".article form").submit(function () {
                var title = $(this).find("input[name='title']").val();
                var text = $(this).find("input[name='text']").val();
                var $error = $(this).find(".error");

                myAjax({
                    data: {
                        action: "create",
                        title: title,
                        text: text
                    },
                    success: function (response) {
                        if (response["error"]) {
                            $error.text(response["error"]);
                        } else {
                            location.href = response["redirect"];
                        }
                    }
                });

                // $.ajax({
                //     type: "POST",
                //     url: "",
                //     dataType: "json",
                //     data: {
                //         action: "register",
                //         login: login,
                //         password: password
                //     },
                //     success: function (response) {
                //         if (response["error"]) {
                //             $error.text(response["error"]);
                //         } else {
                //             location.href = response["redirect"];
                //         }
                //     }
                // });
                //
                // $.post("", {
                //     action: "register",
                //     login: login,
                //     password: password
                // }, function (response) {
                //     if (response["error"]) {
                //         $error.text(response["error"]);
                //     } else {
                //         location.href = response["redirect"];
                //     }
                // });

                return false;
            });
        })
    </script>
</@c.page>