<#-- @ftlvariable name="userArticles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide</th>
            </tr>
            </thead>
            <tbody>
            <#list userArticles as userArticle>
                <tr>
                    <td class="article_id">${userArticle.id}</td>
                    <td class="article_title">${userArticle.title}</td>
                    <td class="article_hidden">
                        <label>
                            <input type="checkbox" id="${userArticle.id}" <#if userArticle.isHidden()>checked</#if>>
                        </label>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $('input[type="checkbox"]').on('click', function() {
            var id = $(this).attr('id');
            var value = $(this).is(':checked') ? 1 : 0;

            $.ajax({
                dataType: "json",
                data: {
                    action: "setHidden",
                    id: id,
                    value: value
                },

                success: function (response) {
                    if(response["error"]) {
                        notify(response["error"]);
                    } else {
                        if (value === 0) {
                            notify("Post #" + id + " is now public");
                        } else {
                            notify("Post #" + id + " is now hidden");
                        }
                    }
                },
                type: "POST"
            })
        })
    </script>
</@c.page>