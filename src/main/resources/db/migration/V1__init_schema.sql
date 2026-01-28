CREATE TABLE Accounts (
    Account_ID INT AUTO_INCREMENT PRIMARY KEY,
    Document_Number VARCHAR(45)
);

CREATE TABLE OperationsTypes (
    OperationType_ID INT AUTO_INCREMENT PRIMARY KEY,
    Description VARCHAR(100)
);

CREATE TABLE Transactions (
    Transaction_ID INT AUTO_INCREMENT PRIMARY KEY,
    Account_ID INT,
    OperationType_ID INT,
    Amount DECIMAL(19, 4) NOT NULL,
    EventDate DATETIME(6) NOT NULL,
    FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID),
    FOREIGN KEY (OperationType_ID) REFERENCES OperationsTypes(OperationType_ID)
);
