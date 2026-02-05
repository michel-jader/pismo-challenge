CREATE TABLE Accounts (
    Account_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Document_Number VARCHAR(45)
);

CREATE TABLE OperationsTypes (
    OperationType_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Description VARCHAR(100),
    Direction ENUM('CREDIT', 'DEBIT') NOT NULL
);

CREATE TABLE Transactions (
    Transaction_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Account_ID BIGINT,
    OperationType_ID BIGINT,
    Amount DECIMAL(19, 4) NOT NULL,
    Balance DECIMAL(19, 4) NOT NULL,
    EventDate DATETIME(6) NOT NULL,
    FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID),
    FOREIGN KEY (OperationType_ID) REFERENCES OperationsTypes(OperationType_ID)
);
