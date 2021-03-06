/*
 * This file is generated by jOOQ.
 */
package com.ra.course.stackoverflow.entity.jooq.tables.records;


import com.ra.course.stackoverflow.entity.jooq.tables.AccountTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountRecord extends UpdatableRecordImpl<AccountRecord> implements Record7<Integer, String, String, String, String, String, Integer> {

    private static final long serialVersionUID = 918624841;

    /**
     * Setter for <code>stackoverflow.account.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stackoverflow.account.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stackoverflow.account.password</code>.
     */
    public void setPassword(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>stackoverflow.account.password</code>.
     */
    public String getPassword() {
        return (String) get(1);
    }

    /**
     * Setter for <code>stackoverflow.account.account_status</code>.
     */
    public void setAccountStatus(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>stackoverflow.account.account_status</code>.
     */
    public String getAccountStatus() {
        return (String) get(2);
    }

    /**
     * Setter for <code>stackoverflow.account.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>stackoverflow.account.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stackoverflow.account.email</code>.
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>stackoverflow.account.email</code>.
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>stackoverflow.account.phone</code>.
     */
    public void setPhone(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>stackoverflow.account.phone</code>.
     */
    public String getPhone() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stackoverflow.account.reputation</code>.
     */
    public void setReputation(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>stackoverflow.account.reputation</code>.
     */
    public Integer getReputation() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, String, String, String, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Integer, String, String, String, String, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AccountTable.ACCOUNT_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return AccountTable.ACCOUNT_TABLE.PASSWORD;
    }

    @Override
    public Field<String> field3() {
        return AccountTable.ACCOUNT_TABLE.ACCOUNT_STATUS;
    }

    @Override
    public Field<String> field4() {
        return AccountTable.ACCOUNT_TABLE.NAME;
    }

    @Override
    public Field<String> field5() {
        return AccountTable.ACCOUNT_TABLE.EMAIL;
    }

    @Override
    public Field<String> field6() {
        return AccountTable.ACCOUNT_TABLE.PHONE;
    }

    @Override
    public Field<Integer> field7() {
        return AccountTable.ACCOUNT_TABLE.REPUTATION;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getPassword();
    }

    @Override
    public String component3() {
        return getAccountStatus();
    }

    @Override
    public String component4() {
        return getName();
    }

    @Override
    public String component5() {
        return getEmail();
    }

    @Override
    public String component6() {
        return getPhone();
    }

    @Override
    public Integer component7() {
        return getReputation();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPassword();
    }

    @Override
    public String value3() {
        return getAccountStatus();
    }

    @Override
    public String value4() {
        return getName();
    }

    @Override
    public String value5() {
        return getEmail();
    }

    @Override
    public String value6() {
        return getPhone();
    }

    @Override
    public Integer value7() {
        return getReputation();
    }

    @Override
    public AccountRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public AccountRecord value2(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public AccountRecord value3(String value) {
        setAccountStatus(value);
        return this;
    }

    @Override
    public AccountRecord value4(String value) {
        setName(value);
        return this;
    }

    @Override
    public AccountRecord value5(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public AccountRecord value6(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public AccountRecord value7(Integer value) {
        setReputation(value);
        return this;
    }

    @Override
    public AccountRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, Integer value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountRecord
     */
    public AccountRecord() {
        super(AccountTable.ACCOUNT_TABLE);
    }

    /**
     * Create a detached, initialised AccountRecord
     */
    public AccountRecord(Integer id, String password, String accountStatus, String name, String email, String phone, Integer reputation) {
        super(AccountTable.ACCOUNT_TABLE);

        set(0, id);
        set(1, password);
        set(2, accountStatus);
        set(3, name);
        set(4, email);
        set(5, phone);
        set(6, reputation);
    }
}
