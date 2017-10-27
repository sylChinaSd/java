package com.syl.msp.utils.hibernate;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

public class MspMysqlDialect extends MySQLDialect {
	public MspMysqlDialect() {
		super();
		 //函数名必须是小写，试验大写出错  
        //SQLFunctionTemplate函数第一个参数是函数的输出类型，varchar2对应new StringType()    number对应new IntegerType()  
        //?1代表第一个参数,?2代表第二个参数     这是数据库wx_f_get_partystr函数只需要一个参数，所以写成wx_f_get_partystr(?1)  
        //this.registerFunction("wx_f_get_partystr", new SQLFunctionTemplate(new StringType(), "wx_f_get_partystr(?1)"));  
		this.registerFunction("nextval", new SQLFunctionTemplate(new StringType(), "nextval(?1)"));
		this.registerFunction("currval", new SQLFunctionTemplate(new StringType(), "currval()"));
	}
}
