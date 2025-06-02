CREATE TABLE t_book (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    isbn VARCHAR(255),
    title VARCHAR(255),
    author VARCHAR(255),
    borrowed_by UUID,
    status VARCHAR(20)
);

CREATE TABLE t_borrower (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    email_address VARCHAR(255),
    status VARCHAR(20)
);