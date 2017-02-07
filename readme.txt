本程序说明
1.所有的测试用数据全都存放在\src\test\resources文件夹下，具体与testng.xml中class类一一对应。
其中testng.xml中的test name为excel表格的名字，class name为sheet的名字。以后要测试哪个接口请直接修改相对应的excel表格
2.excel表格只读取除第一行（用来写表头）和最后一列（用来填写备注）外的所有数据。假设表格有n*m行数据，请填满这范围内所有单元格，不要留空，留空会造成程序异常
如果需要传的数据为空，请在excel相应位置写一个空字，本人对所有从excel中读取到的空字，在程序中都会转换成字符串“”。
3.unit文件夹下的类说明
getMysql：主要是获取数据库中的shopid，employeeid等相关信息，尤其是token这个每次登录都会变化的参数。把他读取出来。
setMysql：把读出来的参数写入到根目录下的config.properties配置文件下供其他类方法调用（如果要换成别人的手机号码请修改这两个函数中的相关数据）
httpget，httpput和httppost，三种发送请求的方式
jsonbuilt 当请求方式为post的时候，每个post接口都必须发送的参数都会预先构造在这个类中，其中json_built添加的是所有baserequest。
url_to_start 当请求方式为get时，每个网址最后需要拼接的公有参数都会在这里出现，可以理解为json_built的get方法
readexcel 读取excel中的每个单元格的数据，按行读取。注意事项在第二点中明确。
PropertiesHandle  读取config.properties中的配置信息
jsonutil中的函数是用来操作读取返回的结果（json格式）
以下的接口都是操作数据库的
undoModify是执行"UPDATE employees SET UserName='15658890633' WHERE Id='b43c796e-1b43-45e8-a2bb-d84dd01ad034';"这条数据库指令，免得其他接口把username修改了。
del_xiaotangge 主要是删除添加店员中添加的小汤哥系列店员。"DELETE FROM employeeshoprelations where Remark LIKE '%小汤哥%';"
del_shop 与de37to39 也是类似的效果，为了删除增加的店铺店员等。
default_pw 把密码回复成默认，避免修改密码和重置密码后把更换密码导致二次测试错误
balance_money 设置账户余额为100，避免其他接口或者提现接口的使用导致账户余额接口错误。
4.tws接口状况.xlsx为我以前记录接口完成情况的表格，只是给我自己看的，对他人没有什么重要影响。
5.testng.xml是一次性跑所有接口测试的配置文件
6.pom.xml是maven的配置文件，配置所需要的jar包。
7.config.properties包含一些必要的基本信息。主要是baserequest类
除此之外还有连接数据库的地址，账户，密码。分别是sql_add=jdbc\:mysql\://192.168.1.17\:3306/uxscan   sql_user=root     sql_pwd=123456
如果需要修改数据库的信息请在此处相应的地方修改
baseUrl=http\://192.168.1.16\:80 是测试的ip地址，如有需要请在这里修改
