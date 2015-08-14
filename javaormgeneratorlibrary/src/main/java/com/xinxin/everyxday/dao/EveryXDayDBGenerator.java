package com.xinxin.everyxday.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class EveryXDayDBGenerator {

    public static void main(String[] args) throws Exception {
        // first parameter for version, <span></span> second for default generate package
        Schema schema = new Schema(1, "com.xinxin.everyxday.dao.model");

        addLike(schema);

        // set dao class generate package
        schema.setDefaultJavaPackageDao("com.xinxin.everyxday.dao.newdao");
        // keep custom code block
        schema.enableKeepSectionsByDefault();
        new DaoGenerator().generateAll(schema, "/Users/mengxiao/Documents/workspace/LDrawer/EveryXDay/EveryXDay/src/main/java");
    }

    private static void addLike(Schema schema) {
        Entity like = schema.addEntity("Like");
        like.addIdProperty();
        like.addStringProperty("newid").unique();
        like.addStringProperty("avatar");
        like.addStringProperty("title");
        like.addStringProperty("cover");
        like.addStringProperty("detailNew");
        like.addDateProperty("createTime");
    }

//    private static void addCustomerOrder(Schema schema) {
//        Entity customer = schema.addEntity("Customer");
//        customer.addIdProperty();
//        customer.addStringProperty("name").notNull();
//
//        Entity order = schema.addEntity("Order");
//        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
//        order.addIdProperty();
//        Property orderDate = order.addDateProperty("date").getProperty();
//        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
//        order.addToOne(customer, customerId);
//
//        ToMany customerToOrders = customer.addToMany(order, customerId);
//        customerToOrders.setName("orders");
//        customerToOrders.orderAsc(orderDate);
//    }
}
