package javaOne.javaConcurrency.task3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alexi on 09.01.2026
 */

class ConcurrentBank {
    private final Map<String, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount createAccount(double initialBalance) {
        BankAccount account = new BankAccount(initialBalance);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public BankAccount createAccount() {
        return createAccount(0.0);
    }

    public boolean transfer(BankAccount from, BankAccount to, double amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Accounts cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (from == to) {
            return false;
        }

        BankAccount firstLock, secondLock;

        int compareResult = from.getAccountNumber().compareTo(to.getAccountNumber());
        if (compareResult < 0) {
            firstLock = from;
            secondLock = to;
        } else if (compareResult > 0) {
            firstLock = to;
            secondLock = from;
        } else {
            return false;
        }

        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    return true;
                }
                return false;
            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        BankAccount from = accounts.get(fromAccountNumber);
        BankAccount to = accounts.get(toAccountNumber);

        if (from == null || to == null) {
            return false;
        }

        return transfer(from, to, amount);
    }

    public double getTotalBalance() {
        double total = 0.0;
        for (BankAccount account : accounts.values()) {
            account.getLock().lock();
        }

        try {
            for (BankAccount account : accounts.values()) {
                total += account.getBalance();
            }
        } finally {
            for (BankAccount account : accounts.values()) {
                account.getLock().unlock();
            }
        }
        return total;
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public int getAccountsCount() {
        return accounts.size();
    }
}