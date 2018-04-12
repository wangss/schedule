
<script type="text/javascript">

    function gotoPage(pageNo) {
        document.roll_page.curr_page.value = pageNo;
        document.roll_page.action = '${url}';
        document.roll_page.submit();
    }

    function isInteger(v){
        if (v == null || v == "")
            return (false);
        else
        {
            var expr = new RegExp(/^[1-9][0-9]*$/);
            return (expr.test(v));
        }
    }

    function gotoInputPage(){

        pageSize = document.roll_page.pageSize.value;
        toPage = document.roll_page.toPage.value;
        totalPage = '${queryResult.properties['total_page']}';

        if (!isInteger(pageSize))
        {
            alert('页数大小只能为0到50的整数！');

            return false;
        }
        if (!isInteger(toPage))
        {
            alert('页数必须是大于0的整数');

            return false;
        }
        if (parseInt(pageSize) > 50)
        {
            alert('每页最多显示50条');

            return false;
        }
        if (parseInt(toPage) > parseInt(totalPage))
        {
            alert('输入值超过了总页数');

            return false;
        }

        document.roll_page.page_size.value = pageSize;

        gotoPage(toPage);
    }


</script>

<form name="roll_page" method="POST"  class="form-inline">
    <!-- query param -->
    <#if queryResult.param??>
        <#list queryResult.param?keys as key>
            <input type="hidden" name="${key!''}" value="${queryResult.param["${key}"]!''}"/>
        </#list>
    </#if>

    <!-- end -->

    <nav>
        <ul class="pagination" style="margin: 0px;">

            <#if queryResult.properties??>
                <#if queryResult.properties['exist_prev'] == "true">
                     <li>
                         <a href="#" aria-label="Previous" onclick="gotoPage(1);">
                             <span aria-hidden="true">首页</span>
                         </a>
                     </li>
                    <li>
                        <a href="#" onclick='gotoPage(${queryResult.properties['prev_page']});'/>
                        前一页
                        </a>
                    </li>

                <#else>
                    <li class="disabled">
                        <a href="#" aria-label="Previous">
                            <span aria-hidden="true" >首页</span>
                        </a>
                    </li>
                    <li class="disabled"><a href="#">前一页</a></li>
                </#if>

            <#else>
                <li class="disabled">
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true" >首页</span>
                    </a>
                </li>
                <li class="disabled"><a href="#">前一页</a></li>
            </#if>

            <#if queryResult.properties??>

                <#if queryResult.properties['exist_next'] == "true">
                <li><a href="#" onclick='gotoPage(${queryResult.properties['next_page']}); return false;'>下一页</a></li>

                <li>
                    <a href="#" aria-label="Next" onclick='gotoPage(${queryResult.properties['total_page']}); return false;'>
                        <span aria-hidden="true">尾页</span>
                    </a>
                </li>
                <#else>
                <li class="disabled"><a>下一页</a></li>
                <li class="disabled">
                    <a aria-label="Next">
                        <span aria-hidden="true">尾页</span>
                    </a>
                </li>
                </#if>
            <#else>
                <li class="disabled"><a>下一页</a></li>
                <li class="disabled">
                    <a aria-label="Next">
                        <span aria-hidden="true">尾页</span>
                    </a>
                </li>
            </#if>



            <div class="form-group">
                每页显示:

                <input type="text" class="form-control" name="pageSize"  maxlength="2"  style="width: 50px;" value='${queryResult.param['page_size']}'/> 条
            </div>
            <div class="form-group">
                , 当前页:
                <input type="text" class="form-control" name="toPage" maxlength="2" style="width: 50px;" value='${queryResult.param['curr_page']}'/> of ${queryResult.properties['total_page']}

            </div>

            <button  onclick='gotoInputPage();' class="btn btn-default">跳转</button>

            显示记录：
            ${queryResult.properties['start_row']} - ${queryResult.properties['end_row']}&nbsp;of&nbsp;${queryResult.properties['total_row']}

        </ul>
    </nav>



</form>