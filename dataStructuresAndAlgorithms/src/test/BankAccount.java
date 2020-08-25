package test;

import java.util.Date;

public class BankAccount {
    /**
     * 4. 设计一个BankAccount类，实现银行某账号的资金往来账目管理，包括建账号、存入、取出等。
     * BankAccount类包括，账号（BankAccountId）、开户日期Date(日期)，Money(金额)，Rest(余额)。
     * 另有一个构造函数和三个成员函数Bankin()(处理存入账)，
     * Bankout()处理取出账)和和一个负责生成账号的自动增长的函数。
     */

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.bankin(10000);
        System.out.println(bankAccount.toString());
    }

    long accountId;
    Date createDate;
    long money;
    long rest;

    public BankAccount() {
        this.accountId = accountGenerate();
        this.createDate = new Date(System.currentTimeMillis());
        this.money = 0;
        this.rest = 0;
    }

    synchronized void bankin(long money) {
        this.rest += money;
    }

    synchronized void bankout(long money) {
        this.rest -= money;
    }

    public long accountGenerate() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", createDate=" + createDate +
                ", money=" + money +
                ", rest=" + rest +
                '}';
    }
}
