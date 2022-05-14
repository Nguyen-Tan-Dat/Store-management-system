DROP DATABASE IF EXISTS Store_Management;
CREATE DATABASE Store_Management;
USE Store_Management;
CREATE TABLE Customers
(
    id           int           not null auto_increment,
    name         nvarchar(50)  NOT NULL,
    address      nvarchar(100) NOT NULL,
    birthday     date          NOT NULL,
    phone_number varchar(11)   NOT NULL,
    CONSTRAINT Customers_PK PRIMARY KEY (id)
);

CREATE TABLE Authorizations
(
    id     int          not null auto_increment,
    name   varchar(20)  NOT NULL UNIQUE,
    detail varchar(255) NOT NULL,
    CONSTRAINT Authorization_PK PRIMARY KEY (id)
);
CREATE TABLE Employees
(
    id               int           not null auto_increment,
    name             nvarchar(50)  NOT NULL,
    address          nvarchar(100) NOT NULL,
    birthday         date          NOT NULL,
    phone_number     varchar(11)   NOT NULL,
    account_password varchar(50)   null,
    authorization_id int           null,
    CONSTRAINT Employees_PK PRIMARY KEY (id),
    CONSTRAINT Employees_FK FOREIGN KEY (authorization_id) REFERENCES Authorizations (id) ON UPDATE CASCADE
);

CREATE TABLE Bills
(
    id          int  not null auto_increment,
    employee_id int  NOT NULL,
    customer_id int  NOT NULL,
    date        date NOT NULL,
    time        time NOT NULL,
    money_total int  NOT NULL,
    CONSTRAINT Bills_PK PRIMARY KEY (id),
    CONSTRAINT Bills_FK_1 FOREIGN KEY (customer_id) REFERENCES Customers (id) ON UPDATE CASCADE,
    CONSTRAINT Bills_FK_2 FOREIGN KEY (employee_id) REFERENCES Employees (id) ON UPDATE CASCADE
);

CREATE TABLE Products
(
    id    int          not null auto_increment,
    name  varchar(70)  NOT NULL,
    price int          NOT NULL,
    image varchar(200) NOT NULL,
    CONSTRAINT Products_PK PRIMARY KEY (id)
);

create table Individual_Products
(
    id           varchar(50) not null,
    product_id   int         not null,
    is_purchased bool        not null,
    constraint PK_Individual_Products primary key (id),
    constraint FK_Individual_Products foreign key (product_id) references Products (id) on UPDATE CASCADE
);

CREATE TABLE Bill_Details
(
    bill_id               int         NOT NULL,
    individual_product_id varchar(50) not null,
    price                 int         NOT NULL,
    CONSTRAINT Bill_Details_PK PRIMARY KEY (bill_id, individual_product_id),
    CONSTRAINT Bill_Details_FK_1 FOREIGN KEY (bill_id) REFERENCES Bills (id) ON UPDATE CASCADE,
    CONSTRAINT Bill_Details_FK_2 FOREIGN KEY (individual_product_id) REFERENCES Individual_Products (id) ON UPDATE CASCADE
);
CREATE TABLE Suppliers
(
    id           int          not null auto_increment,
    name         varchar(70)  NOT NULL,
    address      varchar(100) NOT NULL,
    phone_number varchar(15)  NOT NULL,
    CONSTRAINT Suppliers_PK PRIMARY KEY (id)
);
CREATE TABLE Imports
(
    id          int  not null auto_increment,
    supplier_id int  NOT NULL,
    employee_id int  NOT NULL,
    date        date NOT NULL,
    time        time NOT NULL,
    money_total int  NOT NULL,
    CONSTRAINT Imports_PK PRIMARY KEY (id),
    CONSTRAINT Imports_FK_1 FOREIGN KEY (supplier_id) REFERENCES Suppliers (id) ON UPDATE CASCADE,
    CONSTRAINT Imports_FK_2 FOREIGN KEY (employee_id) REFERENCES Employees (id) ON UPDATE CASCADE
);
CREATE TABLE Import_Details
(
    import_id             int         NOT NULL,
    individual_product_id varchar(50) not null,
    price                 int         NOT NULL,
    CONSTRAINT Bill_Details_PK PRIMARY KEY (import_id, individual_product_id),
    CONSTRAINT Bill_Details_FK_1 FOREIGN KEY (import_id) REFERENCES Imports (id) ON UPDATE CASCADE,
    CONSTRAINT Bill_Details_FK_2 FOREIGN KEY (individual_product_id) REFERENCES Individual_Products (id) ON UPDATE CASCADE
);
