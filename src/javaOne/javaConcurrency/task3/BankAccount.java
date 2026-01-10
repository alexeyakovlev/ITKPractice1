package javaOne.javaConcurrency.task3;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexi on 09.01.2026
 */

class BankAccount {
    private final String accountNumber;
    private double balance;
    private final Lock lock = new ReentrantLock();
    private static final AtomicLong accountCounter = new AtomicLong(1);

    public BankAccount(double initialBalance) {
        this.accountNumber = "ACC-" + accountCounter.getAndIncrement();
        this.balance = initialBalance;
    }

    public BankAccount() {
        this(0.0);
    }

    // Пополнение счета
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    // Снятие со счета
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Получение баланса
    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Lock getLock() {
        return lock;
    }
}